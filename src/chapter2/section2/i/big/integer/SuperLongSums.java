package chapter2.section2.i.big.integer;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/03/21.
 */
public class SuperLongSums {

    private static class Pair {
        int value1;
        int value2;

        public Pair(int value1, int value2) {
            this.value1 = value1;
            this.value2 = value2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            int digits = FastReader.nextInt();
            Pair[] numberPairs = new Pair[digits];

            for (int i = 0; i < digits; i++) {
                int number1 = FastReader.nextInt();
                int number2 = FastReader.nextInt();
                numberPairs[i] = new Pair(number1, number2);
            }

            String result = computeSum(numberPairs);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String computeSum(Pair[] numberPairs) {
        StringBuilder sumDigits = new StringBuilder();
        boolean hasCarryover = false;

        for (int i = numberPairs.length - 1; i >= 0; i--) {
            int sum = numberPairs[i].value1 + numberPairs[i].value2;
            if (hasCarryover) {
                sum++;
            }

            if (sum > 9) {
                hasCarryover = true;
                sum %= 10;
            } else {
                hasCarryover = false;
            }
            sumDigits.append(sum);
        }
        if (hasCarryover) {
            sumDigits.append("1");
        }
        return sumDigits.reverse().toString();
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
