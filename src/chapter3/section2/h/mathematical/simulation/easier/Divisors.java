package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class Divisors {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            int k = FastReader.nextInt();

            long sum = computeSum(number, k);
            outputWriter.printLine(sum);
        }
        outputWriter.flush();
    }

    private static long computeSum(int number, int k) {
        long sum = 0;
        int upperBound = (int) Math.sqrt(number);

        for (int divisor = 1; divisor <= upperBound; divisor++) {
            if (number % divisor == 0) {
                if (divisor % k != 0) {
                    sum += divisor;
                }

                int otherDivisor = number / divisor;
                if (otherDivisor != divisor && otherDivisor % k != 0) {
                    sum += otherDivisor;
                }
            }
        }
        return sum;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
