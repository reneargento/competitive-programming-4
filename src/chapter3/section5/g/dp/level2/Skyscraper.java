package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/01/23.
 */
public class Skyscraper {

    private static class Order implements Comparable<Order> {
        int lowestFloor;
        int highestFloor;
        int moneyEarned;

        public Order(int lowestFloor, int numberOfFloors, int moneyEarned) {
            this.lowestFloor = lowestFloor;
            this.highestFloor = lowestFloor + numberOfFloors - 1;
            this.moneyEarned = moneyEarned;
        }

        @Override
        public int compareTo(Order other) {
            return Integer.compare(lowestFloor, other.lowestFloor);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            Order[] orders = new Order[FastReader.nextInt()];
            for (int i = 0; i < orders.length; i++) {
                orders[i] = new Order(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            }
            int maximumProfit = computeMaximumProfit(orders);
            outputWriter.printLine(String.format("Case %d: %d", t, maximumProfit));
        }
        outputWriter.flush();
    }

    private static int computeMaximumProfit(Order[] orders) {
        // dp[orderID] = maximum profit
        int[] dp = new int[orders.length];
        Arrays.fill(dp, -1);
        Arrays.sort(orders);
        return computeMaximumProfit(orders, dp, 0);
    }

    private static int computeMaximumProfit(Order[] orders, int[] dp, int orderIndex) {
        if (orderIndex == orders.length) {
            return 0;
        }
        if (dp[orderIndex] != -1) {
            return dp[orderIndex];
        }
        int profitWithoutOrder = computeMaximumProfit(orders, dp, orderIndex + 1);

        int nextOrderIndex = ceilIndex(orders, orderIndex + 1, orders[orderIndex].highestFloor + 1);
        int profitWithOrder = orders[orderIndex].moneyEarned + computeMaximumProfit(orders, dp, nextOrderIndex);
        dp[orderIndex] = Math.max(profitWithoutOrder, profitWithOrder);
        return dp[orderIndex];
    }

    private static int ceilIndex(Order[] orders, int low, int floor) {
        if (floor > orders[orders.length - 1].lowestFloor) {
            return orders.length;
        }
        int high = orders.length - 1;

        while (high > low) {
            int middle = low + (high - low) / 2;

            if (orders[middle].lowestFloor >= floor) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
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
