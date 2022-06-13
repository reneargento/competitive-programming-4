package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/06/22.
 */
public class ShopaholicKattis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] prices = new int[FastReader.nextInt()];
        for (int i = 0; i < prices.length; i++) {
            prices[i] = FastReader.nextInt();
        }

        long maximumDiscount = computeMaximumDiscount(prices);
        outputWriter.printLine(maximumDiscount);
        outputWriter.flush();
    }

    private static long computeMaximumDiscount(int[] prices) {
        long maximumDiscount = 0;
        Arrays.sort(prices);

        for (int i = prices.length - 3; i >= 0; i -= 3) {
            maximumDiscount += prices[i];
        }
        return maximumDiscount;
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
