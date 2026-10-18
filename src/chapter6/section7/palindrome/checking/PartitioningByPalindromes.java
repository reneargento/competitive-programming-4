package chapter6.section7.palindrome.checking;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/10/2026.
 */
public class PartitioningByPalindromes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.getLine();
            long minimumGroups = computeGroups(string);
            outputWriter.printLine(minimumGroups);
        }
        outputWriter.flush();
    }

    private static long computeGroups(String string) {
        boolean[][] isPalindrome = precomputePalindromes(string);

        // dp[index] = minimum number of palindromes from index to end
        long[] dp = new long[string.length() + 1];
        for (int i = string.length() - 1; i >= 0; i--) {
            dp[i] = Integer.MAX_VALUE;

            for (int j = i; j < string.length(); j++) {
                if (isPalindrome[i][j]) {
                    dp[i] = Math.min(dp[i], 1 + dp[j + 1]);
                }
            }
        }
        return dp[0];
    }

    private static boolean[][] precomputePalindromes(String string) {
        boolean[][] isPalindrome = new boolean[string.length()][string.length()];

        for (int i = string.length() - 1; i >= 0; i--) {
            for (int j = i; j < string.length(); j++) {
                isPalindrome[i][j] = string.charAt(i) == string.charAt(j) &&
                        (j - i < 2 || isPalindrome[i + 1][j - 1]);
            }
        }
        return isPalindrome;
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