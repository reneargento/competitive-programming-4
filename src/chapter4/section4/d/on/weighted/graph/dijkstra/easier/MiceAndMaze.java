package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/01/24.
 */
@SuppressWarnings("unchecked")
public class MiceAndMaze {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            List<Edge>[] adjacencyList = new List[FastReader.nextInt()];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int exitCell = FastReader.nextInt() - 1;
            int totalTime = FastReader.nextInt();
            int connections = FastReader.nextInt();

            for (int i = 0; i < connections; i++) {
                int cellID1 = FastReader.nextInt() - 1;
                int cellID2 = FastReader.nextInt() - 1;
                int time = FastReader.nextInt();
                adjacencyList[cellID2].add(new Edge(cellID2, cellID1, time));
            }

            Dijkstra dijkstra = new Dijkstra(adjacencyList, exitCell);
            int escapedMice = countEscapedMice(dijkstra, totalTime);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(escapedMice);
        }
        outputWriter.flush();
    }

    private static int countEscapedMice(Dijkstra dijkstra, int totalTime) {
        int escapedMice = 0;
        for (int vertex = 0; vertex < dijkstra.distTo.length; vertex++) {
            if (dijkstra.distTo[vertex] <= totalTime) {
                escapedMice++;
            }
        }
        return escapedMice;
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
            public int compareTo(Dijkstra.Vertex other) {
                return Long.compare(distance, other.distance);
            }
        }

        private final long[] distTo;  // length of path to vertex
        private final PriorityQueue<Dijkstra.Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            distTo = new long[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = 0;
            priorityQueue.offer(new Dijkstra.Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                relax(adjacencyList, priorityQueue.poll());
            }
        }

        private void relax(List<Edge>[] adjacencyList, Dijkstra.Vertex vertex) {
            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    priorityQueue.offer(new Dijkstra.Vertex(neighbor, distTo[neighbor]));
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
