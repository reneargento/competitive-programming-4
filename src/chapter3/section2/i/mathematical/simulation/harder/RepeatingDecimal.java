package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;

/**
 * Created by Rene Argento on 18/01/22.
 */
public class RepeatingDecimal {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int number1 = Integer.parseInt(data[0]);
            int number2 = Integer.parseInt(data[1]);
            int digits = Integer.parseInt(data[2]);
            String result = divide(number1, number2, digits);

            outputWriter.printLine(result);
            line = FastReader.getLine();
        }

        outputWriter.flush();
    }

    private static String divide(int number1, int number2, int digits) {
        StringBuilder result = new StringBuilder();
        boolean startedFraction = false;

        for (int i = 0; i < digits; i++) {
            if (number1 < number2) {
                number1 = number1 * 10;

                if (!startedFraction) {
                    result.append("0");
                    result.append(".");
                    startedFraction = true;
                }
            }

            int partialResult = number1 / number2;
            result.append(partialResult);
            number1 = number1 % number2;
        }

        return result.toString();
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
