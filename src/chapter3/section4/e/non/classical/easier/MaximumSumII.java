package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/07/22.
 */
public class MaximumSumII {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numbers = FastReader.nextInt();

        while (numbers != 0) {
            boolean isFirstValue = true;

            for (int i = 0; i < numbers; i++) {
                int value = FastReader.nextInt();
                if (value > 0) {
                    if (isFirstValue) {
                        outputWriter.print(value);
                        isFirstValue = false;
                    } else {
                        outputWriter.print(" " + value);
                    }
                }
            }

            if (isFirstValue) {
                outputWriter.printLine("0");
            } else {
                outputWriter.printLine();
            }
            numbers = FastReader.nextInt();
        }
        outputWriter.flush();
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
