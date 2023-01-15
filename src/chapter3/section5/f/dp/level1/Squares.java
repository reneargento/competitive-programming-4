package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/01/23.
 */
public class Squares {

    private static final int INFINITE = 1000000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] dp = computeMinimumTerms(10001);

        for (int t = 0; t < tests; t++) {
            int n = FastReader.nextInt();
            int minimumTerms = dp[n];
            outputWriter.printLine(minimumTerms);
        }
        outputWriter.flush();
    }

    private static int[] computeMinimumTerms(int n) {
        int maxValue = (int) Math.sqrt(n);
        // dp[sum] = minimumTerms
        int[] dp = new int[n];
        Arrays.fill(dp, INFINITE);
        dp[0] = 0;

        for (int value = 1; value <= maxValue; value++) {
            for (int sum = value * value; sum < dp.length; sum++) {
                int squareValue = value * value;
                if (squareValue == sum) {
                    dp[sum] = 1;
                    continue;
                }
                dp[sum] = Math.min(dp[sum], dp[sum - squareValue] + 1);
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
