package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/12/25.
 */
public class TileCode {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        long[] dpAllArrangements = computeWaysToPlaceTilesAllArrangements();
        long[] dpArrangementsOdd = computeWaysToPlaceTilesSymmetrical(1, 2);
        long[] dpArrangementsEven = computeWaysToPlaceTilesSymmetrical(3, 2);

        for (int t = 0; t < tests; t++) {
            int length = FastReader.nextInt();
            long arrangements = dpAllArrangements[length];

            if (length % 2 == 1) {
                arrangements += dpArrangementsOdd[(length - 1) / 2];
            } else {
                arrangements += dpArrangementsEven[length / 2];
            }
            outputWriter.printLine(arrangements / 2);
        }
        outputWriter.flush();
    }

    private static long[] computeWaysToPlaceTilesAllArrangements() {
        long[] dp = new long[32];
        dp[0] = 1;

        for (int i = 0; i < dp.length - 2; i++) {
            dp[i + 1] += dp[i];
            dp[i + 2] += dp[i] * 2;
        }
        return dp;
    }

    private static long[] computeWaysToPlaceTilesSymmetrical(int startValue1, int startValue2) {
        long[] dp = new long[32];
        dp[0] = 1;
        dp[1] = startValue1;
        dp[2] = startValue2;

        for (int i = 1; i <= dp.length / 2; i++) {
            dp[i + 1] += dp[i];
            dp[i + 2] += dp[i] * 2;
        }
        return dp;
    }

    // Alternative solution
    // https://oeis.org/A090597
    private static long[] computeWaysToPlaceTiles() {
        long[] ways = new long[31];
        ways[0] = 1;
        ways[1] = 1;
        ways[2] = 3;
        ways[3] = 3;
        ways[4] = 8;
        ways[5] = 12;
        ways[6] = 27;

        for (int i = 7; i < ways.length; i++) {
            ways[i] = ways[i - 1] + 3 * ways[i - 2] - ways[i - 3] - 2 * ways[i - 5] - 4 * ways[i - 6];
        }
        return ways;
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
