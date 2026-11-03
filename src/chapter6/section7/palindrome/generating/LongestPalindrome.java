package chapter6.section7.palindrome.generating;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/10/2026.
 */
public class LongestPalindrome {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.getLine();
            int longestPalindromeLength = computeLongestPalindrome(string);
            outputWriter.printLine(longestPalindromeLength);
        }
        outputWriter.flush();
    }

    private static int computeLongestPalindrome(String string) {
        // dp[start index][end index] = minimum number of deleted characters
        int[][] dp = new int[string.length()][string.length()];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return string.length() - computeLongestPalindrome(string, dp, 0, string.length() - 1);
    }

    private static int computeLongestPalindrome(String string, int[][] dp, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return 0;
        }
        if (dp[startIndex][endIndex] != -1) {
            return dp[startIndex][endIndex];
        }

        int longestPalindromeLength;
        if (string.charAt(startIndex) == string.charAt(endIndex)) {
            longestPalindromeLength = computeLongestPalindrome(string, dp, startIndex + 1, endIndex - 1);
        } else {
            int lengthCandidate1 = 1 + computeLongestPalindrome(string, dp, startIndex + 1, endIndex);
            int lengthCandidate2 = 1 + computeLongestPalindrome(string, dp, startIndex, endIndex - 1);
            longestPalindromeLength = Math.min(lengthCandidate1, lengthCandidate2);
        }
        dp[startIndex][endIndex] = longestPalindromeLength;
        return longestPalindromeLength;
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