package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/11/22.
 */
public class ShortSell {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] prices = new int[FastReader.nextInt()];
        int borrowCost = FastReader.nextInt();

        for (int i = 0; i < prices.length; i++) {
            prices[i] = FastReader.nextInt();
        }
        long maxProfit = computeMaxProfit(prices, borrowCost);
        outputWriter.printLine(maxProfit);
        outputWriter.flush();
    }

    private static long computeMaxProfit(int[] prices, int borrowCost) {
        long maxProfit = 0;
        long bestGain = 0;

        for (int price : prices) {
            bestGain = Math.max(bestGain - borrowCost, price * 100L);
            long profit = bestGain - (price * 100L) - borrowCost;
            maxProfit = Math.max(maxProfit, profit);
        }
        return maxProfit;
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
