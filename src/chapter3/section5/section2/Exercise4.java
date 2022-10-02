package chapter3.section5.section2;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Rene Argento on 02/10/22.
 */
// Slightly improving the Held-Karp algorithm by adding pruning based on the current best tour found.
public class Exercise4 {

    public static void main(String[] args) {
        Random random = new Random();
        int[][] distances = new int[20][20];
        for (int row = 0; row < distances.length; row++) {
            for (int column = row + 1; column < distances[0].length; column++) {
                int distance = random.nextInt(100000);
                distances[row][column] = distance;
                distances[column][row] = distance;
            }
        }

        long startTime = System.currentTimeMillis();
        int tspDistance = computeTSPDistance(distances);
        double seconds = (System.currentTimeMillis() - startTime) / 1000.0;
        // Note that the execution time in C++ would be much smaller
        System.out.println("Time: " + seconds + " seconds");
        System.out.println("Distance: " + tspDistance);
    }

    private static int currentMinTourDistance = Integer.MAX_VALUE;
    private static final int MAX_INT = 1000000000;

    // O(2^n * n^2)
    private static int computeTSPDistance(int[][] distances) {
        int maxBitmaskSize = (int) Math.pow(2, distances.length);
        int[][] dp = new int[distances.length][maxBitmaskSize];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        // Tour starts at vertex 0
        int initialBitmask = 1;
        return computeTSPDistance(distances, dp, 0, initialBitmask, 0);
    }

    // Computes the minimum cost of the tour if we are at vertex and have visited the vertices
    // that are marked as 1 in the bitmask
    private static int computeTSPDistance(int[][] distances, int[][] dp, int vertex, int bitmask,
                                          int currentDistance) {
        if (bitmask == (1 << (distances.length)) - 1) {
            currentMinTourDistance = Math.min(currentMinTourDistance, currentDistance + distances[vertex][0]);
            // Close the tour
            return distances[vertex][0];
        }

        int minimumDistance = dp[vertex][bitmask];
        if (minimumDistance != -1) {
            return minimumDistance;
        }

        minimumDistance = MAX_INT;
        for (int nextVertex = 0; nextVertex < distances.length; nextVertex++) {
            if ((bitmask & (1 << nextVertex)) == 0) {
                if (currentDistance + distances[vertex][nextVertex] < currentMinTourDistance) {
                    int newMask = bitmask | (1 << nextVertex); // set bit
                    int newDistance = distances[vertex][nextVertex] +
                            computeTSPDistance(distances, dp, nextVertex, newMask,
                                    currentDistance + distances[vertex][nextVertex]);
                    minimumDistance = Math.min(minimumDistance, newDistance);
                }
            }
        }
        dp[vertex][bitmask] = minimumDistance;
        return minimumDistance;
    }
}
