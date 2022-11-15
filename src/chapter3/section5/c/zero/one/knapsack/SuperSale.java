package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/11/22.
 */
public class SuperSale {

    private static class Item {
        int price;
        int weight;

        public Item(int price, int weight) {
            this.price = price;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Item[] items = new Item[FastReader.nextInt()];
            for (int i = 0; i < items.length; i++) {
                items[i] = new Item(FastReader.nextInt(), FastReader.nextInt());
            }

            int[] maxWeights = new int[FastReader.nextInt()];
            int highestMaxWeight = 0;
            for (int i = 0; i < maxWeights.length; i++) {
                maxWeights[i] = FastReader.nextInt();
                highestMaxWeight = Math.max(highestMaxWeight, maxWeights[i]);
            }

            int[] dp = knapsack(items, highestMaxWeight);
            int maxValue = 0;
            for (int maxWeight : maxWeights) {
                maxValue += dp[maxWeight];
            }
            outputWriter.printLine(maxValue);
        }
        outputWriter.flush();
    }

    private static int[] knapsack(Item[] items, int maxWeight) {
        int[] dp = new int[maxWeight + 1];

        for (int item = 1; item <= items.length; item++) {
            for (int weight = maxWeight; weight >= 0; weight--) {
                if (weight - items[item - 1].weight >= 0) {
                    int indexWithItem = weight - items[item - 1].weight;
                    int priceWithoutItem = dp[weight];
                    int priceWithItem = dp[indexWithItem] + items[item - 1].price;
                    dp[weight] = Math.max(priceWithoutItem, priceWithItem);
                }
            }
        }
        return dp;
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
