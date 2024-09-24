package chapter4.section6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/06/24.
 */
public class AdventuresInMovingPartIVUVa {

    private static class GasStation {
        int distance;
        int price;

        public GasStation(int distance, int price) {
            this.distance = distance;
            this.price = price;
        }
    }

    private static final int INFINITE = 100000000;
    private static final int TANK_CAPACITY = 200;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int totalDistance = FastReader.nextInt();
            List<GasStation> gasStations = new ArrayList<>();

            String line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");
                int distance = Integer.parseInt(data[0]);
                int price = Integer.parseInt(data[1]);
                gasStations.add(new GasStation(distance, price));

                line = FastReader.getLine();
            }

            int tripMinimumPrice = computeTripMinimumPrice(gasStations, totalDistance);
            if (t > 0) {
                outputWriter.printLine();
            }
            if (tripMinimumPrice == INFINITE) {
                outputWriter.printLine("Impossible");
            } else {
                outputWriter.printLine(tripMinimumPrice);
            }
        }
        outputWriter.flush();
    }

    private static int computeTripMinimumPrice(List<GasStation> gasStations, int totalDistance) {
        // dp[gas station id][current liters]
        int[][] dp = new int[gasStations.size()][TANK_CAPACITY + 1];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        if (gasStations.size() == 0) {
            if (totalDistance == 0) {
                return 0;
            } else {
                return INFINITE;
            }
        }
        if (gasStations.get(0).distance > 100) {
            return INFINITE;
        }
        int startLiters = 100 - gasStations.get(0).distance;
        int startRemainingDistance = totalDistance - gasStations.get(0).distance;

        return computeTripMinimumPrice(gasStations, dp, totalDistance, startRemainingDistance, 0, startLiters);
    }

    private static int computeTripMinimumPrice(List<GasStation> gasStations, int[][] dp, int totalDistance,
                                               int remainingDistance, int gasStationId, int currentLiters) {
        if (gasStationId == gasStations.size() && remainingDistance <= 0 && currentLiters >= 100) {
            return 0;
        }
        if (gasStationId == gasStations.size() || currentLiters == dp[0].length) {
            return INFINITE;
        }
        if (dp[gasStationId][currentLiters] != -1) {
            return dp[gasStationId][currentLiters];
        }

        int minimumPrice = INFINITE;
        GasStation gasStation = gasStations.get(gasStationId);
        int possibleLitersToFill = TANK_CAPACITY - currentLiters;

        int distanceToNextPoint;
        if (gasStationId != gasStations.size() - 1) {
            distanceToNextPoint = gasStations.get(gasStationId + 1).distance - gasStation.distance;
        } else {
            distanceToNextPoint = totalDistance - gasStation.distance;
        }

        for (int liters = 0; liters <= possibleLitersToFill; liters++) {
            if (currentLiters + liters >= distanceToNextPoint) {
                int nextLiters = currentLiters + liters - distanceToNextPoint;

                int price = liters * gasStation.price +
                        computeTripMinimumPrice(gasStations, dp, totalDistance,
                                remainingDistance - distanceToNextPoint,
                                gasStationId + 1, nextLiters);
                minimumPrice = Math.min(minimumPrice, price);
            }
        }

        dp[gasStationId][currentLiters] = minimumPrice;
        return minimumPrice;
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

        public void flush() {
            writer.flush();
        }
    }
}
