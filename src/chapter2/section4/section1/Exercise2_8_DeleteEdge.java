package chapter2.section4.section1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 12/06/21.
 */
public class Exercise2_8_DeleteEdge {

    public static void deleteEdgeAM(int[][] adjacencyMatrix, int vertex1, int vertex2) {
        adjacencyMatrix[vertex1][vertex2] = 0;
    }

    public static void deleteEdgeAL(List<List<IntegerPair>> adjacencyList, int vertex1, int vertex2) {
        int edgeIndex = -1;

        for (int i = 0; i < adjacencyList.get(vertex1).size(); i++) {
            if (adjacencyList.get(vertex1).get(i).vertex == vertex2) {
                edgeIndex = i;
                break;
            }
        }

        if (edgeIndex != -1) {
            adjacencyList.get(vertex1).remove(edgeIndex);
        }
    }

    public static void deleteEdgeEL(List<Edge> edgeList, int vertex1, int vertex2) {
        int edgeIndex = -1;

        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            if (edge.vertex1 == vertex1 && edge.vertex2 == vertex2) {
                edgeIndex = i;
                break;
            }
        }

        if (edgeIndex != -1) {
            edgeList.remove(edgeIndex);
        }
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

        deleteEdgeAM(adjacencyMatrix, 3, 1);

        System.out.println("Adjacency Matrix after deleting edge 3->1");
        for (int row = 0; row < adjacencyMatrix.length; row++) {
            for (int column = 0; column < adjacencyMatrix[0].length; column++) {
                System.out.print(adjacencyMatrix[row][column]);

                if (column != adjacencyMatrix[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        deleteEdgeAL(adjacencyList, 3, 1);

        System.out.println("\nAdjacency List after deleting edge 3->1");
        for (int vertex1 = 0; vertex1 < adjacencyList.size(); vertex1++) {
            System.out.print("Vertex " + vertex1 + ":");
            for (IntegerPair edge : adjacencyList.get(vertex1)) {
                System.out.print(" (" + edge.vertex + " weight: " + edge.weight + ")");
            }
            System.out.println();
        }

        deleteEdgeEL(edgeList, 3, 1);
        System.out.println("\nEdge List after deleting edge 3->1");
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
