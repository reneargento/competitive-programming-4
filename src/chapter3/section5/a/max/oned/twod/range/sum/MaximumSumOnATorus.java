package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/10/22.
 */
public class MaximumSumOnATorus {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int torusSize = FastReader.nextInt();
            int[][] torus = new int[torusSize][torusSize];

            for (int row = 0; row < torusSize; row++) {
                for (int column = 0; column < torusSize; column++) {
                    int value = FastReader.nextInt();
                    torus[row][column] = value;
                }
            }
            int maxSum = computeMaxSum(torus);
            outputWriter.printLine(maxSum);
        }
        outputWriter.flush();
    }

    private static int computeMaxSum(int[][] torus) {
        int maxSum = Integer.MIN_VALUE;

        for (int startRow = 0; startRow < torus.length; startRow++) {
            int[] cumulativeColumnSum = new int[torus[startRow].length];

            for (int i = 0, endRow = startRow; i < torus.length; i++, endRow++) {
                if (endRow == torus.length) {
                    endRow = 0;
                }
                for (int column = 0; column < torus[startRow].length; column++) {
                    cumulativeColumnSum[column] += torus[endRow][column];
                }
                int sum1 = kadane(cumulativeColumnSum);
                int sum2 = kadaneWithWrapping(cumulativeColumnSum);
                int maxCurrentSum = Math.max(sum1, sum2);
                maxSum = Math.max(maxSum, maxCurrentSum);
            }
        }
        return maxSum;
    }

    private static int kadane(int[] values) {
        int maxSum = values[0];
        int currentSum = values[0];

        for (int i = 1; i < values.length; i++) {
            currentSum = Math.max(currentSum + values[i], values[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }

    private static int kadaneWithWrapping(int[] values) {
        int totalSum = values[0];
        int minSum = values[0];
        int currentSum = values[0];

        for (int i = 1; i < values.length; i++) {
            totalSum += values[i];
            currentSum = Math.min(currentSum + values[i], values[i]);
            minSum = Math.min(minSum, currentSum);
        }

        if (totalSum == minSum) {
            return totalSum;
        }
        return totalSum - minSum;
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