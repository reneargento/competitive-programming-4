package chapter4.section4.f.on.small.graph.with.negative.cycle.bellman.ford;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/02/24.
 */
public class SingleSourceShortestPathNegativeWeights {

    private static class Result {
        long[] distances;
        boolean[] nodesReachableFromCycles;

        public Result(long[] distances, boolean[] nodesReachableFromCycles) {
            this.distances = distances;
            this.nodesReachableFromCycles = nodesReachableFromCycles;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodes = FastReader.nextInt();
        int edges = FastReader.nextInt();
        int queries = FastReader.nextInt();
        int startingNode = FastReader.nextInt();

        while (nodes != 0 || edges != 0 || queries != 0 || startingNode != 0) {
            EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(nodes);
            for (int m = 0; m < edges; m++) {
                DirectedEdge edge = new DirectedEdge(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
                edgeWeightedDigraph.addEdge(edge);
            }
            Result result = computeDistances(edgeWeightedDigraph, startingNode);

            for (int q = 0; q < queries; q++) {
                int targetNodeID = FastReader.nextInt();
                if (result.nodesReachableFromCycles[targetNodeID]) {
                    outputWriter.printLine("-Infinity");
                } else if (result.distances[targetNodeID] != Long.MAX_VALUE) {
                    outputWriter.printLine(result.distances[targetNodeID]);
                } else {
                    outputWriter.printLine("Impossible");
                }
            }
            nodes = FastReader.nextInt();
            edges = FastReader.nextInt();
            queries = FastReader.nextInt();
            startingNode = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    public static Result computeDistances(EdgeWeightedDigraph edgeWeightedDigraph, int sourceVertex) {
        long[] distances = new long[edgeWeightedDigraph.vertices()];
        Arrays.fill(distances, Long.MAX_VALUE);
        distances[sourceVertex] = 0;
        new BellmanFord(edgeWeightedDigraph, distances);

        long[] originalDistances = new long[distances.length];
        System.arraycopy(distances, 0, originalDistances, 0, distances.length);
        new BellmanFord(edgeWeightedDigraph, distances);

        boolean[] nodesInCycles = computeNodesInCycles(originalDistances, distances);
        boolean[] nodesReachableFromCycles = computeNodesReachableFromCycles(edgeWeightedDigraph, nodesInCycles);
        return new Result(distances, nodesReachableFromCycles);
    }

    private static boolean[] computeNodesInCycles(long[] originalDistances, long[] updatedDistances) {
        boolean[] nodesInCycles = new boolean[originalDistances.length];
        for (int vertexID = 0; vertexID < originalDistances.length; vertexID++) {
            if (originalDistances[vertexID] != updatedDistances[vertexID]) {
                nodesInCycles[vertexID] = true;
            }
        }
        return nodesInCycles;
    }

    private static boolean[] computeNodesReachableFromCycles(EdgeWeightedDigraph edgeWeightedDigraph,
                                                             boolean[] nodesInCycles) {
        boolean[] nodesReachableFromCycles = new boolean[nodesInCycles.length];
        boolean[] visited = new boolean[nodesInCycles.length];
        Queue<Integer> queue = new LinkedList<>();

        for (int vertexID = 0; vertexID < nodesInCycles.length; vertexID++) {
            if (nodesInCycles[vertexID]) {
                nodesReachableFromCycles[vertexID] = true;
                visited[vertexID] = true;
                queue.offer(vertexID);
            }
        }

        while (!queue.isEmpty()) {
            int vertexID = queue.poll();
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertexID)) {
                if (!visited[edge.vertex2]) {
                    visited[edge.vertex2] = true;
                    nodesReachableFromCycles[edge.vertex2] = true;
                    queue.offer(edge.vertex2);
                }
            }
        }
        return nodesReachableFromCycles;
    }

    private static class DirectedEdge {
        private final int vertex1;
        private final int vertex2;
        private final long weight;

        public DirectedEdge(int vertex1, int vertex2, long weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public long weight() {
            return weight;
        }

        public int from() {
            return vertex1;
        }

        public int to() {
            return vertex2;
        }
    }

    @SuppressWarnings("unchecked")
    private static class EdgeWeightedDigraph {
        private final int vertices;
        private final List<DirectedEdge>[] adjacent;

        public EdgeWeightedDigraph(int vertices) {
            this.vertices = vertices;
            adjacent = (List<DirectedEdge>[]) new ArrayList[vertices];
            for (int vertex = 0; vertex < vertices; vertex++) {
                adjacent[vertex] = new ArrayList<>();
            }
        }

        public int vertices() {
            return vertices;
        }

        public void addEdge(DirectedEdge edge) {
            adjacent[edge.from()].add(edge);
        }

        public Iterable<DirectedEdge> adjacent(int vertex) {
            return adjacent[vertex];
        }
    }

    private static class BellmanFord {
        public BellmanFord(EdgeWeightedDigraph edgeWeightedDigraph, long[] distTo) {
            for (int iteration = 0; iteration < edgeWeightedDigraph.vertices; iteration++) {
                boolean updated = false;
                for (int vertexID = 0; vertexID < edgeWeightedDigraph.vertices; vertexID++) {
                    if (distTo[vertexID] != Long.MAX_VALUE) {
                        updated |= relax(edgeWeightedDigraph, distTo, vertexID);
                    }
                }
                if (!updated) {
                    break;
                }
            }
        }

        private boolean relax(EdgeWeightedDigraph edgeWeightedDigraph, long[] distTo, int vertex) {
            boolean updated = false;
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.to();

                if (distTo[neighbor] > distTo[vertex] + edge.weight()) {
                    distTo[neighbor] = distTo[vertex] + edge.weight();
                    updated = true;
                }
            }
            return updated;
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
