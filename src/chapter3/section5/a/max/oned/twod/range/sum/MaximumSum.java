package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/09/22.
 */
public class MaximumSum {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int size = FastReader.nextInt();
        int[][] matrix = new int[size][size];
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[0].length; column++) {
                matrix[row][column] = FastReader.nextInt();
            }
        }
        int maxSum = computeMaxSum2D(matrix);
        outputWriter.printLine(maxSum);
        outputWriter.flush();
    }

    private static int computeMaxSum2D(int[][] matrix) {
        int maxSum = Integer.MIN_VALUE;

        for (int startRow = 0; startRow < matrix.length; startRow++) {
            int[] cumulativeColumnSum = new int[matrix[0].length];

            for (int endRow = startRow; endRow < matrix.length; endRow++) {
                for (int column = 0; column < matrix[0].length; column++) {
                    cumulativeColumnSum[column] += matrix[endRow][column];
                }

                int currentMaxSum = computeMaxSum1D(cumulativeColumnSum);
                maxSum = Math.max(maxSum, currentMaxSum);
            }
        }
        return maxSum;
    }

    private static int computeMaxSum1D(int[] array) {
        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;

        for (int value : array) {
            currentSum += value;
            maxSum = Math.max(maxSum, currentSum);
            currentSum = Math.max(currentSum, 0);
        }
        return maxSum;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}