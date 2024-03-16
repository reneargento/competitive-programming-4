package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 13/03/24.
 */
public class USHER {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int boxCapacity = FastReader.nextInt();
            int parishioners = FastReader.nextInt();
            int[][] edgeWeightedDigraph = new int[parishioners + 1][parishioners + 1];
            for (int[] values : edgeWeightedDigraph) {
                Arrays.fill(values, INFINITE);
            }

            int parishionersToPassBox = FastReader.nextInt();
            for (int i = 0; i < parishionersToPassBox; i++) {
                int parishionerID = FastReader.nextInt();
                edgeWeightedDigraph[0][parishionerID] = 0;
            }

            for (int parishionerID = 1; parishionerID <= parishioners; parishionerID++) {
                int rules = FastReader.nextInt();
                for (int r = 0; r < rules; r++) {
                    int coins = FastReader.nextInt();
                    int nextParishioner = FastReader.nextInt();
                    int currentCoins = edgeWeightedDigraph[parishionerID][nextParishioner];
                    edgeWeightedDigraph[parishionerID][nextParishioner] = Math.min(currentCoins, coins);
                }
            }

            int maxUsherCoins = computeMaxUsherCoins(boxCapacity, edgeWeightedDigraph);
            outputWriter.printLine(maxUsherCoins);
        }
        outputWriter.flush();
    }

    private static int computeMaxUsherCoins(int boxCapacity, int[][] edgeWeightedDigraph) {
        FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph);

        int minCycleLength = floydWarshall.distance(0, 0);
        if (minCycleLength >= boxCapacity) {
            return 0;
        }

        int maxUsherCoins = (boxCapacity - 1) / (minCycleLength - 1);
        if ((boxCapacity - 1) % (minCycleLength - 1) == 0) {
            maxUsherCoins--;
        }
        return maxUsherCoins;
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

        public FloydWarshall(int[][] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;
            distances = new int[vertices][vertices];

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    int distance = edgeWeightedDigraph[vertex1][vertex2];
                    distances[vertex1][vertex2] = distance;
                    DirectedEdge edge = new DirectedEdge(vertex1, vertex2, distance);
                }
            }

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

        public int distance(int source, int target) {
            return distances[source][target];
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
