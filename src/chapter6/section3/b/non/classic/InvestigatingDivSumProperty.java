package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/08/2026.
 */
public class InvestigatingDivSumProperty {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int startIndex = FastReader.nextInt();
            int endIndex = FastReader.nextInt();
            int divisor = FastReader.nextInt();

            int count = countDivisors(endIndex, divisor) - countDivisors(startIndex - 1, divisor);
            outputWriter.printLine(count);
        }
        outputWriter.flush();
    }

    private static int countDivisors(int value, int divisor) {
        divisor = Math.min(divisor, 99);

        // dp[digit index][is there a restriction][digit sum mod divisor][number mod divisor]
        int[][][][] dp = new int[32][2][100][100];
        for (int[][][] values1 : dp) {
            for (int[][] values2 : values1) {
                for (int[] values3 : values2) {
                    Arrays.fill(values3, -1);
                }
            }
        }

        int[] digits = new int[32];
        int digitIndex;
        for (digitIndex = 0; value != 0; value /= 10) {
            digits[digitIndex] = value % 10;
            digitIndex++;
        }
        return countDivisors(digits, dp, divisor, digitIndex - 1, 1, 0, 0);
    }

    private static int countDivisors(int[] digits, int[][][][] dp, int divisor, int digitIndex, int restricted,
                                     int digitsSumMod, int numberMod) {
        if (digitIndex < 0) {
            if (digitsSumMod == 0 && numberMod == 0) {
                return 1;
            }
            return 0;
        }

        if (dp[digitIndex][restricted][digitsSumMod][numberMod] != -1) {
            return dp[digitIndex][restricted][digitsSumMod][numberMod];
        }
        dp[digitIndex][restricted][digitsSumMod][numberMod] = 0;

        int maxDigit = 9;
        if (restricted != 0) {
            maxDigit = digits[digitIndex];
        }
        for (int digit = 0; digit <= maxDigit; digit++) {
            int nextRestricted = 0;
            if (restricted != 0 && digit == digits[digitIndex]) {
                nextRestricted = 1;
            }
            int nextDigitsSumMod = (digitsSumMod + digit) % divisor;
            int nextNumberMod = (numberMod * 10 + digit) % divisor;
            dp[digitIndex][restricted][digitsSumMod][numberMod] += countDivisors(digits, dp, divisor, digitIndex - 1,
                    nextRestricted, nextDigitsSumMod, nextNumberMod);
        }
        return dp[digitIndex][restricted][digitsSumMod][numberMod];
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