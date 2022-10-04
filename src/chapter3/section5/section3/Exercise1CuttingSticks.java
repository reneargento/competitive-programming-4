package chapter3.section5.section3;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/10/22.
 */
public class Exercise1CuttingSticks {

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

        for (int size = 2; size < cuts.length; size++) {
            for (int left = 0; left + size < cuts.length; left++) {
                int right = left + size;
                int minimumCost = Integer.MAX_VALUE;
                for (int i = left + 1; i < right; i++) {
                    int cost = dp[left][i] + dp[i][right] + (cuts[right] - cuts[left]);
                    minimumCost = Math.min(minimumCost, cost);
                }
                dp[left][right] = minimumCost;
            }
        }
        return dp[0][cuts.length - 1];
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
