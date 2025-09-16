package chapter5.section3.g.factorial;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 11/09/25.
 */
public class ILoveBigNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        BigInteger[] factorials = computeFactorials();

        String line = FastReader.getLine();
        while (line != null) {
            int value = Integer.parseInt(line);
            int factorialDigitsSum = computeFactorialDigitsSum(factorials, value);
            outputWriter.printLine(factorialDigitsSum);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeFactorialDigitsSum(BigInteger[] factorials, int value) {
        int factorialDigitsSum = 0;
        String valueString = factorials[value].toString();

        for (int i = 0; i < valueString.length(); i++) {
            factorialDigitsSum += Character.getNumericValue(valueString.charAt(i));
        }
        return factorialDigitsSum;
    }

    private static BigInteger[] computeFactorials() {
        BigInteger[] factorials = new BigInteger[1001];
        factorials[0] = BigInteger.ONE;

        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1].multiply(BigInteger.valueOf(i));
        }
        return factorials;
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
