package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/03/25.
 */
public class DifferentDistances {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        double x1 = FastReader.nextDouble();
        while (x1 != 0) {
            double y1 = FastReader.nextDouble();
            double x2 = FastReader.nextDouble();
            double y2 = FastReader.nextDouble();
            double p = FastReader.nextDouble();

            double xDifferenceAbsolute = Math.abs(x1 - x2);
            double yDifferenceAbsolute = Math.abs(y1 - y2);
            double pNormDistance = Math.pow(Math.pow(xDifferenceAbsolute, p) + Math.pow(yDifferenceAbsolute, p), 1 / p);
            outputWriter.printLine(pNormDistance);

            x1 = FastReader.nextDouble();
        }
        outputWriter.flush();
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
