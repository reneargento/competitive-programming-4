package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;

/**
 * Created by Rene Argento on 19/02/25.
 */
public class StreetNumbers {

    public static void main(String[] args) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);

        for (long nSqrt = 2; nSqrt <= 10000; nSqrt++) {
            long n = nSqrt * nSqrt;
            checkNumber(n, outputWriter);
            checkNumber(2 * n, outputWriter);
        }
        outputWriter.flush();
    }

    private static void checkNumber(long n, OutputWriter outputWriter) {
        // 2 * k^2 = n * (n+1)
        long sum = n * (n + 1);
        double kDouble = Math.sqrt(sum / 2.0);
        long k = (long) kDouble;

        if (kDouble == k) {
            outputWriter.printLine(String.format("%10d%10d", k, n));
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
