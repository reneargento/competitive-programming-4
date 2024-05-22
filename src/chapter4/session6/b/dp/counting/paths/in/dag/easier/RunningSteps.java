package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/05/24.
 */
// Partly based on http://acmgnyr.org/year2015/solutions/b.c
public class RunningSteps {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        long[][] dp = new long[101][101];
        for (long[] values : dp) {
            Arrays.fill(values, - 1);
        }

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            int steps = FastReader.nextInt();

            long numberOfWays = computeNumberOfWays(steps, dp);
            outputWriter.printLine(String.format("%d %d", dataSetNumber, numberOfWays));
        }
        outputWriter.flush();
    }

    private static long computeNumberOfWays(int steps, long[][] dp) {
        long numberOfWays = 0;

        int stepsPerLeg = steps / 2;
        int oneSteps;
        if (stepsPerLeg % 2 == 1) {
            oneSteps = 1;
        } else {
            oneSteps = 0;
        }

        int limit = stepsPerLeg / 3;
        int strides = (stepsPerLeg / 2) + oneSteps;
        int remainingSteps = stepsPerLeg / 2;
        while (oneSteps <= limit) {
            long ways = computeNumberOfWays(strides, remainingSteps, dp);
            numberOfWays += ways * ways;

            remainingSteps--;
            strides++;
            oneSteps += 2;
        }
        return numberOfWays;
    }

    private static long computeNumberOfWays(int strides, int remaining, long[][] dp) {
        if (remaining == 0 || remaining == strides) {
            return 1;
        }
        if (dp[strides][remaining] != -1) {
            return dp[strides][remaining];
        }

        dp[strides][remaining] = computeNumberOfWays(strides - 1, remaining - 1, dp) +
                computeNumberOfWays(strides - 1, remaining, dp);
        return dp[strides][remaining];
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
