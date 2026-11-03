package chapter6.section7.palindrome.generating;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/10/2026.
 */
public class StringToPalindrome {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String string = FastReader.getLine();
            int minimumCharactersNeeded = computeMinimumCharacters(string);
            outputWriter.printLine(String.format("Case %d: %d", t, minimumCharactersNeeded));
        }
        outputWriter.flush();
    }

    private static int computeMinimumCharacters(String string) {
        // dp[start index][end index] = number of characters added
        int[][] dp = new int[string.length()][string.length()];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumCharacters(string, dp, 0, string.length() - 1);
    }

    private static int computeMinimumCharacters(String string, int[][] dp, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return 0;
        }
        if (dp[startIndex][endIndex] != -1) {
            return dp[startIndex][endIndex];
        }

        int minimumCharacters;
        if (string.charAt(startIndex) == string.charAt(endIndex)) {
            return computeMinimumCharacters(string, dp, startIndex + 1, endIndex - 1);
        } else {
            int addStartCharacter = 1 + computeMinimumCharacters(string, dp, startIndex + 1, endIndex);
            int addEndCharacter = 1 + computeMinimumCharacters(string, dp, startIndex, endIndex - 1);
            int replaceCharacter = 1 + computeMinimumCharacters(string, dp, startIndex + 1, endIndex - 1);

            minimumCharacters = Math.min(addStartCharacter, addEndCharacter);
            minimumCharacters = Math.min(minimumCharacters, replaceCharacter);
        }
        dp[startIndex][endIndex] = minimumCharacters;
        return minimumCharacters;
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