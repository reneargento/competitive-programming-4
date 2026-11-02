package chapter6.section7.palindrome.generating;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 29/10/2026.
 */
public class MakePalindrome {

    private static class Result {
        int charactersToAdd;
        String palindrome;

        public Result(int charactersToAdd, String palindrome) {
            this.charactersToAdd = charactersToAdd;
            this.palindrome = palindrome;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        while (string != null) {
            Result result = computePalindrome(string);
            outputWriter.printLine(result.charactersToAdd + " " + result.palindrome);
            string = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computePalindrome(String string) {
        // dp[start index][end index] = minimum characters to add
        int[][] dp = new int[string.length()][string.length()];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        int minimumCharacters = computeMinimumCharacters(string, dp, 0, string.length() - 1);

        String palindrome = constructPalindrome(string, minimumCharacters, dp);
        return new Result(minimumCharacters, palindrome);
    }

    private static int computeMinimumCharacters(String string, int[][] dp, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            dp[startIndex][endIndex] = 0;
            return 0;
        }
        if (dp[startIndex][endIndex] != -1) {
            return dp[startIndex][endIndex];
        }

        int minimumCharacters;
        if (string.charAt(startIndex) == string.charAt(endIndex)) {
            minimumCharacters = computeMinimumCharacters(string, dp, startIndex + 1, endIndex - 1);
        } else {
            int minimumCandidate1 = 1 + computeMinimumCharacters(string, dp, startIndex + 1, endIndex);
            int minimumCandidate2 = 1 + computeMinimumCharacters(string, dp, startIndex, endIndex - 1);
            minimumCharacters = Math.min(minimumCandidate1, minimumCandidate2);
        }
        dp[startIndex][endIndex] = minimumCharacters;
        return minimumCharacters;
    }

    private static String constructPalindrome(String string, int minimumCharacters, int[][] dp) {
        StringBuilder palindrome = new StringBuilder();
        int startIndex = 0;
        int endIndex = string.length() - 1;
        int palindromeLength = string.length() + minimumCharacters;

        while (startIndex <= endIndex) {
            int matchCharacters = getValue(dp, startIndex, endIndex);
            int addStartCharacter = getValue(dp, startIndex + 1, endIndex);;
            int addEndCharacter = getValue(dp, startIndex, endIndex - 1);;

            if (isBestValue(matchCharacters, addStartCharacter, addEndCharacter)) {
                palindrome.append(string.charAt(startIndex));
                startIndex++;
                endIndex--;
            } else if (isBestValue(addEndCharacter, matchCharacters, addStartCharacter)) {
                palindrome.append(string.charAt(endIndex));
                endIndex--;
            } else {
                palindrome.append(string.charAt(startIndex));
                startIndex++;
            }
        }

        int firstHalfEndIndex = palindromeLength % 2 == 0 ? palindrome.length() : palindrome.length() - 1;
        StringBuilder reverseCharacters = new StringBuilder(palindrome.substring(0, firstHalfEndIndex)).reverse();
        return palindrome.append(reverseCharacters).toString();
    }

    private static int getValue(int[][] dp, int startIndex, int endIndex) {
        if (startIndex >= dp.length || endIndex < 0 || dp[startIndex][endIndex] == -1) {
            return Integer.MAX_VALUE;
        }
        return dp[startIndex][endIndex];
    }

    private static boolean isBestValue(int value, int otherValue1, int otherValue2) {
        return value <= otherValue1 && value <= otherValue2;
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