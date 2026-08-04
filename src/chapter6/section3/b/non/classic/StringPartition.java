package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/08/2026.
 */
public class StringPartition {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String digits = FastReader.getLine();
            long maxSum = computeMaxSum(digits);
            outputWriter.printLine(maxSum);
        }
        outputWriter.flush();
    }

    private static long computeMaxSum(String digitsString) {
        char[] digits = digitsString.toCharArray();
        // dp[index] = max sum starting from index
        long[] dp = new long[digits.length];
        Arrays.fill(dp, -1);
        return computeMaxSum(digits, dp, 0);
    }

    private static long computeMaxSum(char[] digits, long[] dp, int index) {
        if (index == digits.length) {
            return 0;
        }
        if (dp[index] != -1) {
            return dp[index];
        }

        long maxSum = 0;
        long currentNumber = 0;
        for (int i = index; i < digits.length; i++) {
            currentNumber = (currentNumber * 10) + Character.getNumericValue(digits[i]);
            if (currentNumber > Integer.MAX_VALUE) {
                break;
            }
            long sum = currentNumber + computeMaxSum(digits, dp, i + 1);
            maxSum = Math.max(maxSum, sum);
        }
        dp[index] = maxSum;
        return dp[index];
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

        private static String getLine() throws IOException {
            return reader.readLine();
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