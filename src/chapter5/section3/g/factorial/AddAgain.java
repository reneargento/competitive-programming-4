package chapter5.section3.g.factorial;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/08/25.
 */
// Based on https://pavelsimo.blogspot.com/2012/11/uva-11076-add-again.html
public class AddAgain {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        BigDecimal[] factorials = computeFactorials();

        int length = FastReader.nextInt();
        while (length != 0) {
            int[] digits = new int[length];
            int[] frequencies = new int[10];

            for (int i = 0; i < length; i++) {
                digits[i] = FastReader.nextInt();
                frequencies[digits[i]]++;
            }

            BigInteger sum = computeSum(digits, frequencies, factorials);
            outputWriter.printLine(sum);
            length = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static BigInteger computeSum(int[] digits, int[] frequencies, BigDecimal[] factorials) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal bigDecimal9 = BigDecimal.valueOf(9);
        int length = digits.length;
        int scale = 15;

        BigDecimal repetitions = BigDecimal.ONE;
        for (int frequency : frequencies) {
            repetitions = repetitions.multiply(factorials[frequency]);
        }
        BigDecimal digitsSumNominator = factorials[length - 1].divide(repetitions, scale, RoundingMode.CEILING);
        BigDecimal digitsSum = digitsSumNominator.multiply(BigDecimal.valueOf(Math.pow(10, length))
                .subtract(BigDecimal.ONE)).divide(bigDecimal9, scale, RoundingMode.CEILING);

        for (long digit : digits) {
            BigDecimal bigDecimalDigit = BigDecimal.valueOf(digit);
            sum = sum.add(bigDecimalDigit.multiply(digitsSum));
        }
        return sum.toBigInteger();
    }

    private static BigDecimal[] computeFactorials() {
        BigDecimal[] factorials = new BigDecimal[13];
        factorials[0] = BigDecimal.ONE;

        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1].multiply(BigDecimal.valueOf(i));
        }
        return factorials;
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
