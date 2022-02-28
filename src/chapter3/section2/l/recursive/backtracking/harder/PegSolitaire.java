package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/02/22.
 */
public class PegSolitaire {

    private static class Result {
        int minimumPegs;
        int minimumMoves;

        public Result(int minimumPegs, int minimumMoves) {
            this.minimumPegs = minimumPegs;
            this.minimumMoves = minimumMoves;
        }
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                FastReader.getLine();
            }

            int pegsCount = 0;
            char[][] board = new char[5][9];
            for (int row = 0; row < board.length; row++) {
                board[row] = FastReader.getLine().toCharArray();
                for (int column = 0; column < board[row].length; column++) {
                    if (board[row][column] == 'o') {
                        pegsCount++;
                    }
                }
            }
            Result currentResult = new Result(pegsCount, 0);
            Result bestResult = playPegSolitaire(board, neighborRows, neighborColumns, currentResult);
            outputWriter.printLine(String.format("%d %d", bestResult.minimumPegs, bestResult.minimumMoves));
        }
        outputWriter.flush();
    }

    private static Result playPegSolitaire(char[][] board, int[] neighborRows, int[] neighborColumns,
                                           Result currentResult) {
        Result bestResult = currentResult;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == 'o') {
                    Cell pegCell = new Cell(row, column);

                    for (int i = 0; i < neighborRows.length; i++) {
                        int neighborRow = row + neighborRows[i];
                        int neighborColumn = column + neighborColumns[i];
                        int neighbor2Row = neighborRow + neighborRows[i];
                        int neighbor2Column = neighborColumn + neighborColumns[i];

                        if (isValid(board, neighbor2Row, neighbor2Column)
                                && board[neighborRow][neighborColumn] == 'o'
                                && board[neighbor2Row][neighbor2Column] == '.') {
                            Cell destinationCell = new Cell(neighbor2Row, neighbor2Column);
                            Cell jumpedCell = new Cell(neighborRow, neighborColumn);

                            jumpPeg(board, pegCell, jumpedCell, destinationCell);
                            currentResult.minimumPegs--;
                            currentResult.minimumMoves++;
                            Result result = playPegSolitaire(board, neighborRows, neighborColumns, currentResult);
                            bestResult = getBestResult(bestResult, result);

                            currentResult.minimumPegs++;
                            currentResult.minimumMoves--;
                            rollbackPeg(board, pegCell, jumpedCell, destinationCell);
                        }
                    }
                }
            }
        }
        return bestResult;
    }

    private static boolean isValid(char[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
    }

    private static void jumpPeg(char[][] board, Cell pegCell, Cell jumpedCell, Cell destinationCell) {
        board[destinationCell.row][destinationCell.column] = 'o';
        board[jumpedCell.row][jumpedCell.column] = '.';
        board[pegCell.row][pegCell.column] = '.';
    }

    private static void rollbackPeg(char[][] board, Cell pegCell, Cell jumpedCell, Cell destinationCell) {
        board[destinationCell.row][destinationCell.column] = '.';
        board[jumpedCell.row][jumpedCell.column] = 'o';
        board[pegCell.row][pegCell.column] = 'o';
    }

    private static Result getBestResult(Result bestResult, Result candidateResult) {
        Result newBestResult;

        if (bestResult == null
                || bestResult.minimumPegs > candidateResult.minimumPegs
                || (bestResult.minimumPegs == candidateResult.minimumPegs
                    && bestResult.minimumMoves > candidateResult.minimumMoves)) {
            newBestResult = new Result(candidateResult.minimumPegs, candidateResult.minimumMoves);
        } else {
            newBestResult = new Result(bestResult.minimumPegs, bestResult.minimumMoves);
        }
        return newBestResult;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
