package chapter3.section5.d.coin.change;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/12/22.
 */
public class CanonicalCoinSystems {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] denominations = new int[FastReader.nextInt()];
        for (int i = 0; i < denominations.length; i++) {
            denominations[i] = FastReader.nextInt();
        }

        outputWriter.printLine(isCanonical(denominations) ? "canonical" : "non-canonical");
        outputWriter.flush();
    }

    private static boolean isCanonical(int[] denominations) {
        int maxValue = denominations[denominations.length - 1] + denominations[denominations.length - 2];
        int[] dp = new int[maxValue + 1];
        Arrays.fill(dp, INFINITE);
        dp[0] = 0;

        for (int denomination : denominations) {
            for (int value = denomination; value < dp.length; value++) {
                int coinsWithoutCurrent = dp[value];
                int coinsWithCurrent = dp[value - denomination] + 1;
                dp[value] = Math.min(coinsWithoutCurrent, coinsWithCurrent);
            }
        }

        int[] greedyDp = new int[maxValue];
        Arrays.fill(greedyDp, -1);
        for (int counter = 1; counter < maxValue; counter++) {
            int greedySolution = greedySolution(denominations, counter, greedyDp);
            if (greedySolution != dp[counter]) {
                return false;
            }
        }
        return true;
    }

    private static int greedySolution(int[] denominations, int value, int[] greedyDp) {
        if (greedyDp[value] != -1) {
            return greedyDp[value];
        }
        int coinsNeeded = 0;
        int currentValue = value;

        for (int i = denominations.length - 1; i >= 0; i--) {
            if (denominations[i] <= currentValue) {
                coinsNeeded += currentValue / denominations[i];
                currentValue = currentValue % denominations[i];
            } else {
                continue;
            }

            if (currentValue == 0) {
                break;
            }
            if (greedyDp[currentValue] != -1) {
                greedyDp[value] = coinsNeeded + greedyDp[currentValue];
                return greedyDp[value];
            }
        }
        greedyDp[value] = coinsNeeded;
        return coinsNeeded;
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
