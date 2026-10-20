package chapter6.section7.palindrome.checking;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/10/2026.
 */
public class PalindromeSubstring {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        while (string != null) {
            List<String> palindromeSubstrings = computePalindromeSubstrings(string);
            for (String palindromeSubstring : palindromeSubstrings) {
                outputWriter.printLine(palindromeSubstring);
            }
            outputWriter.printLine();
            string = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> computePalindromeSubstrings(String string) {
        Set<String> palindromeSet = new HashSet<>();

        Boolean[][] dp = new Boolean[string.length()][string.length()];
        for (int i = 0; i < string.length(); i++) {
            for (int j = i + 1; j < string.length(); j++) {
                if (isPalindrome(string, dp, i, j)) {
                    palindromeSet.add(string.substring(i, j + 1));
                }
            }
        }

        List<String> palindromeSubstrings = new ArrayList<>(palindromeSet);
        Collections.sort(palindromeSubstrings);
        return palindromeSubstrings;
    }

    private static Boolean isPalindrome(String string, Boolean[][] dp, int start, int end) {
        if (start == end) {
            return true;
        }
        if (start + 1 == end) {
            return string.charAt(start) == string.charAt(end);
        }
        if (dp[start][end] != null) {
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