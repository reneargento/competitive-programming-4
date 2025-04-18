package chapter5.section2.h.polynomial;

import java.io.*;

/**
 * Created by Rene Argento on 16/04/25.
 */
public class SummationOfPolynomials {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long[] sums = computeSums();

        String line = FastReader.getLine();
        while (line != null) {
            int value = Integer.parseInt(line);
            outputWriter.printLine(sums[value]);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long[] computeSums() {
        long[] sums = new long[50001];
        sums[1] = 1;

        for (int i = 2; i < sums.length; i++) {
            sums[i] = sums[i - 1] + (long) i * i * i;
        }
        return sums;
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
