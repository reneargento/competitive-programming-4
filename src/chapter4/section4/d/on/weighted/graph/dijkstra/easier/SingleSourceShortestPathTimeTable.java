package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/01/24.
 */
@SuppressWarnings("unchecked")
public class SingleSourceShortestPathTimeTable {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodes = FastReader.nextInt();
        int edges = FastReader.nextInt();
        int queries = FastReader.nextInt();
        int startNode = FastReader.nextInt();

        while (nodes != 0 || edges != 0 || queries != 0 || startNode != 0) {
            List<Edge>[] adjacencyList = new List[nodes];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int m = 0; m < edges; m++) {
                int nodeID1 = FastReader.nextInt();
                int nodeID2 = FastReader.nextInt();
                int startTime = FastReader.nextInt();
                int increment = FastReader.nextInt();
                int distance = FastReader.nextInt();
                Edge edge1 = new Edge(nodeID1, nodeID2, startTime, increment, distance);
                Edge edge2 = new Edge(nodeID1, nodeID2, startTime, increment, distance);
                adjacencyList[nodeID1].add(edge1);
                adjacencyList[nodeID2].add(edge2);
            }
            Dijkstra dijkstra = new Dijkstra(adjacencyList, startNode);
            long[] shortestDistances = dijkstra.distTo;

            for (int q = 0; q < queries; q++) {
                int targetNodeID = FastReader.nextInt();
                if (shortestDistances[targetNodeID] != Dijkstra.MAX_VALUE) {
                    outputWriter.printLine(shortestDistances[targetNodeID]);
                } else {
                    outputWriter.printLine("Impossible");
                }
            }

            nodes = FastReader.nextInt();
            edges = FastReader.nextInt();
            queries = FastReader.nextInt();
            startNode = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static class Edge {
        private final int vertex1;
        private final int vertex2;
        private final int startTime;
        private final double increment;
        private final int distance;

        public Edge(int vertex1, int vertex2, int startTime, double increment, int distance) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.startTime = startTime;
            this.increment = increment;
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

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            distTo = new long[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                relax(adjacencyList, priorityQueue.poll());
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                long nextTimeToMove;
                if (edge.increment == 0) {
                    if (edge.startTime < distTo[vertex.id]) {
                        continue;
                    }
                    nextTimeToMove = edge.startTime;
                } else {
                    long multiplier = (long) Math.ceil((distTo[vertex.id] - edge.startTime) / edge.increment);
                    nextTimeToMove = edge.startTime + multiplier * (long) edge.increment;
                }
                long timeToStart = Math.max(edge.startTime, nextTimeToMove);
                long distance = timeToStart + edge.distance;

                if (distTo[neighbor] > distance) {
                    distTo[neighbor] = distance;
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
