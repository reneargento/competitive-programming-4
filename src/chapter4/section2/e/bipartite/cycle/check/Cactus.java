package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/04/23.
 */
@SuppressWarnings("unchecked")
public class Cactus {

    private static class Edge {
        int vertexID1;
        int vertexID2;

        public Edge(int vertexID1, int vertexID2) {
            this.vertexID1 = vertexID1;
            this.vertexID2 = vertexID2;
        }

        @Override
        public boolean equals(Object otherEdge) {
            if (this == otherEdge) return true;
            if (otherEdge == null || getClass() != otherEdge.getClass()) {
                return false;
            }
            Edge edge = (Edge) otherEdge;
            return vertexID1 == edge.vertexID1 && vertexID2 == edge.vertexID2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertexID1, vertexID2);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
           List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
           for (int i = 0; i < adjacencyList.length; i++) {
               adjacencyList[i] = new ArrayList<>();
           }
           int edges = FastReader.nextInt();
           for (int e = 0; e < edges; e++) {
               int vertexID1 = FastReader.nextInt();
               int vertexID2 = FastReader.nextInt();
               adjacencyList[vertexID1].add(vertexID2);
           }

           CactusChecker cactusChecker = new CactusChecker(adjacencyList);
           outputWriter.printLine(cactusChecker.isCactus ? "YES" : "NO");
        }
        outputWriter.flush();
    }

    private static class CactusChecker {
        private final boolean[] visited;
        private final int[] edgeTo;
        private final boolean[] onStack;
        private boolean isCactus;

        public CactusChecker(List<Integer>[] adjacent) {
            Set<Edge> edgesProcessed = new HashSet<>();
            onStack = new boolean[adjacent.length];
            edgeTo = new int[adjacent.length];
            visited = new boolean[adjacent.length];
            isCactus = true;

            boolean hasOnlySimpleCycles = dfs(adjacent, 0, edgesProcessed);
            for (int vertexID = 0; vertexID < visited.length; vertexID++) {
                if (!visited[vertexID]) {
                    isCactus = false;
                    break;
                }
            }
            isCactus &= hasOnlySimpleCycles;
        }

        private boolean dfs(List<Integer>[] adjacent, int vertex, Set<Edge> edgesProcessed) {
            boolean hasOnlySimpleCycles = true;
            onStack[vertex] = true;
            visited[vertex] = true;

            for (int neighbor : adjacent[vertex]) {
                if (!onStack[neighbor]) {
                    edgeTo[neighbor] = vertex;
                    hasOnlySimpleCycles &= dfs(adjacent, neighbor, edgesProcessed);
                } else {
                    for (int currentVertex = vertex; currentVertex != neighbor; currentVertex = edgeTo[currentVertex]) {
                        Edge edge = new Edge(edgeTo[currentVertex], currentVertex);
                        if (edgesProcessed.contains(edge)) {
                            return false;
                        }
                        edgesProcessed.add(edge);
                    }
                    Edge lastEdge = new Edge(vertex, neighbor);
                    if (edgesProcessed.contains(lastEdge)) {
                        return false;
                    }
                    edgesProcessed.add(lastEdge);
                }
            }
            onStack[vertex] = false;
            return hasOnlySimpleCycles;
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
