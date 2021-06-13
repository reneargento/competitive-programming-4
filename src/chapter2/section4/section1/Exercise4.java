package chapter2.section4.section1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 13/06/21.
 */
public class Exercise4 {

    private static List<List<IntegerPair>> convertAMtoAL(int[][] adjacencyMatrix) {
        List<List<IntegerPair>> adjacencyList = new ArrayList<>();

        for (int vertex1 = 0; vertex1 < adjacencyMatrix.length; vertex1++) {
            List<IntegerPair> edges = new ArrayList<>();

            for (int vertex2 = 0; vertex2 < adjacencyMatrix[0].length; vertex2++) {
                if (adjacencyMatrix[vertex1][vertex2] > 0) {
                    edges.add(new IntegerPair(vertex2, adjacencyMatrix[vertex1][vertex2]));
                }
            }
            adjacencyList.add(edges);
        }
        return adjacencyList;
    }

    private static List<Edge> convertAMtoEL(int[][] adjacencyMatrix) {
        List<Edge> edgeList = new ArrayList<>();

        for (int vertex1 = 0; vertex1 < adjacencyMatrix.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < adjacencyMatrix[0].length; vertex2++) {
                if (adjacencyMatrix[vertex1][vertex2] > 0) {
                    edgeList.add(new Edge(vertex1, vertex2, adjacencyMatrix[vertex1][vertex2]));
                }
            }
        }
        return edgeList;
    }

    private static int[][] convertALtoAM(List<List<IntegerPair>> adjacencyList) {
        int vertices = adjacencyList.size();
        int[][] adjacencyMatrix = new int[vertices][vertices];

        for (int vertex1 = 0; vertex1 < adjacencyList.size(); vertex1++) {
            for (IntegerPair edge : adjacencyList.get(vertex1)) {
                adjacencyMatrix[vertex1][edge.vertex] = edge.weight;
            }
        }
        return adjacencyMatrix;
    }

    private static List<Edge> convertALtoEL(List<List<IntegerPair>> adjacencyList) {
        List<Edge> edgeList = new ArrayList<>();

        for (int vertex1 = 0; vertex1 < adjacencyList.size(); vertex1++) {
            for (IntegerPair edge : adjacencyList.get(vertex1)) {
                edgeList.add(new Edge(vertex1, edge.vertex, edge.weight));
            }
        }
        return edgeList;
    }

    private static int[][] convertELtoAM(List<Edge> edgeList) {
        Set<Integer> uniqueVertices = new HashSet<>();
        for (Edge edge : edgeList) {
            uniqueVertices.add(edge.vertex1);
            uniqueVertices.add(edge.vertex2);
        }

        int vertices = uniqueVertices.size();
        int[][] adjacencyMatrix = new int[vertices][vertices];

        for (Edge edge : edgeList) {
            adjacencyMatrix[edge.vertex1][edge.vertex2] = edge.weight;
        }
        return adjacencyMatrix;
    }

    private static List<List<IntegerPair>> convertELtoAL(List<Edge> edgeList) {
        Set<Integer> uniqueVertices = new HashSet<>();
        for (Edge edge : edgeList) {
            uniqueVertices.add(edge.vertex1);
            uniqueVertices.add(edge.vertex2);
        }

        List<List<IntegerPair>> adjacencyList = new ArrayList<>();
        for (int vertex = 0; vertex < uniqueVertices.size(); vertex++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (Edge edge : edgeList) {
            adjacencyList.get(edge.vertex1).add(new IntegerPair(edge.vertex2, edge.weight));
        }
        return adjacencyList;
    }

    public static void main(String[] args) {
        System.out.println("Original Adjacency Matrix");
        int[][] adjacencyMatrix = {
                {0, 1, 1, 0, 0, 0, 0},
                {1, 0, 1, 1, 0, 0, 0},
                {1, 1, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 0, 0},
                {0, 0, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 1, 0}
        };
        printAdjacencyMatrix(adjacencyMatrix);

        System.out.println("\nAdjacency Matrix -> Adjacency List");
        List<List<IntegerPair>> adjacencyList = convertAMtoAL(adjacencyMatrix);
        printAdjacencyList(adjacencyList);

        System.out.println("\nAdjacency Matrix -> Edge List");
        List<Edge> edgeList = convertAMtoEL(adjacencyMatrix);
        printEdgeList(edgeList);

        System.out.println("\nAdjacency List -> Adjacency Matrix");
        int[][] adjacencyMatrix2 = convertALtoAM(adjacencyList);
        printAdjacencyMatrix(adjacencyMatrix2);

        System.out.println("\nAdjacency List -> Edge List");
        List<Edge> edgeList2 = convertALtoEL(adjacencyList);
        printEdgeList(edgeList2);

        System.out.println("\nEdge List -> Adjacency Matrix");
        int[][] adjacencyMatrix3 = convertELtoAM(edgeList);
        printAdjacencyMatrix(adjacencyMatrix3);

        System.out.println("\nEdge Matrix -> Adjacency List");
        List<List<IntegerPair>> adjacencyList2 = convertELtoAL(edgeList);
        printAdjacencyList(adjacencyList2);
    }

    private static void printAdjacencyMatrix(int[][] adjacencyMatrix) {
        for (int row = 0; row < adjacencyMatrix.length; row++) {
            for (int column = 0; column < adjacencyMatrix[0].length; column++) {
                System.out.print(adjacencyMatrix[row][column]);

                if (column != adjacencyMatrix[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static void printAdjacencyList(List<List<IntegerPair>> adjacencyList) {
        for (int vertex1 = 0; vertex1 < adjacencyList.size(); vertex1++) {
            System.out.print("Vertex " + vertex1 + ":");
            for (IntegerPair edge : adjacencyList.get(vertex1)) {
                System.out.print(" (" + edge.vertex + " weight: " + edge.weight + ")");
            }
            System.out.println();
        }
    }

    private static void printEdgeList(List<Edge> edgeList) {
        for (Edge edge : edgeList) {
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
