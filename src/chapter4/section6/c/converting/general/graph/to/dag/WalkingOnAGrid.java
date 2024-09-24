package chapter4.section6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/05/24.
 */
public class WalkingOnAGrid {

    private static final int NO_PATH = -10000000;
    private static final int LEFT_PARENT = 0;
    private static final int RIGHT_PARENT = 1;
    private static final int UP_PARENT = 2;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int gridDimension = FastReader.nextInt();
        int maxNegativeNumbers = FastReader.nextInt();
        int caseId = 1;

        while (gridDimension != 0 || maxNegativeNumbers != 0) {
            int[][] grid = new int[gridDimension][gridDimension];
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[0].length; column++) {
                    grid[row][column] = FastReader.nextInt();
                }
            }

            int maxSum = computeMaxSum(grid, maxNegativeNumbers);
            outputWriter.print(String.format("Case %d: ", caseId));
            if (maxSum == NO_PATH) {
                outputWriter.printLine("impossible");
            } else {
                outputWriter.printLine(maxSum);
            }

            caseId++;
            gridDimension = FastReader.nextInt();
            maxNegativeNumbers = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMaxSum(int[][] grid, int maxNegativeNumbers) {
        // dp[row][column][negative numbers left][parent]
        int[][][][] dp = new int[grid.length][grid.length][maxNegativeNumbers + 1][3];
        for (int[][][] dpValues1 : dp) {
            for (int[][] dpValues2 : dpValues1) {
                for (int[] values : dpValues2) {
                    Arrays.fill(values, -1);
                }
            }
        }
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        return computeMaxSum(grid, dp, visited, 0, 0, maxNegativeNumbers, 0);
    }

    private static int computeMaxSum(int[][] grid, int[][][][] dp, boolean[][] visited, int row, int column,
                                     int negativeNumbersLeft, int parentCell) {
        int lastPosition = grid.length - 1;
        if (negativeNumbersLeft < 0 || row > lastPosition || column < 0 || column > lastPosition
                || visited[row][column]) {
            return NO_PATH;
        }
        if (row == lastPosition && column == lastPosition) {
            if (grid[row][column] < 0 && negativeNumbersLeft == 0) {
                return NO_PATH;
            }
            return grid[row][column];
        }
        visited[row][column] = true;

        if (dp[row][column][negativeNumbersLeft][parentCell] != -1) {
            visited[row][column] = false;
            return dp[row][column][negativeNumbersLeft][parentCell];
        }

        int maxSum = NO_PATH;
        int nextNegativeNumbersLeft = negativeNumbersLeft;
        if (grid[row][column] < 0) {
            nextNegativeNumbersLeft--;
        }

        int leftPathSum = computeMaxSum(grid, dp, visited, row, column - 1, nextNegativeNumbersLeft, RIGHT_PARENT);
        int rightPathSum = computeMaxSum(grid, dp, visited, row, column + 1, nextNegativeNumbersLeft, LEFT_PARENT);
        int downPathSum = computeMaxSum(grid, dp, visited, row + 1, column, nextNegativeNumbersLeft, UP_PARENT);
        visited[row][column] = false;

        maxSum = Math.max(maxSum, leftPathSum);
        maxSum = Math.max(maxSum, rightPathSum);
        maxSum = Math.max(maxSum, downPathSum);
        if (maxSum == NO_PATH) {
            dp[row][column][negativeNumbersLeft][parentCell] = NO_PATH;
            return NO_PATH;
        }
        maxSum += grid[row][column];

        dp[row][column][negativeNumbersLeft][parentCell] = maxSum;
        return dp[row][column][negativeNumbersLeft][parentCell];
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
