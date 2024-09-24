package chapter4.section6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/06/24.
 */
public class GatheringFood {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class State {
        int row;
        int column;
        int lastPickedFood;
        int pathLength;

        public State(int row, int column, int lastPickedFood, int pathLength) {
            this.row = row;
            this.column = column;
            this.lastPickedFood = lastPickedFood;
            this.pathLength = pathLength;
        }
    }

    private static class Result {
        int shortestDistance;
        int distinctPaths;

        public Result(int shortestDistance, int distinctPaths) {
            this.shortestDistance = shortestDistance;
            this.distinctPaths = distinctPaths;
        }
    }

    private static final int MOD = 20437;
    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int gridSize = FastReader.nextInt();
        int caseId = 1;

        while (gridSize != 0) {
            Cell startCell = null;
            char highestFood = 'A';

            char[][] grid = new char[gridSize][gridSize];
            for (int row = 0; row < grid.length; row++) {
                String rowValue = FastReader.next();

                for (int column = 0; column < rowValue.length(); column++) {
                    char cell = rowValue.charAt(column);

                    if (cell == 'A') {
                        startCell = new Cell(row, column);
                    }
                    if (Character.isLetter(cell) && cell > highestFood) {
                        highestFood = cell;
                    }
                    grid[row][column] = cell;
                }
            }

            Result result = computeShortestPaths(grid, startCell, highestFood);
            outputWriter.print(String.format("Case %d: ", caseId));
            if (result == null) {
                outputWriter.printLine("Impossible");
            } else {
                outputWriter.printLine(String.format("%d %d", result.shortestDistance, result.distinctPaths));
            }
            caseId++;
            gridSize = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeShortestPaths(char[][] grid, Cell startCell, char highestFood) {
        int dimension = grid.length;
        int maxFoodId = 27;
        int maxPathLength = dimension * dimension * maxFoodId;
        // dp[row][column][last picked food][path length] = number of paths
        int[][][][] dp = new int[dimension][dimension][maxFoodId][maxPathLength];
        dp[startCell.row][startCell.column][0][0] = 1;

        boolean[][][] visited = new boolean[dimension][dimension][maxFoodId];
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(startCell.row, startCell.column, 0, 0));

        while (!queue.isEmpty()) {
            State state = queue.poll();
            int row = state.row;
            int column = state.column;
            int lastPickedFoodId = state.lastPickedFood;
            int pathLength = state.pathLength;

            if (visited[row][column][lastPickedFoodId]) {
                continue;
            }
            visited[row][column][lastPickedFoodId] = true;

            if (grid[row][column] == highestFood) {
                int shortestPathsCount = dp[row][column][lastPickedFoodId][pathLength];
                return new Result(pathLength, shortestPathsCount);
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(grid, neighborRow, neighborColumn)
                        && grid[neighborRow][neighborColumn] != '#') {
                    int nextPickedFoodId = lastPickedFoodId;
                    if (Character.isLetter(grid[neighborRow][neighborColumn])) {
                        int foodId = grid[neighborRow][neighborColumn] - 'A';
                        if (foodId > lastPickedFoodId + 1) {
                            continue;
                        } else if (foodId == lastPickedFoodId + 1) {
                            nextPickedFoodId = foodId;
                        }
                    }

                    dp[neighborRow][neighborColumn][nextPickedFoodId][pathLength + 1] += dp[row][column][lastPickedFoodId][pathLength];
                    dp[neighborRow][neighborColumn][nextPickedFoodId][pathLength + 1] %= MOD;
                    queue.offer(new State(neighborRow, neighborColumn, nextPickedFoodId, pathLength + 1));
                }
            }
        }
        return null;
    }

    private static boolean isValid(char[][] grid, int row, int column) {
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
