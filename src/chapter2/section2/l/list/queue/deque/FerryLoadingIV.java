package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/04/21.
 */
public class FerryLoadingIV {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int ferryLength = FastReader.nextInt() * 100;
            int cars = FastReader.nextInt();
            Queue<Integer> carsOnLeft = new LinkedList<>();
            Queue<Integer> carsOnRight = new LinkedList<>();

            for (int i = 0; i < cars; i++) {
                int length = FastReader.nextInt();
                String location = FastReader.next();

                if (location.equals("left")) {
                    carsOnLeft.offer(length);
                } else {
                    carsOnRight.offer(length);
                }
            }

            int timesCrossed = computeTimesCrossed(ferryLength, carsOnLeft, carsOnRight);
            outputWriter.printLine(timesCrossed);
        }
        outputWriter.flush();
    }

    private static int computeTimesCrossed(int ferryLength, Queue<Integer> carsOnLeft, Queue<Integer> carsOnRight) {
        int timesCrossed = 0;
        boolean isOnLeft = true;
        int currentLength = 0;

        while (!carsOnLeft.isEmpty() || !carsOnRight.isEmpty()) {
            if (isOnLeft) {
                while (!carsOnLeft.isEmpty() && currentLength <= ferryLength) {
                    if (currentLength + carsOnLeft.peek() <= ferryLength) {
                        currentLength += carsOnLeft.poll();
                    } else {
                        break;
                    }
                }
                isOnLeft = false;
            } else {
                while (!carsOnRight.isEmpty() && currentLength <= ferryLength) {
                    if (currentLength + carsOnRight.peek() <= ferryLength) {
                        currentLength += carsOnRight.poll();
                    } else {
                        break;
                    }
                }
                isOnLeft = true;
            }
            timesCrossed++;
            currentLength = 0;
        }
        return timesCrossed;
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
