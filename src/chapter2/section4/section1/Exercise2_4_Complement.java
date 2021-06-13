package chapter2.section4.section1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 12/06/21.
 */
@SuppressWarnings("unchecked")
public class Exercise2_4_Complement {

    public static int[][] complementAM(int[][] adjacencyMatrix) {
        int[][] complementAM = new int[adjacencyMatrix.length][adjacencyMatrix[0].length];

        for (int row = 0; row < adjacencyMatrix.length; row++) {
            for (int column = 0; column < adjacencyMatrix[0].length; column++) {
                if (adjacencyMatrix[row][column] > 0) {
                    complementAM[row][column] = 0;
                } else {
                    complementAM[row][column] = 1;
                }
            }
        }
        return complementAM;
    }

    public static List<List<IntegerPair>> complementAL(List<List<IntegerPair>> adjacencyList) {
        List<List<IntegerPair>> complementAL = new ArrayList<>();

        for (int vertex1 = 0; vertex1 < adjacencyList.size(); vertex1++) {
            Set<Integer> neighbors = new HashSet<>();
            for (IntegerPair edge : adjacencyList.get(vertex1)) {
                neighbors.add(edge.vertex);
            }
            List<IntegerPair> complementNeighbors = new ArrayList<>();

            for (int vertex2 = 0; vertex2 < adjacencyList.size(); vertex2++) {
                if (!neighbors.contains(vertex2)) {
                    complementNeighbors.add(new IntegerPair(vertex2, 1));
                }
            }
            complementAL.add(complementNeighbors);
        }
        return complementAL;
    }

    public static List<Edge> complementEL(List<Edge> edgeList) {
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
            adjacencySet[edge.vertex1].add(edge.vertex2);
        }

        List<Edge> complementEL = new ArrayList<>();
        for (int vertex1 = 0; vertex1 < adjacencySet.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < adjacencySet.length; vertex2++) {
                if (!adjacencySet[vertex1].contains(vertex2)) {
                    complementEL.add(new Edge(vertex1, vertex2, 1));
                }
            }
        }
        return complementEL;
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

        int[][] complementAM = complementAM(adjacencyMatrix);

        System.out.println("Complement Adjacency Matrix");
        for (int row = 0; row < complementAM.length; row++) {
            for (int column = 0; column < complementAM[0].length; column++) {
                System.out.print(complementAM[row][column]);

                if (column != complementAM[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        List<List<IntegerPair>> complementAL = complementAL(adjacencyList);

        System.out.println("\nComplement Adjacency List");
        for (int vertex1 = 0; vertex1 < complementAL.size(); vertex1++) {
            System.out.print("Vertex " + vertex1 + ":");
            for (IntegerPair edge : complementAL.get(vertex1)) {
                System.out.print(" (" + edge.vertex + " weight: " + edge.weight + ")");
            }
            System.out.println();
        }

        List<Edge> complementEL = complementEL(edgeList);
        System.out.println("\nComplement Edge List");
        for (Edge edge : complementEL) {
            System.out.println(edge.vertex1 + "->" + edge.vertex2 + " weight: " + edge.weight);
        }
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
