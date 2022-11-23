package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/11/22.
 */
public class DividingCoins {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] coins = new int[FastReader.nextInt()];
            int totalValue = 0;
            for (int i = 0; i < coins.length; i++) {
                coins[i] = FastReader.nextInt();
                totalValue += coins[i];
            }

            int minimumDifference = computeMinimumDifference(coins, totalValue);
            outputWriter.printLine(minimumDifference);
        }
        outputWriter.flush();
    }

    private static int computeMinimumDifference(int[] coins, int totalValue) {
        int halfValue = totalValue / 2;
        int[] dp = new int[halfValue + 1];

        for (int coinId = 1; coinId <= coins.length; coinId++) {
            for (int value =  dp.length - 1; value >= 0; value--) {
                if (value >= coins[coinId - 1]) {
                    int valueWithoutCoin = dp[value];
                    int valueWithCoin = dp[value - coins[coinId - 1]] + coins[coinId - 1];
                    dp[value] = Math.max(valueWithoutCoin, valueWithCoin);
                }
            }
        }
        return totalValue - (2 * dp[halfValue]);
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
