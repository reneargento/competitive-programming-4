package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/03/24.
 */
public class InterstarTransport {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int planets = FastReader.nextInt();
        int transports = FastReader.nextInt();

        double[][] edgeWeightedDigraph = new double[planets][planets];
        for (double[] values : edgeWeightedDigraph) {
            Arrays.fill(values, Double.POSITIVE_INFINITY);
        }
        for (int p = 0; p  < edgeWeightedDigraph.length; p++) {
            edgeWeightedDigraph[p][p] = 0;
        }

        for (int i = 0; i < transports; i++) {
            int planetID1 = getPlanetID(FastReader.next());
            int planetID2 = getPlanetID(FastReader.next());
            int cost = FastReader.nextInt();
            edgeWeightedDigraph[planetID1][planetID2] = cost;
            edgeWeightedDigraph[planetID2][planetID1] = cost;
        }
        FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph);

        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            int planetID1 = getPlanetID(FastReader.next());
            int planetID2 = getPlanetID(FastReader.next());
            Deque<Character> path = floydWarshall.path(planetID1, planetID2);
            outputWriter.print(path.pop());
            while (!path.isEmpty()) {
                outputWriter.print(" " + path.pop());
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static int getPlanetID(String planetName) {
        return planetName.charAt(0) - 'A';
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
        private static double[][] distances;     // length of shortest v->w path
        private static DirectedEdge[][] edgeTo;  // last edge on shortest v->w path

        public FloydWarshall(double[][] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;
            distances = new double[vertices][vertices];
            edgeTo = new DirectedEdge[vertices][vertices];

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    double distance = edgeWeightedDigraph[vertex1][vertex2];
                    distances[vertex1][vertex2] = distance;
                    edgeTo[vertex1][vertex2] = new DirectedEdge(vertex1, vertex2, distance);
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

        public static double distance(int source, int target) {
            return distances[source][target];
        }

        public Deque<Character> path(int source, int target) {
            if (!hasPath(source, target)) {
                return null;
            }

            Deque<Character> path = new ArrayDeque<>();
            for (DirectedEdge edge = edgeTo[source][target]; true; edge = edgeTo[source][edge.from()]) {
                char planetName = (char) (edge.to() + 'A');
                path.push(planetName);

                if (source == edge.to()) {
                    break;
                }
            }
            return path;
        }

        public static boolean hasPath(int source, int target) {
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
