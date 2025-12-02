package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/12/25.
 */
public class HyperBox {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        long[] fibonacci = computeFibonacci();

        for (int t = 1; t <= tests; t++) {
            int[] lengths = new int[FastReader.nextInt()];
            for (int i = 0; i < lengths.length; i++) {
                lengths[i] = FastReader.nextInt();
            }

            long hyperBricks = computeHyperBricks(fibonacci, lengths);
            outputWriter.printLine(String.format("Case %d: %d", t, hyperBricks));
        }
        outputWriter.flush();
    }

    private static long computeHyperBricks(long[] fibonacci, int[] lengths) {
        long hyperBricks = 1;

        for (long length : lengths) {
            int bricksUsed = 0;

            for (int i = fibonacci.length - 1; i >= 0; i--) {
                if (fibonacci[i] <= length) {
                    length -= fibonacci[i];
                    bricksUsed++;

                    if (length == 0) {
                        break;
                    }
                }
            }
            hyperBricks *= bricksUsed;
        }
        return hyperBricks;
    }

    private static long[] computeFibonacci() {
        long[] fibonacci = new long[50];
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
