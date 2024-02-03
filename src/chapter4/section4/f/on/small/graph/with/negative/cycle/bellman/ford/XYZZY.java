package chapter4.section4.f.on.small.graph.with.negative.cycle.bellman.ford;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/01/24.
 */
@SuppressWarnings("unchecked")
public class XYZZY {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rooms = FastReader.nextInt();

        while (rooms != -1) {
            EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(rooms);
            int[] energyLevels = new int[rooms];

            for (int roomID = 0; roomID < rooms; roomID++) {
                energyLevels[roomID] = FastReader.nextInt();
                int doors = FastReader.nextInt();
                for (int d = 0; d < doors; d++) {
                    int neighborRoom = FastReader.nextInt() - 1;
                    edgeWeightedDigraph.addEdge(new DirectedEdge(roomID, neighborRoom, 0));
                }
            }

            for (int roomID = 0; roomID < edgeWeightedDigraph.vertices(); roomID++) {
                for (DirectedEdge edge : edgeWeightedDigraph.adjacent(roomID)) {
                    edge.weight = energyLevels[edge.to()];
                }
            }

            int targetRoomID = rooms - 1;
            BellmanFord bellmanFord = new BellmanFord(edgeWeightedDigraph, 0);
            if (bellmanFord.hasPathTo(targetRoomID)) {
                outputWriter.printLine("winnable");
            } else {
                outputWriter.printLine("hopeless");
            }
            rooms = FastReader.nextInt();
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
        private double weight;

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

        private final double[] distTo;         // length of path to vertex
        private final DirectedEdge[] edgeTo;   // last edge on path to vertex
        private final boolean[] onQueue;       // is this vertex on the queue?
        private final Queue<Integer> queue;    // vertices being relaxed
        private int callsToRelax;              // number of calls to relax()
        private Iterable<DirectedEdge> cycle;  // if there is a cycle in edgeTo[], return it
        private final boolean[] isInCycle;

        //O(E * V), but typically runs in (E + V)
        public BellmanFord(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
            distTo = new double[edgeWeightedDigraph.vertices()];
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
            onQueue = new boolean[edgeWeightedDigraph.vertices()];
            queue = new LinkedList<>();
            isInCycle = new boolean[edgeWeightedDigraph.vertices()];

            for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                distTo[vertex] = Double.NEGATIVE_INFINITY;
            }

            distTo[source] = 100;
            queue.offer(source);
            onQueue[source] = true;

            // The only vertices that could be relaxed are the ones which had their distance from the source updated
            // on the last pass.
            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                onQueue[vertex] = false;
                relax(edgeWeightedDigraph, vertex);
            }
        }

        private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.to();
                if (isInCycle[neighbor]) {
                    continue;
                }
                double candidateDistance = distTo[vertex] + edge.weight();
                if (distTo[neighbor] < candidateDistance && candidateDistance > 0) {
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
                    if (hasCycle()) {
                        addAllCycleOutgoingVertices(edgeWeightedDigraph);
                    }
                }
            }
        }

        private void addAllCycleOutgoingVertices(EdgeWeightedDigraph edgeWeightedDigraph) {
            for (int roomID = 0; roomID < isInCycle.length; roomID++) {
                if (isInCycle[roomID]) {
                    for (DirectedEdge edge : edgeWeightedDigraph.adjacent(roomID)) {
                        int neighbor = edge.to();
                        distTo[neighbor] = Double.POSITIVE_INFINITY;
                        queue.offer(neighbor);
                        onQueue[neighbor] = true;
                    }
                }
            }
        }

        public double distTo(int vertex) {
            return distTo[vertex];
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] > 0;
        }

        private void findCycle() {
            int vertices = edgeTo.length;
            EdgeWeightedDigraph shortestPathsTree = new EdgeWeightedDigraph(vertices);

            for (int vertex = 0; vertex < vertices; vertex++) {
                if (edgeTo[vertex] != null) {
                    shortestPathsTree.addEdge(edgeTo[vertex]);
                }
            }

            EdgeWeightedDirectedCycle edgeWeightedCycleFinder = new EdgeWeightedDirectedCycle(shortestPathsTree,
                    isInCycle);
            cycle = edgeWeightedCycleFinder.cycle();
        }

        public boolean hasCycle() {
            return cycle != null;
        }

        public Iterable<DirectedEdge> getCycle() {
            return cycle;
        }
    }

    private static class EdgeWeightedDirectedCycle {

        private final boolean[] visited;
        private final DirectedEdge[] edgeTo;
        private Deque<DirectedEdge> cycle;
        private final boolean[] onStack;

        public EdgeWeightedDirectedCycle(EdgeWeightedDigraph edgeWeightedDigraph, boolean[] isInCycle) {
            onStack = new boolean[edgeWeightedDigraph.vertices()];
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
            visited = new boolean[edgeWeightedDigraph.vertices()];

            for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                if (!visited[vertex] && !isInCycle[vertex]) {
                    dfs(edgeWeightedDigraph, vertex, isInCycle);
                }
            }
        }

        private void dfs(EdgeWeightedDigraph edgeWeightedDigraph, int vertex, boolean[] isInCycle) {
            onStack[vertex] = true;
            visited[vertex] = true;

            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.to();

                if (hasCycle()) {
                    return;
                } else if (!visited[neighbor]) {
                    edgeTo[neighbor] = edge;
                    dfs(edgeWeightedDigraph, neighbor, isInCycle);
                } else if (onStack[neighbor]) {
                    cycle = new ArrayDeque<>();

                    DirectedEdge edgeInCycle = edge;
                    while (edgeInCycle.from() != neighbor) {
                        isInCycle[edgeInCycle.from()] = true;
                        isInCycle[edgeInCycle.to()] = true;
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
