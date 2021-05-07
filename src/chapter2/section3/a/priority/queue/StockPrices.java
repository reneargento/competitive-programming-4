package chapter2.section3.a.priority.queue;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/05/21.
 */
public class StockPrices {

    private static class Order {
        int quantity;
        int price;

        public Order(int quantity, int price) {
            this.quantity = quantity;
            this.price = price;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int orders = FastReader.nextInt();
            PriorityQueue<Order> buyPriorityQueue = new PriorityQueue<>(new Comparator<Order>() {
                @Override
                public int compare(Order order1, Order order2) {
                    return Integer.compare(order2.price, order1.price);
                }
            });
            PriorityQueue<Order> sellPriorityQueue = new PriorityQueue<>(new Comparator<Order>() {
                @Override
                public int compare(Order order1, Order order2) {
                    return Integer.compare(order1.price, order2.price);
                }
            });
            int stockPriceValue = -1;

            for (int i = 0; i < orders; i++) {
                String[] data = FastReader.getLine().split(" ");
                String type = data[0];
                int quantity = Integer.parseInt(data[1]);
                int price = Integer.parseInt(data[4]);

                if (type.equals("buy")) {
                    while (!sellPriorityQueue.isEmpty()
                            && sellPriorityQueue.peek().price <= price
                            && quantity > 0) {
                        Order sellOrder = sellPriorityQueue.poll();
                        stockPriceValue = sellOrder.price;
                        int sellOrderQuantity = sellOrder.quantity;

                        if (quantity < sellOrder.quantity) {
                            sellOrder.quantity -= quantity;
                            sellPriorityQueue.offer(sellOrder);
                        }
                        quantity -= sellOrderQuantity;
                    }

                    if (quantity > 0) {
                        buyPriorityQueue.offer(new Order(quantity, price));
                    }
                } else {
                    while (!buyPriorityQueue.isEmpty()
                            && buyPriorityQueue.peek().price >= price
                            && quantity > 0) {
                        Order buyOrder = buyPriorityQueue.poll();
                        stockPriceValue = price;
                        int buyOrderQuantity = buyOrder.quantity;

                        if (quantity < buyOrder.quantity) {
                            buyOrder.quantity -= quantity;
                            buyPriorityQueue.offer(buyOrder);
                        }
                        quantity -= buyOrderQuantity;
                    }

                    if (quantity > 0) {
                        sellPriorityQueue.offer(new Order(quantity, price));
                    }
                }

                String askPrice = "-";
                if (!sellPriorityQueue.isEmpty()) {
                    askPrice = String.valueOf(sellPriorityQueue.peek().price);
                }
                String bidPrice = "-";
                if (!buyPriorityQueue.isEmpty()) {
                    bidPrice = String.valueOf(buyPriorityQueue.peek().price);
                }
                String stockPrice = "-";
                if (stockPriceValue != -1) {
                    stockPrice = String.valueOf(stockPriceValue);
                }
                outputWriter.printLine(askPrice + " " + bidPrice + " " + stockPrice);
            }
        }
        outputWriter.flush();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
