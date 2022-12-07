package chapter3.section5.d.coin.change;

import java.io.*;

/**
 * Created by Rene Argento on 05/12/22.
 */
public class PayThePrice {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long[][] ways = countWays();
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int value = Integer.parseInt(data[0]);
            long waysToCount;

            if (data.length == 1) {
                waysToCount = ways[ways.length - 1][value];
            } else if (data.length == 2) {
                int upperBound = Integer.parseInt(data[1]);
                waysToCount = ways[upperBound][value];
            } else {
                int lowerBound = Integer.parseInt(data[1]);
                int upperBound = Integer.parseInt(data[2]);
                if (lowerBound > 0) {
                    waysToCount = ways[upperBound][value] - ways[lowerBound - 1][value];
                } else {
                    waysToCount = ways[upperBound][value];
                }
            }
            outputWriter.printLine(waysToCount);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long[][] countWays() {
        // dp[maximum coins used][sum] = number of ways
        long[][] dp = new long[1001][301];
        dp[0][0] = 1;

        for (int coin = 1; coin < dp.length; coin++) {
            for (int value = 0; value < dp[0].length; value++) {
                if (value >= coin) {
                    dp[coin][value] = dp[coin - 1][value] + dp[coin][value - coin];
                } else {
                    dp[coin][value] = dp[coin - 1][value];
                }
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
