package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/03/24.
 */
public class RoadConstruction {

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Result {
        int node1;
        int node2;

        public Result(int node1, int node2) {
            this.node1 = node1;
            this.node2 = node2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodes = FastReader.nextInt();
        int roads = FastReader.nextInt();

        while (nodes != 0 || roads != 0) {
            double[][] distances = new double[nodes][nodes];
            for (double[] values : distances) {
                Arrays.fill(values, Double.POSITIVE_INFINITY);
            }
            for (int nodeID = 0; nodeID < nodes; nodeID++) {
                distances[nodeID][nodeID] = 0;
            }

            Coordinate[] coordinates = new Coordinate[nodes];
            for (int i = 0; i < coordinates.length; i++) {
                coordinates[i] = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            }

            for (int r = 0; r < roads; r++) {
                int node1 = FastReader.nextInt() - 1;
                int node2 = FastReader.nextInt() - 1;
                double distance = distanceBetweenPoints(coordinates[node1], coordinates[node2]);
                distances[node1][node2] = distance;
                distances[node2][node1] = distance;
            }

            Result result = computeBestRoad(coordinates, distances);
            if (result == null) {
                outputWriter.printLine("No road required");
            } else {
                outputWriter.printLine(result.node1 + " " + result.node2);
            }

            nodes = FastReader.nextInt();
            roads = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeBestRoad(Coordinate[] coordinates, double[][] distances) {
        int bestNode1 = coordinates.length;
        int bestNode2 = coordinates.length;
        double bestDifference = 0;
        double bestDistance = Double.POSITIVE_INFINITY;
        FloydWarshall floydWarshall = new FloydWarshall(distances);

        for (int nodeID1 = 0; nodeID1 < coordinates.length; nodeID1++) {
            for (int nodeID2 = nodeID1 + 1; nodeID2 < coordinates.length; nodeID2++) {
                if (distances[nodeID1][nodeID2] == Double.POSITIVE_INFINITY) {
                    continue;
                }
                double totalCostDifference = 0;
                double newRoadDistance = distanceBetweenPoints(coordinates[nodeID1], coordinates[nodeID2]);

                for (int currentNode1 = 0; currentNode1 < coordinates.length; currentNode1++) {
                    for (int currentNode2 = currentNode1 + 1; currentNode2 < coordinates.length; currentNode2++) {
                        double currentDistance = floydWarshall.distance(currentNode1, currentNode2);
                        double newDistance1 = floydWarshall.distance(currentNode1, nodeID1) + newRoadDistance
                                + floydWarshall.distance(nodeID2, currentNode2);
                        double newDistance2 = floydWarshall.distance(currentNode2, nodeID1) + newRoadDistance
                                + floydWarshall.distance(nodeID2, currentNode1);
                        double minDistance1 = Math.min(currentDistance, newDistance1);
                        double minDistance2 = Math.min(minDistance1, newDistance2);

                        double costDifference = currentDistance - minDistance2;
                        totalCostDifference += costDifference;
                    }
                }

                if (totalCostDifference > bestDifference) {
                    bestDifference = totalCostDifference;
                    bestNode1 = nodeID1;
                    bestNode2 = nodeID2;
                    bestDistance = newRoadDistance;
                } else if (totalCostDifference == bestDifference) {
                    if (newRoadDistance < bestDistance) {
                        bestNode1 = nodeID1;
                        bestNode2 = nodeID2;
                        bestDistance = newRoadDistance;
                    } else if (newRoadDistance == bestDistance && nodeID1 < bestNode1 && nodeID2 < bestNode2) {
                        bestNode1 = nodeID1;
                        bestNode2 = nodeID2;
                        bestDistance = newRoadDistance;
                    }
                }
            }
        }

        if (bestDifference <= 1) {
            return null;
        }
        return new Result(bestNode1 + 1, bestNode2 + 1);
    }

    private static double distanceBetweenPoints(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.sqrt(Math.pow(coordinate1.x - coordinate2.x, 2) + Math.pow(coordinate1.y - coordinate2.y, 2));
    }

    private static class FloydWarshall {
        private final double[][] distances;

        public FloydWarshall(double[][] distances) {
            int vertices = distances.length;
            this.distances = distances;

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

        public double distance(int source, int target) {
            return distances[source][target];
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
