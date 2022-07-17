package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/07/22.
 */
public class KeepTheCustomerSatisfied {

    private static class Order implements Comparable<Order> {
        int steel;
        int dueDate;

        public Order(int steel, int dueDate) {
            this.steel = steel;
            this.dueDate = dueDate;
        }

        @Override
        public int compareTo(Order other) {
            if (dueDate != other.dueDate) {
                return Integer.compare(dueDate, other.dueDate);
            }
            return Integer.compare(steel, other.steel);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            Order[] orders = new Order[FastReader.nextInt()];
            for (int i = 0; i < orders.length; i++) {
                orders[i] = new Order(FastReader.nextInt(), FastReader.nextInt());
            }
            int ordersAccepted = computeOrdersAccepted(orders);
            outputWriter.printLine(ordersAccepted);
        }
        outputWriter.flush();
    }

    private static int computeOrdersAccepted(Order[] orders) {
        Arrays.sort(orders);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        int time = 0;

        for (Order order : orders) {
            priorityQueue.offer(order.steel);
            time += order.steel;

            if (time > order.dueDate) {
                time -= priorityQueue.poll();
            }
        }
        return priorityQueue.size();
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
