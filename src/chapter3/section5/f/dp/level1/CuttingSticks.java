package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/10/22.
 */
public class CuttingSticks {

    private static final int MAX_VALUE = 1000000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int stickLength = FastReader.nextInt();

        while (stickLength != 0) {
            int[] cuts = new int[FastReader.nextInt() + 2];
            cuts[0] = 0;
            cuts[cuts.length - 1] = stickLength;
            for (int i = 1; i < cuts.length - 1; i++) {
                cuts[i] = FastReader.nextInt();
            }

            int minimumCost = computeMinimumCost(cuts);
            outputWriter.printLine(String.format("The minimum cutting is %d.", minimumCost));
            stickLength = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumCost(int[] cuts) {
        int[][] dp = new int[cuts.length][cuts.length];
        // Knuth-Yao DP optimization
        int[][] opt = new int[cuts.length][cuts.length];

        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumCost(cuts, dp, opt, 0, cuts.length - 1);
    }

    private static int computeMinimumCost(int[] cuts, int[][] dp, int[][] opt, int left, int right) {
        if (left + 1 >= right) {
            opt[left][right] = left;
            return 0;
        }

        if (dp[left][right] != -1) {
            return dp[left][right];
        }
        dp[left][right] = MAX_VALUE;

        computeMinimumCost(cuts, dp, opt, left, right - 1);
        computeMinimumCost(cuts, dp, opt, left + 1, right);

        int minimumCost = Integer.MAX_VALUE;
        for (int i = opt[left][right - 1]; i <= opt[left + 1][right]; i++) {
            int leftCost = computeMinimumCost(cuts, dp, opt, left, i);
            int rightCost = computeMinimumCost(cuts, dp, opt, i, right);
            int cost = leftCost + rightCost + (cuts[right] - cuts[left]);
            if (cost < minimumCost) {
                minimumCost = cost;
                opt[left][right] = i;
            }
        }
        dp[left][right] = minimumCost;
        return dp[left][right];
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
