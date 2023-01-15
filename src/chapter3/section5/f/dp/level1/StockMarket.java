package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/01/23.
 */
public class StockMarket {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int[] prices = new int[Integer.parseInt(data[0])];
            int purchaseCost = Integer.parseInt(data[1]);

            for (int i = 0; i < prices.length; i++) {
                prices[i] = FastReader.nextInt();
            }

            int maximumProfit = computeMaximumProfit(prices, purchaseCost);
            outputWriter.printLine(maximumProfit);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMaximumProfit(int[] prices, int purchaseCost) {
        // dp[dayId][has stock or not]
        int[][] dp = new int[prices.length + 1][2];

        for (int dayId = prices.length - 1; dayId >= 0; dayId--) {
            for (int hasStock = 0; hasStock < 2; hasStock++) {
                int profitMaintainingPosition = dp[dayId + 1][hasStock];
                int profitWithOperation;
                if (hasStock == 0) {
                    // Buy stock
                    profitWithOperation = dp[dayId + 1][1] - prices[dayId] - purchaseCost;
                } else {
                    // Sell stock
                    profitWithOperation = dp[dayId + 1][0] + prices[dayId];
                }
                dp[dayId][hasStock] = Math.max(profitWithOperation, profitMaintainingPosition);
            }
        }
        return dp[0][0];
    }

    // Top-down approach looks more intuitive but unfortunately gets a Stackoverflow on UVa
    private static int computeMaximumProfitTopDown(int[] prices, int purchaseCost) {
        // dp[dayId][has stock or not]
        int[][] dp = new int[prices.length][2];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMaximumProfitTopDown(prices, purchaseCost, dp, 0, 0);
    }

    private static int computeMaximumProfitTopDown(int[] prices, int purchaseCost, int[][] dp, int dayId, int hasStock) {
        if (dayId == dp.length) {
            return 0;
        }
        if (dp[dayId][hasStock] != -1) {
            return dp[dayId][hasStock];
        }

        int maximumProfit = 0;
        if (hasStock == 0) {
            int profitBuying = computeMaximumProfitTopDown(prices, purchaseCost, dp, dayId + 1, 1)
                    - prices[dayId] - purchaseCost;
            maximumProfit = Math.max(maximumProfit, profitBuying);
        } else {
            int profitSelling = prices[dayId] + computeMaximumProfitTopDown(prices, purchaseCost, dp, dayId + 1, 0);
            maximumProfit = Math.max(maximumProfit, profitSelling);
        }
        int profitMaintainingPosition = computeMaximumProfitTopDown(prices, purchaseCost, dp, dayId + 1, hasStock);
        maximumProfit = Math.max(maximumProfit, profitMaintainingPosition);

        dp[dayId][hasStock] = maximumProfit;
        return maximumProfit;
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
