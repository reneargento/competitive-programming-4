package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/06/24.
 */
public class Chopsticks {

    private static final int INFINITE = 1000000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int guests = FastReader.nextInt() + 8;
            Integer[] chopstickLengths = new Integer[FastReader.nextInt() + 1];
            chopstickLengths[0] = 0;
            for (int i = 1; i < chopstickLengths.length; i++) {
                chopstickLengths[i] = FastReader.nextInt();
            }

            int minimumBadness = computeMinimumBadness(guests, chopstickLengths);
            outputWriter.printLine(minimumBadness);
        }
        outputWriter.flush();
    }

    private static int computeMinimumBadness(int guests, Integer[] chopstickLengths) {
        // dp[guest id][chopstick id]
        int[][] dp = new int[guests + 1][chopstickLengths.length];
        int[] badnessLevel = new int[chopstickLengths.length];

        Arrays.sort(chopstickLengths, 1, chopstickLengths.length, Collections.reverseOrder());

        for (int i = 1; i < chopstickLengths.length; i++) {
            badnessLevel[i] = (chopstickLengths[i] - chopstickLengths[i - 1])
                    * (chopstickLengths[i] - chopstickLengths[i - 1]);
        }

        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumBadness(badnessLevel, dp, guests, chopstickLengths.length - 1);
    }

    private static int computeMinimumBadness(int[] badnessLevel, int[][] dp, int guestId, int chopstickId) {
        if (dp[guestId][chopstickId] != -1) {
            return dp[guestId][chopstickId];
        }

        if (guestId * 3 > chopstickId) {
            dp[guestId][chopstickId] = INFINITE;
        } else if (guestId == 0) {
            dp[guestId][chopstickId] = 0;
        } else {
            int badnessWithoutChopstick = computeMinimumBadness(badnessLevel, dp, guestId,
                    chopstickId - 1);
            int badnessWithChopstick = computeMinimumBadness(badnessLevel, dp, guestId - 1,
                    chopstickId - 2) + badnessLevel[chopstickId];
            dp[guestId][chopstickId] = Math.min(badnessWithoutChopstick, badnessWithChopstick);
        }
        return dp[guestId][chopstickId];
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
