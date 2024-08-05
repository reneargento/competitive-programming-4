package chapter4.session6.e.bipartite.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/07/24.
 */
@SuppressWarnings("unchecked")
public class TheDogTask {

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

        for (int t = 0; t < tests; t++) {
            Coordinate[] routePoints = new Coordinate[FastReader.nextInt()];
            Coordinate[] interestingPlaces = new Coordinate[FastReader.nextInt()];

            readCoordinates(routePoints);
            readCoordinates(interestingPlaces);
            List<Coordinate> bestDogRoute = computeBestDogRoute(routePoints, interestingPlaces);

            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(bestDogRoute.size());
            outputWriter.print(bestDogRoute.get(0).x + " " + bestDogRoute.get(0).y);
            for (int i = 1; i < bestDogRoute.size(); i++) {
                Coordinate coordinate = bestDogRoute.get(i);
                outputWriter.print(" " + coordinate.x + " " + coordinate.y);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static void readCoordinates(Coordinate[] coordinates) throws IOException {
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
        }
    }

    private static List<Coordinate> computeBestDogRoute(Coordinate[] routePoints, Coordinate[] interestingPlaces) {
        List<Integer>[] adjacencyList = buildGraph(routePoints, interestingPlaces);
        int[] match = computeMaximumCardinality(adjacencyList, routePoints.length, adjacencyList.length);

        List<Coordinate> bestDogRoute = new ArrayList<>();
        for (int vertexId = 0; vertexId < routePoints.length - 1; vertexId++) {
            bestDogRoute.add(routePoints[vertexId]);

            if (match[vertexId + 1] != -1) {
                int interestingPlaceId = match[vertexId + 1] - routePoints.length;
                bestDogRoute.add(interestingPlaces[interestingPlaceId]);
            }
        }
        bestDogRoute.add(routePoints[routePoints.length - 1]);
        return bestDogRoute;
    }

    private static List<Integer>[] buildGraph(Coordinate[] routePoints, Coordinate[] interestingPlaces) {
        List<Integer>[] adjacencyList = new List[routePoints.length + interestingPlaces.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int routePointId = 0; routePointId < routePoints.length - 1; routePointId++) {
            double distanceToNextPoint = distanceBetweenPoints(routePoints[routePointId], routePoints[routePointId + 1]);

            for (int interestingPlaceId = 0; interestingPlaceId < interestingPlaces.length; interestingPlaceId++) {
                double distanceFromRoutePoints =
                        distanceBetweenPoints(routePoints[routePointId], interestingPlaces[interestingPlaceId]) +
                                distanceBetweenPoints(interestingPlaces[interestingPlaceId], routePoints[routePointId + 1]);
                if (distanceFromRoutePoints <= distanceToNextPoint * 2) {
                    int vertexId = routePoints.length + interestingPlaceId;
                    adjacencyList[routePointId + 1].add(vertexId);
                    adjacencyList[vertexId].add(routePointId + 1);
                }
            }
        }
        return adjacencyList;
    }

    private static int[] computeMaximumCardinality(List<Integer>[] adjacencyList, int startVertexIDs, int endVertexIds) {
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID = startVertexIDs; vertexID < endVertexIds; vertexID++) {
            boolean[] visited = new boolean[adjacencyList.length];
            tryToMatch(adjacencyList, match, visited, vertexID);
        }
        return match;
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, boolean[] visited, int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (match[neighbor] == -1 || tryToMatch(adjacencyList, match, visited, match[neighbor]) == 1) {
                match[neighbor] = vertexID; // flip status
                return 1;
            }
        }
        return 0;
    }

    public static double distanceBetweenPoints(Coordinate point1, Coordinate point2) {
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
