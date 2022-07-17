package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/07/22.
 */
public class AddAll {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numbers = FastReader.nextInt();

        while (numbers != 0) {
            PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
            for (int i = 0; i < numbers; i++) {
                priorityQueue.offer(FastReader.nextLong());
            }
            long totalSumCost = computeTotalSumCost(priorityQueue);
            outputWriter.printLine(totalSumCost);
            numbers = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeTotalSumCost(PriorityQueue<Long> priorityQueue) {
        long totalSumCost = 0;

        while (priorityQueue.size() >= 2) {
            long value1 = priorityQueue.poll();
            long value2 = priorityQueue.poll();
            long sum = value1 + value2;
            totalSumCost += sum;
            priorityQueue.offer(sum);
        }
        return totalSumCost;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
