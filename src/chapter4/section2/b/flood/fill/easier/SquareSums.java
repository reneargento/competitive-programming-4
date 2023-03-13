package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/23.
 */
public class SquareSums {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = FastReader.nextInt();
        int caseID = 1;

        while (dimension != 0) {
            int[][] grid = new int[dimension][dimension];
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[0].length; column++) {
                    grid[row][column] = FastReader.nextInt();
                }
            }

            List<Integer> sums = computeSums(grid);
            outputWriter.print(String.format("Case %d:", caseID));
            for (int sum : sums) {
                outputWriter.print(" " + sum);
            }
            outputWriter.printLine();

            caseID++;
            dimension = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeSums(int[][] grid) {
        List<Integer> sums = new ArrayList<>();
        int start = 0;
        int end = grid.length - 1;

        while (start < end) {
            int sumColumnLeftRight = sumColumns(grid, start, start, end);
            int sumColumnRightLeft = sumColumns(grid, end, start, end);
            int sumRowTopDown = sumRows(grid, end, start + 1, end - 1);
            int sumRowDownTop = sumRows(grid, start, start + 1, end - 1);
            int totalSum = sumColumnLeftRight + sumColumnRightLeft + sumRowTopDown + sumRowDownTop;
            sums.add(totalSum);
            start++;
            end--;
        }

        if (grid.length % 2 == 1) {
            int center = grid.length / 2;
            sums.add(grid[center][center]);
        }
        return sums;
    }

    private static int sumColumns(int[][] grid, int row, int startColumn, int endColumn) {
        int sum = 0;
        for (int column = startColumn; column <= endColumn; column++) {
            sum += grid[row][column];
        }
        return sum;
    }

    private static int sumRows(int[][] grid, int column, int startRow, int endRow) {
        int sum = 0;
        for (int row = startRow; row <= endRow; row++) {
            sum += grid[row][column];
        }
        return sum;
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
