package chapter3.section5.section3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 03/10/22.
 */
// Based on https://www.interviewbit.com/blog/travelling-salesman-problem/
public class Exercise1TSP {

    public static void main(String[] args) {
        int[][] distances = {
                { 0, 20, 42, 35 },
                { 20, 0, 30, 34 },
                { 42, 30, 0, 12 },
                { 35, 34, 12, 0 }
        };
        int tspDistance = computeTSPDistance(distances);
        System.out.println("Distance: " + tspDistance + " Expected: 97");
    }

    // O(2^n * n^2)
    private static int computeTSPDistance(int[][] distances) {
        int vertices = distances.length;
        int[][] dp = new int[vertices][1 << vertices];

        for (int vertex2 = 1; vertex2 < vertices; vertex2++) {
            int setWithOriginAndVertex2 = 1 | (1 << vertex2);
            dp[vertex2][setWithOriginAndVertex2] = distances[0][vertex2];
        }

        for (int numberOfBitsSet = 3; numberOfBitsSet <= vertices; numberOfBitsSet++) {
            List<Integer> sets = generateSets(vertices, numberOfBitsSet - 1);
            for (int set : sets) {
                for (int vertex2 = 1; vertex2 < vertices; vertex2++) {
                    // Vertex2 has to be in the set
                    if ((set & (1 << vertex2)) == 0) {
                        continue;
                    }
                    int subsetWithoutVertex2 = set ^ (1 << vertex2);
                    int minimumDistance = Integer.MAX_VALUE;
                    for (int vertex1 = 1; vertex1 < vertices; vertex1++) {
                        // Vertex1 has to be in the set
                        if ((subsetWithoutVertex2 & (1 << vertex1)) == 0) {
                            continue;
                        }
                        int distance = dp[vertex1][subsetWithoutVertex2] + distances[vertex1][vertex2];
                        minimumDistance = Math.min(minimumDistance, distance);
                    }
                    dp[vertex2][set] = minimumDistance;
                }
            }
        }

        int tspDistance = Integer.MAX_VALUE;
        int setWithAllVerticesVisited = (1 << vertices) - 1;
        for (int vertex2 = 1; vertex2 < vertices; vertex2++) {
            // Connect tour back to start vertex
            int completeTourDistance = dp[vertex2][setWithAllVerticesVisited] + distances[vertex2][0];
            tspDistance = Math.min(tspDistance, completeTourDistance);
        }
        return tspDistance;
    }

    // Generates all bit sets of size size where numberOfBitsSet bits are set.
    private static List<Integer> generateSets(int size, int numberOfBitsSet) {
        List<Integer> sets = new ArrayList<>();
        // Vertex 0 (origin) is always set
        generateSets(size, sets, 1, numberOfBitsSet, 1);
        return sets;
    }

    private static void generateSets(int size, List<Integer> sets, int set, int numberOfBitsToSet, int currentIndex) {
        int elementsLeftToPick = size - currentIndex;
        if (elementsLeftToPick < numberOfBitsToSet) {
            return;
        }

        if (numberOfBitsToSet == 0) {
            sets.add(set);
            return;
        }

        for (int i = currentIndex; i < size; i++) {
            // Include element
            set |= (1 << i);
            generateSets(size, sets, set, numberOfBitsToSet - 1, i + 1);
            // Backtrack and reset element
            set ^= (1 << i);
        }
    }
}
