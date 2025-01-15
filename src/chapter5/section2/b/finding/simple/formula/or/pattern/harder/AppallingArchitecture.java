package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/01/25.
 */
public class AppallingArchitecture {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int baseStartIndex = 0;
        int baseEndIndex = 0;
        int totalBlocks = 0;

        char[][] grid = new char[FastReader.nextInt()][FastReader.nextInt()];
        for (int row = 0; row < grid.length; row++) {
            boolean started = false;
            String values = FastReader.next();

            for (int column = 0; column < grid[0].length; column++) {
                grid[row][column] = values.charAt(column);
                if (grid[row][column] != '.') {
                    totalBlocks++;

                    if (row == grid.length - 1) {
                        if (!started) {
                            started = true;
                            baseStartIndex = column;
                        }
                        baseEndIndex = column + 1;
                    }
                }
            }
        }

        String gridBalance = computeGridBalance(grid, baseStartIndex, baseEndIndex, totalBlocks);
        outputWriter.printLine(gridBalance);
        outputWriter.flush();
    }

    private static String computeGridBalance(char[][] grid, int baseStartIndex, int baseEndIndex, int totalBlocks) {
        double xCoordinateCenter = getXCoordinateCenter(grid, totalBlocks);
        if (xCoordinateCenter < baseStartIndex) {
            return "left";
        } else if (xCoordinateCenter > baseEndIndex) {
            return "right";
        } else {
            return "balanced";
        }
    }

    private static double getXCoordinateCenter(char[][] grid, int totalBlocks) {
        double total = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] != '.') {
                    total += column + 0.5;
                }
            }
        }
        return total / totalBlocks;
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
