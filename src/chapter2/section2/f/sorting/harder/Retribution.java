package chapter2.section2.f.sorting.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/02/21.
 */
public class Retribution {

    private static class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class LocationDistance implements Comparable<LocationDistance> {
        int judgeId;
        int locationId;
        double distance;

        public LocationDistance(int judgeId, int locationId, double distance) {
            this.judgeId = judgeId;
            this.locationId = locationId;
            this.distance = distance;
        }

        @Override
        public int compareTo(LocationDistance other) {
            return Double.compare(distance, other.distance);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int judgesNumber = FastReader.nextInt();
        int tarRepositoriesNumber = FastReader.nextInt();
        int featherStorehousesNumber = FastReader.nextInt();

        Location[] judges = new Location[judgesNumber];
        readLocations(judges);
        Location[] tarRepositories = new Location[tarRepositoriesNumber];
        readLocations(tarRepositories);
        Location[] featherStorehouses = new Location[featherStorehousesNumber];
        readLocations(featherStorehouses);

        double minimumDistance = getMinimumDistance(judges, tarRepositories);
        minimumDistance += getMinimumDistance(judges, featherStorehouses);

        System.out.println(minimumDistance);
    }

    private static void readLocations(Location[] locations) throws IOException {
        for (int i = 0; i < locations.length; i++) {
            int x = FastReader.nextInt();
            int y = FastReader.nextInt();
            locations[i] = new Location(x, y);
        }
    }

    private static double getMinimumDistance(Location[] judges, Location[] locations) {
        LocationDistance[] locationDistances = computeDistances(judges, locations);
        Arrays.sort(locationDistances);
        return matchAndGetDistances(locationDistances, judges.length);
    }

    private static LocationDistance[] computeDistances(Location[] judges, Location[] locations) {
        LocationDistance[] locationDistances = new LocationDistance[judges.length * locations.length];
        int distancesIndex = 0;

        for (int i = 0; i < judges.length; i++) {
            for (int j = 0; j < locations.length; j++) {
                double distance = distance(judges[i], locations[j]);
                locationDistances[distancesIndex++] = new LocationDistance(i, j, distance);
            }
        }
        return locationDistances;
    }

    private static double matchAndGetDistances(LocationDistance[] locationDistances, int judgesNumber) {
        double totalDistance = 0;
        Set<Integer> assignedJudges = new HashSet<>();
        Set<Integer> assignedLocations = new HashSet<>();

        for (LocationDistance locationDistance : locationDistances) {
            int judgeId = locationDistance.judgeId;
            int locationId = locationDistance.locationId;

            if (assignedJudges.contains(judgeId) || assignedLocations.contains(locationId)) {
                continue;
            }
            totalDistance += locationDistance.distance;
            assignedJudges.add(judgeId);
            assignedLocations.add(locationId);

            if (assignedJudges.size() == judgesNumber) {
                break;
            }
        }
        return totalDistance;
    }

    private static double distance(Location location1, Location location2) {
        return Math.sqrt(Math.pow(location1.x - location2.x, 2) + (Math.pow(location1.y - location2.y, 2)));
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
}
