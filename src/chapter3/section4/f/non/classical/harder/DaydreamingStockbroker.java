package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/08/22.
 */
public class DaydreamingStockbroker {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] prices = new int[FastReader.nextInt()];
        for (int i = 0; i < prices.length; i++) {
            prices[i] = FastReader.nextInt();
        }

        long maximumMoney = computeMaximumMoney(prices);
        outputWriter.printLine(maximumMoney);
        outputWriter.flush();
    }

    private static long computeMaximumMoney(int[] prices) {
        long stocksNumber = 0;
        long maximumMoney = 100;

        for (int i = 0; i < prices.length; i++) {
            maximumMoney += (prices[i] * stocksNumber);
            stocksNumber = 0;

            if (i < prices.length - 1
                    && prices[i] < prices[i + 1]) {
                if (maximumMoney > 0) {
                    long stocksToBuy = maximumMoney / prices[i];
                    stocksToBuy = Math.min(100000, stocksToBuy);

                    maximumMoney -= (prices[i] * stocksToBuy);
                    stocksNumber += stocksToBuy;
                }
            }
        }
        return maximumMoney;
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
