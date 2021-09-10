package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/09/21.
 */
public class LastFactorialDigit {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] lastFactorialDigits = computeLastFactorialDigits();

        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            outputWriter.printLine(lastFactorialDigits[number]);
        }
        outputWriter.flush();
    }

    private static int[] computeLastFactorialDigits() {
        int[] lastFactorialDigits = new int[11];
        int number = 1;

        for (int i = 1; i <= 10; i++) {
            lastFactorialDigits[i] = number % 10;
            number *= (i + 1);
        }
        return lastFactorialDigits;
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
