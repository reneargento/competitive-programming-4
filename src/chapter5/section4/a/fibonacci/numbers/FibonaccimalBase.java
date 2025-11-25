package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/11/25.
 */
public class FibonaccimalBase {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        int[] fibonacci = computeFibonacci();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            String fibonaccimal = convertToFibanaccimal(fibonacci, number);
            outputWriter.printLine(String.format("%d = %s (fib)", number, fibonaccimal));
        }
        outputWriter.flush();
    }

    private static String convertToFibanaccimal(int[] fibonacci, int number) {
        StringBuilder fibonaccimal = new StringBuilder();
        boolean startedConvertion = false;

        for (int i = fibonacci.length - 1; i >= 1; i--) {
            if (fibonacci[i] <= number) {
                fibonaccimal.append("1");
                number -= fibonacci[i];
                startedConvertion = true;
            } else {
                if (startedConvertion) {
                    fibonaccimal.append("0");
                }
            }
        }
        return fibonaccimal.toString();
    }

    private static int[] computeFibonacci() {
        int[] fibonacci = new int[40];
        fibonacci[1] = 1;
        fibonacci[2] = 2;

        for (int i = 3; i < fibonacci.length; i++) {
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
