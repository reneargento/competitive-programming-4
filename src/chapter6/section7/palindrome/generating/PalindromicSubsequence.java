package chapter6.section7.palindrome.generating;

import java.io.*;

/**
 * Created by Rene Argento on 27/10/2026.
 */
public class PalindromicSubsequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        while (string != null) {
            String longestPalindromicSubsequence = computeLongestPalindromicSubsequence(string);
            outputWriter.printLine(longestPalindromicSubsequence);
            string = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computeLongestPalindromicSubsequence(String string) {
        int length = string.length();
        if (length == 0) {
            return "";
        }

        int[][] dp = new int[length][length];
        for (int i = length - 1; i >= 0; i--) {
            dp[i][i] = 1;

            for (int j = i + 1; j < length; j++) {
                if (string.charAt(i) == string.charAt(j)) {
                    dp[i][j] = 2 + (i + 1 <= j - 1 ? dp[i + 1][j - 1] : 0);
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        String[][] memo = new String[length][length];
        return computeMaxPalindromicSubsequence(string, dp, memo, 0, length - 1);
    }

    private static String computeMaxPalindromicSubsequence(String string, int[][] dp, String[][] memo, int start,
                                                           int end) {
        if (start > end) {
            return "";
        }
        if (start == end) {
            return string.substring(start, start + 1);
        }
        if (memo[start][end] != null) {
            return memo[start][end];
        }

        int maxLength = dp[start][end];
        String bestPalindrome = null;

        // Take both endpoints if they produce an optimal palindrome
        if (string.charAt(start) == string.charAt(end) &&
                2 + (start + 1 <= end - 1 ? dp[start + 1][end - 1] : 0) == maxLength) {
            String innerPalindrome = computeMaxPalindromicSubsequence(string, dp, memo, start + 1, end - 1);
            bestPalindrome = string.charAt(start) + innerPalindrome + string.charAt(end);
        }
        // Skip the left endpoint if the optimal length is preserved
        if (dp[start + 1][end] == maxLength) {
            String palindromeCandidate = computeMaxPalindromicSubsequence(string, dp, memo, start + 1, end);
            if (bestPalindrome == null || palindromeCandidate.compareTo(bestPalindrome) < 0) {
                bestPalindrome = palindromeCandidate;
            }
        }
        // Skip the right endpoint if the optimal length is preserved
        if (dp[start][end - 1] == maxLength) {
            String palindromeCandidate = computeMaxPalindromicSubsequence(string, dp, memo, start, end - 1);
            if (bestPalindrome == null || palindromeCandidate.compareTo(bestPalindrome) < 0) {
                bestPalindrome = palindromeCandidate;
            }
        }
        memo[start][end] = bestPalindrome;
        return bestPalindrome;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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