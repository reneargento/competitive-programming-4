package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/07/22.
 */
public class APrizeNoOneCanWin {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] prices = new int[FastReader.nextInt()];
        int minimumCost = FastReader.nextInt();

        for (int i = 0; i < prices.length; i++) {
            prices[i] = FastReader.nextInt();
        }
        int maxItemsInPromotion = computeMaxItemsInPromotion(prices, minimumCost);
        outputWriter.printLine(maxItemsInPromotion);
        outputWriter.flush();
    }

    private static int computeMaxItemsInPromotion(int[] prices, int minimumCost) {
        int maxItemsInPromotion = 1;
        Arrays.sort(prices);

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] + prices[i - 1] <= minimumCost) {
                maxItemsInPromotion++;
            }
        }
        return maxItemsInPromotion;
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
