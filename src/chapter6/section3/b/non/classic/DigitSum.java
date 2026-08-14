package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/08/2026.
 */
public class DigitSum {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            long lowerBound = FastReader.nextLong();
            long upperBound = FastReader.nextLong();

            long digitSum = sumDigits(lowerBound, upperBound);
            outputWriter.printLine(digitSum);
        }
        outputWriter.flush();
    }

    private static long sumDigits(long lowerBound, long upperBound) {
        return sumDigits(upperBound) - sumDigits(lowerBound - 1);
    }

    private static long sumDigits(long upperBound) {
        char[] digits = String.valueOf(upperBound).toCharArray();
        // dp[digit index][is upper limit]
        long[][] dp = new long[digits.length][2];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        return sumDigits(digits, dp, 0, 1);
    }

    private static long sumDigits(char[] digits, long[][] dp, int digitIndex, int isUpperLimit) {
        if (digitIndex == digits.length) {
            return 0;
        }
        if (dp[digitIndex][isUpperLimit] != -1) {
            return dp[digitIndex][isUpperLimit];
        }

        long totalSum = 0;
        int maxDigit = isUpperLimit == 1 ? digits[digitIndex] - '0' : 9;
        for (int digit = 0; digit <= maxDigit; digit++) {
            int nextUpperLimit = isUpperLimit == 1
                    && digit == maxDigit ? 1 : 0;

            int digitsRemaining = digits.length - digitIndex - 1;
            long quantity = 1;
            if (digitsRemaining != 0) {
                if (nextUpperLimit == 1) {
                    quantity = Long.parseLong(new String(digits).substring(digitIndex + 1)) + 1;
                } else {
                    quantity = (long) Math.pow(10, digitsRemaining);
                }
            }
            totalSum += (digit * quantity) +
                    sumDigits(digits, dp, digitIndex + 1, nextUpperLimit);
        }

        dp[digitIndex][isUpperLimit] = totalSum;
        return dp[digitIndex][isUpperLimit];
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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