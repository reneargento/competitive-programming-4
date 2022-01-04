package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class JustAnotherEasyProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int number = FastReader.nextInt();
            int waysToMakeSquare = countWaysToMakeSquare(number);
            outputWriter.printLine(String.format("Case %d: %d", t, waysToMakeSquare));
        }
        outputWriter.flush();
    }

    private static int countWaysToMakeSquare(int number) {
        int waysToMakeSquare = 0;
        int[] digits = getDigitsArray(number);

        for (int i = 0; i < digits.length; i++) {
            for (int digit = 0; digit <= 9; digit++) {
                if ((i == 0 && digit == 0)
                        || digits[i] == digit) {
                    continue;
                }

                int[] newDigits = new int[4];
                System.arraycopy(digits, 0, newDigits, 0, digits.length);
                newDigits[i] = digit;

                int newNumber = buildNumberFromDigits(newDigits);
                int sqrt = (int) Math.sqrt(newNumber);
                if (sqrt * sqrt == newNumber) {
                    waysToMakeSquare++;
                }
            }
        }
        return waysToMakeSquare;
    }

    private static int[] getDigitsArray(int number) {
        int[] digits = new int[4];
        int index = 3;

        while (number > 0) {
            digits[index] = number % 10;
            number /= 10;
            index--;
        }
        return digits;
    }

    private static int buildNumberFromDigits(int[] digits) {
        int number = 0;
        int multiplier = 1;

        for (int i = 3; i >= 0; i--) {
            int valueToAdd = digits[i] * multiplier;
            number += valueToAdd;
            multiplier *= 10;
        }
        return number;
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
