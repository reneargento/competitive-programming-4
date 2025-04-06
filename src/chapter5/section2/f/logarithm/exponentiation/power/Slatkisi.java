package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/04/25.
 */
public class Slatkisi {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String price = FastReader.next();
        int multiple = FastReader.nextInt();

        String roundedNumber = roundNumber(price, multiple);
        outputWriter.printLine(roundedNumber);
        outputWriter.flush();
    }

    private static String roundNumber(String price, int multiple) {
        char[] digits = price.toCharArray();
        int indexToStart;
        if (multiple <= price.length()) {
            indexToStart = price.length() - multiple;
        } else {
            return "0";
        }

        boolean prepend1 = false;

        for (int i = indexToStart; i >= 0 && i < digits.length; i--) {
            int numericValue = Character.getNumericValue(digits[i]);

            if (i == indexToStart) {
                digits[i] = '0';

                if (numericValue <= 4) {
                    break;
                }
            } else {
                if (numericValue == 9) {
                    digits[i] = '0';
                } else {
                    digits[i] = (char) ('0' + (numericValue + 1));
                    break;
                }
            }

            if (i == 0) {
                prepend1 = true;
            }
        }

        for (int i = indexToStart + 1; i < digits.length; i++) {
            digits[i] = '0';
        }
        String roundedNumber = new String(digits);
        if (prepend1) {
            roundedNumber = "1" + roundedNumber;
        }
        return roundedNumber;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
