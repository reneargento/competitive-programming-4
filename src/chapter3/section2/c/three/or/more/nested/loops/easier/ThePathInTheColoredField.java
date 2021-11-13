package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rene Argento on 11/11/21.
 */
public class ThePathInTheColoredField {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int gridSize = Integer.parseInt(line);
            String[][] grid = new String[gridSize][gridSize];

            for (int i = 0; i < grid.length; i++) {
                grid[i] = FastReader.getLine().split("");
            }
            int minimalSteps = computeMinimalSteps(grid);
            outputWriter.printLine(minimalSteps);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimalSteps(String[][] grid) {
        int maxDistance = 0;
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column].equals("1")) {
                    int cellId = getCellId(grid, row, column);
                    int distance = bfs(grid, cellId, neighborRows, neighborColumns);
                    maxDistance = Math.max(maxDistance, distance);
                }
            }
        }
        return maxDistance;
    }

    private static int bfs(String[][] grid, int startCellId, int[] neighborRows, int[] neighborColumns) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startCellId);

        int totalCells = grid.length * grid[0].length;
        int[] distances = new int[totalCells];
        boolean[] visited = new boolean[totalCells];

        while (!queue.isEmpty()) {
            int cellId = queue.poll();
            int row = cellId / grid.length;
            int column = cellId % grid.length;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(grid, neighborRow, neighborColumn)) {
                    int neighborCellId = getCellId(grid, neighborRow, neighborColumn);

                    if (!visited[neighborCellId]) {
                        visited[neighborCellId] = true;
                        int distance = distances[cellId] + 1;
                        distances[neighborCellId] = distance;

                        if (grid[neighborRow][neighborColumn].equals("3")) {
                            return distance;
                        } else {
                            queue.offer(neighborCellId);
                        }
                    }
                }
            }
        }
        return -1;
    }

    private static int getCellId(String[][] grid, int row, int column) {
        return (grid[0].length * row) + column;
    }

    private static boolean isValid(String[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[row].length;
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
