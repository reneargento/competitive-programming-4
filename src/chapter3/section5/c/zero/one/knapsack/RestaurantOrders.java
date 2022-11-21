package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 15/11/22.
 */
public class RestaurantOrders {

    private static class OrderItems {
        String status;
        List<Integer> indexes;

        public OrderItems(String status, List<Integer> indexes) {
            this.status = status;
            this.indexes = indexes;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] costs = new int[FastReader.nextInt()];
        for (int i = 0; i < costs.length; i++) {
            costs[i] = FastReader.nextInt();
        }
        long[] dp = computeItemsDp(costs, 30000);

        int orders = FastReader.nextInt();
        for (int i = 0; i < orders; i++) {
            int order = FastReader.nextInt();
            OrderItems orderItems = computeOrderItems(order, costs, dp);

            if (!orderItems.status.equals("Ok")) {
                outputWriter.printLine(orderItems.status);
            } else {
                List<Integer> indexes = orderItems.indexes;
                outputWriter.print(indexes.get(0));
                for (int j = 1; j < indexes.size(); j++) {
                    outputWriter.print(" " + indexes.get(j));
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static long[] computeItemsDp(int[] costs, int targetExchange) {
        long[] dp = new long[targetExchange + 1];

        // Base case - for 0 exchange, there is 1 solution (no coins)
        dp[0] = 1;

        for (int coinValue : costs) {
            for (int currentSum = coinValue; currentSum <= targetExchange; currentSum++) {
                dp[currentSum] += dp[currentSum - coinValue];
            }
        }
        return dp;
    }

    private static OrderItems computeOrderItems(int order, int[] costs, long[] dp) {
        if (dp[order] == 0) {
            return new OrderItems("Impossible", null);
        }
        if (dp[order] > 1) {
            return new OrderItems("Ambiguous", null);
        }

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < costs.length; i++) {
            while (order >= costs[i] && dp[order - costs[i]] == 1) {
                indexes.add(i + 1);
                order -= costs[i];
            }
        }
        return new OrderItems("Ok", indexes);
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
