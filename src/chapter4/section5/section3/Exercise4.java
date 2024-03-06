package chapter4.section5.section3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 22/02/24.
 */
@SuppressWarnings("unchecked")
public class Exercise4 {

    public static void main(String[] args) {
        List<DirectedEdge>[] edgeWeightedDigraph = new List[5];
        for (int i = 0; i < edgeWeightedDigraph.length; i++) {
            edgeWeightedDigraph[i] = new ArrayList<>();
        }
        edgeWeightedDigraph[0].add(new DirectedEdge(2, 10));
        edgeWeightedDigraph[0].add(new DirectedEdge(3, 13.5));
        edgeWeightedDigraph[1].add(new DirectedEdge(2, 5.5));
        edgeWeightedDigraph[2].add(new DirectedEdge(3, 1));
        edgeWeightedDigraph[2].add(new DirectedEdge(4, 2.2));

        new FloydWarshall(edgeWeightedDigraph);
        for (int vertexID = 0; vertexID < edgeWeightedDigraph.length; vertexID++) {
            System.out.print("Distances from " + vertexID + ":");
            for (int otherVertex = 0; otherVertex < edgeWeightedDigraph.length; otherVertex++) {
                System.out.print(" " + FloydWarshall.distance(vertexID, otherVertex));
            }
            System.out.println();
        }
    }

    private static class DirectedEdge {
        private final int vertex2;
        private final double weight;

        public DirectedEdge(int vertex2, double weight) {
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private static class FloydWarshall {
        private static double[][] distances;     // length of shortest v->w path
        private static boolean hasNegativeCycle;

        // O(V^2), since E = V
        public FloydWarshall(List<DirectedEdge>[] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;
            distances = new double[vertices][vertices];
            for (double[] values : distances) {
                Arrays.fill(values, Double.POSITIVE_INFINITY);
            }

            // Initialize distances using edge-weighted digraph's
            for (int vertexID = 0; vertexID < vertices; vertexID++) {
                distances[vertexID][vertexID] = 0;
                for (DirectedEdge edge : edgeWeightedDigraph[vertexID]) {
                    distances[vertexID][edge.vertex2] = edge.weight;
                }
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    for (DirectedEdge edge : edgeWeightedDigraph[vertex2]) {
                        int vertex3 = edge.vertex2;
                        if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                        }
                    }

                    if (distances[vertex2][vertex2] < 0.0) {
                        hasNegativeCycle = true;
                        return;
                    }
                }
            }
        }

        public static double distance(int source, int target) {
            if (hasNegativeCycle()) {
                throw new UnsupportedOperationException("Negative cost cycle exists");
            }
            return distances[source][target];
        }

        public static boolean hasNegativeCycle() {
            return hasNegativeCycle;
        }
    }
}
