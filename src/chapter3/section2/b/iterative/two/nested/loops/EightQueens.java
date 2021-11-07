package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;

/**
 * Created by Rene Argento on 04/11/21.
 */
public class EightQueens {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[][] board = new String[8][8];

        for (int row = 0; row < 8; row++) {
            board[row] = FastReader.getLine().split("");
        }

        if (isValid(board)) {
            outputWriter.printLine("valid");
        } else {
            outputWriter.printLine("invalid");
        }
        outputWriter.flush();
    }

    private static boolean isValid(String[][] board) {
        boolean[] usedRows = new boolean[8];
        boolean[] usedColumns = new boolean[8];
        boolean[] usedLeftDiagonals = new boolean[15];
        boolean[] usedRightDiagonals = new boolean[15];
        int queens = 0;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column].equals("*")) {
                    queens++;
                    int leftDiagonal = row - column + 7;
                    int rightDiagonal = row + column;

                    if (usedRows[row] || usedColumns[column]
                            || usedLeftDiagonals[leftDiagonal]
                            || usedRightDiagonals[rightDiagonal]) {
                        return false;
                    }
                    usedRows[row] = true;
                    usedColumns[column] = true;
                    usedLeftDiagonals[leftDiagonal] = true;
                    usedRightDiagonals[rightDiagonal] = true;
                }
            }
        }
        return queens == 8;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
