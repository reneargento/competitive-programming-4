package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/01/22.
 */
public class SuperNumber {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int multipleDigits = FastReader.nextInt();
            int totalDigits = FastReader.nextInt();

            int[] number = computeNumber(multipleDigits, totalDigits);
            outputWriter.print(String.format("Case %d: ", t));
            if (number == null) {
                outputWriter.printLine("-1");
            } else {
                for (int i = 0; i < totalDigits; i++) {
                    outputWriter.print(number[i + 1]);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static int[] computeNumber(int multipleDigits, int totalDigits) {
        int[] number = new int[totalDigits + 1];
        return computeNumber(multipleDigits, totalDigits, number, 1);
    }

    private static int[] computeNumber(int multipleDigits, int totalDigits, int[] number, int index) {
        if (index == totalDigits + 1) {
            return number;
        }

        int startDigit = 0;
        if (index == 1) {
            startDigit = 1;
        }

        for (int digit = startDigit; digit <= 9; digit++) {
            if (index >= multipleDigits) {
                if (!isDivisible(number, index, digit)) {
                    continue;
                }
            }
            number[index] = digit;

            int[] finalNumber = computeNumber(multipleDigits, totalDigits, number, index + 1);
            if (finalNumber != null) {
                return finalNumber;
            }
        }
        return null;
    }

    private static boolean isDivisible(int[] number, int length, int lastDigit) {
        int currentNumber = 0;

        for (int i = 1; i < length; i++) {
            currentNumber = currentNumber * 10 + number[i];
            currentNumber %= length;
        }
        currentNumber = currentNumber * 10 + lastDigit;

        return currentNumber % length == 0;
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
