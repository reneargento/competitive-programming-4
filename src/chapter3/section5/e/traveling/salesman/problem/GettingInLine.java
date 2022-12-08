package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/12/22.
 */
public class GettingInLine {

    private static class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Solution {
        List<Location> locationsToConnect;
        double totalLength;

        public Solution(List<Location> locationsToConnect, double totalLength) {
            this.locationsToConnect = locationsToConnect;
            this.totalLength = totalLength;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int computers = FastReader.nextInt();
        int networkId = 1;

        while (computers != 0) {
            Location[] locations = new Location[computers];
            for (int i = 0; i < locations.length; i++) {
                locations[i] = new Location(FastReader.nextInt(), FastReader.nextInt());
            }
            Solution solution = connectComputers(locations);

            outputWriter.printLine("**********************************************************");
            outputWriter.printLine(String.format("Network #%d", networkId));
            for (int i = 0; i < solution.locationsToConnect.size() - 1; i++) {
                Location location1 = solution.locationsToConnect.get(i);
                Location location2 = solution.locationsToConnect.get(i + 1);
                double distance = computeDistance(location1, location2) + 16;
                outputWriter.printLine(String.format("Cable requirement to connect (%d,%d) to (%d,%d) is %.2f feet.",
                        location1.x, location1.y, location2.x, location2.y, distance));
            }
            outputWriter.printLine(String.format("Number of feet of cable required is %.2f.", solution.totalLength));

            networkId++;
            computers = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int startLocationId;

    private static Solution connectComputers(Location[] locations) {
        int bitmaskSize = (int) Math.pow(2, locations.length);
        double[][] dp = new double[locations.length][bitmaskSize];
        for (double[] values : dp) {
            Arrays.fill(values, -1);
        }

        double connectedDistance = connectComputers(locations, dp, 0, 0);
        int additionalCable = 16 * (locations.length - 1);
        double minimumDistance = connectedDistance + additionalCable;

        List<Location> locationsToConnect = computePath(locations, dp);
        return new Solution(locationsToConnect, minimumDistance);
    }

    private static double connectComputers(Location[] locations, double[][] dp, int currentLocationId, int bitmask) {
        if (bitmask == dp[0].length - 1) {
            return 0;
        }
        if (dp[currentLocationId][bitmask] != -1) {
            return dp[currentLocationId][bitmask];
        }

        double minimumDistance = Double.POSITIVE_INFINITY;
        for (int locationId = 0; locationId < locations.length; locationId++) {
            if ((bitmask & (1 << locationId)) == 0) {
                int bitmaskWithLocation = bitmask | (1 << locationId);
                double nextDistance = 0;
                if (bitmask != 0) {
                    nextDistance = computeDistance(locations[currentLocationId], locations[locationId]);
                }

                double distance = nextDistance + connectComputers(locations, dp, locationId, bitmaskWithLocation);
                if (distance < minimumDistance) {
                    minimumDistance = distance;
                    if (bitmask == 0) {
                        startLocationId = locationId;
                    }
                }
            }
        }
        dp[currentLocationId][bitmask] = minimumDistance;
        return dp[currentLocationId][bitmask];
    }

    private static List<Location> computePath(Location[] locations, double[][] dp) {
        List<Location> path = new ArrayList<>();
        path.add(locations[startLocationId]);
        int currentLocationId = startLocationId;
        int bitmask = (1 << startLocationId);
        int nextBitmask = -1;

        for (int size = 0; size < locations.length - 1; size++) {
            int nextLocationId = -1;
            for (int locationId = 0; locationId < locations.length; locationId++) {
                if (nextLocationId == locationId || (bitmask & (1 << locationId)) != 0) {
                    continue;
                }
                if (nextLocationId == -1) {
                    nextLocationId = locationId;
                    nextBitmask = bitmask | (1 << locationId);
                }
                double distance1;
                if (dp[nextLocationId][nextBitmask] != -1) {
                    distance1 = computeDistance(locations[currentLocationId], locations[nextLocationId])
                            + dp[nextLocationId][nextBitmask];
                } else {
                    distance1 = Double.POSITIVE_INFINITY;
                }

                int alternativeBitmask = bitmask | (1 << locationId);
                double distance2;
                if (dp[locationId][alternativeBitmask] != -1) {
                    distance2 = computeDistance(locations[currentLocationId], locations[locationId])
                            + dp[locationId][alternativeBitmask];
                } else {
                    distance2 = Double.POSITIVE_INFINITY;
                }
                if (distance2 < distance1) {
                    nextLocationId = locationId;
                    nextBitmask = alternativeBitmask;
                }
            }
            path.add(locations[nextLocationId]);
            currentLocationId = nextLocationId;
            bitmask = nextBitmask;
        }
        return path;
    }

    private static double computeDistance(Location location1, Location location2) {
        return Math.sqrt(Math.pow(location1.x - location2.x, 2) + Math.pow(location1.y - location2.y, 2));
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
