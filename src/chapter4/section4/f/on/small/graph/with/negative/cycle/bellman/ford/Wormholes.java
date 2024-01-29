package chapter4.section4.f.on.small.graph.with.negative.cycle.bellman.ford;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/01/24.
 */
public class Wormholes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int starSystems = FastReader.nextInt();
            int wormholes = FastReader.nextInt();
            EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(starSystems);

            for (int w = 0; w < wormholes; w++) {
                int starSystemID1 = FastReader.nextInt();
                int starSystemID2 = FastReader.nextInt();
                int years = FastReader.nextInt();
                edgeWeightedDigraph.addEdge(new DirectedEdge(starSystemID1, starSystemID2, years));
            }

            BellmanFord bellmanFord = new BellmanFord(edgeWeightedDigraph, 0);
            if (bellmanFord.hasNegativeCycle()) {
                outputWriter.printLine("possible");
            } else {
                outputWriter.printLine("not possible");
            }
        }
        outputWriter.flush();
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

    private static class BellmanFord {
        private final double[] distTo;
        private final DirectedEdge[] edgeTo;
        private final boolean[] onQueue;
        private final Queue<Integer> queue;
        private int callsToRelax;
        private Iterable<DirectedEdge> cycle;

        public BellmanFord(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
            distTo = new double[edgeWeightedDigraph.vertices()];
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
            onQueue = new boolean[edgeWeightedDigraph.vertices()];
            queue = new LinkedList<>();

            for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                distTo[vertex] = Double.POSITIVE_INFINITY;
            }

            distTo[source] = 0;
            queue.offer(source);
            onQueue[source] = true;

            while (!queue.isEmpty() && !hasNegativeCycle()) {
                int vertex = queue.poll();
                onQueue[vertex] = false;
                relax(edgeWeightedDigraph, vertex);
            }
        }

        private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.to();

                if (distTo[neighbor] > distTo[vertex] + edge.weight()) {
                    distTo[neighbor] = distTo[vertex] + edge.weight();
                    edgeTo[neighbor] = edge;

                    if (!onQueue[neighbor]) {
                        queue.offer(neighbor);
                        onQueue[neighbor] = true;
                    }
                }

                // Check if there is a negative cycle after every V calls to relax()
                if (callsToRelax++ % edgeWeightedDigraph.vertices() == 0) {
                    findNegativeCycle();
                }
            }
        }

        public double distTo(int vertex) {
            return distTo[vertex];
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] < Double.POSITIVE_INFINITY;
        }

        public Iterable<DirectedEdge> pathTo(int vertex) {
            if (!hasPathTo(vertex)) {
                return null;
            }

            Deque<DirectedEdge> path = new ArrayDeque<>();
            for (DirectedEdge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.from()]) {
                path.push(edge);
            }
            return path;
        }

        private void findNegativeCycle() {
            int vertices = edgeTo.length;
            EdgeWeightedDigraph shortestPathsTree = new EdgeWeightedDigraph(vertices);

            for (int vertex = 0; vertex < vertices; vertex++) {
                if (edgeTo[vertex] != null) {
                    shortestPathsTree.addEdge(edgeTo[vertex]);
                }
            }

            EdgeWeightedDirectedCycle edgeWeightedCycleFinder = new EdgeWeightedDirectedCycle(shortestPathsTree);
            cycle = edgeWeightedCycleFinder.cycle();
        }

        public boolean hasNegativeCycle() {
            return cycle != null;
        }

        public Iterable<DirectedEdge> negativeCycle() {
            return cycle;
        }
    }

    private static class EdgeWeightedDirectedCycle {

        private final boolean[] visited;
        private final DirectedEdge[] edgeTo;
        private Deque<DirectedEdge> cycle;   // vertices on a cycle (if one exists)
        private final boolean[] onStack;     // vertices on recursive call stack

        public EdgeWeightedDirectedCycle(EdgeWeightedDigraph edgeWeightedDigraph) {
            onStack = new boolean[edgeWeightedDigraph.vertices()];
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
            visited = new boolean[edgeWeightedDigraph.vertices()];

            for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                if (!visited[vertex]) {
                    dfs(edgeWeightedDigraph, vertex);
                }
            }
        }

        private void dfs(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
            onStack[vertex] = true;
            visited[vertex] = true;

            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.to();

                if (hasCycle()) {
                    return;
                } else if (!visited[neighbor]) {
                    edgeTo[neighbor] = edge;
                    dfs(edgeWeightedDigraph, neighbor);
                } else if (onStack[neighbor]) {
                    cycle = new ArrayDeque<>();

                    DirectedEdge edgeInCycle = edge;

                    while (edgeInCycle.from() != neighbor) {
                        cycle.push(edgeInCycle);
                        edgeInCycle = edgeTo[edgeInCycle.from()];
                    }

                    cycle.push(edgeInCycle);
                    return;
                }
            }

            onStack[vertex] = false;
        }

        public boolean hasCycle() {
            return cycle != null;
        }

        public Iterable<DirectedEdge> cycle() {
            return cycle;
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
