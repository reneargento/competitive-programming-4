package chapter2.section3.i.order.statistics.tree;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/06/21.
 */
public class ContinuousMedian {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int numbers = FastReader.nextInt();
            PriorityQueue<Integer> maxPriorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
            PriorityQueue<Integer> minPriorityQueue = new PriorityQueue<>();
            long medianSum = 0;

            for (int i = 0; i < numbers; i++) {
                int number = FastReader.nextInt();

                if (!maxPriorityQueue.isEmpty() && number <= maxPriorityQueue.peek()) {
                    maxPriorityQueue.offer(number);
                } else {
                    minPriorityQueue.offer(number);
                }

                if (maxPriorityQueue.size() > minPriorityQueue.size() + 1) {
                    int elementToTransfer = maxPriorityQueue.poll();
                    minPriorityQueue.offer(elementToTransfer);
                } else if (minPriorityQueue.size() > maxPriorityQueue.size() + 1) {
                    int elementToTransfer = minPriorityQueue.poll();
                    maxPriorityQueue.offer(elementToTransfer);
                }

                if (maxPriorityQueue.size() > minPriorityQueue.size()) {
                    medianSum += maxPriorityQueue.peek();
                } else if (minPriorityQueue.size() > maxPriorityQueue.size()) {
                    medianSum += minPriorityQueue.peek();
                } else {
                    medianSum += (maxPriorityQueue.peek() + minPriorityQueue.peek()) / 2;
                }
            }
            outputWriter.printLine(medianSum);
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
