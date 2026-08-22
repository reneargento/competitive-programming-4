package chapter6.section4.b.in.twod.grid;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/08/2026.
 */
public class KInARow {

    private static class Result {
        int hanselScore;
        int gretelScore;
    }

    private static final int[] NEIGHBOR_ROWS = { 1, 0, 1, 1 };
    private static final int[] NEIGHBOR_COLUMNS = { 0, 1, -1, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int games = FastReader.nextInt();
        Result result = new Result();

        for (int g = 0; g < games; g++) {
            int columns = FastReader.nextInt();
            int rows = FastReader.nextInt();
            int sequenceSize = FastReader.nextInt();

            char[][] grid = new char[rows][columns];
            for (int row = 0; row < rows; row++) {
                grid[row] = FastReader.getLine().toCharArray();
            }
            checkGame(grid, sequenceSize, result);
        }
        outputWriter.printLine(result.hanselScore + ":" + result.gretelScore);
        outputWriter.flush();
    }

    private static void checkGame(char[][] grid, int sequenceSize, Result result) {
        if (wonGame(grid, sequenceSize, 'x')) {
            result.hanselScore++;
            return;
        }
        if (wonGame(grid, sequenceSize, 'o')) {
            result.gretelScore++;
        }
    }

    private static boolean wonGame(char[][] grid, int sequenceSize, char sign) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                for (int direction = 0; direction < NEIGHBOR_ROWS.length; direction++) {
                    boolean result = wonGame(grid, sequenceSize, sign, direction, row, column, 0);
                    if (result) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean wonGame(char[][] grid, int sequenceSize, char sign, int direction, int row, int column,
                                   int moveNumber) {
        if (moveNumber == sequenceSize) {
            return true;
        }
        if (row < 0
                || row >= grid.length
                || column < 0
                || column >= grid[row].length
                || grid[row][column] != sign) {
            return false;
        }

        int nextRow = row + NEIGHBOR_ROWS[direction];
        int nextColumn = column + NEIGHBOR_COLUMNS[direction];
        return wonGame(grid, sequenceSize, sign, direction, nextRow, nextColumn, moveNumber + 1);
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

        public void flush() {
            writer.flush();
        }
    }
}