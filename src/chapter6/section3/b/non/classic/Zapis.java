package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/08/2026.
 */
public class Zapis {

    private static class Result {
        long value;
        boolean is6DigitsOrMore;

        public Result(long value, boolean is6DigitsOrMore) {
            this.value = value;
            this.is6DigitsOrMore = is6DigitsOrMore;
        }
    }

    private static final int MOD = 100000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        FastReader.nextInt();
        char[] string = FastReader.getLine().toCharArray();

        String regularSequences = countRegularSequences(string);
        outputWriter.printLine(regularSequences);
        outputWriter.flush();
    }

    private static String countRegularSequences(char[] string) {
        // dp[string start index][string end index] = regular sequences count
        Result[][] dp = new Result[string.length][string.length];
        Result result = countRegularSequences(string, dp, 0, string.length - 1);
        return formatResult(result);
    }

    private static Result countRegularSequences(char[] string, Result[][] dp, int startIndex, int endIndex) {
        if (startIndex > endIndex) {
            return new Result(1, false);
        }
        if (dp[startIndex][endIndex] != null) {
            return dp[startIndex][endIndex];
        }

        long regularSequences = 0;
        boolean is6DigitsOrMore = false;
        if (string[startIndex] == '(') {
            Result result = processSymbol(string, dp, startIndex, endIndex,')');
            is6DigitsOrMore = result.is6DigitsOrMore;
            regularSequences = result.value;
        } else if (string[startIndex] == '[') {
            Result result = processSymbol(string, dp, startIndex, endIndex,']');
            is6DigitsOrMore = result.is6DigitsOrMore;
            regularSequences = result.value;
        } else if (string[startIndex] == '{') {
            Result result = processSymbol(string, dp, startIndex, endIndex,'}');
            is6DigitsOrMore = result.is6DigitsOrMore;
            regularSequences = result.value;
        } else if (string[startIndex] == '?') {
            Result result1 = processSymbol(string, dp, startIndex, endIndex,')');
            Result result2 = processSymbol(string, dp, startIndex, endIndex,']');
            Result result3 = processSymbol(string, dp, startIndex, endIndex,'}');

            regularSequences = result1.value + result2.value + result3.value;
            is6DigitsOrMore |= result1.is6DigitsOrMore || result2.is6DigitsOrMore || result3.is6DigitsOrMore;
        }
        if (regularSequences >= MOD) {
            is6DigitsOrMore = true;
        }
        dp[startIndex][endIndex] = new Result(regularSequences % MOD, is6DigitsOrMore);
        return dp[startIndex][endIndex];
    }

    private static Result processSymbol(char[] string, Result[][] dp, int startIndex, int endIndex, char closeSymbol) {
        long regularSequences = 0;
        boolean is6DigitsOrMore = false;
        for (int i = startIndex + 1; i <= endIndex; i++) {
            if (string[i] == closeSymbol || string[i] == '?') {
                Result sequences = countRegularSequences(string, dp, startIndex + 1, i - 1);
                long sequencesValue = sequences.value;
                if (i != endIndex) {
                    Result sequencesInSubstring = countRegularSequences(string, dp, i + 1, endIndex);
                    is6DigitsOrMore |= sequencesInSubstring.is6DigitsOrMore;

                    sequencesValue *= sequencesInSubstring.value;
                    if (sequencesValue >= MOD) {
                        is6DigitsOrMore = true;
                    }
                    sequencesValue %= MOD;
                }
                regularSequences += sequencesValue;
                if (regularSequences >= MOD) {
                    is6DigitsOrMore = true;
                }
            }
        }
        return new Result(regularSequences % MOD, is6DigitsOrMore);
    }

    private static String formatResult(Result result) {
        if (!result.is6DigitsOrMore) {
            return String.valueOf(result.value);
        }
        StringBuilder resultString = new StringBuilder(String.valueOf(result.value));
        int zeroesNeeded = 5 - resultString.length();
        for (int i = 0; i < zeroesNeeded; i++) {
            resultString.insert(0, "0");
        }
        return resultString.toString();
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