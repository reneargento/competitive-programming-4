package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/12/25.
 */
public class PolynomialCoefficients {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long[] factorials = computeFactorials();

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int power = Integer.parseInt(data[0]);
            int terms = Integer.parseInt(data[1]);

            int[] targetTermPowers = new int[terms];
            for (int i = 0; i < targetTermPowers.length; i++) {
                targetTermPowers[i] = FastReader.nextInt();
            }

            long coefficient = computeCoefficient(factorials, power, targetTermPowers);
            outputWriter.printLine(coefficient);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeCoefficient(long[] factorials, int power, int[] targetTermPowers) {
        long denominator = 1;
        for (int termPower : targetTermPowers) {
            denominator = denominator * factorials[termPower];
        }
        return factorials[power] / denominator;
    }

    private static long[] computeFactorials() {
        long[] factorials = new long[13];
        factorials[0] = 1;

        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1] * i;
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
