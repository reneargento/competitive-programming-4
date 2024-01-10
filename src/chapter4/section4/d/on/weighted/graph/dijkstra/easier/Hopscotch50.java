package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/01/24.
 */
@SuppressWarnings("unchecked")
public class Hopscotch50 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = FastReader.nextInt();
        int maxValue = FastReader.nextInt();

        List<Cell>[] cells = new List[maxValue + 1];
        int[][] values = new int[dimension][dimension];

        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
                int value = FastReader.nextInt();
                values[row][column] = value;
                if (cells[value] == null) {
                    cells[value] = new ArrayList<>();
                }
                cells[value].add(new Cell(row, column));
            }
        }

        long shortestPathLength = computeShortestPathLength(cells, values, maxValue);
        outputWriter.printLine(shortestPathLength);
        outputWriter.flush();
    }

    private static long computeShortestPathLength(List<Cell>[] cells, int[][] values, int maxValue) {
        List<Edge>[] adjacencyList = buildGraph(cells, values.length);
        if (adjacencyList == null) {
            return -1;
        }

        Dijkstra dijkstra = new Dijkstra(adjacencyList, cells[1], values, maxValue);
        if (dijkstra.shortestPathToTarget == Dijkstra.MAX_VALUE) {
            return -1;
        }
        return dijkstra.shortestPathToTarget;
    }

    private static List<Edge>[] buildGraph(List<Cell>[] cells, int dimension) {
        int numberOfNodes = dimension * dimension;
        List<Edge>[] adjacencyList = new List[numberOfNodes];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int value = 1; value < cells.length - 1; value++) {
            if (cells[value] == null || cells[value + 1] == null) {
                return null;
            }
            for (Cell cell : cells[value]) {
                int cellID = getCellID(dimension, cell);

                for (Cell nextCell : cells[value + 1]) {
                    int nextCellID = getCellID(dimension, nextCell);
                    int distance = computeDistance(cell, nextCell);
                    adjacencyList[cellID].add(new Edge(nextCellID, distance));
                }
            }
        }
        return adjacencyList;
    }

    private static int computeDistance(Cell cell1, Cell cell2) {
        return Math.abs(cell1.row - cell2.row) + Math.abs(cell1.column - cell2.column);
    }

    private static int getCellID(int dimension, Cell cell) {
        return cell.row * dimension + cell.column;
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class Edge {
        private final int vertex2;
        private final long distance;

        public Edge(int vertex2, long distance) {
            this.vertex2 = vertex2;
            this.distance = distance;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            long distance;

            public Vertex(int id, long distance) {
                this.id = id;
                this.distance = distance;
            }

            @Override
            public int compareTo(Vertex other) {
                return Long.compare(distance, other.distance);
            }
        }

        private final long[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        public static long MAX_VALUE = 10000000000000000L;
        public long shortestPathToTarget = MAX_VALUE;

        public Dijkstra(List<Edge>[] adjacencyList, List<Cell> sources, int[][] values, int maxValue) {
            distTo = new long[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);
            int dimension = values.length;

            Arrays.fill(distTo, MAX_VALUE);
            for (Cell source : sources) {
                int cellID = getCellID(dimension, source);
                distTo[cellID] = 0;
                priorityQueue.offer(new Vertex(cellID, 0));
            }

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                int row = vertex.id / dimension;
                int column = vertex.id % dimension;
                if (values[row][column] == maxValue) {
                    shortestPathToTarget = vertex.distance;
                    break;
                }
                relax(adjacencyList, vertex);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                }
            }
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
