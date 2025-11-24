package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/11/25.
 */
public class OceansAnti11 {

    private static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int length = FastReader.nextInt();
            long validSubstrings = computeValidSubstrings(length);
            outputWriter.printLine(validSubstrings);
        }
        outputWriter.flush();
    }

    private static long computeValidSubstrings(int length) {
        // dp[current index][bit value] = valid substrings
        long[][] dp = new long[length][2];
        dp[0][0] = 1;
        dp[0][1] = 1;

        for (int index = 1; index < length; index++) {
            dp[index][0] = (dp[index - 1][0] + dp[index - 1][1]) % MOD;
            dp[index][1] = dp[index - 1][0] % MOD;
        }
        return (dp[length - 1][0] + dp[length - 1][1]) % MOD;
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
