package chapter6.section7.palindrome.checking;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 18/10/2026.
 */
public class Palinwords {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split("\\s+");
            List<String> palinwords = computePalinwords(data);
            for (String word : palinwords) {
                outputWriter.printLine(word);
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> computePalinwords(String[] words) {
        List<String> palinwords = new ArrayList<>();
        for (String word : words) {
            boolean[][] dp = new boolean[word.length()][word.length()];
            for (int i = 0; i < word.length(); i++) {
                for (int j = i + 1; j < word.length(); j++) {
                    isPalindrome(word, dp, i, j);
                }
            }

            if (isPalinword(word, dp)) {
                palinwords.add(word);
            }
        }
        return palinwords;
    }

    private static boolean isPalinword(String word, boolean[][] dp) {
        Set<String> palindromes = new HashSet<>();

        for (int i = 0; i < word.length(); i++) {
            for (int j = i + 2; j <= i + 3 && j < word.length(); j++) {
                if (dp[i][j]) {
                    String palindrome = word.substring(i, j + 1);
                    palindromes.add(palindrome);
                }
            }
        }

        for (String palindrome1 : palindromes) {
            for (String palindrome2 : palindromes) {
                if (!palindrome1.contains(palindrome2)
                        && !palindrome2.contains(palindrome1)) {
                    return true;
                }
            }
        }
        return false;
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