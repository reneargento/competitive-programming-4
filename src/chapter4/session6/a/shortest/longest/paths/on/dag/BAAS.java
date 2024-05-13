package chapter4.session6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/05/24.
 */
public class BAAS {

    private static class Edge {
        int vertex1;
        int vertex2;
        int time;

        Edge(int vertex1, int vertex2, int time) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.time = time;
        }
    }

    private static final int INFINITE = 1000000000;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] stepsTime = new int[FastReader.nextInt()];
        List<Edge>[] adjacent = new List[stepsTime.length + 1];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int i = 0; i < stepsTime.length; i++) {
            stepsTime[i] = FastReader.nextInt();
        }

        for (int vertexID = 1; vertexID < adjacent.length; vertexID++) {
            int dependencies = FastReader.nextInt();
            for (int d = 0; d < dependencies; d++) {
                int dependencyVertexID = FastReader.nextInt();
                adjacent[dependencyVertexID].add(new Edge(dependencyVertexID, vertexID, -stepsTime[vertexID - 1]));
            }
        }
        adjacent[0].add(new Edge(0, 1, -stepsTime[0]));

        int shortestPossibleTime = computeShortestPossibleTime(adjacent);
        outputWriter.printLine(shortestPossibleTime);
        outputWriter.flush();
    }

    private static int computeShortestPossibleTime(List<Edge>[] adjacent) {
        DAGLongestPathWeighted longestPath = new DAGLongestPathWeighted(adjacent, 0, -1);
        int shortestPossibleTime = INFINITE;

        List<Edge> longestPathEdges = longestPath.getLongestPath();
        for (Edge edge : longestPathEdges) {
            int stepToOptimize = edge.vertex2;
            DAGLongestPathWeighted candidateLongestPath = new DAGLongestPathWeighted(adjacent, 0, stepToOptimize);
            int candidateTime = candidateLongestPath.getLongestDistance();

            if (candidateTime < shortestPossibleTime) {
                shortestPossibleTime = candidateTime;
            }
        }
        return shortestPossibleTime;
    }

    private static class DAGLongestPathWeighted {
        private static int[] distTo;
        private static Edge[] edgeTo;

        public DAGLongestPathWeighted(List<Edge>[] adjacent, int source, int stepToOptimize) {
            distTo = new int[adjacent.length];
            edgeTo = new Edge[adjacent.length];
            Arrays.fill(distTo, INFINITE);
            distTo[source] = 0;

            int[] topological = topologicalSort(adjacent);
            for (int vertex : topological) {
                relax(adjacent, vertex, stepToOptimize);
            }
        }

        private void relax(List<Edge>[] adjacent, int vertex, int stepToOptimize) {
            for (Edge edge : adjacent[vertex]) {
                int neighbor = edge.vertex2;
                int distanceToNeighbor = edge.time;
                if (stepToOptimize == neighbor) {
                    distanceToNeighbor = 0;
                }

                if (distTo[neighbor] > distTo[vertex] + distanceToNeighbor) {
                    distTo[neighbor] = distTo[vertex] + distanceToNeighbor;
                    edgeTo[neighbor] = edge;
                }
            }
        }

        public static int distTo(int vertex) {
            return distTo[vertex];
        }

        public static boolean hasPathTo(int vertex) {
            return distTo[vertex] < INFINITE;
        }

        public List<Edge> pathTo(int vertex) {
            if (!hasPathTo(vertex)) {
                return null;
            }

            Stack<Edge> path = new Stack<>();
            for (Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
                path.push(edge);
            }
            return path;
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

        public List<Edge> getLongestPath() {
            int furthestVertex = -1;
            double longestDistance = Double.NEGATIVE_INFINITY;

            for (int vertex = 0; vertex < distTo.length; vertex++) {
                if (-distTo(vertex) > longestDistance) {
                    longestDistance = -distTo(vertex);
                    furthestVertex = vertex;
                }
            }
            return pathTo(furthestVertex);
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
