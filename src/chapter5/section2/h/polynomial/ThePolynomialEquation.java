package chapter5.section2.h.polynomial;

import java.io.*;

/**
 * Created by Rene Argento on 21/04/25.
 */
public class ThePolynomialEquation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] rootsValue = FastReader.getLine().split(" ");

            int[] roots = new int[rootsValue.length];
            for (int i = 0; i < roots.length; i++) {
                roots[i] = Integer.parseInt(rootsValue[i]);
            }

            String polynomialEquation = buildPolynomialEquation(roots);
            outputWriter.printLine(polynomialEquation);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String buildPolynomialEquation(int[] roots) {
        long[] coefficients = new long[52];
        coefficients[1] = 1;
        coefficients[0] = -roots[0];

        for (int i = 1; i < roots.length; i++) {
            int root = roots[i];

            for (int c = coefficients.length - 2; c >= 0; c--) {
                long coefficient = coefficients[c];
                if (coefficient != 0) {
                    coefficients[c + 1] += coefficients[c];
                    coefficients[c] *= -root;
                }
            }
        }

        StringBuilder polynomialEquation = new StringBuilder();
        boolean isFirstTerm = true;
        for (int c = coefficients.length - 1; c >= 0; c--) {
            long coefficient = coefficients[c];

            if (c != 0) {
                if (coefficient == 0) {
                    continue;
                }

                if (coefficient < 0) {
                    if (isFirstTerm) {
                        polynomialEquation.append("-");
                    } else {
                        polynomialEquation.append(" - ");
                    }
                } else if (!isFirstTerm) {
                    polynomialEquation.append(" + ");
                }

                long absCoefficient = Math.abs(coefficient);
                if (absCoefficient != 1) {
                    polynomialEquation.append(absCoefficient);
                }

                polynomialEquation.append("x");
                if (c > 1) {
                    polynomialEquation.append("^").append(c);
                }
                isFirstTerm = false;
            } else {
                if (coefficient < 0) {
                    if (isFirstTerm) {
                        polynomialEquation.append("-");
                    } else {
                        polynomialEquation.append(" - ");
                    }
                } else if (!isFirstTerm) {
                    polynomialEquation.append(" + ");
                }
                polynomialEquation.append(Math.abs(coefficient));
            }
        }
        polynomialEquation.append(" = 0");
        return polynomialEquation.toString();
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
