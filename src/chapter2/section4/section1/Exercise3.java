package chapter2.section4.section1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 13/06/21.
 */
public class Exercise3 {

    public static void main(String[] args) {
        System.out.println("Figure 4.1 graphs");
        createFigure4_1Graphs();

        System.out.println("\nFigure 4.8 graphs");
        createFigure4_8Graphs();
    }

    private static void createFigure4_1Graphs() {
        int[][] adjacencyMatrix = {
                {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0}
        };

        List<List<IntegerPair>> adjacencyList = new ArrayList<>();
        List<IntegerPair> row0 = new ArrayList<>();
        row0.add(new IntegerPair(1, 1));
        List<IntegerPair> row1 = new ArrayList<>();
        row1.add(new IntegerPair(0, 1)); row1.add(new IntegerPair(2, 1)); row1.add(new IntegerPair(3, 1));
        List<IntegerPair> row2 = new ArrayList<>();
        row2.add(new IntegerPair(1, 1)); row2.add(new IntegerPair(3, 1));
        List<IntegerPair> row3 = new ArrayList<>();
        row3.add(new IntegerPair(1, 1)); row3.add(new IntegerPair(2, 1)); row3.add(new IntegerPair(4, 1));
        List<IntegerPair> row4 = new ArrayList<>();
        row4.add(new IntegerPair(3, 1));
        List<IntegerPair> row5 = new ArrayList<>();
        List<IntegerPair> row6 = new ArrayList<>();
        row6.add(new IntegerPair(7, 1)); row6.add(new IntegerPair(8, 1));
        List<IntegerPair> row7 = new ArrayList<>();
        row7.add(new IntegerPair(6, 1));
        List<IntegerPair> row8 = new ArrayList<>();
        row8.add(new IntegerPair(6, 1));
        adjacencyList.add(row0);
        adjacencyList.add(row1);
        adjacencyList.add(row2);
        adjacencyList.add(row3);
        adjacencyList.add(row4);
        adjacencyList.add(row5);
        adjacencyList.add(row6);
        adjacencyList.add(row7);
        adjacencyList.add(row8);

        List<Edge> edgeList = new ArrayList<>();
        edgeList.add(new Edge(0, 1, 1));
        edgeList.add(new Edge(1, 0, 1));
        edgeList.add(new Edge(1, 2, 1));
        edgeList.add(new Edge(1, 3, 1));
        edgeList.add(new Edge(2, 1, 1));
        edgeList.add(new Edge(2, 3, 1));
        edgeList.add(new Edge(3, 1, 1));
        edgeList.add(new Edge(3, 2, 1));
        edgeList.add(new Edge(3, 4, 1));
        edgeList.add(new Edge(4, 3, 1));
        edgeList.add(new Edge(6, 7, 1));
        edgeList.add(new Edge(6, 8, 1));
        edgeList.add(new Edge(7, 6, 1));
        edgeList.add(new Edge(8, 6, 1));

        printAdjacencyMatrix(adjacencyMatrix);
        printAdjacencyList(adjacencyList);
        printEdgeList(edgeList);
    }

    private static void createFigure4_8Graphs() {
        int[][] adjacencyMatrix = {
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0}
        };

        List<List<IntegerPair>> adjacencyList = new ArrayList<>();
        List<IntegerPair> row0 = new ArrayList<>();
        row0.add(new IntegerPair(1, 1));
        List<IntegerPair> row1 = new ArrayList<>();
        row1.add(new IntegerPair(3, 1));
        List<IntegerPair> row2 = new ArrayList<>();
        row2.add(new IntegerPair(1, 1));
        List<IntegerPair> row3 = new ArrayList<>();
        row3.add(new IntegerPair(2, 1)); row3.add(new IntegerPair(4, 1));
        List<IntegerPair> row4 = new ArrayList<>();
        row4.add(new IntegerPair(5, 1));
        List<IntegerPair> row5 = new ArrayList<>();
        row5.add(new IntegerPair(7, 1));
        List<IntegerPair> row6 = new ArrayList<>();
        row6.add(new IntegerPair(4, 1));
        List<IntegerPair> row7 = new ArrayList<>();
        row7.add(new IntegerPair(6, 1));
        adjacencyList.add(row0);
        adjacencyList.add(row1);
        adjacencyList.add(row2);
        adjacencyList.add(row3);
        adjacencyList.add(row4);
        adjacencyList.add(row5);
        adjacencyList.add(row6);
        adjacencyList.add(row7);

        List<Edge> edgeList = new ArrayList<>();
        edgeList.add(new Edge(0, 1, 1));
        edgeList.add(new Edge(1, 3, 1));
        edgeList.add(new Edge(2, 1, 1));
        edgeList.add(new Edge(3, 2, 1));
        edgeList.add(new Edge(3, 4, 1));
        edgeList.add(new Edge(4, 5, 1));
        edgeList.add(new Edge(5, 7, 1));
        edgeList.add(new Edge(6, 4, 1));
        edgeList.add(new Edge(7, 6, 1));

        printAdjacencyMatrix(adjacencyMatrix);
        printAdjacencyList(adjacencyList);
        printEdgeList(edgeList);
    }

    private static void printAdjacencyMatrix(int[][] adjacencyMatrix) {
        System.out.println("Adjacency Matrix");
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
        System.out.println("\nAdjacency List");
        for (int vertex1 = 0; vertex1 < adjacencyList.size(); vertex1++) {
            System.out.print("Vertex " + vertex1 + ":");
            for (IntegerPair edge : adjacencyList.get(vertex1)) {
                System.out.print(" (" + edge.vertex + " weight: " + edge.weight + ")");
            }
            System.out.println();
        }
    }

    private static void printEdgeList(List<Edge> edgeList) {
        System.out.println("\nEdge List");
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
