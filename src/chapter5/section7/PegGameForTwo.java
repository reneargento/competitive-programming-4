package chapter5.section7;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/04/26.
 */
public class PegGameForTwo {

    private static final int NO_JUMP = -99999;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[][] board = new int[5][];
        for (int row = 0; row < board.length; row++) {
            board[row] = new int[row + 1];
            for (int column = 0; column < board[row].length; column++) {
                board[row][column] = FastReader.nextInt();
            }
        }
        int bestScore = computeBestScore(board);
        outputWriter.printLine(bestScore);
        outputWriter.flush();
    }

    private static int computeBestScore(int[][] board) {
        int highestScore = NO_JUMP;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == 0) {
                    // Diagonal left-right, top-down
                    if (row >= 2
                            && column >= 2
                            && board[row - 2][column - 2] > 0
                            && board[row - 1][column - 1] > 0) {
                        int score = jumpAndGetScore(board, row, column, -2, -2, -1,-1);
                        highestScore = Math.max(highestScore, score);
                    }

                    // Diagonal left-right, down-top
                    if (row < board.length - 2
                            && column <= 2
                            && board[row + 2][column + 2] > 0
                            && board[row + 1][column + 1] > 0) {
                        int score = jumpAndGetScore(board, row, column, 2, 2, 1, 1);
                        highestScore = Math.max(highestScore, score);
                    }

                    // Diagonal right-left, top-down
                    if (row >= 2
                            && board[row - 2].length >= column + 1
                            && board[row - 2][column] > 0
                            && board[row - 1][column] > 0) {
                        int score = jumpAndGetScore(board, row, column, -2, 0, -1, 0);
                        highestScore = Math.max(highestScore, score);
                    }

                    // Diagonal right-left, down-top
                    if (row <= 2
                            && column <= 2
                            && board[row + 2][column] > 0
                            && board[row + 1][column] > 0) {
                        int score = jumpAndGetScore(board, row, column, 2, 0, 1, 0);
                        highestScore = Math.max(highestScore, score);
                    }

                    // Horizontal, left-right
                    if (column >= 2
                            && board[row][column - 2] > 0
                            && board[row][column - 1] > 0) {
                        int score = jumpAndGetScore(board, row, column, 0, -2, 0, -1);
                        highestScore = Math.max(highestScore, score);
                    }

                    // Horizontal, right-left
                    if (column < board[row].length - 2
                            && board[row][column + 2] > 0
                            && board[row][column + 1] > 0) {
                        int score = jumpAndGetScore(board, row, column, 0, 2, 0, 1);
                        highestScore = Math.max(highestScore, score);
                    }
                }
            }
        }

        if (highestScore == NO_JUMP) {
            return 0;
        }
        return highestScore;
    }

    private static int jumpAndGetScore(int[][] board, int row, int column, int rowIncrement1, int columnIncrement1,
                                       int rowIncrement2, int columnIncrement2) {
        int jumpScore = board[row + rowIncrement1][column + columnIncrement1]
                * board[row + rowIncrement2][column + columnIncrement2];
        int jumpedValue = board[row + rowIncrement2][column + columnIncrement2];
        jump(board, row, column, rowIncrement1, columnIncrement1, rowIncrement2, columnIncrement2);
        int score = jumpScore - computeBestScore(board);
        resetJump(board, row, column, rowIncrement1, columnIncrement1, rowIncrement2, columnIncrement2, jumpedValue);
        return score;
    }

    private static void jump(int[][] board, int row, int column, int rowIncrement1, int columnIncrement1,
                             int rowIncrement2, int columnIncrement2) {
        board[row][column] = board[row + rowIncrement1][column + columnIncrement1];
        board[row + rowIncrement1][column + columnIncrement1] = 0;
        board[row + rowIncrement2][column + columnIncrement2] = 0;
    }

    private static void resetJump(int[][] board, int row, int column, int rowIncrement1, int columnIncrement1,
                                  int rowIncrement2, int columnIncrement2, int jumpedValue) {
        board[row + rowIncrement1][column + columnIncrement1] = board[row][column];
        board[row + rowIncrement2][column + columnIncrement2] = jumpedValue;
        board[row][column] = 0;
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
