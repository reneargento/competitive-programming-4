package chapter5.section3.i.modular.arithmetic;

import java.io.*;

/**
 * Created by Rene Argento on 08/10/25.
 */
public class Ones {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int number = Integer.parseInt(line);
            int digitsInMultiple = computeDigitsInMultiple(number);
            outputWriter.printLine(digitsInMultiple);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeDigitsInMultiple(int number) {
        int digits = 1;
        long multiple = 1;

        while (multiple % number != 0) {
            multiple = multiple * 10 + 1;
            multiple %= number;
            digits++;
        }
        return digits;
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
