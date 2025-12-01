package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/11/25.
 */
public class FiBinaryNumber {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        long[] fibonacci = computeFibonacci();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            String fibinaryNumber = getFibinaryNumber(fibonacci, number);
            outputWriter.printLine(fibinaryNumber);
        }
        outputWriter.flush();
    }

    private static String getFibinaryNumber(long[] fibonacci, long number) {
        StringBuilder fibinaryNumber = new StringBuilder();
        boolean numberStarted = false;

        for (int i = fibonacci.length - 1; i >= 0; i--) {
            if (fibonacci[i] <= number) {
                number -= fibonacci[i];
                numberStarted = true;
                fibinaryNumber.append("1");
            } else if (numberStarted) {
                fibinaryNumber.append("0");
            }
        }
        return fibinaryNumber.toString();
    }

    private static long[] computeFibonacci() {
        long[] fibonacci = new long[45];
        fibonacci[0] = 1;
        fibonacci[1] = 2;

        for (int i = 2; i < fibonacci.length; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }
        return fibonacci;
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
