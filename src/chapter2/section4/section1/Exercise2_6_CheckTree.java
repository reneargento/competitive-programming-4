package chapter2.section4.section1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 12/06/21.
 */
public class Exercise2_6_CheckTree {

    public static boolean isTreeAM(int[][] adjacencyMatrix) {
        int vertices = adjacencyMatrix.length;
        int edges = 0;

        for (int vertex2 = 0; vertex2 < adjacencyMatrix[0].length; vertex2++) {
            int inDegree = 0;

            for (int vertex1 = 0; vertex1 < adjacencyMatrix.length; vertex1++) {
                if (adjacencyMatrix[vertex1][vertex2] > 0) {
                    if (vertex1 == vertex2) {
                        return false;
                    }
                    edges++;
                    inDegree++;
                }
            }
            if (inDegree == 0) {
                return false;
            }
        }
        edges /= 2;
        return edges == (vertices - 1);
    }

    public static boolean isTreeAL(List<List<IntegerPair>> adjacencyList) {
        int vertices = adjacencyList.size();
        int edges = 0;
        int[] inDegrees = new int[vertices];

        for (int vertex1 = 0; vertex1 < adjacencyList.size(); vertex1++) {
            edges += adjacencyList.get(vertex1).size();
            for (IntegerPair edge : adjacencyList.get(vertex1)) {
                if (vertex1 == edge.vertex) {
                    return false;
                }
                inDegrees[edge.vertex]++;
            }
        }

        for (int inDegree : inDegrees) {
            if (inDegree == 0) {
                return false;
            }
        }
        edges /= 2;
        return edges == (vertices - 1);
    }

    public static boolean isTreeEL(List<Edge> edgeList) {
        Set<Integer> vertices = new HashSet<>();
        for (Edge edge : edgeList) {
            vertices.add(edge.vertex1);
            vertices.add(edge.vertex2);
        }
        int verticesNumber = vertices.size();
        int edges = edgeList.size() / 2;
        int[] inDegrees = new int[verticesNumber];

        for (Edge edge : edgeList) {
            if (edge.vertex1 == edge.vertex2) {
                return false;
            }
            inDegrees[edge.vertex2]++;
        }

        for (int inDegree : inDegrees) {
            if (inDegree == 0) {
                return false;
            }
        }
        return edges == (verticesNumber - 1);
    }

    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
                {0, 1, 1, 0, 0, 0, 0},
                {1, 0, 1, 1, 0, 0, 0},
                {1, 1, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 0, 0},
                {0, 0, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 1, 0}
        };

        int[][] adjacencyMatrixTree = {
                {0, 1, 0, 0, 0, 0, 0},
                {1, 0, 1, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 1, 1, 0, 1},
                {0, 0, 0, 0, 0, 1, 0}
        };

        List<List<IntegerPair>> adjacencyList = new ArrayList<>();
        List<IntegerPair> row0 = new ArrayList<>();
        row0.add(new IntegerPair(1, 1)); row0.add(new IntegerPair(2, 1));
        List<IntegerPair> row1 = new ArrayList<>();
        row1.add(new IntegerPair(0, 1)); row1.add(new IntegerPair(2, 1)); row1.add(new IntegerPair(3, 1));
        List<IntegerPair> row2 = new ArrayList<>();
        row2.add(new IntegerPair(0, 1)); row2.add(new IntegerPair(1, 1)); row2.add(new IntegerPair(4, 1));
        List<IntegerPair> row3 = new ArrayList<>();
        row3.add(new IntegerPair(1, 1)); row3.add(new IntegerPair(4, 1));
        List<IntegerPair> row4 = new ArrayList<>();
        row4.add(new IntegerPair(2, 1)); row4.add(new IntegerPair(3, 1)); row4.add(new IntegerPair(5, 1));
        List<IntegerPair> row5 = new ArrayList<>();
        row5.add(new IntegerPair(4, 1)); row5.add(new IntegerPair(6, 1));
        List<IntegerPair> row6 = new ArrayList<>();
        row6.add(new IntegerPair(5, 1));
        adjacencyList.add(row0);
        adjacencyList.add(row1);
        adjacencyList.add(row2);
        adjacencyList.add(row3);
        adjacencyList.add(row4);
        adjacencyList.add(row5);
        adjacencyList.add(row6);

        List<List<IntegerPair>> adjacencyListTree = new ArrayList<>();
        List<IntegerPair> rowTree0 = new ArrayList<>();
        rowTree0.add(new IntegerPair(1, 1));
        List<IntegerPair> rowTree1 = new ArrayList<>();
        rowTree1.add(new IntegerPair(0, 1)); rowTree1.add(new IntegerPair(2, 1)); rowTree1.add(new IntegerPair(3, 1));
        List<IntegerPair> rowTree2 = new ArrayList<>();
        rowTree2.add(new IntegerPair(1, 1));
        List<IntegerPair> rowTree3 = new ArrayList<>();
        rowTree3.add(new IntegerPair(1, 1)); rowTree3.add(new IntegerPair(5, 1));
        List<IntegerPair> rowTree4 = new ArrayList<>();
        rowTree4.add(new IntegerPair(5, 1));
        List<IntegerPair> rowTree5 = new ArrayList<>();
        rowTree5.add(new IntegerPair(3, 1)); rowTree5.add(new IntegerPair(4, 1)); rowTree5.add(new IntegerPair(6, 1));
        List<IntegerPair> rowTree6 = new ArrayList<>();
        rowTree6.add(new IntegerPair(5, 1));
        adjacencyListTree.add(rowTree0);
        adjacencyListTree.add(rowTree1);
        adjacencyListTree.add(rowTree2);
        adjacencyListTree.add(rowTree3);
        adjacencyListTree.add(rowTree4);
        adjacencyListTree.add(rowTree5);
        adjacencyListTree.add(rowTree6);

        List<Edge> edgeList = new ArrayList<>();
        edgeList.add(new Edge(0, 1, 1));
        edgeList.add(new Edge(0, 2, 1));
        edgeList.add(new Edge(1, 0, 1));
        edgeList.add(new Edge(1, 2, 1));
        edgeList.add(new Edge(1, 3, 1));
        edgeList.add(new Edge(2, 0, 1));
        edgeList.add(new Edge(2, 1, 1));
        edgeList.add(new Edge(2, 4, 1));
        edgeList.add(new Edge(3, 1, 1));
        edgeList.add(new Edge(3, 4, 1));
        edgeList.add(new Edge(4, 2, 1));
        edgeList.add(new Edge(4, 3, 1));
        edgeList.add(new Edge(4, 5, 1));
        edgeList.add(new Edge(5, 4, 1));
        edgeList.add(new Edge(5, 6, 1));
        edgeList.add(new Edge(6, 5, 1));

        List<Edge> edgeListTree = new ArrayList<>();
        edgeListTree.add(new Edge(0, 1, 1));
        edgeListTree.add(new Edge(1, 0, 1));
        edgeListTree.add(new Edge(1, 2, 1));
        edgeListTree.add(new Edge(1, 3, 1));
        edgeListTree.add(new Edge(2, 1, 1));
        edgeListTree.add(new Edge(3, 1, 1));
        edgeListTree.add(new Edge(3, 5, 1));
        edgeListTree.add(new Edge(4, 5, 1));
        edgeListTree.add(new Edge(5, 3, 1));
        edgeListTree.add(new Edge(5, 4, 1));
        edgeListTree.add(new Edge(5, 6, 1));
        edgeListTree.add(new Edge(6, 5, 1));

        System.out.println("Expected: not tree");
        boolean isTreeAM1 = isTreeAM(adjacencyMatrix);
        System.out.print("Adjacency Matrix ");
        System.out.println(isTreeAM1 ? "tree" : "not tree");

        boolean isTreeAL1 = isTreeAL(adjacencyList);
        System.out.print("Adjacency List ");
        System.out.println(isTreeAL1 ? "tree" : "not tree");

        boolean isTreeEL1 = isTreeEL(edgeList);
        System.out.print("Edge List ");
        System.out.println(isTreeEL1 ? "tree" : "not tree");

        System.out.println("\nExpected: tree");
        boolean isTreeAM2 = isTreeAM(adjacencyMatrixTree);
        System.out.print("Adjacency Matrix ");
        System.out.println(isTreeAM2 ? "tree" : "not tree");

        boolean isTreeAL2 = isTreeAL(adjacencyListTree);
        System.out.print("Adjacency List ");
        System.out.println(isTreeAL2 ? "tree" : "not tree");

        boolean isTreeEL2 = isTreeEL(edgeListTree);
        System.out.print("Edge List ");
        System.out.println(isTreeEL2 ? "tree" : "not tree");
    }

    private static class Edge {
        int vertex1;
        int vertex2;
        int weight;

        public Edge(int vertex1, int vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private static class IntegerPair {
        int vertex;
        int weight;

        public IntegerPair(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }
}
