package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/11/22.
 */
public class TroubleOf13Dots {

    private static class Item {
        int price;
        int favourIndex;

        public Item(int price, int favourIndex) {
            this.price = price;
            this.favourIndex = favourIndex;
        }
    }

    private static class Result {
        int maximumTotalFavourValue;
        int moneySpent;

        public Result(int maximumTotalFavourValue, int moneySpent) {
            this.maximumTotalFavourValue = maximumTotalFavourValue;
            this.moneySpent = moneySpent;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int money = Integer.parseInt(data[0]);
            int itemsNumber = Integer.parseInt(data[1]);

            Item[] items = new Item[itemsNumber];
            for (int i = 0; i < items.length; i++) {
                items[i] = new Item(FastReader.nextInt(), FastReader.nextInt());
            }

            Result result;
            if (money >= 1801) {
                result = computeMaximumTotalFavourValue(items, money + 200);
                if (result.moneySpent <= 2000) {
                    result = computeMaximumTotalFavourValue(items, money);
                }
            } else {
                result = computeMaximumTotalFavourValue(items, money);
            }
            outputWriter.printLine(result.maximumTotalFavourValue);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeMaximumTotalFavourValue(Item[] items, int money) {
        int[][] dp = new int[items.length + 1][money + 1];

        for (int itemId = 1; itemId < dp.length; itemId++) {
            for (int currentMoney = 1; currentMoney < dp[0].length; currentMoney++) {
                int favourValueWithoutItem = dp[itemId - 1][currentMoney];
                int favourValueWithItem = 0;
                if (currentMoney >= items[itemId - 1].price) {
                    favourValueWithItem = dp[itemId - 1][currentMoney - items[itemId - 1].price]
                            + items[itemId - 1].favourIndex;
                }
                dp[itemId][currentMoney] = Math.max(favourValueWithoutItem, favourValueWithItem);
            }
        }

        int moneySpent = 0;
        int currentMoney = money;
        for (int itemId = dp.length - 1; itemId > 0; itemId--) {
            if (dp[itemId][currentMoney] != dp[itemId - 1][currentMoney]) {
                moneySpent += items[itemId - 1].price;
                currentMoney -= items[itemId - 1].price;
            }
        }
        return new Result(dp[items.length][money], moneySpent);
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
