package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/11/22.
 */
public class Luggage {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] data = FastReader.getLine().split(" ");
            int[] weights = new int[data.length];
            int sum = 0;
            for (int i = 0; i < weights.length; i++) {
                weights[i] = Integer.parseInt(data[i]);
                sum += weights[i];
            }
            outputWriter.printLine(isPossible(weights, sum) ? "YES" : "NO");
        }
        outputWriter.flush();
    }

    private static boolean isPossible(int[] weights, int sum) {
        if (sum % 2 != 0) {
            return false;
        }

        int halfSum = sum / 2;
        int[][] dp = new int[weights.length + 1][halfSum + 1];

        for (int luggageId = 1; luggageId < dp.length; luggageId++) {
            for (int totalWeight = 1; totalWeight < dp[0].length; totalWeight++) {
                int weightWithoutLuggage = dp[luggageId - 1][totalWeight];
                int weightWithLuggage = 0;
                if (totalWeight >= weights[luggageId - 1]) {
                    weightWithLuggage = dp[luggageId - 1][totalWeight - weights[luggageId - 1]] + weights[luggageId - 1];
                }
                dp[luggageId][totalWeight] = Math.max(weightWithoutLuggage, weightWithLuggage);
            }
        }
        return dp[weights.length][halfSum] == halfSum;
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
