package chapter2.section4.section1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 12/06/21.
 */
@SuppressWarnings("unchecked")
public class Exercise2_5_CheckComplete {

    public static boolean isCompleteAM(int[][] adjacencyMatrix) {
        for (int vertex1 = 0; vertex1 < adjacencyMatrix.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < adjacencyMatrix[0].length; vertex2++) {
                if ((vertex1 != vertex2 && adjacencyMatrix[vertex1][vertex2] == 0)
                        || (vertex1 == vertex2 && adjacencyMatrix[vertex1][vertex2] > 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isCompleteAL(List<List<IntegerPair>> adjacencyList) {
        for (int vertex1 = 0; vertex1 < adjacencyList.size(); vertex1++) {
            Set<Integer> neighbors = new HashSet<>();
            for (IntegerPair edge : adjacencyList.get(vertex1)) {
                neighbors.add(edge.vertex);
            }
            if (neighbors.size() != adjacencyList.size() - 1) {
                return false;
            }

            for (IntegerPair edge : adjacencyList.get(vertex1)) {
                if (vertex1 == edge.vertex) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isCompleteEL(List<Edge> edgeList) {
        Set<Integer> vertices = new HashSet<>();
        for (Edge edge : edgeList) {
            vertices.add(edge.vertex1);
            vertices.add(edge.vertex2);
        }
        int verticesNumber = vertices.size();

        Set<Integer>[] adjacencySet = new HashSet[verticesNumber];
        for (int i = 0; i < adjacencySet.length; i++) {
            adjacencySet[i] = new HashSet<>();
        }

        for (Edge edge : edgeList) {
            if (edge.vertex1 == edge.vertex2) {
                return false;
            }
            adjacencySet[edge.vertex1].add(edge.vertex2);
        }

        for (Set<Integer> neighbors : adjacencySet) {
            if (neighbors.size() != verticesNumber - 1) {
                return false;
            }
        }
        return true;
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

        int[][] adjacencyMatrixComplete = {
                {0, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 1, 1},
                {1, 1, 0, 1, 1, 1, 1},
                {1, 1, 1, 0, 1, 1, 1},
                {1, 1, 1, 1, 0, 1, 1},
                {1, 1, 1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 0}
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

        List<List<IntegerPair>> adjacencyListComplete = new ArrayList<>();
        for (int vertex1 = 0; vertex1 < 7; vertex1++) {
            adjacencyListComplete.add(new ArrayList<>());
            for (int vertex2 = 0; vertex2 < 7; vertex2++) {
                if (vertex1 == vertex2) {
                    continue;
                }
                adjacencyListComplete.get(vertex1).add(new IntegerPair(vertex2, 1));
            }
        }

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

        List<Edge> edgeListComplete = new ArrayList<>();
        for (int vertex1 = 0; vertex1 < 7; vertex1++) {
            for (int vertex2 = 0; vertex2 < 7; vertex2++) {
                if (vertex1 == vertex2) {
                    continue;
                }
                edgeListComplete.add(new Edge(vertex1, vertex2, 1));
            }
        }

        System.out.println("Expected: not complete");
        boolean isCompleteAM1 = isCompleteAM(adjacencyMatrix);
        System.out.print("Adjacency Matrix ");
        System.out.println(isCompleteAM1 ? "complete" : "not complete");

        boolean isCompleteAL1 = isCompleteAL(adjacencyList);
        System.out.print("Adjacency List ");
        System.out.println(isCompleteAL1 ? "complete" : "not complete");

        boolean isCompleteEL1 = isCompleteEL(edgeList);
        System.out.print("Edge List ");
        System.out.println(isCompleteEL1 ? "complete" : "not complete");

        System.out.println("\nExpected: complete");
        boolean isCompleteAM2 = isCompleteAM(adjacencyMatrixComplete);
        System.out.print("Adjacency Matrix ");
        System.out.println(isCompleteAM2 ? "complete" : "not complete");

        boolean isCompleteAL2 = isCompleteAL(adjacencyListComplete);
        System.out.print("Adjacency List ");
        System.out.println(isCompleteAL2 ? "complete" : "not complete");

        boolean isCompleteEL2 = isCompleteEL(edgeListComplete);
        System.out.print("Edge List ");
        System.out.println(isCompleteEL2 ? "complete" : "not complete");
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
