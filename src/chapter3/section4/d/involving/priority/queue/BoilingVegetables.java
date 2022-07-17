package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/07/22.
 */
public class BoilingVegetables {

    private static class Vegetable implements Comparable<Vegetable> {
        double currentWeight;
        double totalWeight;
        int cuts;

        public Vegetable(double totalWeight) {
            this.totalWeight = totalWeight;
            currentWeight = totalWeight;
            cuts = 1;
        }

        @Override
        public int compareTo(Vegetable other) {
            return Double.compare(other.currentWeight, currentWeight);
        }
    }

    private static final double EPSILON = .0001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        double targetRatio = FastReader.nextDouble();
        int numberOfWeights = FastReader.nextInt();
        double minimumWeight = Double.MAX_VALUE;
        PriorityQueue<Vegetable> weights = new PriorityQueue<>();

        for (int i = 0; i < numberOfWeights; i++) {
            double weight = FastReader.nextDouble();
            weights.add(new Vegetable(weight));
            minimumWeight = Math.min(minimumWeight, weight);
        }
        if (numberOfWeights == 0) {
            outputWriter.printLine(0);
        } else {
            int minimumCutsNeeded = computeMinimumCutsNeeded(targetRatio, weights, minimumWeight);
            outputWriter.printLine(minimumCutsNeeded);
        }
        outputWriter.flush();
    }

    private static int computeMinimumCutsNeeded(double targetRatio, PriorityQueue<Vegetable> weights,
                                                double minimumWeight) {
        int minimumCutsNeeded = 0;

        while (true) {
            double currentRatio = minimumWeight / weights.peek().currentWeight;
            if (currentRatio + EPSILON - targetRatio >= 0) {
                break;
            }
            Vegetable vegetable = weights.poll();
            vegetable.cuts++;
            vegetable.currentWeight = vegetable.totalWeight / vegetable.cuts;

            weights.offer(vegetable);
            minimumWeight = Math.min(minimumWeight, vegetable.currentWeight);
            minimumCutsNeeded++;
        }
        return minimumCutsNeeded;
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
