package chapter3.section5.d.coin.change;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 04/12/22.
 */
public class MakingChange {

    private static final double EPSILON = 0.000000000001;
    private static final int INFINITE = 1000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int[] coins = { 1, 2, 4, 10, 20, 40 };
        int[] dpUnrestricted = computeMinimumCoinsUnrestricted(coins);

        while (!line.equals("0 0 0 0 0 0")) {
            String[] data = line.split(" ");
            int[] quantities = new int[6];
            for (int i = 0; i < data.length - 1; i++) {
                quantities[i] = Integer.parseInt(data[i]);
            }
            int target = (int) ((Double.parseDouble(data[6]) + EPSILON) * 100) / 5;
            int minimumCoins = computeMinimumCoins(coins, quantities, target, dpUnrestricted);
            outputWriter.printLine(String.format("%3d", minimumCoins));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumCoins(int[] coins, int[] quantities, int target, int[] dpUnrestricted) {
        int[][] dp = new int[coins.length][1001];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumCoins(coins, quantities, dp, dpUnrestricted, 0, target);
    }

    private static int computeMinimumCoins(int[] coins, int[] quantities, int[][] dp, int[] dpUnrestricted,
                                           int coinId, int value) {
        if (value == 0) {
            return 0;
        }
        if (value < 0) {
            return dpUnrestricted[-value];
        }
        if (coinId == coins.length) {
            return INFINITE;
        }
        if (dp[coinId][value] != -1) {
            return dp[coinId][value];
        }

        int bestCoinsUsed = INFINITE;
        int quantitiesAvailable = quantities[coinId];
        for (int quantity = 0; quantity <= quantitiesAvailable; quantity++) {
            int valueUsed = coins[coinId] * quantity;
            quantities[coinId] -= quantity;
            int coinsWithCurrent = computeMinimumCoins(coins, quantities, dp, dpUnrestricted, coinId + 1,
                    value - valueUsed) + quantity;
            quantities[coinId] += quantity;
            bestCoinsUsed = Math.min(bestCoinsUsed, coinsWithCurrent);
        }
        dp[coinId][value] = bestCoinsUsed;
        return dp[coinId][value];
    }

    private static int[] computeMinimumCoinsUnrestricted(int[] coins) {
        int[] dp = new int[1001];
        Arrays.fill(dp, INFINITE);
        dp[0] = 0;

        for (int coin : coins) {
            for (int value = coin; value < dp.length; value++) {
                int currentCoins = dp[value];
                int alternativeCoins = dp[value - coin] + 1;
                dp[value] = Math.min(currentCoins, alternativeCoins);
            }
        }
        return dp;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
