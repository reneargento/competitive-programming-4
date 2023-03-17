package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/03/23.
 */
public class TheSameGame {

    private static final char EMPTY = 'E';

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int games = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int game = 1; game <= games; game++) {
            char[][] board = new char[10][15];
            for (int row = 0; row < board.length; row++) {
                board[row] = FastReader.next().toCharArray();
            }

            if (game > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Game %d:", game));
            outputWriter.printLine();
            playGame(board, neighborRows, neighborColumns, outputWriter);
        }
        outputWriter.flush();
    }

    private static void playGame(char[][] board, int[] neighborRows, int[] neighborColumns, OutputWriter outputWriter) {
        boolean canMakeMove;
        int moveID = 1;
        int finalScore = 0;
        int ballsRemaining = board.length * board[0].length;

        do {
            canMakeMove = false;
            int[][] clusterIDs = new int[board.length][board[0].length];
            int highestClusterSize = 0;
            int highestClusterID = 0;
            int clusterID = 1;
            int selectedRow = 0;
            int selectedColumn = 0;

            for (int column = 0; column < board[0].length; column++) {
                for (int row = 0; row < board.length; row++) {
                    int reverseRow = board.length - 1 - row;

                    if (board[reverseRow][column] != EMPTY && clusterIDs[reverseRow][column] == 0) {
                        char color = board[reverseRow][column];
                        int clusterSize = computeClusterSize(board, clusterIDs, neighborRows, neighborColumns, clusterID,
                                color, reverseRow, column);
                        if (clusterSize > 1) {
                            if (clusterSize > highestClusterSize) {
                                highestClusterSize = clusterSize;
                                highestClusterID = clusterID;
                                selectedRow = row;
                                selectedColumn = column;
                            }
                            canMakeMove = true;
                        }
                        clusterID++;
                    }
                }
            }

            if (canMakeMove) {
                int points = (highestClusterSize - 2) * (highestClusterSize - 2);
                finalScore += points;
                outputWriter.printLine(String.format("Move %d at (%d,%d): removed %d balls of color %c, " +
                                "got %d points.", moveID, selectedRow + 1, selectedColumn + 1, highestClusterSize,
                        board[board.length - 1 - selectedRow][selectedColumn], points));
                removeBalls(board, clusterIDs, highestClusterID);
                ballsRemaining -= highestClusterSize;
            }
            moveID++;
        } while (canMakeMove);

        if (ballsRemaining == 0) {
            finalScore += 1000;
        }
        outputWriter.printLine(String.format("Final score: %d, with %d balls remaining.", finalScore,
                ballsRemaining));
    }

    private static int computeClusterSize(char[][] board, int[][] clusterIDs, int[] neighborRows, int[] neighborColumns,
                                          int clusterID, char color, int row, int column) {
        int clusterSize = 1;
        clusterIDs[row][column] = clusterID;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(board, neighborRow, neighborColumn)
                    && clusterIDs[neighborRow][neighborColumn] == 0
                    && board[neighborRow][neighborColumn] == color) {
                clusterSize += computeClusterSize(board, clusterIDs, neighborRows, neighborColumns, clusterID,
                        color, neighborRow, neighborColumn);
            }
        }
        return clusterSize;
    }

    private static void removeBalls(char[][] board, int[][] clusterIDs, int clusterIDToRemove) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (clusterIDs[row][column] == clusterIDToRemove) {
                    board[row][column] = EMPTY;
                }
            }
        }

        // Update rows
        for (int row = board.length - 1; row > 0; row--) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == EMPTY) {
                    for (int rowAbove = row - 1; rowAbove >= 0; rowAbove--) {
                        if (board[rowAbove][column] != EMPTY) {
                            board[row][column] = board[rowAbove][column];
                            board[rowAbove][column] = EMPTY;
                            break;
                        }
                    }
                }
            }
        }

        int emptyColumn = findEmptyMiddleColumn(board);
        while (emptyColumn != -1) {
            shiftColumnsLeft(board, emptyColumn);
            emptyColumn = findEmptyMiddleColumn(board);
        }
    }

    private static int findEmptyMiddleColumn(char[][] board) {
        for (int column = 0; column < board[0].length; column++) {
            boolean isEmpty = true;

            for (int row = 0; row < board.length; row++) {
                if (board[row][column] != EMPTY) {
                    isEmpty = false;
                    break;
                }
            }

            if (isEmpty) {
                for (int row = 0; row < board.length; row++) {
                    for (int nextColumn = column + 1; nextColumn < board[0].length; nextColumn++) {
                        if (board[row][nextColumn] != EMPTY) {
                            return column;
                        }
                    }
                }
                return -1;
            }
        }
        return -1;
    }

    private static void shiftColumnsLeft(char[][] board, int emptyColumn) {
        for (int column = emptyColumn; column < board[0].length - 1; column++) {
            for (int row = 0; row < board.length; row++) {
                board[row][column] = board[row][column + 1];
            }
        }

        for (int row = 0; row < board.length; row++) {
            board[row][board[0].length - 1] = EMPTY;
        }
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

        public void flush() {
            writer.flush();
        }
    }
}
