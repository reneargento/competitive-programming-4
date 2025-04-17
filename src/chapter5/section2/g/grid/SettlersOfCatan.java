package chapter5.section2.g.grid;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/04/25.
 */
public class SettlersOfCatan {

    private static class MoveResult {
        int newRow;
        int newColumn;
        int newValue;

        public MoveResult(int newRow, int newColumn, int newValue) {
            this.newRow = newRow;
            this.newColumn = newColumn;
            this.newValue = newValue;
        }
    }

    private static final int[] neighborRows = { 0, 1, 1, 0, -1, -1 };
    private static final int[] neighborColumns = { -1, -1, 0, 1, 1, 0 };
    private static final int NORTHEAST_INDEX = 4;
    private static final int NORTH_INDEX = 5;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] resources = allocateResources();

        for (int t = 0; t < tests; t++) {
            int tileId = FastReader.nextInt();
            outputWriter.printLine(resources[tileId]);
        }
        outputWriter.flush();
    }

    private static int[] allocateResources() {
        int[] resources = new int[11000];
        int[] resourcesFrequency = new int[6];
        computeGrid(resources, resourcesFrequency);
        return resources;
    }

    private static void computeGrid(int[] resources, int[] resourcesFrequency) {
        int[][] grid = new int[120][120];
        int row = grid.length / 2;
        int column = grid.length / 2;
        int tileId = 1;
        int iterations = 1;

        MoveResult moveResult = move(grid, row, column, tileId, iterations, 0, 0, resources,
                resourcesFrequency);
        // Move northeast first
        moveResult = move(grid, moveResult.newRow, moveResult.newColumn, moveResult.newValue, iterations,
                -1, 1, resources, resourcesFrequency);

        while (moveResult.newValue <= 10000) {
            for (int i = 0; i < neighborRows.length; i++) {
                if (i == NORTHEAST_INDEX) {
                    iterations++;
                }
                int iterationsToUse = i != NORTH_INDEX ? iterations : iterations - 1;

                moveResult = move(grid, moveResult.newRow, moveResult.newColumn, moveResult.newValue,
                        iterationsToUse, neighborRows[i], neighborColumns[i], resources, resourcesFrequency);
            }
        }
    }

    private static MoveResult move(int[][] grid, int row, int column, int tileId, int iterations, int rowDelta,
                                   int columnDelta, int[] resources, int[] resourcesFrequency) {
        for (int i = 0; i < iterations; i++) {
            row += rowDelta;
            column += columnDelta;
            if (!isValid(grid, row, column)) {
                break;
            }

            int resource = chooseResource(grid, row, column, resourcesFrequency);
            grid[row][column] = resource;
            resources[tileId] = resource;
            resourcesFrequency[resource]++;
            tileId++;
        }
        return new MoveResult(row, column, tileId);
    }

    private static int chooseResource(int[][] grid, int row, int column, int[] resourcesFrequency) {
        boolean[] neighborResourceExists = new boolean[6];

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)) {
                int resource = grid[neighborRow][neighborColumn];
                neighborResourceExists[resource] = true;
            }
        }

        int lowestFrequencyValidResource = 0;
        int lowestFrequency = Integer.MAX_VALUE;

        for (int resource = 1; resource < resourcesFrequency.length; resource++) {
            if (neighborResourceExists[resource]) {
                continue;
            }

            if (resourcesFrequency[resource] < lowestFrequency) {
                lowestFrequencyValidResource = resource;
                lowestFrequency = resourcesFrequency[resource];
            }
        }
        return lowestFrequencyValidResource;
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
