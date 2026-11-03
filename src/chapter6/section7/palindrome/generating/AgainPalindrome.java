package chapter6.section7.palindrome.generating;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/10/2026.
 */
public class AgainPalindrome {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String sequence = FastReader.getLine();
            long ways = countWaysToMakePalindrome(sequence);
            outputWriter.printLine(ways);
        }
        outputWriter.flush();
    }

    private static long countWaysToMakePalindrome(String sequence) {
        // dp[start index][end index] = number of ways
        long[][] dp = new long[sequence.length()][sequence.length()];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        return countWaysToMakePalindrome(sequence, dp, 0, sequence.length() - 1);
    }

    private static long countWaysToMakePalindrome(String sequence, long[][] dp, int startIndex, int endIndex) {
        if (startIndex > endIndex) {
            return 0;
        }
        if (startIndex == endIndex) {
            return 1;
        }
        if (dp[startIndex][endIndex] != -1) {
            return dp[startIndex][endIndex];
        }

        long ways;
        if (sequence.charAt(startIndex) == sequence.charAt(endIndex)) {
            ways = countWaysToMakePalindrome(sequence, dp, startIndex + 1, endIndex) +
                    countWaysToMakePalindrome(sequence, dp, startIndex, endIndex - 1) + 1;
        } else {
            ways = countWaysToMakePalindrome(sequence, dp, startIndex + 1, endIndex) +
                    countWaysToMakePalindrome(sequence, dp, startIndex, endIndex - 1) -
                    countWaysToMakePalindrome(sequence, dp, startIndex + 1, endIndex - 1);
        }
        dp[startIndex][endIndex] = ways;
        return ways;
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