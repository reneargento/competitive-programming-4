package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/11/25.
 */
public class Bee {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int years = FastReader.nextInt();

        long[] fibonacci = computeFibonacci();

        while (years >= 0) {
            if (years == 0) {
                outputWriter.printLine("0 1");
            } else {
                long maleBees = fibonacci[years - 1] - 1;
                long totalBees = fibonacci[years] - 1;
                outputWriter.printLine(maleBees + " " + totalBees);
            }
            years = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long[] computeFibonacci() {
        long[] fibonacci = new long[45];
        fibonacci[0] = 2;
        fibonacci[1] = 3;

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
