package chapter4.section3.b.variants;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/09/23.
 */
public class MuddyHike {

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int[][] grid = new int[rows][columns];

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                grid[row][column] = FastReader.nextInt();
            }
        }

        int minimumMudDepth = computeMinimumMudDepth(grid);
        outputWriter.printLine(minimumMudDepth);
        outputWriter.flush();
    }

    private static int computeMinimumMudDepth(int[][] grid) {
        int minimumMudDepth = Integer.MAX_VALUE;
        int low = 0;
        int high = 1000000;

        while (low <= high) {
            int middleDepth = low + (high - low) / 2;

            if (canComputeMinimumMudDepth(grid, middleDepth)) {
                minimumMudDepth = Math.min(minimumMudDepth, middleDepth);
                high = middleDepth - 1;
            } else {
                low = middleDepth + 1;
            }
        }
        return minimumMudDepth;
    }

    private static boolean canComputeMinimumMudDepth(int[][] grid, int highestDepth) {
        for (int startRow = 0; startRow < grid.length; startRow++) {
            if (grid[startRow][0] > highestDepth) {
                continue;
            }
            boolean[][] visited = new boolean[grid.length][grid[0].length];

            boolean result = isPossibleToReach(grid, visited, highestDepth, startRow, 0);
            if (result) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPossibleToReach(int[][] grid, boolean[][] visited, int highestDepth, int row, int column) {
        visited[row][column] = true;
        if (column == grid[0].length - 1) {
            return true;
        }

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn]
                    && grid[neighborRow][neighborColumn] <= highestDepth) {
                boolean result = isPossibleToReach(grid, visited, highestDepth, neighborRow, neighborColumn);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValid(int[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
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
