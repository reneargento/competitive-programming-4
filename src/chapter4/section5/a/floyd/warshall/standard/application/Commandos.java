package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/03/24.
 */
public class Commandos {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int buildings = FastReader.nextInt();
            int[][] edgeWeightedDigraph = new int[buildings][buildings];
            for (int[] values : edgeWeightedDigraph) {
                Arrays.fill(values, INFINITE);
            }

            int roads = FastReader.nextInt();
            for (int r = 0; r < roads; r++) {
                int buildingID1 = FastReader.nextInt();
                int buildingID2 = FastReader.nextInt();
                edgeWeightedDigraph[buildingID1][buildingID2] = 1;
                edgeWeightedDigraph[buildingID2][buildingID1] = 1;
            }
            int startBuilding = FastReader.nextInt();
            int endBuilding = FastReader.nextInt();

            int missionMinimumTime = computeMissionMinimumTime(edgeWeightedDigraph, startBuilding, endBuilding);
            outputWriter.printLine(String.format("Case %d: %d", t, missionMinimumTime));
        }
        outputWriter.flush();
    }

    private static int computeMissionMinimumTime(int[][] edgeWeightedDigraph, int startBuilding, int endBuilding) {
        int missionMinimumTime = 0;
        FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph);

        for (int buildingID = 0; buildingID < edgeWeightedDigraph.length; buildingID++) {
            int totalTime = floydWarshall.distances[startBuilding][buildingID] +
                    floydWarshall.distances[buildingID][endBuilding];
            missionMinimumTime = Math.max(missionMinimumTime, totalTime);
        }
        return missionMinimumTime;
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
