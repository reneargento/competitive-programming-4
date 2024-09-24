package chapter4.section6.section1;

import java.util.*;

/**
 * Created by Rene Argento on 05/04/24.
 */
// Solution: BFS algorithm
public class Exercise1_CC4 {

    private static class Edge {
        int vertex2;
        int weight;

        Edge(int vertex2, int weight) {
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private static final int INFINITE = 100000;
    private static final int MAX_VALUE = 10001;

    private static int computeMinimumWeightNeeded(int[] coinValue, int[] weight, int target) {
        List<Edge>[] adjacent = createGraph(coinValue, weight, target);
        int[] minimumWeights = new int[MAX_VALUE];
        Arrays.fill(minimumWeights, INFINITE);
        minimumWeights[target] = 0;

        boolean[] visited = new boolean[MAX_VALUE];
        visited[target] = true;

        Queue<Edge> queue = new LinkedList<>();
        queue.offer(new Edge(target, 0));

        while (!queue.isEmpty()) {
            Edge currentEdge = queue.poll();

            for (Edge edge : adjacent[currentEdge.vertex2]) {
                if (!visited[edge.vertex2]) {
                    visited[edge.vertex2] = true;
                    minimumWeights[edge.vertex2] = minimumWeights[currentEdge.vertex2] + 1;
                    queue.offer(edge);
                }
            }
        }
        return minimumWeights[0];
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

    public static void main(String[] args) {
        int[] coinValue = { -10, 0, 1, 2, 5, 6 };
        int[] weight =    {   1, 1, 1, 1, 1, 1 };
        int target = 19;

        int minimumWeight = computeMinimumWeightNeeded(coinValue, weight, target);
        System.out.println("Minimum Weight: " + minimumWeight);
        System.out.println("Expected: 4");
    }
}
