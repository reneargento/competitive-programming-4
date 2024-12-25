package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/12/24.
 */
public class RUKiddingMrFeynman {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        double number = FastReader.nextDouble();
        while (number != 0) {
            double approximatedCubicRoot = computeApproximatedCubicRoot(number);
            outputWriter.printLine(String.format("%.4f", approximatedCubicRoot));
            number = FastReader.nextDouble();
        }
        outputWriter.flush();
    }

    private static double computeApproximatedCubicRoot(double number) {
        long perfectCubicSquare = (long) Math.cbrt(number);
        double dx = 1/3.0 * (number - Math.pow(perfectCubicSquare, 3)) / perfectCubicSquare / perfectCubicSquare;
        return roundValuePrecisionDigits(perfectCubicSquare + dx);
    }

    private static double roundValuePrecisionDigits(double value) {
        long valueToMultiply = 10000;
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
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
