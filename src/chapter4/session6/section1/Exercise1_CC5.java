package chapter4.session6.section1;

import java.util.*;

/**
 * Created by Rene Argento on 05/04/24.
 */
// Solution: Dijkstra's algorithm
public class Exercise1_CC5 {

    private static class Edge {
        int vertex2;
        int weight;

        Edge(int vertex2, int weight) {
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private static final int MAX_VALUE = 10001;

    private static int computeMinimumWeightNeeded(int[] coinValue, int[] weight, int target) {
        List<Edge>[] adjacent = createGraph(coinValue, weight, target);
        Dijkstra dijkstra = new Dijkstra(adjacent, target);
        return dijkstra.distTo[0];
    }

    @SuppressWarnings("unchecked")
    private static List<Edge>[] createGraph(int[] coinValue, int[] weight, int target) {
        List<Edge>[] adjacent = new List[MAX_VALUE];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int totalValue = 0; totalValue < adjacent.length; totalValue++) {
            for (int coinType = 0; coinType < coinValue.length; coinType++) {
                int value = coinValue[coinType];
                int neighborValue = totalValue - value;

                if (neighborValue >= 0 && neighborValue <= target) {
                    adjacent[totalValue].add(new Edge(neighborValue, weight[coinType]));
                }
            }
        }
        return adjacent;
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            int distance;

            public Vertex(int id, int distance) {
                this.id = id;
                this.distance = distance;
            }

            @Override
            public int compareTo(Vertex other) {
                return Integer.compare(distance, other.distance);
            }
        }

        private final int[] distTo;  // length of path to vertex
        private final PriorityQueue<Vertex> priorityQueue;

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            distTo = new int[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                relax(adjacencyList, vertex);
                if (vertex.id == 0) {
                    break;
                }
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.weight) {
                    distTo[neighbor] = distTo[vertex.id] + edge.weight;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] != MAX_VALUE;
        }
    }

    public static void main(String[] args) {
        int[] coinValue = { -10, -2, 0, 1, 2, 5, 6 };
        int[] weight =    {   1,  3, 2, 4, 3, 3, 5 };
        int target = 19;

        int minimumWeight = computeMinimumWeightNeeded(coinValue, weight, target);
        System.out.println("Minimum Weight: " + minimumWeight);
        System.out.println("Expected: 15");
    }
}
