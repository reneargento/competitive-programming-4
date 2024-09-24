package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/05/24.
 */
@SuppressWarnings("unchecked")
public class LongestPaths {

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

    private static class Result {
        int pathLength;
        int endPoint;

        public Result(int pathLength, int endPoint) {
            this.pathLength = pathLength;
            this.endPoint = endPoint;
        }
    }

    private static final int INFINITE = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int points = FastReader.nextInt();
        int caseID = 1;

        while (points != 0) {
            int startPoint = FastReader.nextInt();
            List<Edge>[] adjacencyList = new List[points];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int pointID1 = FastReader.nextInt();
            int pointID2 = FastReader.nextInt();

            while (pointID1 != 0) {
                int vertexID1 = pointID1 - 1;
                int vertexID2 = pointID2 - 1;
                adjacencyList[vertexID1].add(new Edge(vertexID1, vertexID2, -1));

                pointID1 = FastReader.nextInt();
                pointID2 = FastReader.nextInt();
            }

            DAGLongestPathWeighted longestPathWeighted = new DAGLongestPathWeighted(adjacencyList, startPoint - 1);
            Result result = longestPathWeighted.getLongestDistance();

            outputWriter.printLine(String.format("Case %d: The longest path from %d has length %d, finishing at %d.\n",
                    caseID, startPoint, result.pathLength, result.endPoint));

            caseID++;
            points = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static class DAGLongestPathWeighted {
        private static int[] distTo;

        public DAGLongestPathWeighted(List<Edge>[] adjacent, int source) {
            distTo = new int[adjacent.length];
            Arrays.fill(distTo, INFINITE);
            distTo[source] = 0;

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

        public Result getLongestDistance() {
            int longestDistance = -INFINITE;
            int endPoint = 0;

            for (int vertex = 0; vertex < distTo.length; vertex++) {
                if (-distTo(vertex) > longestDistance) {
                    longestDistance = -distTo(vertex);
                    endPoint = vertex;
                }
            }
            return new Result(longestDistance, endPoint + 1);
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
