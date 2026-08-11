package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/08/2026.
 */
public class AnagramDivision {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        long[] factorials = computeFactorials();

        for (int t = 0; t < tests; t++) {
            char[] digits = FastReader.next().toCharArray();
            int mod = FastReader.nextInt();

            long divisiblePermutations = countDivisiblePermutations(digits, mod, factorials);
            outputWriter.printLine(divisiblePermutations);
        }
        outputWriter.flush();
    }

    private static long countDivisiblePermutations(char[] digits, int mod, long[] factorials) {
        int bitmaskSize = (int) Math.pow(2, digits.length);
        // dp[current permutation % mod][bitmask of used digits] = total divisible permutations
        long[][] dp = new long[mod][bitmaskSize];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }

        long divisiblePermutations = countDivisiblePermutations(digits, mod, dp, 0, 0);
        int[] digitFrequencies = countDigitFrequencies(digits);
        for (int frequency : digitFrequencies) {
            divisiblePermutations /= factorials[frequency];
        }
        return divisiblePermutations;
    }

    private static long countDivisiblePermutations(char[] digits, int mod, long[][] dp, int permutationMod, int bitmask) {
        if (bitmask == Math.pow(2, digits.length) - 1) {
            return permutationMod == 0 ? 1 : 0;
        }
        if (dp[permutationMod][bitmask] != -1) {
            return dp[permutationMod][bitmask];
        }

        long divisiblePermutations = 0;
        for (int i = 0; i < digits.length; i++) {
            if ((bitmask & (1 << i)) == 0) {
                int nextBitmask = bitmask | (1 << i);
                int digitValue = Character.getNumericValue(digits[i]);
                int nextPermutation = (permutationMod * 10 + digitValue) % mod;
                divisiblePermutations += countDivisiblePermutations(digits, mod, dp, nextPermutation, nextBitmask);
            }
        }

        dp[permutationMod][bitmask] = divisiblePermutations;
        return dp[permutationMod][bitmask];
    }

    private static long[] computeFactorials() {
        long[] factorials = new long[10];
        factorials[0] = 1;
        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        return factorials;
    }

    private static int[] countDigitFrequencies(char[] digits) {
        int[] digitFrequencies = new int[10];
        for (char digitChar : digits) {
            int digitValue = Character.getNumericValue(digitChar);
            digitFrequencies[digitValue]++;
        }
        return digitFrequencies;
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