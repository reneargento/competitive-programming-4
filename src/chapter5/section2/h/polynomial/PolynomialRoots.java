package chapter5.section2.h.polynomial;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/04/25.
 */
public class PolynomialRoots {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int polynomials = FastReader.nextInt();

        for (int i = 0; i < polynomials; i++) {
            int degree = FastReader.nextInt();
            List<Double> coefficients = new ArrayList<>();
            for (int c = 0; c < degree + 1; c++) {
                coefficients.add(FastReader.nextDouble());
            }

            double[] roots = new double[degree - 2];
            for (int c = 0; c < roots.length; c++) {
                roots[c] = FastReader.nextDouble();
            }

            Double[] otherRoots = computeRoots(coefficients, roots);
            outputWriter.printLine(String.format("%.1f", otherRoots[0]));
            outputWriter.printLine(String.format("%.1f", otherRoots[1]));
        }
        outputWriter.flush();
    }

    private static Double[] computeRoots(List<Double> coefficients, double[] roots) {
        for (double root : roots) {
            List<Double> nextCoefficients = new ArrayList<>();
            double sumValue = 0;

            for (int i = 0; i < coefficients.size() - 1; i++) {
                double coefficient = coefficients.get(i);
                double nextCoefficient = coefficient + sumValue;
                sumValue = nextCoefficient * root;
                nextCoefficients.add(nextCoefficient);
            }
            coefficients = nextCoefficients;
        }

        double a = coefficients.get(0);
        double b = coefficients.get(1);
        double c = coefficients.get(2);
        double delta = Math.sqrt(Math.pow(b, 2) - 4 * a * c);
        double root1 = (-b + delta) / (2 * a);
        double root2 = (-b - delta) / (2 * a);

        Double[] otherRoots = new Double[] { root1, root2 };
        Arrays.sort(otherRoots, new Comparator<Double>() {
            @Override
            public int compare(Double value1, Double value2) {
                return Double.compare(value2, value1);
            }
        });
        return otherRoots;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
