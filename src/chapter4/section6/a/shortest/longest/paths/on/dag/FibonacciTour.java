package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/04/24.
 */
@SuppressWarnings("unchecked")
public class FibonacciTour {

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

    private static final int INFINITE = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long[] mansionHeights = new long[FastReader.nextInt()];
        int roads = FastReader.nextInt();

        int[] distances = new int[mansionHeights.length];
        Arrays.fill(distances, INFINITE);
        Map<Integer, Long> fibonacciIndexToValue = new HashMap<>();
        Map<Long, Integer> fibonacciValueToIndex = new HashMap<>();
        getFibonacciSequence(fibonacciIndexToValue, fibonacciValueToIndex);

        List<Edge>[] adjacencyList = new List[mansionHeights.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int mansionID = 0; mansionID < mansionHeights.length; mansionID++) {
            mansionHeights[mansionID] = FastReader.nextLong();
            if (fibonacciValueToIndex.containsKey(mansionHeights[mansionID])) {
                distances[mansionID] = -1;
            }
        }

        for (int r = 0; r < roads; r++) {
            int mansionID1 = FastReader.nextInt() - 1;
            int mansionID2 = FastReader.nextInt() - 1;
            Edge edge1 = new Edge(mansionID1, mansionID2, -1);
            Edge edge2 = new Edge(mansionID2, mansionID1, -1);

            if (mansionHeights[mansionID1] == 1 && mansionHeights[mansionID2] == 1) {
                distances[mansionID1] = -2;
                distances[mansionID2] = -2;
            } else {
                if (fibonacciValueToIndex.containsKey(mansionHeights[mansionID1])) {
                    int fibonacciIndex = fibonacciValueToIndex.get(mansionHeights[mansionID1]);
                    long fibonacciValue = fibonacciIndexToValue.get(fibonacciIndex + 1);
                    if (mansionHeights[mansionID2] == fibonacciValue) {
                        adjacencyList[mansionID1].add(edge1);
                    }
                }

                if (fibonacciValueToIndex.containsKey(mansionHeights[mansionID2])) {
                    int fibonacciIndex = fibonacciValueToIndex.get(mansionHeights[mansionID2]);
                    long fibonacciValue = fibonacciIndexToValue.get(fibonacciIndex + 1);
                    if (mansionHeights[mansionID1] == fibonacciValue) {
                        adjacencyList[mansionID2].add(edge2);
                    }
                }
            }
        }

        DAGLongestPathWeighted dagLongestPathWeighted = new DAGLongestPathWeighted(adjacencyList, distances);
        outputWriter.printLine(dagLongestPathWeighted.getLongestDistance());
        outputWriter.flush();
    }

    private static void getFibonacciSequence(Map<Integer, Long> fibonacciIndexToValue,
                                             Map<Long, Integer> fibonacciValueToIndex) {
        fibonacciIndexToValue.put(0, 1L);
        fibonacciIndexToValue.put(1, 1L);

        fibonacciValueToIndex.put(1L, 1);

        long fibonacciValue = 2L;
        int index = 2;
        while (fibonacciValue <= 1000000000000000000L) {
            fibonacciValue = fibonacciIndexToValue.get(index - 1) + fibonacciIndexToValue.get(index - 2);
            fibonacciIndexToValue.put(index, fibonacciValue);
            fibonacciValueToIndex.put(fibonacciValue, index);
            index++;
        }
    }

    private static class DAGLongestPathWeighted {
        private static int[] distTo;

        public DAGLongestPathWeighted(List<Edge>[] adjacent, int[] distances) {
            distTo = distances;

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

        public static long distTo(int vertex) {
            return distTo[vertex];
        }

        public long getLongestDistance() {
            long longestDistance = -INFINITE;

            for (int vertex = 0; vertex < distTo.length; vertex++) {
                if (-distTo(vertex) > longestDistance) {
                    longestDistance = -distTo(vertex);
                }
            }

            if (longestDistance == -INFINITE) {
                return 0;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
