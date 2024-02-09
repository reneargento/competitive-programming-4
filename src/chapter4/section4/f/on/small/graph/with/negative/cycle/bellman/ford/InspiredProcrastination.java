package chapter4.section4.f.on.small.graph.with.negative.cycle.bellman.ford;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/01/24.
 */
public class InspiredProcrastination {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int lifePoints = FastReader.nextInt();
        int decisions = FastReader.nextInt();

        while (lifePoints != 0 || decisions != 0) {
            EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(lifePoints);
            for (int i = 0; i < decisions; i++) {
                int lifePoint1 = FastReader.nextInt() - 1;
                int lifePoint2 = FastReader.nextInt() - 1;
                edgeWeightedDigraph.addEdge(new DirectedEdge(lifePoint1, lifePoint2, FastReader.nextInt()));
            }

            BellmanFord bellmanFord = new BellmanFord(edgeWeightedDigraph, 0);
            if (bellmanFord.hasCycle()) {
                outputWriter.printLine("Unlimited!");
            } else {
                int highestProcrastination = computeHighestProcrastination(bellmanFord, edgeWeightedDigraph);
                outputWriter.printLine(highestProcrastination);
            }
            lifePoints = FastReader.nextInt();
            decisions = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeHighestProcrastination(BellmanFord bellmanFord, EdgeWeightedDigraph edgeWeightedDigraph) {
        int highestProcrastination = 0;
        for (int lifePoint = 0; lifePoint < edgeWeightedDigraph.vertices; lifePoint++) {
            highestProcrastination = Math.max(highestProcrastination, bellmanFord.distTo(lifePoint));
        }
        return highestProcrastination;
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

        public Iterable<DirectedEdge> edges() {
            List<DirectedEdge> bag = new ArrayList<>();
            for (int vertex = 0; vertex < vertices; vertex++) {
                bag.addAll(adjacent[vertex]);
            }
            return bag;
        }

        public EdgeWeightedDigraph reverse() {
            EdgeWeightedDigraph reverse = new EdgeWeightedDigraph(vertices);
            for (int vertex = 0; vertex < vertices; vertex++) {
                for (DirectedEdge edge : adjacent(vertex)) {
                    int neighbor = edge.to();
                    reverse.addEdge(new DirectedEdge(neighbor, vertex, edge.weight()));
                }
            }
            return reverse;
        }
    }

    private static class BellmanFord {

        private final int[] distTo;         // length of path to vertex
        private final DirectedEdge[] edgeTo;   // last edge on path to vertex
        private final boolean[] onQueue;       // is this vertex on the queue?
        private final Queue<Integer> queue;    // vertices being relaxed
        private int callsToRelax;              // number of calls to relax()
        private Iterable<DirectedEdge> cycle;  // if there is a cycle in edgeTo[], return it

        //O(E * V), but typically runs in (E + V)
        public BellmanFord(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
            distTo = new int[edgeWeightedDigraph.vertices()];
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
            onQueue = new boolean[edgeWeightedDigraph.vertices()];

            Arrays.fill(distTo, Integer.MIN_VALUE);
            distTo[0] = 0;
            queue = new LinkedList<>();

            queue.offer(source);
            onQueue[source] = true;

            // The only vertices that could be relaxed are the ones which had their distance from the source updated
            // on the last pass.
            while (!queue.isEmpty() && !hasCycle()) {
                int vertex = queue.poll();
                onQueue[vertex] = false;
                relax(edgeWeightedDigraph, vertex);
            }
        }

        private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.to();

                if (distTo[neighbor] < distTo[vertex] + edge.weight()) {
                    distTo[neighbor] = distTo[vertex] + edge.weight();
                    edgeTo[neighbor] = edge;

                    if (!onQueue[neighbor]) {
                        queue.offer(neighbor);
                        onQueue[neighbor] = true;
                    }
                }

                // Check if there is a cycle after every V calls to relax()
                if (callsToRelax++ % edgeWeightedDigraph.vertices() == 0) {
                    findCycle();
                }
            }
        }

        public int distTo(int vertex) {
            return distTo[vertex];
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] < Double.POSITIVE_INFINITY;
        }

        private void findCycle() {
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

        public boolean hasCycle() {
            return cycle != null;
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
