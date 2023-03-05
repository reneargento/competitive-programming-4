package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/03/23.
 */
@SuppressWarnings("unchecked")
public class MonitoringTheAmazon {

    private static final double EPSILON = 0.0000000001;

    private static class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        double distance(Location other) {
            return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int stations = FastReader.nextInt();

        while (stations != 0) {
            Location[] locations = new Location[stations];
            for (int i = 0; i < locations.length; i++) {
                locations[i] = new Location(FastReader.nextInt(), FastReader.nextInt());
            }

            boolean canReachAllStations = canReachAllStations(locations);
            outputWriter.printLine(canReachAllStations ? "All stations are reachable."
                    : "There are stations that are unreachable.");
            stations = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean canReachAllStations(Location[] locations) {
        List<Integer>[] adjacencyList = buildGraph(locations);
        boolean[] visited = new boolean[locations.length];
        depthFirstSearch(adjacencyList, visited, 0);

        for (int vertex = 0; vertex < locations.length; vertex++) {
            if (!visited[vertex]) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer>[] buildGraph(Location[] locations) {
        List<Integer>[] adjacencyList = new List[locations.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < locations.length; i++) {
            Location closestLocation = null;
            Location secondClosestLocation = null;
            int closestLocationID = 0;
            int secondClosestLocationID = 0;
            double closestDistance = Double.POSITIVE_INFINITY;
            double secondClosestDistance = Double.POSITIVE_INFINITY;

            for (int j = 0; j < locations.length; j++) {
                if (i == j) {
                    continue;
                }
                double distance = locations[i].distance(locations[j]);
                if (distance - closestDistance < EPSILON
                        || (Math.abs(distance - closestDistance) < EPSILON
                             && isClosest(closestLocation, locations[j]))) {
                    secondClosestDistance = closestDistance;
                    secondClosestLocation = closestLocation;
                    secondClosestLocationID = closestLocationID;

                    closestLocation = locations[j];
                    closestDistance = distance;
                    closestLocationID = j;
                } else if (distance - secondClosestDistance < EPSILON
                        || (Math.abs(distance - secondClosestDistance) < EPSILON
                             && isClosest(secondClosestLocation, locations[j]))) {
                    secondClosestLocation = locations[j];
                    secondClosestDistance = distance;
                    secondClosestLocationID = j;
                }
            }
            adjacencyList[i].add(closestLocationID);
            adjacencyList[i].add(secondClosestLocationID);
        }
        return adjacencyList;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int vertex) {
        visited[vertex] = true;
        for (int neighbor : adjacencyList[vertex]) {
            if (!visited[neighbor]) {
                depthFirstSearch(adjacencyList, visited, neighbor);
            }
        }
    }

    private static boolean isClosest(Location currentLocation, Location candidateLocation) {
        return candidateLocation.x < currentLocation.x
                || (candidateLocation.x == currentLocation.x && candidateLocation.y < currentLocation.y);
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
