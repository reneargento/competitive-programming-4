package chapter2.section4.section1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 12/06/21.
 */
public class Exercise2_1_CountVerticesEdges {

    public static int countVerticesAM(int[][] adjacencyMatrix) {
        return adjacencyMatrix.length;
    }

    public static int countVerticesAL(List<List<IntegerPair>> adjacencyList) {
        return adjacencyList.size();
    }

    public static int countVerticesEL(List<Edge> edgeList) {
        Set<Integer> vertices = new HashSet<>();
        for (Edge edge : edgeList) {
            vertices.add(edge.vertex1);
            vertices.add(edge.vertex2);
        }
        return vertices.size();
    }

    public static int countEdgesAM(int[][] adjacencyMatrix) {
        int edgesCount = 0;
        for (int row = 0; row < adjacencyMatrix.length; row++) {
            for (int column = 0; column < adjacencyMatrix[0].length; column++) {
                if (adjacencyMatrix[row][column] > 0) {
                    edgesCount++;
                }
            }
        }
        return edgesCount;
    }

    public static int countEdgesAL(List<List<IntegerPair>> adjacencyList) {
        int edgesCount = 0;
        for (List<IntegerPair> neighbors : adjacencyList) {
            edgesCount += neighbors.size();
        }
        return edgesCount;
    }

    public static int countEdgesEL(List<Edge> edgeList) {
        return edgeList.size();
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

        int verticesAM = countVerticesAM(adjacencyMatrix);
        int verticesAL = countVerticesAL(adjacencyList);
        int verticesEL = countVerticesEL(edgeList);

        System.out.println("Vertices count. Expected: 7");
        System.out.println("Adjacency matrix: " + verticesAM);
        System.out.println("Adjacency list: " + verticesAL);
        System.out.println("Edge list: " + verticesEL);

        int edgesAM = countEdgesAM(adjacencyMatrix);
        int edgesAL = countEdgesAL(adjacencyList);
        int edgesEL = countEdgesEL(edgeList);

        System.out.println("\nEdges count. Expected: 16");
        System.out.println("Adjacency matrix: " + edgesAM);
        System.out.println("Adjacency list: " + edgesAL);
        System.out.println("Edge list: " + edgesEL);
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
