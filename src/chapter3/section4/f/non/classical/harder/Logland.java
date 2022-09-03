package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/08/22.
 */
public class Logland {

    private static final long MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] coinsInDenominations = new int[FastReader.nextInt()];
        for (int i = 0; i < coinsInDenominations.length; i++) {
            coinsInDenominations[i] = FastReader.nextInt();
        }

        long moneyLeftBehind = computeMoneyLeftBehind(coinsInDenominations);
        outputWriter.printLine(moneyLeftBehind);
        outputWriter.flush();
    }

    private static long computeMoneyLeftBehind(int[] coinsInDenominations) {
        long moneyLeftBehind = 0;
        long[] aggregatedValues = computeAggregatedValues(coinsInDenominations);
        long value = fastExponentiation(2, coinsInDenominations.length - 1);

        for (int i = coinsInDenominations.length - 1; i > 0; i--) {
            if (coinsInDenominations[i] % 2 == 0 || aggregatedValues[i] > coinsInDenominations[i]) {
                aggregatedValues[i] -= coinsInDenominations[i];
                aggregatedValues[i - 1] += aggregatedValues[i] * 2;
            } else {
                moneyLeftBehind += value;
                moneyLeftBehind %= MOD;
            }
            value *= 500000004;  // mod inverse of 2, mod 1e9+7
            value %= MOD;
        }

        if (aggregatedValues[0] % 2 == 1) {
            moneyLeftBehind++;
            moneyLeftBehind %= MOD;
        }
        return moneyLeftBehind;
    }

    private static long[] computeAggregatedValues(int[] coinsInDenominations) {
        long[] aggregatedValues = new long[coinsInDenominations.length];
        for (int i = 0; i < aggregatedValues.length; i++) {
            aggregatedValues[i] += coinsInDenominations[i];

            if (i != aggregatedValues.length - 1) {
                aggregatedValues[i + 1] = aggregatedValues[i] / 2;
                aggregatedValues[i] %= 2;
            }
        }
        return aggregatedValues;
    }

    private static long fastExponentiation(long base, int exponent) {
        if (exponent == 0) {
            return 1;
        }

        long baseSquared = base * base;
        long result = fastExponentiation(baseSquared % MOD, exponent / 2);
        if (exponent % 2 == 0) {
            return result;
        } else {
            return (base * result) % MOD;
        }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}