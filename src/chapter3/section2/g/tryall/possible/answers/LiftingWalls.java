package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/12/21.
 */
public class LiftingWalls {

    private static class Location {
        double x;
        double y;

        public Location(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class WallsReached {
        int locationId;
        boolean[] reachesWall;

        public WallsReached(int locationId, boolean[] reachesWall) {
            this.locationId = locationId;
            this.reachesWall = reachesWall;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WallsReached that = (WallsReached) o;
            return Arrays.equals(reachesWall, that.reachesWall);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(reachesWall);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int length = FastReader.nextInt();
        int width = FastReader.nextInt();
        Location[] locations = new Location[FastReader.nextInt()];
        int reachingDistance = FastReader.nextInt();

        for (int i = 0; i < locations.length; i++) {
            locations[i] = new Location(FastReader.nextInt(), FastReader.nextInt());
        }

        int minimumNumberOfCranes = computeMinimumNumberOfCranes(length, width, locations, reachingDistance);
        if (minimumNumberOfCranes == Integer.MAX_VALUE) {
            outputWriter.printLine("Impossible");
        } else {
            outputWriter.printLine(minimumNumberOfCranes);
        }
        outputWriter.flush();
    }

    private static int computeMinimumNumberOfCranes(int length, int width, Location[] locations,
                                                    int reachingDistance) {
        Location[] wallCenters = new Location[4];
        wallCenters[0] = new Location(-length / 2.0, 0);
        wallCenters[1] = new Location(length / 2.0, 0);
        wallCenters[2] = new Location(0, -width / 2.0);
        wallCenters[3] = new Location(0, width / 2.0);

        List<WallsReached> wallsReachedList = filterUniqueLocationsReach(locations, wallCenters, reachingDistance);
        return computeMinimumNumberOfCranes(wallsReachedList, 0, 0);
    }

    private static List<WallsReached> filterUniqueLocationsReach(Location[] locations, Location[] wallCenters,
                                                                int reachingDistance) {
        WallsReached[] wallsReachedArray = new WallsReached[locations.length];

        for (int i = 0; i < locations.length; i++) {
            Location location = locations[i];
            boolean[] reachesWall = new boolean[4];

            for (int j = 0; j < wallCenters.length; j++) {
                Location wallCenter = wallCenters[j];

                if (distanceSquared(location, wallCenter) <= reachingDistance * reachingDistance) {
                    reachesWall[j] = true;
                }
            }
            wallsReachedArray[i] = new WallsReached(i, reachesWall);
        }

        Set<WallsReached> wallsReachedSet = new HashSet<>(Arrays.asList(wallsReachedArray));
        return new ArrayList<>(wallsReachedSet);
    }

    private static int computeMinimumNumberOfCranes(List<WallsReached> wallsReachedList, int mask, int index) {
        if (index == wallsReachedList.size()) {
            if (canReachAllCenters(wallsReachedList, mask)) {
                return countSelectedLocations(mask);
            } else {
                return Integer.MAX_VALUE;
            }
        }

        int locationId = wallsReachedList.get(index).locationId;
        int maskWithCrane = mask | (1 << locationId);
        int cranesCountWithoutLocation = computeMinimumNumberOfCranes(wallsReachedList, mask, index + 1);
        int cranesCountWithLocation = computeMinimumNumberOfCranes(wallsReachedList, maskWithCrane, index + 1);
        return Math.min(cranesCountWithoutLocation, cranesCountWithLocation);
    }

    private static boolean canReachAllCenters(List<WallsReached> wallsReachedSet, int mask) {
        boolean[] totalReached = new boolean[4];
        int reachCount = 0;

        for (WallsReached location : wallsReachedSet) {
            if ((mask & (1 << location.locationId)) != 0) {
                boolean[] reachesWall = location.reachesWall;

                for (int i = 0; i < reachesWall.length; i++) {
                    if (reachesWall[i]) {
                        if (!totalReached[i]) {
                            totalReached[i] = true;
                            reachCount++;
                        }
                    }
                }
            }
        }
        return reachCount == 4;
    }

    private static int countSelectedLocations(int mask) {
        int selectedLocations = 0;

        while (mask > 0) {
            mask &= (mask - 1);
            selectedLocations++;
        }
        return selectedLocations;
    }

    private static double distanceSquared(Location location1, Location location2) {
        double xDistance = location1.x - location2.x;
        double yDistance = location1.y - location2.y;
        return xDistance * xDistance + yDistance * yDistance;
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
