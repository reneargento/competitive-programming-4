package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 04/08/2026.
 */
public class HillNumber {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char[] digits = FastReader.getLine().toCharArray();
        long hillNumbers = countHillNumbers(digits);
        outputWriter.printLine(hillNumbers);
        outputWriter.flush();
    }

    private static long countHillNumbers(char[] digits) {
        if (!isHillNumber(digits)) {
            return -1;
        }
        if (digits.length == 1) {
            return Character.getNumericValue(digits[0]);
        }

        // dp[number index][current digit value][has fallen][use original digit as max] = hill numbers
        long[][][][] dp = new long[digits.length][10][2][2];
        for (long[][][] values1 : dp) {
            for (long[][] values2 : values1) {
                for (long[] values3 : values2) {
                    Arrays.fill(values3, -1);
                }
            }
        }

        long hillNumbers = 0;
        int firstDigit = Character.getNumericValue(digits[0]);
        for (int digit = firstDigit; digit >= 0; digit--) {
            int useOriginalAsMax = digit == firstDigit ? 1 : 0;
            hillNumbers += countHillNumbers(digits, dp, 0, digit, 0, useOriginalAsMax);
        }
        return hillNumbers - 1;
    }

    private static long countHillNumbers(char[] digits, long[][][][] dp, int index, int digitValue, int hasFallen,
                                         int useOriginalAsMax) {
        if (index == digits.length - 1) {
            return 1;
        }
        if (dp[index][digitValue][hasFallen][useOriginalAsMax] != -1) {
            return dp[index][digitValue][hasFallen][useOriginalAsMax];
        }

        long hillNumbers = 0;
        int startDigit;
        if (useOriginalAsMax == 1) {
            startDigit = Character.getNumericValue(digits[index + 1]);
        } else {
            startDigit = 9;
        }

        for (int nextDigit = startDigit; nextDigit >= 0; nextDigit--) {
            if (nextDigit > digitValue) {
                if (hasFallen == 1) {
                    continue;
                }
            }

            int nextUseOriginalAsMax = 0;
            if (useOriginalAsMax == 1 && nextDigit == startDigit) {
                nextUseOriginalAsMax = 1;
            }

            int nextHasFallen = hasFallen;
            if (nextDigit < digitValue) {
                nextHasFallen = 1;
            }
            hillNumbers += countHillNumbers(digits, dp, index + 1, nextDigit, nextHasFallen, nextUseOriginalAsMax);
        }

        dp[index][digitValue][hasFallen][useOriginalAsMax] = hillNumbers;
        return dp[index][digitValue][hasFallen][useOriginalAsMax];
    }

    private static boolean isHillNumber(char[] digits) {
        boolean hasFallen = false;

        for (int i = 1; i < digits.length; i++) {
            if (digits[i] > digits[i - 1]) {
                if (hasFallen) {
                    return false;
                }
            } else if (digits[i] < digits[i - 1]) {
                hasFallen = true;
            }
        }
        return true;
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