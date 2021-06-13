package chapter2.section4.section1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 12/06/21.
 */
public class Exercise2_2_InDegreeOutDegree {

    public static int inDegreeAM(int[][] adjacencyMatrix, int vertex) {
        int inDegree = 0;

        for (int row = 0; row < adjacencyMatrix.length; row++) {
            inDegree += (adjacencyMatrix[row][vertex] > 0) ? 1 : 0;
        }
        return inDegree;
    }

    public static int inDegreeAL(List<List<IntegerPair>> adjacencyList, int vertex) {
        int inDegree = 0;

        for (List<IntegerPair> neighbors : adjacencyList) {
            for (IntegerPair edge : neighbors) {
                if (edge.vertex == vertex) {
                    inDegree++;
                }
            }
        }
        return inDegree;
    }

    public static int inDegreeEL(List<Edge> edgeList, int vertex) {
        int inDegree = 0;

        for (Edge edge : edgeList) {
            if (edge.vertex2 == vertex) {
                inDegree++;
            }
        }
        return inDegree;
    }

    public static int outDegreeAM(int[][] adjacencyMatrix, int vertex) {
        int outDegree = 0;

        for (int column = 0; column < adjacencyMatrix[0].length; column++) {
            outDegree += (adjacencyMatrix[vertex][column]) > 0 ? 1 : 0;
        }
        return outDegree;
    }

    public static int outDegreeAL(List<List<IntegerPair>> adjacencyList, int vertex) {
        return adjacencyList.get(vertex).size();
    }

    public static int outDegreeEL(List<Edge> edgeList, int vertex) {
        int outDegree = 0;

        for (Edge edge : edgeList) {
            if (edge.vertex1 == vertex) {
                outDegree++;
            }
        }
        return outDegree;
    }

    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
                {0, 1, 1, 1, 0, 0, 0},
                {1, 0, 1, 1, 0, 0, 0},
                {1, 1, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 0, 0},
                {0, 0, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 1, 0}
        };

        List<List<IntegerPair>> adjacencyList = new ArrayList<>();
        List<IntegerPair> row0 = new ArrayList<>();
        row0.add(new IntegerPair(1, 1)); row0.add(new IntegerPair(2, 1)); row0.add(new IntegerPair(3, 1));
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
        edgeList.add(new Edge(0, 3, 1));
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

        int inDegreeAM = inDegreeAM(adjacencyMatrix, 0);
        int inDegreeAL = inDegreeAL(adjacencyList, 0);
        int inDegreeEL = inDegreeEL(edgeList, 0);

        System.out.println("In-degree of vertex 0. Expected: 2");
        System.out.println("Adjacency matrix: " + inDegreeAM);
        System.out.println("Adjacency list: " + inDegreeAL);
        System.out.println("Edge list: " + inDegreeEL);

        int outDegreeAM = outDegreeAM(adjacencyMatrix, 0);
        int outDegreeAL = outDegreeAL(adjacencyList, 0);
        int outDegreeEL = outDegreeEL(edgeList, 0);

        System.out.println("\nOut-degree of vertex 0. Expected: 3");
        System.out.println("Adjacency matrix: " + outDegreeAM);
        System.out.println("Adjacency list: " + outDegreeAL);
        System.out.println("Edge list: " + outDegreeEL);
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
