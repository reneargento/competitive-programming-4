package chapter4.section4.section3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 02/12/23.
 */
public class Exercise5 {

    private static class EdgeWeightedDigraph {
        private final int vertices;
        private final List<DirectedEdge>[] adjacent;

        @SuppressWarnings("unchecked")
        public EdgeWeightedDigraph(int vertices) {
            this.vertices = vertices;
            adjacent = (List<DirectedEdge>[]) new ArrayList[vertices];

            for (int vertex = 0; vertex < vertices; vertex++) {
                adjacent[vertex] = new ArrayList<>();
            }
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
        private final double weight;

        public DirectedEdge(int vertex1, int vertex2, double weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(4);
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 1, 1));
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 2, 6));
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 3, 5));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 0, 1));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 2, 5));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 3, 2));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 0, 6));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 1, 5));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 3, 0));
        edgeWeightedDigraph.addEdge(new DirectedEdge(3, 0, 5));
        edgeWeightedDigraph.addEdge(new DirectedEdge(3, 1, 2));
        edgeWeightedDigraph.addEdge(new DirectedEdge(3, 2, 0));

        double[] shortestDistances = dijkstraCompleteGraph(edgeWeightedDigraph, 0);

        System.out.println("Shortest distances:");
        for (int vertexID = 1; vertexID < edgeWeightedDigraph.vertices; vertexID++) {
            System.out.printf("0->%d %.2f ", vertexID, shortestDistances[vertexID]);
        }
        System.out.println("\nExpected:");
        System.out.println("0->1 1.00 0->2 3.00 0->3 3.00");
    }

    public static double[] dijkstraCompleteGraph(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
        double[] shortestDistances = new double[edgeWeightedDigraph.vertices];
        Arrays.fill(shortestDistances, Double.MAX_VALUE);
        boolean[] visited = new boolean[edgeWeightedDigraph.vertices];

        shortestDistances[source] = 0;

        while (true) {
            int nextVertexID = -1;
            double nextShortestDistance = Double.MAX_VALUE;

            for (int vertexID = 0; vertexID < edgeWeightedDigraph.vertices; vertexID++) {
                if (!visited[vertexID] && shortestDistances[vertexID] < nextShortestDistance) {
                    nextShortestDistance = shortestDistances[vertexID];
                    nextVertexID = vertexID;
                }
            }

            if (nextVertexID == -1) {
                break;
            }

            // Relax edges
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(nextVertexID)) {
                double totalDistance = shortestDistances[nextVertexID] + edge.weight;
                if (shortestDistances[edge.vertex2] > totalDistance) {
                    shortestDistances[edge.vertex2] = totalDistance;
                }
            }
            visited[nextVertexID] = true;
        }
        return shortestDistances;
    }
}
