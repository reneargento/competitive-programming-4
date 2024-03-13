package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/03/24.
 */
public class AllPairsShortestPath {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodes = FastReader.nextInt();
        int edges = FastReader.nextInt();
        int queries = FastReader.nextInt();

        while (nodes != 0 || edges != 0 || queries != 0) {
            int[][] edgeWeightedDigraph = new int[nodes][nodes];
            for (int[] values : edgeWeightedDigraph) {
                Arrays.fill(values, INFINITE);
            }

            for (int m = 0; m < edges; m++) {
                int nodeID1 = FastReader.nextInt();
                int nodeID2 = FastReader.nextInt();
                int weight = FastReader.nextInt();
                int currentWeight = edgeWeightedDigraph[nodeID1][nodeID2];
                edgeWeightedDigraph[nodeID1][nodeID2] = Math.min(currentWeight, weight);
            }
            FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph);

            for (int q = 0; q < queries; q++) {
                int sourceNode = FastReader.nextInt();
                int targetNode = FastReader.nextInt();

                if (floydWarshall.goesThroughNegativeCycle(sourceNode, targetNode)) {
                    outputWriter.printLine("-Infinity");
                } else if (!floydWarshall.hasPath(sourceNode, targetNode)) {
                    outputWriter.printLine("Impossible");
                } else {
                    outputWriter.printLine(floydWarshall.distances[sourceNode][targetNode]);
                }
            }
            nodes = FastReader.nextInt();
            edges = FastReader.nextInt();
            queries = FastReader.nextInt();
            outputWriter.printLine();
        }
        outputWriter.flush();
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
        private final Set<Integer> verticesInNegativeCycle;

        public FloydWarshall(int[][] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;
            distances = new int[vertices][vertices];
            edgeTo = new DirectedEdge[vertices][vertices];
            verticesInNegativeCycle = new HashSet<>();

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    int distance = edgeWeightedDigraph[vertex1][vertex2];
                    distances[vertex1][vertex2] = distance;
                    DirectedEdge edge = new DirectedEdge(vertex1, vertex2, distance);
                    edgeTo[vertex1][vertex2] = edge;
                }

                // In case of self-loops
                if (distances[vertex1][vertex1] >= 0) {
                    distances[vertex1][vertex1] = 0;
                    edgeTo[vertex1][vertex1] = null;
                }
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    if (edgeTo[vertex2][vertex1] == null) {
                        continue;  // optimization
                    }

                    for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                        if (distances[vertex2][vertex1] != INFINITE &&
                                distances[vertex1][vertex3] != INFINITE &&
                                distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                            edgeTo[vertex2][vertex3] = edgeTo[vertex1][vertex3];
                        }
                    }

                    if (distances[vertex2][vertex2] < 0.0) {
                        verticesInNegativeCycle.add(vertex2);
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
            return distances[source][target] != INFINITE;
        }

        public boolean goesThroughNegativeCycle(int source, int target) {
            for (int vertexInNegativeCycle : verticesInNegativeCycle) {
                if (hasPath(source, vertexInNegativeCycle)
                        && hasPath(vertexInNegativeCycle, target)) {
                    return true;
                }
            }
            return false;
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
