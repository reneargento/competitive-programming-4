package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/01/23.
 */
// Based on https://github.com/morris821028/UVa/blob/master/volume006/662%20-%20Fast%20Food.cpp
public class FastFood {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int restaurants = FastReader.nextInt();
        int depots = FastReader.nextInt();
        int chainId = 1;

        while (restaurants != 0 || depots != 0) {
            int[] locations = new int[restaurants + 1];
            for (int i = 1; i < locations.length; i++) {
                locations[i] = FastReader.nextInt();
            }

            int[][] depotLocation = new int[locations.length][locations.length];
            // distancesDp[depotsLeft][endLocationId]
            int[][] distancesDp = new int[depots + 1][locations.length];
            // rangeStartLocationDp[depotsLeft][endLocationId]
            int[][] rangeStartLocationDp = new int[depots + 1][locations.length];

            computeOptimalPlacement(locations, depots, depotLocation, distancesDp, rangeStartLocationDp);

            outputWriter.printLine(String.format("Chain %d", chainId));
            printLocations(depotLocation, rangeStartLocationDp, outputWriter, locations.length - 1, depots);

            outputWriter.printLine(String.format("Total distance sum = %d", distancesDp[depots][locations.length - 1]));
            outputWriter.printLine();

            chainId++;
            restaurants = FastReader.nextInt();
            depots = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void computeOptimalPlacement(int[] locations, int depots, int[][] depotLocation, int[][] distancesDp,
                                                int[][] rangeStartLocationDp) {
        int[][] distances = new int[locations.length][locations.length];

        // Compute distances
        for (int distance = 0; distance < locations.length; distance++) {
            for (int startLocationId = 1; startLocationId + distance < locations.length; startLocationId++) {
                int endLocationId = startLocationId + distance;

                if (distance == 0) {
                    depotLocation[startLocationId][endLocationId] = startLocationId;
                } else if (distance == 1) {
                    distances[startLocationId][endLocationId] = locations[endLocationId] - locations[startLocationId];
                    depotLocation[startLocationId][endLocationId] = startLocationId;
                } else {
                    int middleIndex = (startLocationId + endLocationId) / 2;
                    distances[startLocationId][endLocationId] = distances[startLocationId + 1][endLocationId - 1];
                    distances[startLocationId][endLocationId] += (locations[middleIndex] - locations[startLocationId])
                            + (locations[endLocationId] - locations[middleIndex]);
                    depotLocation[startLocationId][endLocationId] = middleIndex;
                }
            }
        }

        // Place depots
        for (int depotsToPlace = 1; depotsToPlace <= depots; depotsToPlace++) {
            for (int locationId = depotsToPlace; locationId < locations.length; locationId++) {
                if (depotsToPlace == 1) {
                    distancesDp[depotsToPlace][locationId] = distances[1][locationId];
                    rangeStartLocationDp[depotsToPlace][locationId] = 1;
                    continue;
                }

                distancesDp[depotsToPlace][locationId] = INFINITE;
                for (int location = depotsToPlace - 1; location < locationId; location++) {
                    if (distancesDp[depotsToPlace][locationId] > distancesDp[depotsToPlace - 1][location] + distances[location + 1][locationId]) {
                        distancesDp[depotsToPlace][locationId] = distancesDp[depotsToPlace - 1][location] + distances[location + 1][locationId];
                        rangeStartLocationDp[depotsToPlace][locationId] = location + 1;
                    }
                }
            }
        }
    }

    private static void printLocations(int[][] depotLocation, int[][] rangeStartLocationDp, OutputWriter outputWriter,
                                       int endLocationId, int depotId) {
        if (depotId == 0) {
            return;
        }
        printLocations(depotLocation, rangeStartLocationDp, outputWriter,
                rangeStartLocationDp[depotId][endLocationId] - 1, depotId - 1);

        int startLocationId = rangeStartLocationDp[depotId][endLocationId];
        outputWriter.print(String.format("Depot %d at restaurant %d serves ", depotId,
                depotLocation[startLocationId][endLocationId]));
        if (startLocationId == endLocationId) {
            outputWriter.printLine(String.format("restaurant %d", startLocationId));
        } else {
            outputWriter.printLine(String.format("restaurants %d to %d", startLocationId, endLocationId));
        }
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
