package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/05/24.
 */
public class ConcertTour {

    private static final int INFINITE = 1000000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int stores = FastReader.nextInt();
            int concerts = FastReader.nextInt();

            int[][] profits = new int[stores][concerts];
            for (int storeId = 0; storeId < profits.length; storeId++) {
                for (int concertId = 0; concertId < concerts; concertId++) {
                    profits[storeId][concertId] = FastReader.nextInt();
                }
            }

            int[][] movingCosts = new int[stores][stores];
            for (int storeId1 = 0; storeId1 < movingCosts.length; storeId1++) {
                for (int storeId2 = 0; storeId2 < movingCosts[0].length; storeId2++) {
                    movingCosts[storeId1][storeId2] = FastReader.nextInt();
                }
            }

            int maxProfit = computeMaxProfit(profits, movingCosts);
            outputWriter.printLine(maxProfit);
        }
        outputWriter.flush();
    }

    private static int computeMaxProfit(int[][] profits, int[][] movingCosts) {
        // dp[storeId][month]
        int[][] dp = new int[profits.length][profits[0].length];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        int maxProfit = Integer.MIN_VALUE;

        for (int startStoreId = 0; startStoreId < profits.length; startStoreId++) {
            int profit = computeMaxProfit(profits, movingCosts, dp, profits[0].length, startStoreId, 0);
            maxProfit = Math.max(maxProfit, profit);
        }
        return maxProfit;
    }

    private static int computeMaxProfit(int[][] profits, int[][] movingCosts, int[][] dp, int concerts,
                                        int storeId, int month) {
        if (month == concerts) {
            return 0;
        }
        if (storeId == profits.length) {
            return INFINITE;
        }
        if (dp[storeId][month] != -1) {
            return dp[storeId][month];
        }

        int maxProfit = Integer.MIN_VALUE;
        for (int nextStoreId = 0; nextStoreId < profits.length; nextStoreId++) {
            int movingCost = movingCosts[storeId][nextStoreId];
            int profit = profits[storeId][month];
            int totalProfit = profit - movingCost + computeMaxProfit(profits, movingCosts, dp, concerts,
                    nextStoreId, month + 1);
            maxProfit = Math.max(maxProfit, totalProfit);
        }
        dp[storeId][month] = maxProfit;
        return dp[storeId][month];
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
