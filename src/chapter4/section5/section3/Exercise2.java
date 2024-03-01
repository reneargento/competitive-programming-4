package chapter4.section5.section3;

import java.util.*;

/**
 * Created by Rene Argento on 22/02/24.
 */
// Based on https://maratsubkhankulov.github.io/Currency-Arbitrage-Using-Floyd-Warshall/
@SuppressWarnings("unchecked")
public class Exercise2 {

    public static void main(String[] args) {
        String[] vertexNames = { "USD", "GBP", "FRF", "RandomCurrency" };
        double[][] edgeWeightedDigraph = {
                { 1, 0.5, 0, 0 },
                { 0, 1, 10, 0 },
                { 0.21, 0, 1, 0 },
                { 0, 0, 0, 1 }
        };

        FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph);
        Deque<DirectedEdge> arbitrageCycle = floydWarshall.getArbitrageCycle();
        if (arbitrageCycle != null) {
            System.out.println("Arbitrage opportunity found:");
            for (DirectedEdge edge : arbitrageCycle) {
                System.out.println(vertexNames[edge.vertex1] + " -> " + vertexNames[edge.vertex2]);
            }
        } else {
            System.out.println("No arbitrage opportunity exists");
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
                    if (edgeTo[vertex2][vertex1] == null) {
                        continue;  // optimization
                    }

                    for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                        if (distances[vertex2][vertex3] < distances[vertex2][vertex1] * distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] * distances[vertex1][vertex3];
                            edgeTo[vertex2][vertex3] = edgeTo[vertex1][vertex3];
                        }
                    }
                }
            }
        }

        public static double distance(int source, int target) {
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

        public static boolean hasPath(int source, int target) {
            return distances[source][target] != Double.POSITIVE_INFINITY;
        }

        public Deque<DirectedEdge> getArbitrageCycle() {
            for (int vertex = 0; vertex < distances.length; vertex++) {
                if (distances[vertex][vertex] > 1.0) {
                    int vertices = edgeTo.length;
                    EdgeWeightedDigraph shortestPathTree = new EdgeWeightedDigraph(vertices);

                    for (int otherVertex = 0; otherVertex < vertices; otherVertex++) {
                        if (edgeTo[vertex][otherVertex] != null) {
                            shortestPathTree.addEdge(edgeTo[vertex][otherVertex]);
                        }
                    }
                    EdgeWeightedDirectedCycle cycleFinder = new EdgeWeightedDirectedCycle(shortestPathTree);
                    return cycleFinder.cycle();
                }
            }
            return null;
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

        public Deque<DirectedEdge> cycle() {
            return cycle;
        }
    }
}
