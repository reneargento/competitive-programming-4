package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/07/22.
 */
@SuppressWarnings("unchecked")
public class BankQueue {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int people = FastReader.nextInt();
        int minutes = FastReader.nextInt();
        PriorityQueue<Integer>[] peoplePriorityQueues = new PriorityQueue[minutes];

        for (int i = 0; i < peoplePriorityQueues.length; i++) {
            peoplePriorityQueues[i] = new PriorityQueue<>(Comparator.reverseOrder());
        }

        for (int i = 0; i < people; i++) {
            int money = FastReader.nextInt();
            int timeBeforeLeaving = FastReader.nextInt();
            peoplePriorityQueues[timeBeforeLeaving].add(money);
        }
        long maximumMoney = computeMaximumMoney(peoplePriorityQueues, minutes);
        outputWriter.printLine(maximumMoney);
        outputWriter.flush();
    }

    private static long computeMaximumMoney(PriorityQueue<Integer>[] peoplePriorityQueues, int minutes) {
        long maximumMoney = 0;
        PriorityQueue<Integer> selected = new PriorityQueue<>();

        for (int i = 0; i < peoplePriorityQueues.length; i++) {
            while (!peoplePriorityQueues[i].isEmpty()) {
                int money = peoplePriorityQueues[i].poll();
                int selectedSize = selected.size();

                if (selectedSize < minutes && selectedSize <= i) {
                    maximumMoney += money;
                    selected.offer(money);
                } else if (money > selected.peek()) {
                    maximumMoney -= selected.poll();
                    selected.offer(money);
                    maximumMoney += money;
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
