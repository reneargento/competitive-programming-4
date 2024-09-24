package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/05/24.
 */
@SuppressWarnings("unchecked")
public class LongestRunOnASnowboard {

    private static class Edge {
        int vertex1;
        int vertex2;
        int weight;

        Edge(int vertex1, int vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private static final int INFINITE = 1000000000;
    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String areaName = FastReader.next();
            int[][] area = new int[FastReader.nextInt()][FastReader.nextInt()];
            for (int row = 0; row < area.length; row++) {
                for (int column = 0; column < area[0].length; column++) {
                    area[row][column] = FastReader.nextInt();
                }
            }

            int longestRun = computeLongestRun(area);
            outputWriter.printLine(String.format("%s: %d", areaName, longestRun));
        }
        outputWriter.flush();
    }

    private static int computeLongestRun(int[][] area) {
        List<Edge>[] adjacent = buildGraph(area);
        DAGLongestPathWeighted longestPath = new DAGLongestPathWeighted(adjacent);
        return longestPath.getLongestDistance();
    }

    private static List<Edge>[] buildGraph(int[][] area) {
        int vertices = area.length * area[0].length;
        List<Edge>[] adjacent = new List[vertices];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int row = 0; row < area.length; row++) {
            for (int column = 0; column < area[0].length; column++) {
                int vertexID = getVertexID(area, row, column);

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];
                    if (isValid(area, neighborRow, neighborColumn)
                            && area[neighborRow][neighborColumn] < area[row][column]) {
                        int neighborVertexID = getVertexID(area, neighborRow, neighborColumn);
                        adjacent[vertexID].add(new Edge(vertexID, neighborVertexID, -1));
                    }
                }
            }
        }
        return adjacent;
    }

    private static boolean isValid(int[][] area, int row, int column) {
        return row >= 0 && row < area.length && column >= 0 && column < area[0].length;
    }

    private static int getVertexID(int[][] area, int row, int column) {
        return row * area[0].length + column;
    }

    private static class DAGLongestPathWeighted {
        private static int[] distTo;

        public DAGLongestPathWeighted(List<Edge>[] adjacent) {
            distTo = new int[adjacent.length];
            Arrays.fill(distTo, -1);

            int[] topological = topologicalSort(adjacent);
            for (int vertex : topological) {
                relax(adjacent, vertex);
            }
        }

        private void relax(List<Edge>[] adjacent, int vertex) {
            for (Edge edge : adjacent[vertex]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex] + edge.weight) {
                    distTo[neighbor] = distTo[vertex] + edge.weight;
                }
            }
        }

        public static int distTo(int vertex) {
            return distTo[vertex];
        }

        public int getLongestDistance() {
            int longestDistance = -INFINITE;

            for (int vertex = 0; vertex < distTo.length; vertex++) {
                if (-distTo(vertex) > longestDistance) {
                    longestDistance = -distTo(vertex);
                }
            }
            return longestDistance;
        }

        private static int[] topologicalSort(List<Edge>[] adjacencyList) {
            int[] inDegrees = new int[adjacencyList.length];

            for (int i = 0; i < adjacencyList.length; i++) {
                for (Edge edge : adjacencyList[i]) {
                    inDegrees[edge.vertex2]++;
                }
            }

            Queue<Integer> queue = new LinkedList<>();
            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                if (inDegrees[vertexID] == 0) {
                    queue.add(vertexID);
                }
            }

            int[] topologicalOrder = new int[adjacencyList.length];
            int topologicalOrderIndex = 0;

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();
                topologicalOrder[topologicalOrderIndex++] = currentVertex;

                for (Edge edge : adjacencyList[currentVertex]) {
                    int neighbor = edge.vertex2;
                    inDegrees[neighbor]--;

                    if (inDegrees[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
            return topologicalOrder;
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
