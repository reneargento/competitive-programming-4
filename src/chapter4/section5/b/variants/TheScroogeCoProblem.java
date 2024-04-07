package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/04/24.
 */
public class TheScroogeCoProblem {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int locations = FastReader.nextInt();
            Map<String, Integer> locationNameToIDMap = new HashMap<>();
            String[] locationIDToName = new String[locations];
            int[][] distances = new int[locations][locations];
            for (int[] values : distances) {
                Arrays.fill(values, INFINITE);
            }

            for (int locationID = 0; locationID < locations; locationID++) {
                getLocationID(locationNameToIDMap, locationIDToName, FastReader.next());
            }

            for (int locationID1 = 0; locationID1 < locations; locationID1++) {
                for (int locationID2 = 0; locationID2 < locations; locationID2++) {
                    int cost = FastReader.nextInt();
                    if (cost >= 0) {
                        distances[locationID1][locationID2] = cost;
                    }
                }
            }
            FloydWarshall floydWarshall = new FloydWarshall(distances);

            int routes = FastReader.nextInt();
            for (int r = 0; r < routes; r++) {
                String employeeName = FastReader.next();
                String startLocationName = FastReader.next();
                String endLocationName = FastReader.next();
                int startLocationID = getLocationID(locationNameToIDMap, locationIDToName, startLocationName);
                int endLocationID = getLocationID(locationNameToIDMap, locationIDToName, endLocationName);

                if (floydWarshall.hasPath(startLocationID, endLocationID)) {
                    int minimumCost = floydWarshall.distance(startLocationID, endLocationID);
                    List<String> path = floydWarshall.path(startLocationID, endLocationID, locationIDToName);

                    outputWriter.printLine(String.format("Mr %s to go from %s to %s, you will receive %d euros",
                            employeeName, startLocationName, endLocationName, minimumCost));
                    outputWriter.print("Path:");
                    outputWriter.print(path.get(0));
                    for (int i = 1; i < path.size(); i++) {
                        outputWriter.print(" " + path.get(i));
                    }
                    outputWriter.printLine();
                } else {
                    outputWriter.printLine(String.format("Sorry Mr %s you can not go from %s to %s", employeeName,
                            startLocationName, endLocationName));
                }
            }
        }
        outputWriter.flush();
    }

    private static int getLocationID(Map<String, Integer> locationNameToIDMap, String[] locationIDToName,
                                     String locationName) {
        if (!locationNameToIDMap.containsKey(locationName)) {
            int locationID = locationNameToIDMap.size();
            locationNameToIDMap.put(locationName, locationID);
            locationIDToName[locationID] = locationName;
        }
        return locationNameToIDMap.get(locationName);
    }

    private static class DirectedEdge {
        private final int vertex1;
        private final int vertex2;
        private final int weight;

        public DirectedEdge(int vertex1, int vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public int weight() {
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
        private final int[][] distances;        // length of shortest v->w path
        private final DirectedEdge[][] edgeTo;  // last edge on shortest v->w path

        public FloydWarshall(int[][] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;
            distances = new int[vertices][vertices];
            edgeTo = new DirectedEdge[vertices][vertices];

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    int distance = edgeWeightedDigraph[vertex1][vertex2];
                    distances[vertex1][vertex2] = distance;
                    DirectedEdge edge = new DirectedEdge(vertex1, vertex2, distance);
                    edgeTo[vertex1][vertex2] = edge;
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

        public int distance(int source, int target) {
            return distances[source][target];
        }

        public List<String> path(int source, int target, String[] locationIDToName) {
            if (!hasPath(source, target)) {
                return null;
            }

            Deque<Integer> pathStack = new ArrayDeque<>();
            for (DirectedEdge edge = edgeTo[source][target]; edge != null; edge = edgeTo[source][edge.from()]) {
                pathStack.push(edge.to());

                if (edge.from() == source) {
                    break;
                }
            }
            pathStack.push(source);

            List<String> path = new ArrayList<>();
            while (!pathStack.isEmpty()) {
                int locationID = pathStack.poll();
                path.add(locationIDToName[locationID]);
            }
            return path;
        }

        public boolean hasPath(int source, int target) {
            return distances[source][target] != INFINITE;
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
