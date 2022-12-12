package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/12/22.
 */
public class Errands {

    private static class Location {
        String name;
        double x;
        double y;

        public Location(String name, double x, double y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Location[] locations = new Location[FastReader.nextInt()];
        Map<String, Integer> nameToIdMap = new HashMap<>();

        for (int i = 0; i < locations.length; i++) {
            locations[i] = new Location(FastReader.next(), FastReader.nextDouble(), FastReader.nextDouble());
            nameToIdMap.put(locations[i].name, i);
        }

        String line = FastReader.getLine();
        while (line != null) {
            String[] names = line.split(" ");
            int[] visitIds = getLocationVisitIds(names, nameToIdMap);
            int homeId = nameToIdMap.get("home");
            List<Integer> visitOrder = computeBestOrder(locations, visitIds, homeId);

            int firstLocationId = visitOrder.get(0);
            String firstLocationName = locations[firstLocationId].name;
            outputWriter.print(firstLocationName);
            for (int i = 1; i < visitOrder.size(); i++) {
                int locationId = visitOrder.get(i);
                String name = locations[locationId].name;
                outputWriter.print(" " + name);
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] getLocationVisitIds(String[] names, Map<String, Integer> nameToIdMap) {
        int[] visitIds = new int[names.length + 1];
        visitIds[0] = nameToIdMap.get("work");
        for (int i = 0; i < names.length; i++) {
            int id = nameToIdMap.get(names[i]);
            visitIds[i + 1] = id;
        }
        return visitIds;
    }

    private static List<Integer> computeBestOrder(Location[] locations, int[] visitIds, int homeId) {
        int bitmaskSize = 1 << visitIds.length;
        double[][] dp = new double[visitIds.length][bitmaskSize];
        for (double[] values : dp) {
            Arrays.fill(values, -1);
        }

        int bitmask = 1;
        computeDp(locations, visitIds, homeId, dp, 0, bitmask);
        return computeBestPath(locations, visitIds, dp);
    }

    private static double computeDp(Location[] locations, int[] visitIds, int homeId, double[][] dp,
                                    int currentLocationId, int bitmask) {
        if (bitmask == dp[0].length - 1) {
            int originalId = visitIds[currentLocationId];
            dp[currentLocationId][bitmask] = computeDistance(locations[originalId], locations[homeId]);
            return dp[currentLocationId][bitmask];
        }
        if (dp[currentLocationId][bitmask] != -1) {
            return dp[currentLocationId][bitmask];
        }

        int originalId = visitIds[currentLocationId];
        double bestDistance = Double.POSITIVE_INFINITY;
        for (int locationId = 1; locationId < visitIds.length; locationId++) {
            if ((bitmask & (1 << locationId)) == 0) {
                int nextBitmask = bitmask | (1 << locationId);
                int locationOriginalId = visitIds[locationId];
                double distance = computeDistance(locations[originalId], locations[locationOriginalId])
                        + computeDp(locations, visitIds, homeId, dp, locationId, nextBitmask);
                bestDistance = Math.min(bestDistance, distance);
            }
        }
        dp[currentLocationId][bitmask] = bestDistance;
        return dp[currentLocationId][bitmask];
    }

    private static List<Integer> computeBestPath(Location[] locations, int[] visitIds, double[][] dp) {
        List<Integer> path = new ArrayList<>();
        int currentLocationId = 0;
        int bitmask = 1;

        for (int size = 1; size < visitIds.length; size++) {
            int nextLocationId = -1;
            int nextBitmask = -1;
            int currentLocationOriginalId = visitIds[currentLocationId];
            for (int locationId = 1; locationId < visitIds.length; locationId++) {
                if (currentLocationId == locationId || (bitmask & (1 << locationId)) != 0) {
                    continue;
                }
                if (nextLocationId == -1) {
                    nextLocationId = locationId;
                    nextBitmask = bitmask | (1 << locationId);
                }

                int originalId1 = visitIds[nextLocationId];
                double distance1;
                if (dp[nextLocationId][nextBitmask] != -1) {
                    distance1 = computeDistance(locations[currentLocationOriginalId], locations[originalId1])
                            + dp[nextLocationId][nextBitmask];
                } else {
                    distance1 = Double.POSITIVE_INFINITY;
                }

                int originalId2 = visitIds[locationId];
                int alternativeBitmask = bitmask | (1 << locationId);
                double distance2;
                if (dp[locationId][alternativeBitmask] != -1) {
                    distance2 = computeDistance(locations[currentLocationOriginalId], locations[originalId2])
                            + dp[locationId][alternativeBitmask];
                } else {
                    distance2 = Double.POSITIVE_INFINITY;
                }

                if (distance2 < distance1) {
                    nextLocationId = locationId;
                    nextBitmask = alternativeBitmask;
                }
            }
            int nextLocationOriginalId = visitIds[nextLocationId];
            path.add(nextLocationOriginalId);
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
