package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/11/22.
 */
public class BestCoalitions {

    private static final double EPSILON = 0.00000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int stockholderNumber = FastReader.nextInt();
        int stockholderIndex = FastReader.nextInt();

        while (stockholderNumber != 0 || stockholderIndex != 0) {
            int stockholderOwnership = 0;
            int[] ownership = new int[stockholderNumber - 1];
            int indexArray = 0;

            for (int i = 1; i <= stockholderNumber; i++) {
                int sharesPercentage = (int) ((FastReader.nextDouble() + EPSILON) * 100);
                if (i == stockholderIndex) {
                    stockholderOwnership = sharesPercentage;
                } else {
                    ownership[indexArray++] = sharesPercentage;
                }
            }

            double maximumProfit = computeMaximumProfit(ownership, stockholderOwnership);
            outputWriter.printLine(String.format("%.2f", maximumProfit));

            stockholderNumber = FastReader.nextInt();
            stockholderIndex = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeMaximumProfit(int[] ownership, int stockholderOwnership) {
        if (stockholderOwnership > 5000) {
            return 100;
        }

        int[][] dp = new int[ownership.length][10001];
        int targetProfit = 5001 - stockholderOwnership;

        for (int[] shares : dp) {
            Arrays.fill(shares, -1);
        }

        int maximumProfit = computeMaximumProfit(ownership, dp, targetProfit, 0, 0);
        double totalPercentage = (maximumProfit + stockholderOwnership) / 100.0;
        return stockholderOwnership / totalPercentage;
    }

    private static int computeMaximumProfit(int[] ownership, int[][] dp, int targetProfit, int stockholderId,
                                            int profit) {
        if (stockholderId == ownership.length) {
            if (profit >= targetProfit) {
                return profit;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        if (dp[stockholderId][profit] != -1) {
            return dp[stockholderId][profit];
        }

        int profitWithoutShareholder = computeMaximumProfit(ownership, dp, targetProfit, stockholderId + 1, profit);
        int profitWithShareholder = computeMaximumProfit(ownership, dp, targetProfit, stockholderId + 1,
                profit + ownership[stockholderId]);
        dp[stockholderId][profit] = Math.min(profitWithShareholder, profitWithoutShareholder);
        return dp[stockholderId][profit];
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
