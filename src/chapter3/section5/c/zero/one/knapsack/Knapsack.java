package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/11/22.
 */
public class Knapsack {

    private static class Item {
        int value;
        int weight;

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int capacity = Integer.parseInt(data[0]);
            int numberOfItems = Integer.parseInt(data[1]);
            Item[] items = new Item[numberOfItems];
            for (int i = 0; i < items.length; i++) {
                items[i] = new Item(FastReader.nextInt(), FastReader.nextInt());
            }

            List<Integer> selectedItems = knapsack(items, capacity);
            outputWriter.printLine(selectedItems.size());
            if (!selectedItems.isEmpty()) {
                outputWriter.print(selectedItems.get(0));
                for (int i = 1; i < selectedItems.size(); i++) {
                    outputWriter.print(" " + selectedItems.get(i));
                }
                outputWriter.printLine();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> knapsack(Item[] items, int capacity) {
        List<Integer> selectedItems = new ArrayList<>();
        int[][] dp = new int[items.length + 1][capacity + 1];

        for (int item = 1; item < dp.length; item++) {
            for (int weight = 1; weight <= capacity; weight++) {
                int valueWithoutItem = dp[item - 1][weight];
                int valueWithItem = 0;
                if (weight >= items[item - 1].weight) {
                    valueWithItem = dp[item - 1][weight - items[item - 1].weight] + items[item - 1].value;
                }
                dp[item][weight] = Math.max(valueWithoutItem, valueWithItem);
            }
        }

        int weightRemaining = capacity;
        for (int item = dp.length - 1; item > 0; item--) {
            if (dp[item][weightRemaining] != dp[item - 1][weightRemaining]) {
                selectedItems.add(item - 1);
                weightRemaining -= items[item - 1].weight;
            }
        }
        return selectedItems;
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
