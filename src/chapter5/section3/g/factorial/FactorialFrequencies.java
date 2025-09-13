package chapter5.section3.g.factorial;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/09/25.
 */
public class FactorialFrequencies {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        BigInteger[] factorials = computeFactorials();

        int value = FastReader.nextInt();

        while (value != 0) {
            int[] digitsCount = countDigits(factorials, value);

            outputWriter.printLine(String.format("%d! --", value));
            printOutput(outputWriter, digitsCount, 0, 4);
            printOutput(outputWriter, digitsCount, 5, 9);

            value = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void printOutput(OutputWriter outputWriter, int[] digitsCount, int minDigit, int maxDigit) {
        for (int digit = minDigit; digit <= maxDigit; digit++) {
            outputWriter.print(String.format("   (%d)%6d", digit, digitsCount[digit]));
        }
        outputWriter.printLine();
    }

    private static int[] countDigits(BigInteger[] factorials, int value) {
        int[] digitsCount = new int[10];
        String factorial = factorials[value].toString();

        for (int i = 0; i < factorial.length(); i++) {
            int digitValue = Character.getNumericValue(factorial.charAt(i));
            digitsCount[digitValue]++;
        }
        return digitsCount;
    }

    private static BigInteger[] computeFactorials() {
        BigInteger[] factorials = new BigInteger[367];
        factorials[0] = BigInteger.ONE;

        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1].multiply(BigInteger.valueOf(i));
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
