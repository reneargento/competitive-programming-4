package chapter4.section4.f.on.small.graph.with.negative.cycle.bellman.ford;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/02/24.
 */
@SuppressWarnings("unchecked")
public class MPIMaelstrom {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int processors = FastReader.nextInt();
        List<Edge>[] adjacencyList = new List[processors];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int vertexID1 = 0; vertexID1 < processors; vertexID1++) {
            for (int vertexID2 = 0; vertexID2 < vertexID1; vertexID2++) {
                String overheadSymbol = FastReader.next();
                if (!overheadSymbol.equals("x")) {
                    int overhead = Integer.parseInt(overheadSymbol);
                    Edge edge1 = new Edge(vertexID2, overhead);
                    Edge edge2 = new Edge(vertexID1, overhead);
                    adjacencyList[vertexID1].add(edge1);
                    adjacencyList[vertexID2].add(edge2);
                }
            }
        }

        Dijkstra dijkstra = new Dijkstra(adjacencyList, 0);
        long minimumBroadcastTime = getMinimumBroadcastTime(dijkstra);
        outputWriter.printLine(minimumBroadcastTime);
        outputWriter.flush();
    }

    private static long getMinimumBroadcastTime(Dijkstra dijkstra) {
        long minimumBroadcastTime = 0;
        for (long distance : dijkstra.distTo) {
            minimumBroadcastTime = Math.max(minimumBroadcastTime, distance);
        }
        return minimumBroadcastTime;
    }

    private static class Edge {
        private final int vertex2;
        private final long weight;

        public Edge(int vertex2, long weight) {
            this.vertex2 = vertex2;
            this.weight = weight;
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
        private final long MAX_VALUE = 10000000000000000L;

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
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.weight) {
                    distTo[neighbor] = distTo[vertex.id] + edge.weight;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] != MAX_VALUE;
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
