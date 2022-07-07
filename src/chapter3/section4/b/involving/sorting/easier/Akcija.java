package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/07/22.
 */
public class Akcija {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] bookPrices = new int[FastReader.nextInt()];

        for (int i = 0; i < bookPrices.length; i++) {
            bookPrices[i] = FastReader.nextInt();
        }
        long minimalPrice = computeMinimalPrice(bookPrices);
        outputWriter.printLine(minimalPrice);
        outputWriter.flush();
    }

    private static long computeMinimalPrice(int[] bookPrices) {
        long minimalPrice = 0;
        Arrays.sort(bookPrices);
        int bookComputed = 0;

        for (int i = bookPrices.length - 1; i >= 0; i--) {
            bookComputed++;
            if (bookComputed % 3 == 0) {
                continue;
            }
            minimalPrice += bookPrices[i];
        }
        return minimalPrice;
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
