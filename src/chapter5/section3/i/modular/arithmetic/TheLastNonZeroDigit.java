package chapter5.section3.i.modular.arithmetic;

import java.io.*;

/**
 * Created by Rene Argento on 07/10/25.
 */
public class TheLastNonZeroDigit {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int n = Integer.parseInt(data[0]);
            int m = Integer.parseInt(data[1]);

            long lastNonZeroDigit = computeLastNonZeroDigit(n, m);
            outputWriter.printLine(lastNonZeroDigit);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeLastNonZeroDigit(int n, int m) {
        long product = 1;

        for (int number = n; number > n - m; number--) {
            product *= number;
            while (product % 10 == 0) {
                product /= 10;
            }
            product %= 20000000;
        }
        return product % 10;
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
