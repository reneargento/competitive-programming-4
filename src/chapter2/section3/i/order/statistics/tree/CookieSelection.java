package chapter2.section3.i.order.statistics.tree;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Rene Argento on 12/06/21.
 */
public class CookieSelection {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        PriorityQueue<Integer> maxPriorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> minPriorityQueue = new PriorityQueue<>();
        String line = FastReader.getLine();

        while (line != null) {
            if (line.charAt(0) == '#') {
                int selectedCookie;
                if (maxPriorityQueue.size() > minPriorityQueue.size()) {
                    selectedCookie = maxPriorityQueue.poll();
                } else {
                    selectedCookie = minPriorityQueue.poll();
                }
                outputWriter.printLine(selectedCookie);
            } else {
                int cookieDiameter = Integer.parseInt(line);

                if (!maxPriorityQueue.isEmpty() && cookieDiameter <= maxPriorityQueue.peek()) {
                    maxPriorityQueue.offer(cookieDiameter);
                } else {
                    minPriorityQueue.offer(cookieDiameter);
                }

                if (maxPriorityQueue.size() > minPriorityQueue.size() + 1) {
                    int cookieToTransfer = maxPriorityQueue.poll();
                    minPriorityQueue.offer(cookieToTransfer);
                } else if (minPriorityQueue.size() > maxPriorityQueue.size() + 1) {
                    int cookieToTransfer = minPriorityQueue.poll();
                    maxPriorityQueue.offer(cookieToTransfer);
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
