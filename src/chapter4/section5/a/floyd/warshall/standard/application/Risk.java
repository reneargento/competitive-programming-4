package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/03/24.
 */
public class Risk {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int testSetID = 1;

        while (line != null) {
            int[][] edgeWeightedDigraph = new int[20][20];
            for (int[] values : edgeWeightedDigraph) {
                Arrays.fill(values, INFINITE);
            }

            String[] data = line.split(" ");
            int firstBoundaries = Integer.parseInt(data[0]);
            for (int i = 0; i < firstBoundaries; i++) {
                int borderCountryID = Integer.parseInt(data[i + 1]) - 1;
                edgeWeightedDigraph[0][borderCountryID] = 1;
                edgeWeightedDigraph[borderCountryID][0] = 1;
            }

            for (int countryID = 1; countryID < edgeWeightedDigraph.length - 1; countryID++) {
                int boundaries = FastReader.nextInt();
                for (int i = 0; i < boundaries; i++) {
                    int borderCountryID = FastReader.nextInt() - 1;
                    edgeWeightedDigraph[countryID][borderCountryID] = 1;
                    edgeWeightedDigraph[borderCountryID][countryID] = 1;
                }
            }
            FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph);
            outputWriter.printLine(String.format("Test Set #%d", testSetID));

            int countryPairs = FastReader.nextInt();
            for (int i = 0; i < countryPairs; i++) {
                int countryID1 = FastReader.nextInt() - 1;
                int countryID2 = FastReader.nextInt() - 1;
                int minimumMoves = floydWarshall.distance(countryID1, countryID2);
                outputWriter.printLine(String.format("%2d to %2d: %d", countryID1 + 1, countryID2 + 1, minimumMoves));
            }

            outputWriter.printLine();
            testSetID++;
            line = FastReader.getLine();
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
