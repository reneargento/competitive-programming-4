package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/03/24.
 */
public class TransportationPlanning {

    private static class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Result {
        int intersectionToConnect1;
        int intersectionToConnect2;
        double originalTime;
        double improvedTime;

        public Result(int intersectionToConnect1, int intersectionToConnect2, double originalTime, double improvedTime) {
            this.intersectionToConnect1 = intersectionToConnect1;
            this.intersectionToConnect2 = intersectionToConnect2;
            this.originalTime = originalTime;
            this.improvedTime = improvedTime;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int intersections = inputReader.nextInt();

        while (intersections != 0) {
            Location[] locations = new Location[intersections];
            for (int i = 0; i < locations.length; i++) {
                locations[i] = new Location(inputReader.nextInt(), inputReader.nextInt());
            }
            double[][] edgeWeightedDigraph = new double[intersections][intersections];
            for (double[] values : edgeWeightedDigraph) {
                Arrays.fill(values, Double.POSITIVE_INFINITY);
            }
            for (int locationID = 0; locationID < edgeWeightedDigraph.length; locationID++) {
                edgeWeightedDigraph[locationID][locationID] = 0;
            }

            int roads = inputReader.nextInt();
            for (int i = 0; i < roads; i++) {
                int locationID1 = inputReader.nextInt();
                int locationID2 = inputReader.nextInt();
                double commuteTime = distanceBetweenPoints(locations[locationID1], locations[locationID2]);
                edgeWeightedDigraph[locationID1][locationID2] = commuteTime;
                edgeWeightedDigraph[locationID2][locationID1] = commuteTime;
            }

            Result result = computeIntersectionsToConnect(edgeWeightedDigraph, locations, roads);
            if (result.intersectionToConnect1 == -1) {
                outputWriter.printLine("no addition reduces " + result.originalTime);
            } else {
                outputWriter.printLine(String.format("adding %d %d reduces %f to %f",
                        result.intersectionToConnect1,
                        result.intersectionToConnect2,
                        result.originalTime,
                        result.improvedTime));
            }
            intersections = inputReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeIntersectionsToConnect(double[][] edgeWeightedDigraph, Location[] locations,
                                                        int roads) {
        FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph);
        double originalTime = computeCommuteTime(floydWarshall);
        int locationsNumber = edgeWeightedDigraph.length;

        if (roads == (locationsNumber * (locationsNumber - 1)) / 2) {
            return new Result(-1, -1, originalTime, -1);
        }

        double lowestTime = Double.POSITIVE_INFINITY;
        int locationIDToConnect1 = -1;
        int locationIDToConnect2 = -1;

        for (int locationID1 = 0; locationID1 < locationsNumber; locationID1++) {
            for (int locationID2 = locationID1 + 1; locationID2 < locationsNumber; locationID2++) {
                if (edgeWeightedDigraph[locationID1][locationID2] == Double.POSITIVE_INFINITY) {
                    double distance = distanceBetweenPoints(locations[locationID1], locations[locationID2]);
                    double totalTime = 0;
                    for (int currentLocationID1 = 0; currentLocationID1 < locationsNumber; currentLocationID1++) {
                        for (int currentLocationID2 = 0; currentLocationID2 < locationsNumber; currentLocationID2++) {
                            double timeDirect = floydWarshall.distance(currentLocationID1, currentLocationID2);
                            double timeWithEdge1 = floydWarshall.distance(currentLocationID1, locationID1)
                                    + distance + floydWarshall.distance(locationID2, currentLocationID2);
                            double timeWithEdge2 = floydWarshall.distance(currentLocationID1, locationID2)
                                    + distance + floydWarshall.distance(locationID1, currentLocationID2);
                            double minimumTime1 = Math.min(timeDirect, timeWithEdge1);
                            double minimumTime = Math.min(minimumTime1, timeWithEdge2);
                            totalTime += minimumTime;
                        }
                    }

                    if (totalTime < lowestTime) {
                        lowestTime = totalTime;
                        locationIDToConnect1 = locationID1;
                        locationIDToConnect2 = locationID2;
                    }
                }
            }
        }
        return new Result(locationIDToConnect1, locationIDToConnect2, originalTime, lowestTime / 2.0);
    }

    private static double computeCommuteTime(FloydWarshall floydWarshall) {
        double commuteTime = 0;
        int locations = floydWarshall.distances.length;

        for (int locationID1 = 0; locationID1 < locations; locationID1++) {
            for (int locationID2 = 0; locationID2 < locations; locationID2++) {
                commuteTime += floydWarshall.distance(locationID1, locationID2);
            }
        }
        return commuteTime / 2.0;
    }

    private static double distanceBetweenPoints(Location point1, Location point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }

    private static class DirectedEdge {
        private final int vertex1;
        private final int vertex2;
        private final double weight;

        public DirectedEdge(int vertex1, int vertex2, double weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public double weight() {
            return weight;
        }

        public int from() {
            return vertex1;
        }

        public int to() {
            return vertex2;
        }
    }

    private static class FloydWarshall {
        private final double[][] distances;     // length of shortest v->w path
        private final DirectedEdge[][] edgeTo;  // last edge on shortest v->w path

        public FloydWarshall(double[][] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;
            distances = new double[vertices][vertices];
            edgeTo = new DirectedEdge[vertices][vertices];

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    double distance = edgeWeightedDigraph[vertex1][vertex2];
                    distances[vertex1][vertex2] = distance;
                    DirectedEdge edge = new DirectedEdge(vertex1, vertex2, distance);
                    edgeTo[vertex1][vertex2] = edge;
                }

                // In case of self-loops
                if (distances[vertex1][vertex1] >= 0.0) {
                    distances[vertex1][vertex1] = 0.0;
                    edgeTo[vertex1][vertex1] = null;
                }
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    if (edgeTo[vertex2][vertex1] == null) {
                        continue;  // optimization
                    }

                    for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                        if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                            edgeTo[vertex2][vertex3] = edgeTo[vertex1][vertex3];
                        }
                    }
                }
            }
        }

        public double distance(int source, int target) {
            return distances[source][target];
        }

        public Iterable<DirectedEdge> path(int source, int target) {
            if (!hasPath(source, target)) {
                return null;
            }

            Deque<DirectedEdge> path = new ArrayDeque<>();
            for (DirectedEdge edge = edgeTo[source][target]; edge != null; edge = edgeTo[source][edge.from()]) {
                path.push(edge);
            }
            return path;
        }

        public boolean hasPath(int source, int target) {
            return distances[source][target] != Double.POSITIVE_INFINITY;
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
