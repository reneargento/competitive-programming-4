package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 13/04/23.
 */
@SuppressWarnings("unchecked")
public class CaveExploration {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
        int tunnels = FastReader.nextInt();

        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < tunnels; i++) {
            int junctionID1 = FastReader.nextInt();
            int junctionID2 = FastReader.nextInt();
            adjacencyList[junctionID1].add(junctionID2);
            adjacencyList[junctionID2].add(junctionID1);
        }

        int safeJunctions = countSafeJunctions(adjacencyList);
        outputWriter.printLine(safeJunctions);
        outputWriter.flush();
    }

    private static int countSafeJunctions(List<Integer>[] adjacencyList) {
        List<Edge> bridges = Bridges.findBridges(adjacencyList);
        boolean[] visited = new boolean[adjacencyList.length];
        return depthFirstSearch(adjacencyList, visited, bridges, 0);
    }

    private static int depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, List<Edge> bridges,
                                        int junctionID) {
        int safeJunctions = 1;
        visited[junctionID] = true;

        for (int neighborID : adjacencyList[junctionID]) {
            int lowestVertexID = Math.min(junctionID, neighborID);
            int highestVertexID = Math.max(junctionID, neighborID);

            if (!visited[neighborID]
                    && !bridges.contains(new Edge(lowestVertexID, highestVertexID))) {
                safeJunctions += depthFirstSearch(adjacencyList, visited, bridges, neighborID);
            }
        }
        return safeJunctions;
    }

    private static class Edge {
        int vertex1;
        int vertex2;

        Edge(int vertex1, int vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            Edge edge = (Edge) other;
            return vertex1 == edge.vertex1 && vertex2 == edge.vertex2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex1, vertex2);
        }
    }

    private static class Bridges {
        private static int count;
        private static int[] time;
        private static int[] low;

        private static List<Edge> findBridges(List<Integer>[] adjacencyList) {
            low = new int[adjacencyList.length];
            time = new int[adjacencyList.length];
            List<Edge> bridges = new ArrayList<>();

            Arrays.fill(low, -1);
            Arrays.fill(time, -1);

            for (int vertex = 0; vertex < adjacencyList.length; vertex++) {
                if (time[vertex] == -1) {
                    dfs(adjacencyList, vertex, vertex, bridges);
                }
            }
            return bridges;
        }

        private static void dfs(List<Integer>[] adjacencyList, int currentVertex, int sourceVertex, List<Edge> bridges) {
            time[currentVertex] = count;
            low[currentVertex] = count;
            count++;

            for (int neighbor : adjacencyList[currentVertex]) {
                if (time[neighbor] == -1) {
                    dfs(adjacencyList, neighbor, currentVertex, bridges);

                    low[currentVertex] = Math.min(low[currentVertex], low[neighbor]);

                    if (low[neighbor] > time[currentVertex]) {
                        int lowestVertexID = Math.min(currentVertex, neighbor);
                        int highestVertexID = Math.max(currentVertex, neighbor);
                        bridges.add(new Edge(lowestVertexID, highestVertexID));
                    }
                } else if (neighbor != sourceVertex) {
                    low[currentVertex] = Math.min(low[currentVertex], time[neighbor]);
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
