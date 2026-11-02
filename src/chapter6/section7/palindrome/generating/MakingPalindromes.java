package chapter6.section7.palindrome.generating;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/10/2026.
 */
public class MakingPalindromes {

    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        FastReader.nextInt();
        String string = FastReader.getLine();

        long palindromicAlphabeticalStrings = computePalindromicAlphabeticalStrings(string);
        outputWriter.printLine(palindromicAlphabeticalStrings);
        outputWriter.flush();
    }

    private static long computePalindromicAlphabeticalStrings(String string) {
        if (string.isEmpty()) {
            return 1;
        }

        int stringLength = string.length();
        // dp[left index][right index][characters remaining] = number of palindromic subsequences
        long[][][] dp = new long[stringLength][stringLength][stringLength + 1];
        for (long[][] values1 : dp) {
            for (long[] values2 : values1) {
                Arrays.fill(values2, -1);
            }
        }
        return computePalindromicAlphabeticalStrings(string, dp, 0, string.length() - 1,
                stringLength);
    }

    private static long computePalindromicAlphabeticalStrings(String string, long[][][] dp, int leftIndex,
                                                              int rightIndex, int charactersRemaining) {
        if (leftIndex > rightIndex) {
            return fastExponentiation(26, charactersRemaining);
        }
        if (charactersRemaining == 0) {
            return 0;
        }
        if (dp[leftIndex][rightIndex][charactersRemaining] != -1) {
            return dp[leftIndex][rightIndex][charactersRemaining];
        }

        long palindromicSubsequences;
        if (string.charAt(leftIndex) == string.charAt(rightIndex)) {
            palindromicSubsequences =
                    computePalindromicAlphabeticalStrings(string, dp, leftIndex + 1, rightIndex - 1, charactersRemaining - 1)
                            + computePalindromicAlphabeticalStrings(string, dp, leftIndex, rightIndex, charactersRemaining - 1) * 25;
        } else {
            palindromicSubsequences = computePalindromicAlphabeticalStrings(string, dp, leftIndex + 1, rightIndex, charactersRemaining - 1)
                    + computePalindromicAlphabeticalStrings(string, dp, leftIndex, rightIndex - 1, charactersRemaining - 1)
                    + computePalindromicAlphabeticalStrings(string, dp, leftIndex, rightIndex, charactersRemaining - 1) * 24;
        }
        palindromicSubsequences %= MOD;
        dp[leftIndex][rightIndex][charactersRemaining] = palindromicSubsequences;
        return palindromicSubsequences;
    }

    private static long fastExponentiation(long base, long exponent) {
        if (exponent == 0) {
            return 1;
        }

        long baseSquared = base * base;
        long result = fastExponentiation(baseSquared % MOD, exponent / 2);
        if (exponent % 2 == 0) {
            return result;
        } else {
            return (base * result) % MOD;
        }
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