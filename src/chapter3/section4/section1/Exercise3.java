package chapter3.section4.section1;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/05/22.
 */
// Based on https://blog.myungwoo.kr/87
public class Exercise3 {

    private static class Event implements Comparable<Event> {
        long quantity;
        double probability;

        public Event(long quantity, double probability) {
            this.quantity = quantity;
            this.probability = probability;
        }

        @Override
        public int compareTo(Event other) {
            return Double.compare(probability, other.probability);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int observations = FastReader.nextInt();
        double[] probabilities = new double[4];

        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = FastReader.nextDouble();
        }
        double minimalExpectedBits = computeMinimalExpectedBits(observations, probabilities);
        outputWriter.printLine(String.format("%.9f", minimalExpectedBits));
        outputWriter.flush();
    }

    private static double computeMinimalExpectedBits(int observations, double[] probabilities) {
        long[] totalEvents = new long[observations + 1];
        totalEvents[0] = 1;
        for (int i = 1; i < totalEvents.length; i++) {
            totalEvents[i] = totalEvents[i - 1] * i;
        }

        PriorityQueue<Event> priorityQueue = buildPriorityQueue(observations, probabilities, totalEvents);
        double minimalExpectedBits = 0;
        while (!priorityQueue.isEmpty()) {
            Event minProbabilityEvent = priorityQueue.poll();
            if (minProbabilityEvent.quantity == 1) {
                if (priorityQueue.isEmpty()) {
                    break;
                }
                Event event2 = priorityQueue.poll();
                if (event2.quantity > 1) {
                    long newValue = event2.quantity - 1;
                    priorityQueue.offer(new Event(newValue, event2.probability));
                }
                double combinedProbabilities = minProbabilityEvent.probability + event2.probability;
                priorityQueue.offer(new Event(1, combinedProbabilities));
                minimalExpectedBits += combinedProbabilities;
            } else {
                long newQuantity = minProbabilityEvent.quantity / 2;
                double newProbability = minProbabilityEvent.probability * 2;
                priorityQueue.offer(new Event(newQuantity, newProbability));
                minimalExpectedBits += newQuantity * newProbability;

                if (minProbabilityEvent.quantity % 2 == 1) {
                    priorityQueue.offer(new Event(1, minProbabilityEvent.probability));
                }
            }
        }
        return minimalExpectedBits;
    }

    private static PriorityQueue<Event> buildPriorityQueue(int observations, double[] probabilities,
                                                          long[] totalEvents) {
        PriorityQueue<Event> priorityQueue = new PriorityQueue<>();

        for (int a = 0; a <= observations; a++) {
            for (int b = 0; a + b <= observations; b++) {
                for (int c = 0; a + b + c <= observations; c++) {
                    int d = observations - a - b - c;
                    int[] array = { a, b, c, d };

                    double probability = 1;
                    for (int i = 0; i < array.length; i++) {
                        for (int j = 0; j < array[i]; j++) {
                            probability *= probabilities[i];
                        }
                    }
                    long quantity = totalEvents[observations] / totalEvents[a] / totalEvents[b]
                            / totalEvents[c] / totalEvents[d];
                    priorityQueue.offer(new Event(quantity, probability));
                }
            }
        }
        return priorityQueue;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
