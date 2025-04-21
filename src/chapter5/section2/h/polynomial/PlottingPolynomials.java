package chapter5.section2.h.polynomial;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/04/25.
 */
public class PlottingPolynomials {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] coefficients = new int[FastReader.nextInt() + 1];
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[coefficients.length - 1 - i] = FastReader.nextInt();
        }

        long[] constants = computeConstants(coefficients);
        outputWriter.print(constants[0]);
        for (int i = 1; i < coefficients.length; i++) {
            outputWriter.print(" " + constants[i]);
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static long[] computeConstants(int[] coefficients) {
        long[] constants = new long[coefficients.length];

        for (int i = 0; i < constants.length; i++) {
            long constant = 0;

            for (int j = 0; j <= i; j++) {
                constant += (long) Math.pow(-1, j)
                        * binomialCoefficient(i, j)
                        * evaluatePolynomial(coefficients, i - j);
            }
            constants[i] = constant;
        }
        return constants;
    }

    private static long binomialCoefficient(int totalNumbers, int numbersToChoose) {
        long result = 1;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result * (totalNumbers - i) / (i + 1);
        }
        return result;
    }

    private static long evaluatePolynomial(int[] coefficients, long x) {
        long result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * (long) Math.pow(x, i);
        }
        return result;
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
