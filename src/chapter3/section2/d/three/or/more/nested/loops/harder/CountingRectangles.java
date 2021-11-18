package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/11/21.
 */
public class CountingRectangles {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();

        while (rows != 0) {
            int columns = FastReader.nextInt();
            String[][] board = new String[rows][columns];

            for (int r = 0; r < rows; r++) {
                board[r] = FastReader.getLine().trim().split("");
            }

            long rectangles = countRectangles(board);
            outputWriter.printLine(rectangles);
            rows = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long countRectangles(String[][] board) {
        long rectangles = 0;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column].equals("1")) {
                    rectangles++;

                    for (int offset = 1; offset <= board[row].length; offset++) {
                        int newRectangles = canExtendHorizontally(board, row, column, offset);
                        rectangles += newRectangles;

                        if (newRectangles == 0) {
                            break;
                        }
                    }

                    int newRectangles = canExtendVertically(board, row, column);
                    rectangles += newRectangles;
                }
            }
        }
        return rectangles;
    }

    private static int canExtendHorizontally(String[][] board, int row, int column, int offset) {
        int minRectanglesInRow = Integer.MAX_VALUE;

        for (int r = 0; r < offset; r++) {
            int nextRow = row + r;
            int nextColumn = column;
            int newRectangles = 0;
            int increment = r == 0 ? 1 : 0;

            if (nextRow == board.length || nextColumn == board[row].length) {
                minRectanglesInRow = Math.min(minRectanglesInRow, newRectangles);
                break;
            }

            while (isValid(board, nextRow, nextColumn + increment)
                    && board[nextRow][nextColumn + increment].equals("1")) {
                newRectangles++;
                nextColumn++;
            }
            if (increment == 0) {
                newRectangles--;
            }
            minRectanglesInRow = Math.min(minRectanglesInRow, newRectangles);
        }
        return Math.max(0, minRectanglesInRow);
    }

    private static int canExtendVertically(String[][] board, int row, int column) {
        int newRectangles = 0;

        while (isValid(board, row + 1, column)
                && board[row + 1][column].equals("1")) {
            newRectangles++;
            row++;
        }
        return newRectangles;
    }

    private static boolean isValid(String[][] board, int row, int column) {
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
