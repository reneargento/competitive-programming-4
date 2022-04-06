package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/04/22.
 */
public class SmallSchedule {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int longerBatchTime = FastReader.nextInt();
        int machines = FastReader.nextInt();
        int oneSecondSlots = FastReader.nextInt();
        int qSecondSlots = FastReader.nextInt();

        long smallestTime = computeSmallestTime(machines, longerBatchTime, oneSecondSlots, qSecondSlots);
        outputWriter.printLine(smallestTime);
        outputWriter.flush();
    }

    private static long computeSmallestTime(int machines, int longerBatchTime, int oneSecondSlots,
                                            int qSecondSlots) {
        long low = 0;
        long high = 1001000000;
        long smallestTime = Long.MAX_VALUE;

        while (low <= high) {
            long middle = low + (high - low) / 2;

            if (canAllocateAllSlotsInTime(machines, longerBatchTime, oneSecondSlots, qSecondSlots, middle)) {
                smallestTime = middle;
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return smallestTime;
    }

    private static boolean canAllocateAllSlotsInTime(int machines, int longerBatchTime, int oneSecondSlots,
                                                     int qSecondSlots, long time) {
        long oneSecondSlotsAllocated = 0;
        long qSecondSlotsAllocated = 0;

        for (int i = 0; i < machines; i++) {
            int timeAllocated = 0;

            long qSecondAllocations = time / longerBatchTime;
            long qSecondAllocationsRemaining = qSecondSlots - qSecondSlotsAllocated;
            qSecondAllocations = Math.min(qSecondAllocations, qSecondAllocationsRemaining);
            qSecondSlotsAllocated += qSecondAllocations;
            timeAllocated += qSecondAllocations * longerBatchTime;

            long oneSecondAllocations = time - timeAllocated;
            long oneSecondAllocationsRemaining = oneSecondSlots - oneSecondSlotsAllocated;
            oneSecondAllocations = Math.min(oneSecondAllocations, oneSecondAllocationsRemaining);
            oneSecondSlotsAllocated += oneSecondAllocations;
        }
        return oneSecondSlotsAllocated == oneSecondSlots && qSecondSlotsAllocated == qSecondSlots;
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
