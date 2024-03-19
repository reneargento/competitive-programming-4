package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/03/24.
 */
public class ThunderMountain {

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            Coordinate[] towns = new Coordinate[FastReader.nextInt()];
            for (int i = 0; i < towns.length; i++) {
                towns[i] = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            }

            double maxTravelDistance = computeMaxTravelDistance(towns);
            outputWriter.printLine(String.format("Case #%d:", t));
            if (maxTravelDistance == -1) {
                outputWriter.printLine("Send Kurdy");
            } else {
                outputWriter.printLine(String.format("%.4f", maxTravelDistance));
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static double computeMaxTravelDistance(Coordinate[] towns) {
        double[][] distances = new double[towns.length][towns.length];
        for (double[] values : distances) {
            Arrays.fill(values, Double.POSITIVE_INFINITY);
        }
        for (int townID = 0; townID < distances.length; townID++) {
            distances[townID][townID] = 0;
        }

        for (int townID1 = 0; townID1 < towns.length; townID1++) {
            for (int townID2 = townID1 + 1; townID2 < towns.length; townID2++) {
                double distance = distanceBetweenPoints(towns[townID1], towns[townID2]);
                if (distance <= 10.0) {
                    distances[townID1][townID2] = distance;
                    distances[townID2][townID1] = distance;
                }
            }
        }

        floydWarshall(distances);
        return computeMaxDistance(distances);
    }

    private static double distanceBetweenPoints(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.sqrt(Math.pow(coordinate1.x - coordinate2.x, 2) + Math.pow(coordinate1.y - coordinate2.y, 2));
    }

    private static double computeMaxDistance(double[][] distances) {
        double maxDistance = -1;

        for (int townID1 = 0; townID1 < distances.length; townID1++) {
            for (int townID2 = townID1; townID2 < distances.length; townID2++) {
                double distance = distances[townID1][townID2];
                if (distance == Double.POSITIVE_INFINITY) {
                    return -1;
                }
                maxDistance = Math.max(maxDistance, distance);
            }
        }
        return roundValuePrecisionDigits(maxDistance, 4);
    }

    private static void floydWarshall(double[][] distances) {
        int vertices = distances.length;

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                    }
                }
            }
        }
    }

    private static double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
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
