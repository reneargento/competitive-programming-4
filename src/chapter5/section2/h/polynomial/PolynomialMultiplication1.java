package chapter5.section2.h.polynomial;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/04/25.
 */
public class PolynomialMultiplication1 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] coefficients1 = readPolynomial(FastReader.nextInt());
            int[] coefficients2 = readPolynomial(FastReader.nextInt());

            int[] result = multiplyPolynomials(coefficients1, coefficients2);
            outputWriter.printLine(result.length - 1);
            outputWriter.print(result[0]);
            for (int i = 1; i < result.length; i++) {
                outputWriter.print(" " + result[i]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static int[] readPolynomial(int degree) throws IOException {
        int[] coefficients = new int[degree + 1];
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] = FastReader.nextInt();
        }
        return coefficients;
    }

    private static int[] multiplyPolynomials(int[] coefficients1, int[] coefficients2) {
        int[] result = new int[coefficients1.length + coefficients2.length - 1];

        for (int i = 0; i < coefficients1.length; i++) {
            for (int j = 0; j < coefficients2.length; j++) {
                int exponent = i + j;

                int coefficient = coefficients1[i] * coefficients2[j];
                result[exponent] += coefficient;
            }
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
