package chapter5.section2.h.polynomial;

import java.io.*;

/**
 * Created by Rene Argento on 21/04/25.
 */
public class QuotientPolynomial {

    private static class Result {
        int[] coefficients;
        int remainder;

        public Result(int[] coefficients, int remainder) {
            this.coefficients = coefficients;
            this.remainder = remainder;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int k = Integer.parseInt(line);
            String[] coefficientsString = FastReader.getLine().split("\\s+");
            int[] coefficients = new int[coefficientsString.length];
            for (int i = 0; i < coefficientsString.length; i++) {
                coefficients[i] = Integer.parseInt(coefficientsString[i]);
            }

            Result result = computeQuotientPolynomial(coefficients, -k);
            outputWriter.print("q(x):");
            for (int coefficient : result.coefficients) {
                outputWriter.print(" " + coefficient);
            }
            outputWriter.printLine();
            outputWriter.printLine("r = " + result.remainder);
            outputWriter.printLine();

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeQuotientPolynomial(int[] coefficients, int k) {
        int[] coefficientsQuotient = new int[coefficients.length - 1];
        int coefficientsQuotientIndex = 0;
        int remainder = 0;
        coefficientsQuotient[coefficientsQuotientIndex++] = coefficients[0];
        int currentCoefficient = coefficients[0];

        for (int i = 1; i < coefficients.length; i++) {
            int coefficientQuotient = coefficients[i] - (currentCoefficient * k);
            if (i < coefficients.length - 1) {
                coefficientsQuotient[coefficientsQuotientIndex++] = coefficientQuotient;
            } else {
                remainder = coefficientQuotient;
            }
            currentCoefficient = coefficientQuotient;
        }
        return new Result(coefficientsQuotient, remainder);
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
