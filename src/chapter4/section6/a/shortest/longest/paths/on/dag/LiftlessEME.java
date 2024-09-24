package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/04/24.
 */
@SuppressWarnings("unchecked")
public class LiftlessEME {

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
        String caseName = FastReader.getLine();

        while (caseName != null) {
            int roofs = FastReader.nextInt();
            int holes = FastReader.nextInt();
            List<Edge>[] adjacent = new List[roofs * holes];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new ArrayList<>();
            }

            for (int roofID = 0; roofID < roofs - 1; roofID++) {
                for (int previousFloorHoleID = 0; previousFloorHoleID < holes; previousFloorHoleID++) {
                    int previousFloorHoleVertexID = roofID * holes + previousFloorHoleID;

                    for (int holeID = 0; holeID < holes; holeID++) {
                        int vertexID = (roofID + 1) * holes + holeID;
                        int walkTime = FastReader.nextInt();
                        adjacent[previousFloorHoleVertexID].add(new Edge(vertexID, walkTime + 2));
                    }
                }
            }

            new DAGShortestPath(adjacent, holes);
            int minimumTimeNeeded = getMinimumTimeToLastFloor(roofs, holes);

            outputWriter.printLine(caseName);
            outputWriter.printLine(minimumTimeNeeded);
            caseName = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int getMinimumTimeToLastFloor(int roofs, int holes) {
        if (roofs <= 1) {
            return 0;
        }
        int minimumTime = MAX_VALUE;

        int lastFloorStartVertexID = (roofs - 1) * holes;
        for (int vertexID = lastFloorStartVertexID; vertexID < DAGShortestPath.distTo.length; vertexID++) {
            minimumTime = Math.min(minimumTime, DAGShortestPath.distTo(vertexID));
        }
        return minimumTime;
    }

    private static class DAGShortestPath {

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

        static int[] distTo;

        public DAGShortestPath(List<Edge>[] adjacent, int holes) {
            distTo = new int[adjacent.length];
            Arrays.fill(distTo, MAX_VALUE);
            for (int vertexID = 0; vertexID < holes; vertexID++) {
                distTo[vertexID] = 0;
            }

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
