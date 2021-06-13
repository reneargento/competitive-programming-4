package chapter2.section4.section1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 12/06/21.
 */
public class Exercise2_7_CheckStar {

    public static boolean isStarAM(int[][] adjacencyMatrix) {
        int vertices = adjacencyMatrix.length;
        int oneInDegree = 0;
        int vMinus1InDegree = 0;

        for (int vertex2 = 0; vertex2 < adjacencyMatrix[0].length; vertex2++) {
            int inDegree = 0;

            for (int vertex1 = 0; vertex1 < adjacencyMatrix.length; vertex1++) {
                if (adjacencyMatrix[vertex1][vertex2] > 0) {
                    if (vertex1 == vertex2) {
                        return false;
                    }
                    inDegree++;
                }
            }
            if (inDegree == 1) {
                oneInDegree++;
            } else if (inDegree == vertices - 1) {
                vMinus1InDegree++;
            } else {
                return false;
            }
        }
        return oneInDegree == (vertices - 1) && vMinus1InDegree == 1;
    }

    public static boolean isStarAL(List<List<IntegerPair>> adjacencyList) {
        int vertices = adjacencyList.size();
        int[] inDegrees = new int[vertices];

        for (int vertex1 = 0; vertex1 < adjacencyList.size(); vertex1++) {
            for (IntegerPair edge : adjacencyList.get(vertex1)) {
                if (vertex1 == edge.vertex) {
                    return false;
                }
                inDegrees[edge.vertex]++;
            }
        }

        int oneInDegree = 0;
        int vMinus1InDegree = 0;

        for (int inDegree : inDegrees) {
            if (inDegree == 1) {
                oneInDegree++;
            } else if (inDegree == vertices - 1) {
                vMinus1InDegree++;
            } else {
                return false;
            }
        }
        return oneInDegree == (vertices - 1) && vMinus1InDegree == 1;
    }

    public static boolean isStarEL(List<Edge> edgeList) {
        Set<Integer> vertices = new HashSet<>();
        for (Edge edge : edgeList) {
            vertices.add(edge.vertex1);
            vertices.add(edge.vertex2);
        }
        int verticesNumber = vertices.size();
        int[] inDegrees = new int[verticesNumber];

        for (Edge edge : edgeList) {
            if (edge.vertex1 == edge.vertex2) {
                return false;
            }
            inDegrees[edge.vertex2]++;
        }

        int oneInDegree = 0;
        int vMinus1InDegree = 0;

        for (int inDegree : inDegrees) {
            if (inDegree == 1) {
                oneInDegree++;
            } else if (inDegree == verticesNumber - 1) {
                vMinus1InDegree++;
            } else {
                return false;
            }
        }
        return oneInDegree == (verticesNumber - 1) && vMinus1InDegree == 1;
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

        int[][] adjacencyMatrixStar = {
                {0, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0}
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

        List<List<IntegerPair>> adjacencyListStar = new ArrayList<>();
        List<IntegerPair> rowStar0 = new ArrayList<>();
        for (int vertex2 = 1; vertex2 < 7; vertex2++) {
            rowStar0.add(new IntegerPair(vertex2, 1));
        }
        adjacencyListStar.add(rowStar0);
        for (int i = 1; i < 7; i++) {
            List<IntegerPair> row = new ArrayList<>();
            row.add(new IntegerPair(0, 1));
            adjacencyListStar.add(row);
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

        List<Edge> edgeListStar = new ArrayList<>();
        for (int vertex2 = 1; vertex2 < 7; vertex2++) {
            edgeListStar.add(new Edge(0, vertex2, 1));
        }
        for (int vertex1 = 1; vertex1 < 7; vertex1++) {
            edgeListStar.add(new Edge(vertex1, 0, 1));
        }

        System.out.println("Expected: not star");
        boolean isStarAM1 = isStarAM(adjacencyMatrix);
        System.out.print("Adjacency Matrix ");
        System.out.println(isStarAM1 ? "star" : "not star");

        boolean isStarAL1 = isStarAL(adjacencyList);
        System.out.print("Adjacency List ");
        System.out.println(isStarAL1 ? "star" : "not star");

        boolean isStarEL1 = isStarEL(edgeList);
        System.out.print("Edge List ");
        System.out.println(isStarEL1 ? "star" : "not star");

        System.out.println("\nExpected: star");
        boolean isStarAM2 = isStarAM(adjacencyMatrixStar);
        System.out.print("Adjacency Matrix ");
        System.out.println(isStarAM2 ? "star" : "not star");

        boolean isStarAL2 = isStarAL(adjacencyListStar);
        System.out.print("Adjacency List ");
        System.out.println(isStarAL2 ? "star" : "not star");

        boolean isStarEL2 = isStarEL(edgeListStar);
        System.out.print("Edge List ");
        System.out.println(isStarEL2 ? "star" : "not star");
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
