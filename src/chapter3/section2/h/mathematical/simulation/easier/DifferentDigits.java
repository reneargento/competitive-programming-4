package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class DifferentDigits {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int left = Integer.parseInt(data[0]);
            int right = Integer.parseInt(data[1]);

            int houseNumbers = computeHouseNumbers(left, right);
            outputWriter.printLine(houseNumbers);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeHouseNumbers(int left, int right) {
        int houseNumbers = 0;

        for (int number = left; number <= right; number++) {
            if (isValidNumber(number)) {
                houseNumbers++;
            }
        }
        return houseNumbers;
    }

    private static boolean isValidNumber(int number) {
        boolean[] digits = new boolean[10];

        while (number > 0) {
            int digit = number % 10;
            if (digits[digit]) {
                return false;
            }
            digits[digit] = true;
            number /= 10;
        }
        return true;
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
