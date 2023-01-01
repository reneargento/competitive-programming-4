package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/09/22.
 */
public class WeddingShopping {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int budget = FastReader.nextInt();
            int garments = FastReader.nextInt();
            int[][] prices = new int[garments][];
            for (int g = 0; g < prices.length; g++) {
                int models = FastReader.nextInt();
                prices[g] = new int[models];
                for (int m = 0; m < models; m++) {
                    prices[g][m] = FastReader.nextInt();
                }
            }

            int maximumMoneyNecessary = computeMaxMoney(budget, prices);
            if (maximumMoneyNecessary < 0) {
                outputWriter.printLine("no solution");
            } else {
                outputWriter.printLine(maximumMoneyNecessary);
            }
        }
        outputWriter.flush();
    }

    private static int computeMaxMoney(int budget, int[][] prices) {
        boolean[][] dp = buildDpTable(budget, prices);
        int remainingBudget = -1;

        for (int i = 0; i < dp[dp.length - 1].length; i++) {
            if (dp[dp.length - 1][i]) {
                remainingBudget = i;
                break;
            }
        }

        if (remainingBudget < 0) {
            return -1;
        } else {
            return budget - remainingBudget;
        }
    }

    // Bottom-up
    private static boolean[][] buildDpTable(int budget, int[][] prices) {
        boolean[][] dp = new boolean[prices.length][budget + 1];
        // Base cases
        for (int m = 0; m < prices[0].length; m++) {
            int remainingBudget = budget - prices[0][m];
            if (remainingBudget >= 0) {
                dp[0][remainingBudget] = true;
            }
        }

        for (int g = 1; g < prices.length; g++) {
            for (int b = 0; b < dp[g].length; b++) {
                if (dp[g - 1][b]) {
                    for (int m = 0; m < prices[g].length; m++) {
                        int remainingBudget = b - prices[g][m];
                        if (remainingBudget >= 0) {
                            dp[g][remainingBudget] = true;
                        }
                    }
                }
            }
        }
        return dp;
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
