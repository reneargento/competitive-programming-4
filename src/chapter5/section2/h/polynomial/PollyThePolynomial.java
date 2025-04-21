package chapter5.section2.h.polynomial;

import java.io.*;

/**
 * Created by Rene Argento on 20/04/25.
 */
public class PollyThePolynomial {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] coefficientsString = line.split("\\s+");
            String[] xValuesString = FastReader.getLine().split("\\s+");

            long[] results = evaluatePolynomial(coefficientsString, xValuesString);
            outputWriter.print(results[0]);
            for (int i = 1; i < results.length; i++) {
                outputWriter.print(" " + results[i]);
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long[] evaluatePolynomial(String[] coefficientsString, String[] xValuesString) {
        long[] results = new long[xValuesString.length];
        int[] coefficients = new int[coefficientsString.length];
        for (int i = 0; i < coefficientsString.length; i++) {
            coefficients[i] = Integer.parseInt(coefficientsString[i]);
        }

        for (int x = 0; x < xValuesString.length; x++) {
            int xValue = Integer.parseInt(xValuesString[x]);
            long result = 0;

            for (int i = 0; i < coefficients.length; i++) {
                int exponent = coefficients.length - 1 - i;
                result += coefficients[i] * (long) Math.pow(xValue, exponent);
            }
            results[x] = result;
        }
        return results;
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
