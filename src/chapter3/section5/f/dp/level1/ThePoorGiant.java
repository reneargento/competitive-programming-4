package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/01/23.
 */
public class ThePoorGiant {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        // dp[apples][kValue] = minimumWeight
        int[][] dp = new int[501][501];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        for (int t = 1; t <= tests; t++) {
            int apples = FastReader.nextInt();
            int k = FastReader.nextInt();

            int minimumWeight = computeMinimumWeight(dp, apples, k);
            outputWriter.printLine(String.format("Case %d: %d", t, minimumWeight));
        }
        outputWriter.flush();
    }

    private static int computeMinimumWeight(int[][] dp, int apples, int k) {
        if (apples <= 1 || k >= dp[0].length) {
            return 0;
        }
        if (dp[apples][k] != -1) {
            return dp[apples][k];
        }

        int minimumWeight = Integer.MAX_VALUE;
        for (int i = 1; i <= apples; i++) {
            int weight = apples * (k + i) + computeMinimumWeight(dp, i - 1, k)
                    + computeMinimumWeight(dp, apples - i, k + i);
            minimumWeight = Math.min(minimumWeight, weight);
        }
        dp[apples][k] = minimumWeight;
        return minimumWeight;
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
