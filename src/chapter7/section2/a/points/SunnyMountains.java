package chapter7.section2.a.points;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/11/2026.
 */
public class SunnyMountains {

    private static class Coordinate implements Comparable<Coordinate> {
        double x;
        double y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Coordinate other) {
            return Double.compare(other.x, x);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Coordinate[] coordinates = new Coordinate[FastReader.nextInt()];
            for (int i = 0; i < coordinates.length; i++) {
                coordinates[i] = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            }

            double sunnyMountainsidesLength = computeSunnyMountainsides(coordinates);
            outputWriter.printLine(String.format("%.2f", sunnyMountainsidesLength));
        }
        outputWriter.flush();
    }

    private static double computeSunnyMountainsides(Coordinate[] coordinates) {
        if (coordinates.length <= 1) {
            return 0;
        }
        Arrays.sort(coordinates);
        double sunnyMountainsidesLength = distanceBetweenPoints(coordinates[0], coordinates[1]);
        double highestY = coordinates[1].y;

        for (int i = 2; i < coordinates.length; i++) {
            if (coordinates[i].y > highestY) {
                double coordinatesDistance = distanceBetweenPoints(coordinates[i - 1], coordinates[i]);
                double triangleHeight = coordinates[i].y - highestY;
                double totalHeight = coordinates[i].y - coordinates[i - 1].y;
                sunnyMountainsidesLength += coordinatesDistance * triangleHeight / totalHeight;
                highestY = coordinates[i].y;
            }
        }
        return sunnyMountainsidesLength;
    }

    private static double distanceBetweenPoints(Coordinate point1, Coordinate point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
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

        public void flush() {
            writer.flush();
        }
    }
}