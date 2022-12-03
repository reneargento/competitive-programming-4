package chapter3.section5.d.coin.change;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/12/22.
 */
public class ExactChange {

    private static class Result {
        int totalAmountPaid;
        int coinsUsed;

        public Result(int totalAmountPaid, int coinsUsed) {
            this.totalAmountPaid = totalAmountPaid;
            this.coinsUsed = coinsUsed;
        }
    }

    private static final int INFINITE = 100000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int targetPrice = FastReader.nextInt();
            int[] values = new int[FastReader.nextInt()];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }

            Result result = computePayment(values, targetPrice);
            outputWriter.printLine(String.format("%d %d", result.totalAmountPaid, result.coinsUsed));
        }
        outputWriter.flush();
    }

    private static Result computePayment(int[] values, int targetPrice) {
        int[] dp = new int[20001];
        Arrays.fill(dp, INFINITE);
        dp[0] = 0;

        for (int value : values) {
            for (int totalValue = dp.length - 1; totalValue >= value; totalValue--) {
                if (dp[totalValue - value] != INFINITE) {
                    int valueWithoutCoin = dp[totalValue];
                    int valueWithCoin = dp[totalValue - value] + 1;
                    dp[totalValue] = Math.min(valueWithoutCoin, valueWithCoin);
                }
            }
        }

        for (int totalValue = targetPrice; totalValue < dp.length; totalValue++) {
            if (dp[totalValue] != INFINITE) {
                return new Result(totalValue, dp[totalValue]);
            }
        }
        return new Result(-1, -1);
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
