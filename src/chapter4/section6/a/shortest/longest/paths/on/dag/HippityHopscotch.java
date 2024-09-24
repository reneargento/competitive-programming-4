package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/04/24.
 */
@SuppressWarnings("unchecked")
public class HippityHopscotch {

    private static class Edge {
        int vertex2;
        int weight;

        Edge(int vertex2, int weight) {
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private static final int MAX_VALUE = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dimension = FastReader.nextInt();
            int jumpingCapability = FastReader.nextInt();
            int[][] grid = new int[dimension][dimension];

            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[0].length; column++) {
                    grid[row][column] = FastReader.nextInt();
                }
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            int maxPennies = computeMaxPennies(grid, jumpingCapability);
            outputWriter.printLine(maxPennies);
        }
        outputWriter.flush();
    }

    private static int computeMaxPennies(int[][] grid, int jumpingCapability) {
        List<Edge>[] adjacent = computeGraph(grid, jumpingCapability);
        DAGLongestPathWeighted longestPath = new DAGLongestPathWeighted(adjacent, grid);
        return longestPath.getLongestDistance();
    }

    private static List<Edge>[] computeGraph(int[][] grid, int jumpingCapability) {
        List<Edge>[] adjacent = new List[grid.length * grid.length];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                int currentPennies = grid[row][column];
                int vertexID = getVertexID(grid, row, column);
                int startRow = Math.max(0, row - jumpingCapability);
                int endRow = Math.min(grid.length - 1, row + jumpingCapability);
                int startColumn = Math.max(0, column - jumpingCapability);
                int endColumn = Math.min(grid.length - 1, column + jumpingCapability);

                // Check verticals
                for (int neighborRow = startRow; neighborRow <= endRow; neighborRow++) {
                    if (grid[neighborRow][column] > currentPennies) {
                        int neighborVertexID = getVertexID(grid, neighborRow, column);
                        adjacent[vertexID].add(new Edge(neighborVertexID, -grid[neighborRow][column]));
                    }
                }
                // Check horizontals
                for (int neighborColumn = startColumn; neighborColumn <= endColumn; neighborColumn++) {
                    if (grid[row][neighborColumn] > currentPennies) {
                        int neighborVertexID = getVertexID(grid, row, neighborColumn);
                        adjacent[vertexID].add(new Edge(neighborVertexID, -grid[row][neighborColumn]));
                    }
                }
            }
        }
        return adjacent;
    }

    private static int getVertexID(int[][] grid, int row, int column) {
        return row * grid[0].length + column;
    }

    private static class DAGLongestPathWeighted {

        public static class TopologicalSort {

            private static int[] topologicalSort(List<Edge>[] adjacent) {
                Deque<Integer> finishTimes = getFinishTimes(adjacent);

                int[] topologicalSort = new int[finishTimes.size()];
                int arrayIndex = 0;

                while (!finishTimes.isEmpty()) {
                    topologicalSort[arrayIndex++] = finishTimes.pop();
                }
                return topologicalSort;
            }

            private static Deque<Integer> getFinishTimes(List<Edge>[] adjacent) {
                boolean[] visited = new boolean[adjacent.length];
                Deque<Integer> finishTimes = new ArrayDeque<>();

                for (int i = 0; i < adjacent.length; i++) {
                    if (!visited[i]) {
                        depthFirstSearch(i, adjacent, finishTimes, visited);
                    }
                }
                return finishTimes;
            }

            private static void depthFirstSearch(int sourceVertex, List<Edge>[] adj, Deque<Integer> finishTimes,
                                                 boolean[] visited) {
                visited[sourceVertex] = true;

                for (Edge edge : adj[sourceVertex]) {
                    int neighbor = edge.vertex2;

                    if (!visited[neighbor]) {
                        depthFirstSearch(neighbor, adj, finishTimes, visited);
                    }
                }
                finishTimes.push(sourceVertex);
            }
        }

        private static int[] distTo;

        public DAGLongestPathWeighted(List<Edge>[] adjacent, int[][] grid) {
            distTo = new int[adjacent.length];
            Arrays.fill(distTo, MAX_VALUE);
            distTo[0] = -grid[0][0];

            int[] topological = TopologicalSort.topologicalSort(adjacent);
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

        public static boolean hasPathTo(int vertex) {
            return distTo[vertex] < MAX_VALUE;
        }

        public int getLongestDistance() {
            int longestDistance = Integer.MIN_VALUE;

            for (int vertex = 0; vertex < distTo.length; vertex++) {
                if (-distTo(vertex) > longestDistance) {
                    longestDistance = -distTo(vertex);
                }
            }
            return longestDistance;
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
