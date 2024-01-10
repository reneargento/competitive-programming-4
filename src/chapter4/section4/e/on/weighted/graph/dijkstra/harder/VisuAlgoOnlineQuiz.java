package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/01/24.
 */
@SuppressWarnings("unchecked")
public class VisuAlgoOnlineQuiz {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Edge>[] adjacencyList = new List[FastReader.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        int edges = FastReader.nextInt();
        for (int e = 0; e < edges; e++) {
            Edge edge = new Edge(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            adjacencyList[edge.vertex1].add(edge);
        }

        int source = FastReader.nextInt();
        int target = FastReader.nextInt();

        int shortestPaths = countShortestPaths(adjacencyList, source, target);
        outputWriter.printLine(shortestPaths);
        outputWriter.flush();
    }

    private static int countShortestPaths(List<Edge>[] adjacencyList, int source, int target) {
        Dijkstra dijkstra = new Dijkstra(adjacencyList, source);
        return dijkstra.shortestPathsCount[target];
    }

    private static class Edge {
        private final int vertex1;
        private final int vertex2;
        private final long distance;

        public Edge(int vertex1, int vertex2, long distance) {
            this.vertex1 = vertex1;
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

        private final int[] shortestPathsCount;
        private final long[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            shortestPathsCount = new int[adjacencyList.length];
            distTo = new long[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = 0;
            shortestPathsCount[source] = 1;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                relax(adjacencyList, priorityQueue.poll());
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    shortestPathsCount[neighbor] = shortestPathsCount[vertex.id];
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                } else if (distTo[neighbor] == distTo[vertex.id] + edge.distance) {
                    shortestPathsCount[neighbor] += shortestPathsCount[vertex.id];
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
