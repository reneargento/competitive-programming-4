package chapter6.section7.palindrome.generating;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/10/2026.
 */
public class GreatestKPalindromeSubstring {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.next();
            int k = FastReader.nextInt();

            int greatestKPalindrome = computeGreatestKPalindrome(string, k);
            outputWriter.printLine(greatestKPalindrome);
        }
        outputWriter.flush();
    }

    private static int computeGreatestKPalindrome(String string, int k) {
        // dp[start index][end index] = number of characters to change to become a palindrome
        int[][] dp = new int[string.length()][string.length()];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        for (int i = 0; i < string.length(); i++) {
            for (int j = i; j < string.length(); j++) {
                checkKPalindrome(string, dp, i, j);
            }
        }

        int greatestKPalindrome = 1;
        for (int startIndex = 0; startIndex < string.length(); startIndex++) {
            for (int endIndex = startIndex; endIndex < string.length(); endIndex++) {
                if (dp[startIndex][endIndex] <= k) {
                    int substringLength = endIndex - startIndex + 1;
                    greatestKPalindrome = Math.max(greatestKPalindrome, substringLength);
                }
            }
        }
        return greatestKPalindrome;
    }

    private static int checkKPalindrome(String string, int[][] dp, int start, int end) {
        if (start > end) {
            return 0;
        }
        if (start == end) {
            dp[start][end] = 0;
            return 0;
        }
        if (dp[start][end] != -1) {
            return dp[start][end];
        }

        int charactersToChange = string.charAt(start) == string.charAt(end) ? 0 : 1;
        int minCharactersToChange = charactersToChange + checkKPalindrome(string, dp, start + 1, end - 1);
        dp[start][end] = minCharactersToChange;
        return minCharactersToChange;
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