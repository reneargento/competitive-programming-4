package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/08/2026.
 */
public class Cudak {

    private static class Result {
        long numberOfIntegers;
        long smallestNumber;

        public Result(long numberOfIntegers, long smallestNumber) {
            this.numberOfIntegers = numberOfIntegers;
            this.smallestNumber = smallestNumber;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char[] lowerBound = FastReader.next().toCharArray();
        char[] upperBound = FastReader.next().toCharArray();
        int targetSum = FastReader.nextInt();

        Result result = computeTargetSum(lowerBound, upperBound, targetSum);
        outputWriter.printLine(result.numberOfIntegers);
        outputWriter.printLine(result.smallestNumber);
        outputWriter.flush();
    }

    private static Result computeTargetSum(char[] lowerBound, char[] upperBound, int targetSum) {
        long minimumNumberValue = Long.parseLong(new String(lowerBound));
        long minimumNumberMinus1 = Math.max(minimumNumberValue - 1, 0);
        char[] minimumNumberBound = String.valueOf(minimumNumberMinus1).toCharArray();

        Result resultUpperBound = computeTargetSum(upperBound, targetSum, lowerBound, upperBound);
        Result resultLowerBound = computeTargetSum(minimumNumberBound, targetSum, lowerBound, upperBound);

        long numberOfIntegers = resultUpperBound.numberOfIntegers - resultLowerBound.numberOfIntegers;
        return new Result(numberOfIntegers, resultUpperBound.smallestNumber);
    }

    private static Result computeTargetSum(char[] maxValue, int targetSum, char[] lowerBound, char[] upperBound) {
        // dp[digit index][current sum][is number upper bound] = number of integers with target digit sum
        long[][][] dp = new long[maxValue.length][targetSum + 1][2];
        for (long[][] values1 : dp) {
            for (long[] values2 : values1) {
                Arrays.fill(values2, -1);
            }
        }

        long numberOfIntegers = computeTargetSum(maxValue, targetSum, dp, 0, 0, 1);
        long smallestNumber = computeSmallestNumber(lowerBound, upperBound, targetSum);
        return new Result(numberOfIntegers, smallestNumber);
    }

    private static long computeTargetSum(char[] maxValue, int targetSum, long[][][] dp, int digitIndex, int currentSum,
                                         int isNumberUpperBound) {
        if (currentSum == targetSum) {
            return 1;
        }
        if (digitIndex == maxValue.length) {
            return 0;
        }
        if (dp[digitIndex][currentSum][isNumberUpperBound] != -1) {
            return dp[digitIndex][currentSum][isNumberUpperBound];
        }

        long numberOfIntegers = 0;
        int endDigit = 9;
        if (isNumberUpperBound == 1) {
            endDigit = Character.getNumericValue(maxValue[digitIndex]);
        }
        for (int digit = 0; digit <= endDigit; digit++) {
            if (currentSum + digit > targetSum) {
                break;
            }

            int isNumberUpperBoundNext = 0;
            if (isNumberUpperBound == 1 && digit == endDigit) {
                isNumberUpperBoundNext = 1;
            }
            numberOfIntegers += computeTargetSum(maxValue, targetSum, dp, digitIndex + 1,
                    currentSum + digit, isNumberUpperBoundNext);
        }

        dp[digitIndex][currentSum][isNumberUpperBound] = numberOfIntegers;
        return dp[digitIndex][currentSum][isNumberUpperBound];
    }

    private static long computeSmallestNumber(char[] lowerBound, char[] upperBound, int targetSum) {
        for (int digitsLength = lowerBound.length; digitsLength <= upperBound.length; digitsLength++) {
            char[] minimumValue;
            char[] maximumValue;

            if (digitsLength == lowerBound.length) {
                minimumValue = lowerBound;
            } else {
                minimumValue = new char[digitsLength];
                Arrays.fill(minimumValue, '0');
                minimumValue[0] = '1';
            }

            if (digitsLength == upperBound.length) {
                maximumValue = upperBound;
            } else {
                maximumValue = new char[digitsLength];
                Arrays.fill(maximumValue, '9');
            }

            long value = computeSmallestWithLength(minimumValue, maximumValue, targetSum);
            if (value != -1) {
                return value;
            }
        }
        return -1;
    }

    private static long computeSmallestWithLength(char[] minimumValue, char[] maximumValue, int targetSum) {
        int length = minimumValue.length;
        // dp[digit index][current sum][is lower limit][is upper limit] = smallest valid number
        long[][][][] dp = new long[length + 1][targetSum + 1][2][2];
        for (long[][][] values1 : dp) {
            for (long[][] values2 : values1) {
                for (long[] values3 : values2) {
                    Arrays.fill(values3, -1);
                }
            }
        }

        char[] number = new char[length];
        int currentSum = 0;
        int lowerLimited = 1;
        int upperLimited = 1;

        for (int digitIndex = 0; digitIndex < length; digitIndex++) {
            int minDigit = 0;
            int maxDigit = 9;
            if (lowerLimited == 1) {
                minDigit = Character.getNumericValue(minimumValue[digitIndex]);
            }
            if (upperLimited == 1) {
                maxDigit = Character.getNumericValue(maximumValue[digitIndex]);
            }

            boolean valid = false;
            for (int digit = minDigit; digit <= maxDigit; digit++) {
                if (currentSum + digit > targetSum) {
                    break;
                }

                int nextLowerLimited = lowerLimited == 1
                        && digit == minDigit ? 1 : 0;
                int nextUpperLimited = upperLimited == 1
                        && digit == maxDigit ? 1 : 0;
                int nextSum = currentSum + digit;

                if (isValidNumber(minimumValue, maximumValue, targetSum, dp, digitIndex + 1, nextSum,
                        nextLowerLimited, nextUpperLimited) == 1) {
                    number[digitIndex] = (char) (digit + '0');
                    currentSum += digit;
                    lowerLimited = nextLowerLimited;
                    upperLimited = nextUpperLimited;
                    valid = true;
                    break;
                }
            }

            if (!valid) {
                return -1;
            }
        }
        return Long.parseLong(new String(number));
    }

    private static long isValidNumber(char[] minimumValue, char[] maximumValue, int targetSum, long[][][][] dp,
                                      int digitIndex, int currentSum, int lowerLimited, int upperLimited) {
        if (currentSum > targetSum) {
            return 0;
        }
        if (digitIndex == maximumValue.length) {
            return currentSum == targetSum ? 1 : 0;
        }
        if (dp[digitIndex][currentSum][lowerLimited][upperLimited] != -1) {
            return dp[digitIndex][currentSum][lowerLimited][upperLimited];
        }

        int minDigit = 0;
        int maxDigit = 9;
        if (lowerLimited == 1) {
            minDigit = Character.getNumericValue(minimumValue[digitIndex]);
        }
        if (upperLimited == 1) {
            maxDigit = Character.getNumericValue(maximumValue[digitIndex]);
        }

        long result = 0;
        for (int digit = minDigit; digit <= maxDigit; digit++) {
            if (currentSum + digit > targetSum) {
                break;
            }
            int nextLowerLimited = lowerLimited == 1
                    && digit == minDigit ? 1 : 0;
            int nextUpperLimited = upperLimited == 1
                    && digit == maxDigit ? 1 : 0;
            int nextSum = currentSum + digit;
            if (isValidNumber(minimumValue, maximumValue, targetSum, dp, digitIndex + 1, nextSum,
                    nextLowerLimited, nextUpperLimited) == 1) {
                result = 1;
                break;
            }
        }

        dp[digitIndex][currentSum][lowerLimited][upperLimited] = result;
        return dp[digitIndex][currentSum][lowerLimited][upperLimited];
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