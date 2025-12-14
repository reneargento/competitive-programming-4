package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/12/25.
 */
public class Perica {

    private static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] keyValues = new int[FastReader.nextInt()];
        int k = FastReader.nextInt();

        for (int i = 0; i < keyValues.length; i++) {
            keyValues[i] = FastReader.nextInt();
        }

        long valuesSum = computeValuesSum(keyValues, k);
        outputWriter.printLine(valuesSum);
        outputWriter.flush();
    }

    private static long computeValuesSum(int[] keyValues, int k) {
        long valuesSum = 0;
        Arrays.sort(keyValues);

        long[][] dp = new long[keyValues.length + 1][k];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }

        for (int i = k - 1; i < keyValues.length; i++) {
            long binomialCoefficient = binomialCoefficient(dp, i, k - 1);
            long combinations = (keyValues[i] * binomialCoefficient) % MOD;
            valuesSum = (valuesSum + combinations) % MOD;
        }
        return valuesSum;
    }

    private static long binomialCoefficient(long[][] dp, int totalNumbers, int numbersToChoose) {
        if (totalNumbers == numbersToChoose || numbersToChoose == 0) {
            return 1;
        }
        if (totalNumbers < numbersToChoose) {
            return 0;
        }
        if (dp[totalNumbers][numbersToChoose] != -1) {
            return dp[totalNumbers][numbersToChoose];
        }

        dp[totalNumbers][numbersToChoose] = (binomialCoefficient(dp, totalNumbers - 1, numbersToChoose - 1)
                + binomialCoefficient(dp, totalNumbers - 1, numbersToChoose)) % MOD;
        return dp[totalNumbers][numbersToChoose];
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
