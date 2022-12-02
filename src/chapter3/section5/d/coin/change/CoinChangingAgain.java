package chapter3.section5.d.coin.change;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/12/22.
 */
public class CoinChangingAgain {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] values = new int[4];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }
            long[] dp = countUnrestrictedWays(values);

            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int[] quantities = new int[4];
                for (int i = 0; i < quantities.length; i++) {
                    quantities[i] = FastReader.nextInt();
                }
                int targetValue = FastReader.nextInt();
                long ways = countWays(values, quantities, targetValue, dp);
                outputWriter.printLine(ways);
            }
        }
        outputWriter.flush();
    }

    private static long[] countUnrestrictedWays(int[] values) {
        long[] dp = new long[100001];
        dp[0] = 1;

        for (int value : values) {
            for (int totalValue = value; totalValue < dp.length; totalValue++) {
                dp[totalValue] += dp[totalValue - value];
            }
        }
        return dp;
    }

    // Inclusion-exclusion principle
    // ways = Ways(A) + Ways(B) + Ways(C) + Ways(D) - Ways(AB) - Ways(AC) ... + Ways(ABC) + Ways(ABD) ... - Ways(ABCD)
    private static long countWays(int[] values, int[] quantities, int targetValue, long[] dp) {
        long ways = 0;

        // Check all permutations in bitwise 0000 to 1111
        for (int bitmap = 0; bitmap < 16; bitmap++) {
            int currentValue = targetValue;
            int multiplier = 1;

            for (int valueId = 0; valueId < values.length; valueId++) {
                if ((bitmap & (1 << valueId)) > 0) {
                    multiplier = -multiplier;
                    // Subtract values achievable with more coins than allowed
                    currentValue -= (quantities[valueId] + 1) * values[valueId];
                }
            }
            if (currentValue >= 0) {
                ways += multiplier * dp[currentValue];
            }
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
