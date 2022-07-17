package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/07/22.
 */
public class CanvasPainting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int canvasses = FastReader.nextInt();
            PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
            for (int i = 0; i < canvasses; i++) {
                priorityQueue.add(FastReader.nextLong());
            }

            long minimumInkNeeded = computeMinimumInkNeeded(priorityQueue);
            outputWriter.printLine(minimumInkNeeded);
        }
        outputWriter.flush();
    }

    private static long computeMinimumInkNeeded(PriorityQueue<Long> priorityQueue) {
        long minimumInkNeeded = 0;

        while (priorityQueue.size() >= 2) {
            long size1 = priorityQueue.poll();
            long size2 = priorityQueue.poll();
            long sum = size1 + size2;

            minimumInkNeeded += sum;
            priorityQueue.offer(sum);
        }
        return minimumInkNeeded;
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
