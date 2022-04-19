package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/04/22.
 */
public class Reconnaissance {

    private static final double EPSILON = 0.0000000001;

    private static class Vehicle {
        int position;
        int velocity;

        public Vehicle(int position, int velocity) {
            this.position = position;
            this.velocity = velocity;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Vehicle[] vehicles = new Vehicle[FastReader.nextInt()];

        for (int i = 0; i < vehicles.length; i++) {
            vehicles[i] = new Vehicle(FastReader.nextInt(), FastReader.nextInt());
        }

        double minimumDistance = computeMinimumDistance(vehicles);
        outputWriter.printLine(minimumDistance);
        outputWriter.flush();
    }

    private static double computeMinimumDistance(Vehicle[] vehicles) {
        double low = 0;
        double high = 100000;
        double minimumDistance = Double.POSITIVE_INFINITY;

        while (low + EPSILON <= high) {
            double thirdLength = (high - low) / 3;
            double section1 = low + thirdLength;
            double section2 = high - thirdLength;

            double distance1 = computeDistanceAtTime(vehicles, section1);
            double distance2 = computeDistanceAtTime(vehicles, section2);

            if (distance1 < distance2) {
                high = section2;
                minimumDistance = Math.min(minimumDistance, distance1);
            } else {
                low = section1;
                minimumDistance = Math.min(minimumDistance, distance2);
            }
        }
        return minimumDistance;
    }

    private static double computeDistanceAtTime(Vehicle[] vehicles, double time) {
        double minimumPosition = Double.POSITIVE_INFINITY;
        double maximumPosition = Double.NEGATIVE_INFINITY;

        for (Vehicle vehicle : vehicles) {
            double position = vehicle.position + (time * vehicle.velocity);
            minimumPosition = Math.min(minimumPosition, position);
            maximumPosition = Math.max(maximumPosition, position);
        }
        return Math.abs(maximumPosition - minimumPosition);
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
