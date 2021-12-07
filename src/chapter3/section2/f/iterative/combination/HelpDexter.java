package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/12/21.
 */
public class HelpDexter {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        long[] powersOf2 = computePowersOf2();

        for (int t = 1; t <= tests; t++) {
            int digits = FastReader.nextInt();
            long divisor = powersOf2[FastReader.nextInt()];
            List<Long> numbers = computeNumbers(digits, divisor);

            outputWriter.print(String.format("Case %d:", t));
            if (numbers.isEmpty()) {
                outputWriter.printLine(" impossible");
            } else {
                for (Long number : numbers) {
                    outputWriter.print(" " + number);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static long[] computePowersOf2() {
        long[] powersOf2 = new long[18];
        powersOf2[0] = 1;

        for (int i = 1; i < powersOf2.length; i++) {
            powersOf2[i] = powersOf2[i - 1] * 2;
        }
        return powersOf2;
    }

    private static List<Long> computeNumbers(int digits, long divisor) {
        char[] digitsValue = new char[digits];
        List<Long> numbers = new ArrayList<>();
        computeNumbers(digits, divisor, digitsValue, 0, numbers);

        if (numbers.size() > 2) {
            List<Long> selectedNumbers = new ArrayList<>();
            selectedNumbers.add(numbers.get(0));
            selectedNumbers.add(numbers.get(numbers.size() - 1));
            return selectedNumbers;
        }
        return numbers;
    }

    private static void computeNumbers(int digits, long divisor, char[] digitsValue,
                                       int index, List<Long> numbers) {
        if (index == digits) {
            long number = Long.parseLong(new String(digitsValue));
            if (number % divisor == 0) {
                numbers.add(number);
            }
            return;
        }

        digitsValue[index] = '1';
        computeNumbers(digits, divisor, digitsValue, index + 1, numbers);

        digitsValue[index] = '2';
        computeNumbers(digits, divisor, digitsValue, index + 1, numbers);
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
