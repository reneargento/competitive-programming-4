package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/01/23.
 */
public class UnidirectionalTSP {

    private static class Solution {
        int minimumCost;
        List<Integer> rowsSelected;

        public Solution(int minimumCost, List<Integer> rowsSelected) {
            this.minimumCost = minimumCost;
            this.rowsSelected = rowsSelected;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);

            int[][] matrix = new int[rows][columns];
            for (int row = 0; row < matrix.length; row++) {
                for (int column = 0; column < matrix[0].length; column++) {
                    matrix[row][column] = FastReader.nextInt();
                }
            }

            Solution solution = computeSolution(matrix);
            outputWriter.print(solution.rowsSelected.get(0));
            for (int i = 1; i < solution.rowsSelected.size(); i++) {
                outputWriter.print(String.format(" %d", solution.rowsSelected.get(i)));
            }
            outputWriter.printLine();
            outputWriter.printLine(solution.minimumCost);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Solution computeSolution(int[][] matrix) {
        // dp[row][column] = totalCost
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        int minimumCost = Integer.MAX_VALUE;
        int startRow = Integer.MAX_VALUE;
        for (int row = 0; row < matrix.length; row++) {
            int cost = computeMinimumCost(matrix, dp, row, 0);
            if (cost < minimumCost) {
                minimumCost = cost;
                startRow = row;
            }
        }

        List<Integer> rowsSelected = computeRowsSelected(matrix, dp, startRow);
        return new Solution(minimumCost, rowsSelected);
    }

    private static int computeMinimumCost(int[][] matrix, int[][] dp, int row, int column) {
        if (column == dp[0].length) {
            return 0;
        }
        if (dp[row][column] != -1) {
            return dp[row][column];
        }

        int[] nextRows = getNextRows(row, matrix.length);
        int minimumCost = Integer.MAX_VALUE;
        for (int nextRow : nextRows) {
            int nextCost = matrix[row][column] + computeMinimumCost(matrix, dp, nextRow, column + 1);
            minimumCost = Math.min(minimumCost, nextCost);
        }

        dp[row][column] = minimumCost;
        return minimumCost;
    }

    private static List<Integer> computeRowsSelected(int[][] matrix, int[][] dp, int startRow) {
        List<Integer> rowsSelected = new ArrayList<>();
        rowsSelected.add(startRow + 1);

        int row = startRow;
        for (int column = 1; column < matrix[0].length; column++) {
            int[] nextRows = getNextRows(row, matrix.length);

            int selectedRow = Integer.MAX_VALUE;
            int minimumCost = Integer.MAX_VALUE;
            for (int nextRow : nextRows) {
                int nextCost = matrix[row][column] + computeMinimumCost(matrix, dp, nextRow, column);
                if (nextCost < minimumCost
                        || (nextCost == minimumCost && nextRow < selectedRow)) {
                    minimumCost = nextCost;
                    selectedRow = nextRow;
                }
            }
            row = selectedRow;
            rowsSelected.add(selectedRow + 1);
        }
        return rowsSelected;
    }

    private static int[] getNextRows(int row, int matrixLength) {
        int topDiagonalRow = (row - 1 + matrixLength) % matrixLength;
        int bottomDiagonalRow = (row + 1) % matrixLength;
        return new int[]{ row, topDiagonalRow, bottomDiagonalRow };
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
