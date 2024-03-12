package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/03/24.
 */
public class AvoidingYourBoss {

    private static final int INFINITE = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int places = Integer.parseInt(data[0]);
            int roads = Integer.parseInt(data[1]);
            int bossHome = Integer.parseInt(data[2]) - 1;
            int office = Integer.parseInt(data[3]) - 1;
            int home = Integer.parseInt(data[4]) - 1;
            int market = Integer.parseInt(data[5]) - 1;

            int[][] edgeWeightedDigraph = new int[places][places];
            for (int[] values : edgeWeightedDigraph) {
                Arrays.fill(values, INFINITE);
            }

            for (int r = 0; r < roads; r++) {
                int placeID1 = FastReader.nextInt() - 1;
                int placeID2 = FastReader.nextInt() - 1;
                int distance = FastReader.nextInt();
                edgeWeightedDigraph[placeID1][placeID2] = distance;
                edgeWeightedDigraph[placeID2][placeID1] = distance;
            }

            int travelCost = computeTravelCost(edgeWeightedDigraph, bossHome, office, home, market);
            if (travelCost == INFINITE) {
                outputWriter.printLine("MISSION IMPOSSIBLE.");
            } else {
                outputWriter.printLine(travelCost);
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeTravelCost(int[][] edgeWeightedDigraph, int bossHome, int office, int home, int market) {
        FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph, new HashSet<>());
        List<DirectedEdge> edgesInAllShortestPaths = floydWarshall.getEdgesInAllShortestPaths(bossHome, office);

        Set<Integer> verticesToSkip = new HashSet<>();
        for (DirectedEdge edge : edgesInAllShortestPaths) {
            verticesToSkip.add(edge.from());
            verticesToSkip.add(edge.to());
        }
        verticesToSkip.add(bossHome);
        verticesToSkip.add(office);

        FloydWarshall floydWarshallWithSkips = new FloydWarshall(edgeWeightedDigraph, verticesToSkip);
        return floydWarshallWithSkips.distance(home, market);
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
        private final List<DirectedEdge> edges;

        public FloydWarshall(int[][] edgeWeightedDigraph, Set<Integer> verticesToSkip) {
            int vertices = edgeWeightedDigraph.length;
            distances = new int[vertices][vertices];
            edgeTo = new DirectedEdge[vertices][vertices];
            edges = new ArrayList<>();

            for (int[] values : distances) {
                Arrays.fill(values, INFINITE);
            }

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    if (!verticesToSkip.contains(vertex1) && !verticesToSkip.contains(vertex2)) {
                        int distance = edgeWeightedDigraph[vertex1][vertex2];
                        distances[vertex1][vertex2] = distance;
                        DirectedEdge edge = new DirectedEdge(vertex1, vertex2, distance);
                        edgeTo[vertex1][vertex2] = edge;
                        edges.add(edge);
                    }
                }

                if (!verticesToSkip.contains(vertex1) && distances[vertex1][vertex1] >= 0) {
                    distances[vertex1][vertex1] = 0;
                    edgeTo[vertex1][vertex1] = null;
                }
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
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

        private List<DirectedEdge> getEdgesInAllShortestPaths(int source, int target) {
            List<DirectedEdge> edgesInAllShortestPaths = new ArrayList<>();

            for (DirectedEdge edge : edges) {
                double distanceThroughEdge = distances[source][edge.from()] + edge.weight() + distances[edge.to()][target];
                if (distanceThroughEdge == distances[source][target]) {
                    edgesInAllShortestPaths.add(edge);
                }
            }
            return edgesInAllShortestPaths;
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
