package chapter4.section4.f.on.small.graph.with.negative.cycle.bellman.ford;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/01/24.
 */
public class Traffic {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int setID = 1;

        while (line != null && !line.trim().isEmpty()) {
            String[] data = line.split(" ");
            int junctions = Integer.parseInt(data[0]);

            int[] busyness = new int[junctions];
            for (int i = 0; i < junctions; i++) {
                busyness[i] = Integer.parseInt(data[i + 1]);
            }

            EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(junctions);

            int roads = FastReader.nextInt();
            for (int r = 0; r < roads; r++) {
                int junctionID1 = FastReader.nextInt() - 1;
                int junctionID2 = FastReader.nextInt() - 1;
                long earning = (long) Math.pow(busyness[junctionID2] - busyness[junctionID1], 3);
                edgeWeightedDigraph.addEdge(new DirectedEdge(junctionID1, junctionID2, earning));
            }
            BellmanFord bellmanFord = new BellmanFord(edgeWeightedDigraph, 0);

            outputWriter.printLine(String.format("Set #%d", setID));
            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int junctionID = FastReader.nextInt() - 1;
                long distance = bellmanFord.distTo(junctionID);
                if (distance >= 3
                        && !(bellmanFord.hasNegativeCycle()
                             && bellmanFord.verticesWithNegativeDistance.contains(junctionID))
                        &&  bellmanFord.hasPathTo(junctionID)) {
                    outputWriter.printLine(distance);
                } else {
                    outputWriter.printLine("?");
                }
            }
            setID++;
            line = FastReader.getLine();
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
            adjacent[edge.vertex1].add(edge);
        }

        public Iterable<DirectedEdge> adjacent(int vertex) {
            return adjacent[vertex];
        }
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
    }

    private static class BellmanFord {

        private final long[] distTo;           // length of path to vertex
        private final DirectedEdge[] edgeTo;   // last edge on path to vertex
        private final boolean[] onQueue;       // is this vertex on the queue?
        private final Queue<Integer> queue;    // vertices being relaxed
        private int callsToRelax;              // number of calls to relax()
        private Set<Integer> verticesWithNegativeDistance;

        //O(E * V), but typically runs in (E + V)
        public BellmanFord(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
            distTo = new long[edgeWeightedDigraph.vertices()];
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
            onQueue = new boolean[edgeWeightedDigraph.vertices()];
            queue = new LinkedList<>();

            for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                distTo[vertex] = Long.MAX_VALUE;
            }
            distTo[source] = 0;
            queue.offer(source);
            onQueue[source] = true;

            // The only vertices that could be relaxed are the ones which had their distance from the source updated
            // on the last pass.
            while (!queue.isEmpty() && !hasNegativeCycle()) {
                int vertex = queue.poll();
                onQueue[vertex] = false;
                relax(edgeWeightedDigraph, vertex);
            }
        }

        private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex] + edge.weight) {
                    distTo[neighbor] = distTo[vertex] + edge.weight;
                    edgeTo[neighbor] = edge;

                    if (!onQueue[neighbor]) {
                        queue.offer(neighbor);
                        onQueue[neighbor] = true;
                    }
                }

                // Check if there is a negative cycle after every V calls to relax()
                if (callsToRelax++ % edgeWeightedDigraph.vertices() == 0) {
                    findNegativeCycleAndReachableVertices();
                }
            }
        }

        public long distTo(int vertex) {
            return distTo[vertex];
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] != Long.MAX_VALUE;
        }

        private void findNegativeCycleAndReachableVertices() {
            int vertices = edgeTo.length;
            EdgeWeightedDigraph shortestPathsTree = new EdgeWeightedDigraph(vertices);

            for (int vertex = 0; vertex < vertices; vertex++) {
                if (edgeTo[vertex] != null) {
                    shortestPathsTree.addEdge(edgeTo[vertex]);
                }
            }

            EdgeWeightedDirectedCycle edgeWeightedCycleFinder = new EdgeWeightedDirectedCycle(shortestPathsTree);
            List<Integer> verticesInCycle = edgeWeightedCycleFinder.cycle();
            if (verticesInCycle != null) {
                verticesWithNegativeDistance = new HashSet<>();
                boolean[] visited = new boolean[vertices];
                dfsToMarkNegativeVertices(shortestPathsTree, visited, verticesInCycle.get(0));
            }
        }

        private void dfsToMarkNegativeVertices(EdgeWeightedDigraph edgeWeightedDigraph, boolean[] visited,
                                               int vertexID) {
            verticesWithNegativeDistance.add(vertexID);
            visited[vertexID] = true;

            if (edgeWeightedDigraph.adjacent(vertexID) != null) {
                for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertexID)) {
                    if (!visited[edge.vertex2]) {
                        dfsToMarkNegativeVertices(edgeWeightedDigraph, visited, edge.vertex2);
                    }
                }
            }
        }

        public boolean hasNegativeCycle() {
            return verticesWithNegativeDistance != null;
        }
    }

    private static class EdgeWeightedDirectedCycle {

        private final boolean[] visited;
        private final DirectedEdge[] edgeTo;
        private List<Integer> cycle;          // vertices on a cycle (if one exists)
        private final boolean[] onStack;      // vertices on recursive call stack

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
                int neighbor = edge.vertex2;

                if (hasCycle()) {
                    return;
                } else if (!visited[neighbor]) {
                    edgeTo[neighbor] = edge;
                    dfs(edgeWeightedDigraph, neighbor);
                } else if (onStack[neighbor]) {
                    cycle = new ArrayList<>();

                    DirectedEdge edgeInCycle = edge;

                    while (edgeInCycle.vertex1 != neighbor) {
                        cycle.add(edgeInCycle.vertex2);
                        edgeInCycle = edgeTo[edgeInCycle.vertex1];
                    }

                    cycle.add(edgeInCycle.vertex1);
                    cycle.add(edgeInCycle.vertex2);
                    return;
                }
            }
            onStack[vertex] = false;
        }

        public boolean hasCycle() {
            return cycle != null;
        }

        public List<Integer> cycle() {
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
