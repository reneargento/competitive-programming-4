package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/11/24.
 */
public class SimpleCalculations {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int numbers = FastReader.nextInt();
            double[] c = new double[numbers];
            double a0 = FastReader.nextDouble();
            double aN1 = FastReader.nextDouble();

            for (int i = 0; i < numbers; i++) {
                c[i] = FastReader.nextDouble();
            }
            double cSum = getCSum(c);
            double a1 = (numbers * a0 + aN1 - 2 * cSum) / (numbers + 1);

            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("%.2f", a1));
        }
        outputWriter.flush();
    }

    private static double getCSum(double[] c) {
        double cSum = 0;
        int n = c.length;

        for (int i = 0; i < c.length; i++, n--) {
            cSum += c[i] * n;
        }
        return cSum;
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
