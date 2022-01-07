package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;

/**
 * Created by Rene Argento on 06/01/22.
 */
public class MultiplyingByRotation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int base = Integer.parseInt(data[0]);
            int lastDigit = Integer.parseInt(data[1]);
            int secondFactor = Integer.parseInt(data[2]);

            int firstFactorLength = computeFirstFactorLength(lastDigit, secondFactor, base);
            outputWriter.printLine(firstFactorLength);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeFirstFactorLength(int lastDigit, int secondFactor, int base) {
        int result = lastDigit * secondFactor;
        int digits;

        for (digits = 1; result != lastDigit; digits++) {
            result = (result % base) * secondFactor + result / base;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
