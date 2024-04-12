package chapter4.session6.section1;

import java.util.*;

/**
 * Created by Rene Argento on 05/04/24.
 */
@SuppressWarnings("unchecked")
// Solution: Bellman-Ford algorithm can be used if the graph is small and contains no negative-weight cycles.
public class Exercise1_CC6 {

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

    private static final int MAX_VALUE = 10001;
    private static final int NEGATIVE_CYCLE_FOUND = Integer.MAX_VALUE;

    private static int computeMinimumWeightNeeded(int[] coinValue, int[] weight, int target) {
        EdgeWeightedDigraph edgeWeightedDigraph = createGraph(coinValue, weight, target);
        BellmanFord bellmanFord = new BellmanFord(edgeWeightedDigraph, target);

        if (bellmanFord.hasNegativeCycle()) {
            return NEGATIVE_CYCLE_FOUND;
        } else {
            return bellmanFord.distTo[0];
        }
    }

    private static EdgeWeightedDigraph createGraph(int[] coinValue, int[] weight, int target) {
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(MAX_VALUE);

        for (int totalValue = 0; totalValue < edgeWeightedDigraph.adjacent.length; totalValue++) {
            for (int coinType = 0; coinType < coinValue.length; coinType++) {
                int value = coinValue[coinType];
                int neighborValue = totalValue - value;

                if (neighborValue >= 0 && neighborValue <= target) {
                    DirectedEdge edge = new DirectedEdge(totalValue, neighborValue, weight[coinType]);
                    edgeWeightedDigraph.addEdge(edge);
                }
            }
        }
        return edgeWeightedDigraph;
    }

    private static class BellmanFord {
        private final int[] distTo;            // length of path to vertex
        private final DirectedEdge[] edgeTo;   // last edge on path to vertex
        private final boolean[] onQueue;       // is this vertex on the queue?
        private final Queue<Integer> queue;    // vertices being relaxed
        private int callsToRelax;              // number of calls to relax()
        private Iterable<DirectedEdge> cycle;  // if there is a negative cycle in edgeTo[], return it

        // O(E * V), but typically runs in (E + V)
        public BellmanFord(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
            distTo = new int[edgeWeightedDigraph.vertices()];
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
            onQueue = new boolean[edgeWeightedDigraph.vertices()];
            queue = new LinkedList<>();

            Arrays.fill(distTo, MAX_VALUE);
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
    }

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

    public static void main(String[] args) {
        int[] coinValue = { -10, -2, 0, 1, 2,  5, 6 };
        int[] weight =    {   7,  3, 2, 4, 3, -3, 5 };
        int target = 19;

        int minimumWeight = computeMinimumWeightNeeded(coinValue, weight, target);
        if (minimumWeight == NEGATIVE_CYCLE_FOUND) {
            System.out.println("Problem is ill-defined: negative weight cycle exists");
        } else {
            System.out.println("Minimum Weight: " + minimumWeight);
        }
        System.out.println("Expected: -6");
    }
}
