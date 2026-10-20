package chapter6.section7.palindrome.checking;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 19/10/2026.
 */
public class PeskyPalindromes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        while (string != null) {
            int uniquePalindromes = countPalindromicSubstrings(string);
            outputWriter.printLine(String.format("The string '%s' contains %d palindromes.", string, uniquePalindromes));
            string = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countPalindromicSubstrings(String string) {
        Set<String> palindromes = new HashSet<>();
        boolean[][] dp = new boolean[string.length()][string.length()];

        for (int i = 0; i < string.length(); i++) {
            for (int j = i; j < string.length(); j++) {
                if (isPalindrome(string, dp, i, j)) {
                    palindromes.add(string.substring(i, j + 1));
                }
            }
        }
        return palindromes.size();
    }

    private static boolean isPalindrome(String string, boolean[][] dp, int start, int end) {
        if (start == end) {
            return true;
        }
        if (start + 1 == end) {
            return string.charAt(start) == string.charAt(end);
        }
        if (dp[start][end]) {
            return dp[start][end];
        }

        boolean isPalindrome = false;
        if (string.charAt(start) == string.charAt(end)) {
            isPalindrome = isPalindrome(string, dp, start + 1, end - 1);
        }
        dp[start][end] = isPalindrome;
        return isPalindrome;
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