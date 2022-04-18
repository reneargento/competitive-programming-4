package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/04/22.
 */
// Based on http://www.cs.ucf.edu/~dmarino/progcontests/mysols/worldfinals/2015/h.java
public class Qanat {

    private static class Result {
        double minimumExcavationCost;
        double[] shaftPositions;

        public Result(double minimumExcavationCost, double[] shaftPositions) {
            this.minimumExcavationCost = minimumExcavationCost;
            this.shaftPositions = shaftPositions;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int horizontalDistance = FastReader.nextInt();
        int verticalDistance = FastReader.nextInt();
        int shafts = FastReader.nextInt();

        Result result = computeMinimumExcavationCost(horizontalDistance, verticalDistance, shafts);
        outputWriter.printLine(String.format("%.6f", result.minimumExcavationCost));
        for (int i = 0; i < result.shaftPositions.length && i < 10; i++) {
            outputWriter.printLine(String.format("%.6f", result.shaftPositions[i]));
        }
        outputWriter.flush();
    }

    private static Result computeMinimumExcavationCost(int horizontalDistance, int verticalDistance, int shafts) {
        double[] locations = null;
        double[] costs = new double[shafts + 1];

        double ratio = verticalDistance / (double) horizontalDistance;
        // Calculate this by bisecting the path of length 1 and length ratio * (vertical).
        costs[0] = (1 + ratio) * (1 + ratio) / 4;

        // Iteratively calculate the answer for a higher number of vertical shafts.
        for (int k = 1; k <= shafts; k++) {
            double low = 0;
            double high = 1;

            while (low + 1e-7 <= high) {
                // Try the middle spot and a bit past it.
                double middle = low + (high - low) / 2;
                double middle2 = middle + 1e-9;

                double cost1 = getBestCostWithLastLocation(k, middle, costs, ratio);
                double cost2 = getBestCostWithLastLocation(k, middle2, costs, ratio);

                if (cost1 <= cost2) {
                    high = middle;
                } else {
                    low = middle;
                }
            }

            // Stop the ternary search early and sweep through here.
            double delta = (high - low) / 100;
            double bestLocation = low;
            double bestCost = getBestCostWithLastLocation(k, bestLocation, costs, ratio);
            for (int i = 1; i <= 100; i++) {
                double lastLocation = low + i * delta;
                double cost = getBestCostWithLastLocation(k, lastLocation, costs, ratio);
                if (cost < bestCost) {
                    bestCost = cost;
                    bestLocation = lastLocation;
                }
            }
            costs[k] = bestCost;

            // Scale all the rest by this factor.
            double[] aux = new double[k];
            for (int i = 0; i < k - 1; i++) {
                aux[i] = locations[i] * bestLocation + 1e-12;
            }
            aux[k - 1] = bestLocation + 1e-12;
            // Copy back
            locations = aux;
        }

        double minimumExcavationCost = costs[shafts] * horizontalDistance * horizontalDistance;
        double[] shaftPositions = new double[shafts];
        for (int i = 0; i < shafts && i < 10; i++) {
            shaftPositions[i] = locations[i] * horizontalDistance;
        }
        return new Result(minimumExcavationCost, shaftPositions);
    }

    private static double getBestCostWithLastLocation(int shafts, double lastLocation, double[] costs, double ratio) {
        // Middle point of the last trapezoid
        double middlePoint = (ratio * lastLocation + 1 - lastLocation + ratio) / 2;
        return costs[shafts - 1] * lastLocation * lastLocation + middlePoint * middlePoint -
                (lastLocation * ratio * lastLocation * ratio) / 2;
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
