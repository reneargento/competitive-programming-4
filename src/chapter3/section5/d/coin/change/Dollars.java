package chapter3.section5.d.coin.change;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/12/22.
 */
public class Dollars {

    private static final double EPSILON = 0.000000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long[] dp = countWays();

        double value = FastReader.nextDouble();
        while (value != 0) {
            int valueInt = (int) ((value + EPSILON) * 100);
            long ways = dp[valueInt];

            outputWriter.printLine(String.format("%6.2f%17d", value, ways));
            value = FastReader.nextDouble();
        }
        outputWriter.flush();
    }

    private static long[] countWays() {
        int[] coins = { 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000 };
        long[] dp = new long[30001];
        dp[0] = 1;

        for (int coinValue : coins) {
            for (int value = coinValue; value < dp.length; value++) {
                dp[value] += dp[value - coinValue];
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
