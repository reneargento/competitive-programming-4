package chapter4.session6.section1;

import java.util.*;

/**
 * Created by Rene Argento on 05/04/24.
 */
// The solution for both CC2 and CC3 is the same.
// Solution: Dijkstra's algorithm on DAG
public class Exercise1_CC2_CC3 {

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
        List<Edge>[] adjacent = createGraph(coinValue, weight);
        int[] minimumWeights = new int[MAX_VALUE];
        Arrays.fill(minimumWeights, INFINITE);
        minimumWeights[target] = 0;

        // Topological order
        for (int value = target; value >= 0; value--) {
            for (Edge edge : adjacent[value]) {
                if (minimumWeights[edge.vertex2] > minimumWeights[value] + edge.weight) {
                    minimumWeights[edge.vertex2] = minimumWeights[value] + edge.weight;
                }
            }
        }
        return minimumWeights[0];
    }

    @SuppressWarnings("unchecked")
    private static List<Edge>[] createGraph(int[] coinValue, int[] weight) {
        List<Edge>[] adjacent = new List[MAX_VALUE];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int totalValue = 0; totalValue < adjacent.length; totalValue++) {
            for (int coinType = 0; coinType < coinValue.length; coinType++) {
                int value = coinValue[coinType];
                int neighborValue = totalValue - value;

                if (neighborValue >= 0) {
                    adjacent[totalValue].add(new Edge(neighborValue, weight[coinType]));
                }
            }
        }
        return adjacent;
    }

    public static void main(String[] args) {
        int[] coinValue = { 1, 2, 5, 6 };
        int[] weight =    { 2, 3, 3, 8 };
        int target = 19;

        int minimumWeight = computeMinimumWeightNeeded(coinValue, weight, target);
        System.out.println("Minimum Weight: " + minimumWeight);
        System.out.println("Expected: 15");
    }
}
