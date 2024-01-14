package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/01/24.
 */
@SuppressWarnings("unchecked")
public class InvitationCards {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            List<Edge>[] adjacencyList = new List[FastReader.nextInt()];
            List<Edge>[] adjacencyListReverse = new List[adjacencyList.length];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
                adjacencyListReverse[i] = new ArrayList<>();
            }

            int busLines = FastReader.nextInt();
            for (int b = 0; b < busLines; b++) {
                int originStopID = FastReader.nextInt() - 1;
                int destinationStopID = FastReader.nextInt() - 1;
                int price = FastReader.nextInt();
                adjacencyList[originStopID].add(new Edge(destinationStopID, price));
                adjacencyListReverse[destinationStopID].add(new Edge(originStopID, price));
            }

            long minimumCost = computeMinimumCost(adjacencyList, adjacencyListReverse);
            outputWriter.printLine(minimumCost);
        }
        outputWriter.flush();
    }

    private static long computeMinimumCost(List<Edge>[] adjacencyList, List<Edge>[] adjacencyListReverse) {
        long minimumCost = 0;
        Dijkstra dijkstra = new Dijkstra(adjacencyList, 0);
        Dijkstra dijkstraReverse = new Dijkstra(adjacencyListReverse, 0);

        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            minimumCost += dijkstra.distTo[vertexID] + dijkstraReverse.distTo[vertexID];
        }
        return minimumCost;
    }

    private static class Edge {
        private final int vertex2;
        private final long distance;

        public Edge(int vertex2, long distance) {
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

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
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
