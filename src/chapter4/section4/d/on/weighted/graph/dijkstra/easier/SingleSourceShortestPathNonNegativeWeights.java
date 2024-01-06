package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/12/23.
 */
@SuppressWarnings("unchecked")
public class SingleSourceShortestPathNonNegativeWeights {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodes = FastReader.nextInt();
        int edges = FastReader.nextInt();
        int queries = FastReader.nextInt();
        int sourceNode = FastReader.nextInt();

        while (nodes != 0 || edges != 0 || queries != 0 || sourceNode != 0) {
            List<Edge>[] adjacencyList = new List[nodes];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < edges; i++) {
                int nodeID1 = FastReader.nextInt();
                int nodeID2 = FastReader.nextInt();
                int weight = FastReader.nextInt();
                adjacencyList[nodeID1].add(new Edge(nodeID1, nodeID2, weight));
            }

            Dijkstra dijkstra = new Dijkstra(adjacencyList, sourceNode);
            for (int q = 0; q < queries; q++) {
                int nodeID = FastReader.nextInt();
                if (dijkstra.hasPathTo(nodeID)) {
                    outputWriter.printLine(dijkstra.distTo[nodeID]);
                } else {
                    outputWriter.printLine("Impossible");
                }
            }

            nodes = FastReader.nextInt();
            edges = FastReader.nextInt();
            queries = FastReader.nextInt();
            sourceNode = FastReader.nextInt();
        }
        outputWriter.flush();
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

        private final long[] distTo;  // length of path to vertex
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
            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
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
