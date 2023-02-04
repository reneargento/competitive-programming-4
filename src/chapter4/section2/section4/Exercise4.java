package chapter4.section2.section4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 02/02/23.
 */
@SuppressWarnings("unchecked")
public class Exercise4 {

    private static class UnionFind {
        private final int[] leaders;
        private final int[] ranks;
        private final int[] sizes;
        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            sizes = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                ranks[i] = 0;
                sizes[i] = 1;
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        // O(α(n)), where α is the inverse Ackermann function.
        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }
            return leaders[site] = find(leaders[site]);
        }

        // O(α(n)), where α is the inverse Ackermann function.
        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
                sizes[leader1] += sizes[leader2];
            } else {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
                ranks[leader2]++;
            }
            components--;
        }

        public int size(int set) {
            int leader = find(set);
            return sizes[leader];
        }
    }

    public static void main(String[] args) {
        List<Integer>[] graph1 = createGraph(6);
        graph1[1].add(2);
        graph1[1].add(3);
        graph1[2].add(1);
        graph1[2].add(3);
        graph1[2].add(5);
        graph1[3].add(1);
        graph1[3].add(2);
        graph1[3].add(4);
        graph1[3].add(5);
        graph1[4].add(3);
        graph1[5].add(2);
        graph1[5].add(3);
        int[] verticesToRemove1 = { 1, 3, 4, 5, 2 };
        int[] componentsNumber1 = computeComponentsAfterRemoves(graph1, verticesToRemove1);
        int[] expected1 = { 1, 2, 1, 1, 0 };
        printResults(componentsNumber1, expected1);

        List<Integer>[] graph2 = createGraph(6);
        graph2[1].add(3);
        graph2[2].add(3);
        graph2[3].add(1);
        graph2[3].add(2);
        graph2[3].add(4);
        graph2[3].add(5);
        graph2[4].add(3);
        graph2[5].add(3);
        int[] verticesToRemove2 = { 3, 1, 2 };
        int[] componentsNumber2 = computeComponentsAfterRemoves(graph2, verticesToRemove2);
        int[] expected2 = { 4, 3, 2 };
        printResults(componentsNumber2, expected2);

        int[] verticesToRemove3 = { 1, 2, 3 };
        int[] componentsNumber3 = computeComponentsAfterRemoves(graph2, verticesToRemove3);
        int[] expected3 = { 1, 1, 2 };
        printResults(componentsNumber3, expected3);
    }

    private static List<Integer>[] createGraph(int size) {
        List<Integer>[] graph = new List[size];
        for (int vertexID = 0; vertexID < graph.length; vertexID++) {
            graph[vertexID] = new ArrayList<>();
        }
        return graph;
    }

    private static void printResults(int[] componentsNumber, int[] expected) {
        for (int i = 0; i < componentsNumber.length; i++) {
            System.out.println("Components number: " + componentsNumber[i] + " Expected: " + expected[i]);
        }
    }

    private static int[] computeComponentsAfterRemoves(List<Integer>[] graph, int[] verticesToRemove) {
        int[] componentsNumber = new int[verticesToRemove.length];
        Set<Integer> verticesToRemoveSet = getVerticesToRemoveSet(verticesToRemove);
        UnionFind unionFind = computeFinalGraph(graph, verticesToRemoveSet);

        for (int i = verticesToRemove.length - 1; i >= 0; i--) {
            componentsNumber[i] = (unionFind.components - 1) - verticesToRemoveSet.size();
            int vertexID = verticesToRemove[i];
            for (int neighborVertexID : graph[vertexID]) {
                if (!verticesToRemoveSet.contains(neighborVertexID)) {
                    unionFind.union(vertexID, neighborVertexID);
                }
            }
            verticesToRemoveSet.remove(vertexID);
        }
        return componentsNumber;
    }

    private static Set<Integer> getVerticesToRemoveSet(int[] verticesToRemove) {
        Set<Integer> verticesToRemoveSet = new HashSet<>();
        for (int vertex : verticesToRemove) {
            verticesToRemoveSet.add(vertex);
        }
        return verticesToRemoveSet;
    }

    private static UnionFind computeFinalGraph(List<Integer>[] graph, Set<Integer> verticesToRemoveSet) {
        UnionFind unionFind = new UnionFind(graph.length);

        for (int vertexID = 0; vertexID < graph.length; vertexID++) {
            if (!verticesToRemoveSet.contains(vertexID)) {
                for (int neighborVertexID : graph[vertexID]) {
                    if (!verticesToRemoveSet.contains(neighborVertexID)) {
                        unionFind.union(vertexID, neighborVertexID);
                    }
                }
            }
        }
        return unionFind;
    }
}
