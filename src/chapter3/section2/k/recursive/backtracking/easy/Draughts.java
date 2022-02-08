package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/02/22.
 */
public class Draughts {

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

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            char[][] board = new char[10][10];
            for (int row = 0; row < board.length; row++) {
                String line = FastReader.getLine();
                board[row] = line.toCharArray();
            }
            int maximalCaptures = computeMaximalCaptures(board);
            outputWriter.printLine(maximalCaptures);
        }
        outputWriter.flush();
    }

    private static int computeMaximalCaptures(char[][] board) {
        int maximalCaptures = 0;
        int[] neighborRows = { -1, -1, 1, 1 };
        int[] neighborColumns = { -1, 1, -1, 1 };

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == 'W') {
                    Cell cell = new Cell(row, column);
                    int captures = computeMaximalCaptures(board, neighborRows, neighborColumns, cell, 0);
                    maximalCaptures = Math.max(maximalCaptures, captures);
                }
            }
        }
        return maximalCaptures;
    }

    private static int computeMaximalCaptures(char[][] board, int[] neighborRows, int[] neighborColumns,
                                              Cell currentCell, int currentCaptures) {
        int maximalCaptures = currentCaptures;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = currentCell.row + neighborRows[i];
            int neighborColumn = currentCell.column + neighborColumns[i];

            if (isValid(board, neighborRow, neighborColumn)) {
                int neighborRowLevel2 = neighborRow + neighborRows[i];
                int neighborColumnLevel2 = neighborColumn + neighborColumns[i];

                if (isValid(board, neighborRowLevel2, neighborColumnLevel2)
                        && board[neighborRow][neighborColumn] == 'B'
                        && board[neighborRowLevel2][neighborColumnLevel2] == '#') {
                    char[][] copyBoard = copyBoard(board);
                    copyBoard[currentCell.row][currentCell.column] = '#';
                    copyBoard[neighborRow][neighborColumn] = '#';

                    Cell nextCell = new Cell(neighborRowLevel2, neighborColumnLevel2);
                    int captures = computeMaximalCaptures(copyBoard, neighborRows, neighborColumns, nextCell,
                            currentCaptures + 1);
                    maximalCaptures = Math.max(maximalCaptures, captures);
                }
            }
        }
        return maximalCaptures;
    }

    private static char[][] copyBoard(char[][] board) {
        char[][] copyBoard = new char[board.length][board[0].length];

        for (int row = 0; row < copyBoard.length; row++) {
            System.arraycopy(board[row], 0, copyBoard[row], 0, board[row].length);
        }
        return copyBoard;
    }

    private static boolean isValid(char[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
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
