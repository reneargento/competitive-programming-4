package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/01/23.
 */
public class AGroupingProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int integers = FastReader.nextInt();
        int mod = FastReader.nextInt();

        while (integers != 0 || mod != 0) {
            int[] values = new int[integers];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }
            long maximumFitness = computeMaximumFitness(values, mod);
            outputWriter.printLine(maximumFitness);

            integers = FastReader.nextInt();
            mod = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeMaximumFitness(int[] values, int mod) {
        // dp[elementsComputed][groupSize] = maximumFitness
        long[][] dp = new long[values.length + 1][values.length + 2];
        dp[0][0] = 1;

        for (int elementId = 1; elementId < dp.length; elementId++) {
            dp[elementId][0] = 1;
            for (int groupSize = 1; groupSize < dp[0].length; groupSize++) {
                dp[elementId][groupSize] = (dp[elementId - 1][groupSize] +
                        dp[elementId - 1][groupSize - 1] * values[elementId - 1]) % mod;
            }
        }

        long maximumFitness = 0;
        for (int groupSize = 0; groupSize < dp[0].length; groupSize++) {
            maximumFitness = Math.max(maximumFitness, dp[values.length][groupSize]);
        }
        return maximumFitness;
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
