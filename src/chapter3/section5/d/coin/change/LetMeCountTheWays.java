package chapter3.section5.d.coin.change;

import java.io.*;

/**
 * Created by Rene Argento on 04/12/22.
 */
public class LetMeCountTheWays {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        long[] dp = countWays();

        while (line != null) {
            int value = Integer.parseInt(line);
            long ways = dp[value];
            if (ways == 1) {
                outputWriter.print("There is only 1 way");
            } else {
                outputWriter.print(String.format("There are %d ways", ways));
            }
            outputWriter.printLine(String.format(" to produce %d cents change.", value));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long[] countWays() {
        int[] coins = { 1, 5, 10, 25, 50 };
        long[] dp = new long[30001];
        dp[0] = 1;

        for (int coin : coins) {
            for (int value = coin; value < dp.length; value++) {
                dp[value] += dp[value - coin];
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
