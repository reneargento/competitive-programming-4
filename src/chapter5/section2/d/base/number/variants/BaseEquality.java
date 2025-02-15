package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/02/25.
 */
public class BaseEquality {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int base1 = FastReader.nextInt();
            int base2 = FastReader.nextInt();
            int low = FastReader.nextInt();
            int high = FastReader.nextInt();

            int highestValue = computeLargestValue(base1, base2, low, high);
            if (highestValue == -1) {
                outputWriter.printLine("Non-existent.");
            } else {
                outputWriter.printLine(highestValue);
            }
        }
        outputWriter.flush();
    }

    private static int computeLargestValue(int base1, int base2, int low, int high) {
        for (int value = high - 1; value > low; --value) {
            List<Integer> digitsBase1 = convertNumber(value, base1);
            try {
                long convertedBase2 = convertFromDigits(digitsBase1, base2);
                if (convertedBase2 % value == 0) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Ignore exception
            }
        }
        return -1;
    }

    private static List<Integer> convertNumber(int number, int radix) {
        List<Integer> digits = new ArrayList<>();

        while (number > 0) {
            int digit = number % radix;
            digits.add(digit);
            number /= radix;
        }
        return digits;
    }

    private static long convertFromDigits(List<Integer> digits, int radix) {
        long convertedNumber = 0;
        long multiplier = 1;

        for (int digit : digits) {
            if (digit >= radix) {
                throw new NumberFormatException();
            }
            convertedNumber += digit * multiplier;
            multiplier *= radix;
        }
        return convertedNumber;
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
