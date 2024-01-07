package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/01/24.
 */
@SuppressWarnings("unchecked")
public class FloweryTrailsUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int points = Integer.parseInt(data[0]);
            int trails = Integer.parseInt(data[1]);

            List<Edge>[] adjacencyList = new List[points];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < trails; i++) {
                int pointID1 = FastReader.nextInt();
                int pointID2 = FastReader.nextInt();
                int length = FastReader.nextInt();

                adjacencyList[pointID1].add(new Edge(pointID1, pointID2, length));
                adjacencyList[pointID2].add(new Edge(pointID2, pointID1, length));
            }

            long flowersExtent = computeFlowersExtent(adjacencyList);
            outputWriter.printLine(flowersExtent);
            line = FastReader.getLine();
        }

        outputWriter.flush();
    }

    private static long computeFlowersExtent(List<Edge>[] adjacencyList) {
        Dijkstra dijkstra = new Dijkstra(adjacencyList, 0);
        int highestPeakID = adjacencyList.length - 1;
        long totalLength = 0;
        boolean[] visited = new boolean[adjacencyList.length];

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(highestPeakID);

        while (!queue.isEmpty()) {
            int pointID = queue.poll();
            if (dijkstra.edgesTo[pointID] == null) {
                continue;
            }

            for (Edge edge : dijkstra.edgesTo[pointID]) {
                totalLength += edge.distance;

                if (!visited[edge.vertex1]) {
                    queue.offer(edge.vertex1);
                    visited[edge.vertex1] = true;
                }
            }
        }
        return totalLength * 2;
    }

    private static class Edge {
        private final int vertex1;
        private final int vertex2;
        private final int distance;

        public Edge(int vertex1, int vertex2, int distance) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.distance = distance;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            long distance;
            int totalDistance;

            public Vertex(int id, long distance) {
                this.id = id;
                this.distance = distance;
            }

            @Override
            public int compareTo(Vertex other) {
                if (distance != other.distance) {
                    return Long.compare(distance, other.distance);
                }
                return Long.compare(other.totalDistance, totalDistance);
            }
        }

        private final long[] distTo;
        private final List<Edge>[] edgesTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            distTo = new long[adjacencyList.length];
            edgesTo = new List[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.distance > distTo[adjacencyList.length - 1]) {
                    break;
                }
                if (vertex.distance > distTo[vertex.id]) {
                    continue;
                }
                relax(adjacencyList, vertex);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    edgesTo[neighbor] = new ArrayList<>();
                    edgesTo[neighbor].add(edge);
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                } else if (distTo[neighbor] == distTo[vertex.id] + edge.distance) {
                    edgesTo[neighbor].add(edge);
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
