package chapter4.section2.section8;

import java.util.*;

/**
 * Created by Rene Argento on 05/02/23.
 */
@SuppressWarnings("unchecked")
// The closest BFS modification to detect cycles that can be made is to modify Khan's algorithm.
// It can be used for undirected and directed graphs.
public class Exercise3 {

    //  Graph 1 (undirected)     Graph 2 (directed)
    //  0 - 1 - 2                 0 -> 1 -> 2 -> 3
    //  |   |                          |    |   ^
    //  |   |                          v    v  /
    //  3 - 4                          4    5
    public static void main(String[] args) {
        List<Integer>[] graph1 = createGraph(5);
        graph1[0].add(1);
        graph1[0].add(3);
        graph1[1].add(0);
        graph1[1].add(2);
        graph1[2].add(1);
        graph1[3].add(0);
        graph1[3].add(4);
        graph1[4].add(1);
        graph1[4].add(3);
        boolean hasCycle1 = cycleCheck(graph1);
        System.out.println("Has cycle: " + hasCycle1 + " Expected: true");

        List<Integer>[] graph2 = createGraph(6);
        graph2[0].add(1);
        graph2[1].add(2);
        graph2[1].add(4);
        graph2[2].add(3);
        graph2[2].add(5);
        graph2[5].add(3);
        boolean hasCycle2 = cycleCheck(graph2);
        System.out.println("Has cycle: " + hasCycle2 + " Expected: false");
    }

    private static List<Integer>[] createGraph(int size) {
        List<Integer>[] graph = new List[size];
        for (int vertexID = 0; vertexID < graph.length; vertexID++) {
            graph[vertexID] = new ArrayList<>();
        }
        return graph;
    }

    private static boolean cycleCheck(List<Integer>[] graph) {
        Queue<Integer> queue = new LinkedList<>();
        int verticesProcessed = 0;
        int[] inDegrees = computeInDegrees(graph);

        for (int vertexID = 0; vertexID < graph.length; vertexID++) {
            if (inDegrees[vertexID] == 0) {
                queue.offer(vertexID);
            }
        }

        while (!queue.isEmpty()) {
            int vertexID = queue.poll();

            for (int neighborVertexID : graph[vertexID]) {
                inDegrees[neighborVertexID]--;
                if (inDegrees[neighborVertexID] == 0) {
                    queue.offer(neighborVertexID);
                }
            }
            verticesProcessed++;
        }
        return verticesProcessed != graph.length;
    }

    private static int[] computeInDegrees(List<Integer>[] graph) {
        int[] inDegrees = new int[graph.length];
        for (int vertexID = 0; vertexID < graph.length; vertexID++) {
            for (int neighborVertexID : graph[vertexID]) {
                inDegrees[neighborVertexID]++;
            }
        }
        return inDegrees;
    }
}
