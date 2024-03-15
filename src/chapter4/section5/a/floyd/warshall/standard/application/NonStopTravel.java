package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/03/24.
 */
public class NonStopTravel {

    private static class Result {
        List<Integer> route;
        int secondsDelay;

        public Result(List<Integer> route, int secondsDelay) {
            this.route = route;
            this.secondsDelay = secondsDelay;
        }
    }

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int intersections = FastReader.nextInt();
        int caseID = 1;

        while (intersections != 0) {
            int[][] edgeWeightedDigraph = new int[intersections][intersections];
            for (int[] values : edgeWeightedDigraph) {
                Arrays.fill(values, INFINITE);
            }

            for (int intersectionID = 0; intersectionID < intersections; intersectionID++) {
                int streets = FastReader.nextInt();
                for (int s = 0; s < streets; s++) {
                    int neighborIntersectionID = FastReader.nextInt() - 1;
                    int secondsDelay = FastReader.nextInt();
                    edgeWeightedDigraph[intersectionID][neighborIntersectionID] = secondsDelay;
                }
            }
            int startIntersectionID = FastReader.nextInt() - 1;
            int endIntersectionID = FastReader.nextInt() - 1;

            Result result = computeMinimumDelayRoute(edgeWeightedDigraph, startIntersectionID, endIntersectionID);
            List<Integer> route = result.route;
            outputWriter.print(String.format("Case %d: Path =", caseID));
            for (int intersectionID : route) {
                outputWriter.print(" " + (intersectionID + 1));
            }
            outputWriter.printLine(String.format("; %d second delay", result.secondsDelay));

            intersections = FastReader.nextInt();
            caseID++;
        }
        outputWriter.flush();
    }

    private static Result computeMinimumDelayRoute(int[][] edgeWeightedDigraph, int startIntersectionID,
                                                   int endIntersectionID) {
        FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph);
        int secondsDelay = floydWarshall.distance(startIntersectionID, endIntersectionID);
        List<Integer> route = floydWarshall.path(startIntersectionID, endIntersectionID);
        return new Result(route, secondsDelay);
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

        public List<Integer> path(int source, int target) {
            if (!hasPath(source, target)) {
                return null;
            }

            Deque<Integer> pathStack = new ArrayDeque<>();
            for (DirectedEdge edge = edgeTo[source][target]; edge != null; edge = edgeTo[source][edge.from()]) {
                pathStack.push(edge.to());
            }
            pathStack.push(source);

            List<Integer> path = new ArrayList<>();
            while (!pathStack.isEmpty()) {
                path.add(pathStack.poll());
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
