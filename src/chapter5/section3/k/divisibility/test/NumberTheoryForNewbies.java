package chapter5.section3.k.divisibility.test;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 21/10/25.
 */
public class NumberTheoryForNewbies {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            char[] digits = line.toCharArray();
            Arrays.sort(digits);
            int firstNonZeroIndex = 0;

            if (digits[0] == '0') {
                for (int i = 1; i < digits.length; i++) {
                    if (digits[i] != '0') {
                        firstNonZeroIndex = i;
                        break;
                    }
                }
                swapFirstDigit(digits, firstNonZeroIndex);
            }

            String lowestNumberString = String.valueOf(digits);
            if (firstNonZeroIndex != 0) {
                swapFirstDigit(digits, firstNonZeroIndex);
            }
            StringBuilder lowestNumberSB = new StringBuilder(String.valueOf(digits));

            long lowestNumber = Long.parseLong(lowestNumberString);
            long highestNumber = Long.parseLong(lowestNumberSB.reverse().toString());

            long difference = highestNumber - lowestNumber;
            long k = difference / 9;

            outputWriter.printLine(String.format("%d - %d = %d = 9 * %d", highestNumber, lowestNumber, difference, k));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void swapFirstDigit(char[] digits, int index) {
        char aux = digits[0];
        digits[0] = digits[index];
        digits[index] = aux;
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
