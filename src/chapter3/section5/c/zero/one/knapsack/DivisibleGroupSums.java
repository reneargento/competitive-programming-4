package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/11/22.
 */
public class DivisibleGroupSums {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int sequenceLength = FastReader.nextInt();
        int setId = 1;

        while (sequenceLength != 0) {
            int[] numbers = new int[sequenceLength];
            int queries = FastReader.nextInt();
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = FastReader.nextInt();
            }
            outputWriter.printLine(String.format("SET %d:", setId));
            for (int q = 1; q <= queries; q++) {
                int divisor = FastReader.nextInt();
                int numbersToChoose = FastReader.nextInt();
                long groupsNumber = computeGroupsNumber(numbers, divisor, numbersToChoose);
                outputWriter.printLine(String.format("QUERY %d: %d", q, groupsNumber));
            }
            setId++;
            sequenceLength = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeGroupsNumber(int[] numbers, int divisor, int numbersToChoose) {
        int[] modNumbers = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            modNumbers[i] = mod(numbers[i], divisor);
        }

        long[][][] dp = new long[numbers.length + 1][divisor + 1][numbersToChoose + 1];
        for (long[][] rows : dp) {
            for (long[] values : rows) {
                Arrays.fill(values, -1);
            }
        }
        return computeGroupsNumber(modNumbers, divisor, dp, 0, 0, numbersToChoose);
    }

    private static long computeGroupsNumber(int[] numbers, int divisor, long[][][] dp, int numberId, int sum,
                                            int numbersToChoose) {
        if (sum == 0 && numbersToChoose == 0) {
            return 1;
        }
        if (numberId == numbers.length || numbersToChoose == 0) {
            return 0;
        }
        if (dp[numberId][sum][numbersToChoose] != -1) {
            return dp[numberId][sum][numbersToChoose];
        }

        int sumWithNumber = mod(sum + numbers[numberId], divisor);
        long groups = computeGroupsNumber(numbers, divisor, dp, numberId + 1, sum, numbersToChoose)
                + computeGroupsNumber(numbers, divisor, dp, numberId + 1, sumWithNumber, numbersToChoose - 1);
        dp[numberId][sum][numbersToChoose] = groups;
        return dp[numberId][sum][numbersToChoose];
    }

    private static int mod(int number, int mod) {
        int modValue = number % mod;
        if (modValue < 0) {
            modValue += mod;
        }
        return modValue;
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
