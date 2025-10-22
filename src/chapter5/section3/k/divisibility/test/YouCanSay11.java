package chapter5.section3.k.divisibility.test;

import java.io.*;

/**
 * Created by Rene Argento on 19/10/25.
 */
public class YouCanSay11 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String number = FastReader.getLine();
        while (!number.equals("0")) {
            boolean isMultipleOf11 = isMultipleOf11(number);
            outputWriter.print(number + " is ");

            if (!isMultipleOf11) {
                outputWriter.print("not ");
            }
            outputWriter.printLine("a multiple of 11.");
            number = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isMultipleOf11(String number) {
        int currentNumber = Character.getNumericValue(number.charAt(number.length() - 1));
        boolean subtract = true;

        for (int i = number.length() - 2; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (subtract) {
                currentNumber -= digit;
            } else {
                currentNumber += digit;
            }
            subtract = !subtract;
        }
        return currentNumber % 11 == 0;
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
