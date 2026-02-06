package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/01/26.
 */
public class DiceThrowing {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int dices = FastReader.nextInt();
        int minimumSum = FastReader.nextInt();

        while (dices != 0 || minimumSum != 0) {
            String probability = computeProbability(dices, minimumSum);
            outputWriter.printLine(probability);

            dices = FastReader.nextInt();
            minimumSum = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String computeProbability(int dices, int minimumSum) {
        // dp[dices remaining][value needed]
        BigInteger[][] dp = new BigInteger[dices + 1][minimumSum + 1];
        for (BigInteger[] values : dp) {
            Arrays.fill(values, BigInteger.valueOf(-1));
        }

        BigInteger validSums = computeValidSums(dp, dices, minimumSum);
        BigInteger possibleValues = BigInteger.valueOf(6).pow(dices);

        BigInteger gcd = gcd(validSums, possibleValues);

        BigInteger validSumsAfterGcd = validSums.divide(gcd);
        BigInteger possibleValuesAfterGcd = possibleValues.divide(gcd);

        if (possibleValuesAfterGcd.equals(BigInteger.ONE)) {
            return validSumsAfterGcd.toString();
        }
        return validSumsAfterGcd + "/" + possibleValuesAfterGcd;
    }

    private static BigInteger computeValidSums(BigInteger[][] dp, int dices, int minimumSum) {
        if (minimumSum <= 0) {
            return BigInteger.valueOf(6).pow(dices);
        }
        if (dices == 0) {
            return BigInteger.ZERO;
        }
        if (dp[dices][minimumSum].compareTo(BigInteger.valueOf(-1)) != 0) {
            return dp[dices][minimumSum];
        }

        BigInteger totalValidSums = BigInteger.ZERO;
        for (int value = 1; value <= 6; value++) {
            totalValidSums = totalValidSums.add(computeValidSums(dp, dices - 1, minimumSum - value));
        }
        dp[dices][minimumSum] = totalValidSums;
        return totalValidSums;
    }

    private static BigInteger gcd(BigInteger number1, BigInteger number2) {
        while (number2.compareTo(BigInteger.ZERO) > 0) {
            BigInteger temp = number2;
            number2 = number1.mod(number2);
            number1 = temp;
        }
        return number1;
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
