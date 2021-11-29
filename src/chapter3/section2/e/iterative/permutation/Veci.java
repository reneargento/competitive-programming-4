package chapter3.section2.e.iterative.permutation;

import java.io.*;

/**
 * Created by Rene Argento on 29/11/21.
 */
public class Veci {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char[] digits = FastReader.getLine().toCharArray();
        String number = new String(digits);
        String nextNumber = getNextNumber(digits, number);
        outputWriter.printLine(nextNumber);

        outputWriter.flush();
    }

    private static String getNextNumber(char[] digits, String number) {
        char[] currentDigits = new char[digits.length];
        String nextNumber = getNextNumber(digits, number, currentDigits, 0, 0);
        if (nextNumber == null) {
            return "0";
        }
        return nextNumber;
    }

    private static String getNextNumber(char[] digits, String number, char[] currentDigits, int index, int mask) {
        if (mask == (1 << digits.length) - 1) {
            String newNumber = new String(currentDigits);
            if (newNumber.compareTo(number) > 0) {
                return newNumber;
            } else {
                return null;
            }
        }

        String nextNumber = null;
        for (int i = 0; i < digits.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                currentDigits[index] = digits[i];
                String candidateNumber = getNextNumber(digits, number, currentDigits, index + 1, newMask);
                if (nextNumber == null
                        || (candidateNumber != null && candidateNumber.compareTo(nextNumber) < 0)) {
                    nextNumber = candidateNumber;
                }
            }
        }
        return nextNumber;
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
