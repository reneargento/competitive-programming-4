package chapter4.section5.section3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Rene Argento on 22/02/24.
 */
// Since the number of queries is small, we can run a BFS (or DFS) for each query.
// The optimal solution for both undirected and directed graphs is the same.
// With directed graphs we could also check if both vertices are in the same SCC, but that would not handle cases of vertices on different SCCs.
public class Exercise1 {

    private static boolean transitiveClosure(List<Integer>[] graph, int vertexID1, int vertexID2) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[graph.length];
        queue.offer(vertexID1);
        visited[vertexID1] = true;

        while (!queue.isEmpty()) {
            int vertexID = queue.poll();
            if (vertexID == vertexID2) {
                return true;
            }

            for (int neighborID : graph[vertexID]) {
                if (!visited[neighborID]) {
                    visited[neighborID] = true;
                    queue.offer(neighborID);
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // Sample graph
        List<Integer>[] graph = new List[5];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        graph[0].add(1);
        graph[1].add(2);
        graph[2].add(3);

        boolean areConnected1 = transitiveClosure(graph, 0, 1);
        boolean areConnected2 = transitiveClosure(graph, 0, 3);
        boolean areConnected3 = transitiveClosure(graph, 2, 1);
        boolean areConnected4 = transitiveClosure(graph, 0, 4);
        System.out.println("Connected 1: " + areConnected1 + " Expected: true");
        System.out.println("Connected 2: " + areConnected2 + " Expected: true");
        System.out.println("Connected 3: " + areConnected3 + " Expected: false");
        System.out.println("Connected 4: " + areConnected4 + " Expected: false");
    }
}
