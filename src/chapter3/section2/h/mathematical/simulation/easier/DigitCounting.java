package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/12/21.
 */
public class DigitCounting {

    private static class DigitCount {
        int[] digits;

        public DigitCount(int[] digits) {
            this.digits = digits;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        DigitCount[] digitsCount = countDigits();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            DigitCount count = digitsCount[number];

            for (int i = 0; i < count.digits.length; i++) {
                int value = count.digits[i];
                outputWriter.print(value);

                if (i != count.digits.length - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static DigitCount[] countDigits() {
        DigitCount[] digitsCount = new DigitCount[10000];
        int[] previousDigits = new int[10];

        for (int number = 1; number < digitsCount.length; number++) {
            int[] digits = countDigits(number, previousDigits);
            digitsCount[number] = new DigitCount(digits);
            previousDigits = digits;
        }
        return digitsCount;
    }

    private static int[] countDigits(int number, int[] previousDigits) {
        int[] digits = new int[10];
        System.arraycopy(previousDigits, 0, digits, 0, previousDigits.length);

        while (number > 0) {
            int digit = number % 10;
            digits[digit]++;
            number /= 10;
        }
        return digits;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
