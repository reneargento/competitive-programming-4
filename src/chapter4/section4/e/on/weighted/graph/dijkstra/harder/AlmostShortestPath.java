package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/01/24.
 */
@SuppressWarnings("unchecked")
public class AlmostShortestPath {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int points = FastReader.nextInt();
        int routes = FastReader.nextInt();

        while (points != 0 || routes != 0) {
            int startPoint = FastReader.nextInt();
            int destinationPoint = FastReader.nextInt();
            List<Edge>[] adjacencyList = new List[points];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int r = 0; r < routes; r++) {
                int pointID1 = FastReader.nextInt();
                int pointID2 = FastReader.nextInt();
                int distance = FastReader.nextInt();
                adjacencyList[pointID1].add(new Edge(pointID1, pointID2, distance));
            }

            long almostShortestPathLength = computeAlmostShortestPath(adjacencyList, startPoint, destinationPoint);
            outputWriter.printLine(almostShortestPathLength);
            points = FastReader.nextInt();
            routes = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeAlmostShortestPath(List<Edge>[] adjacencyList, int startPoint, int destinationPoint) {
        Dijkstra dijkstra = new Dijkstra(adjacencyList, startPoint, destinationPoint, new HashSet<>());
        Set<Edge> edgesToAvoid = dijkstra.computeAllShortestPathEdges(destinationPoint);
        Dijkstra dijkstraSkippingEdges = new Dijkstra(adjacencyList, startPoint, destinationPoint, edgesToAvoid);

        if (dijkstraSkippingEdges.hasPathTo(destinationPoint)) {
            return dijkstraSkippingEdges.distTo[destinationPoint];
        } else {
            return -1;
        }
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

        private final List<Edge>[] edgeTo;
        private final long[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target, Set<Edge> edgesToAvoid) {
            edgeTo = new List[adjacencyList.length];
            for (int i = 0; i < edgeTo.length; i++) {
                edgeTo[i] = new ArrayList<>();
            }
            distTo = new long[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target) {
                    break;
                }
                relax(adjacencyList, vertex, edgesToAvoid);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, Set<Edge> edgesToAvoid) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                if (edgesToAvoid.contains(edge)) {
                    continue;
                }
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    edgeTo[neighbor] = new ArrayList<>();
                    edgeTo[neighbor].add(edge);
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                } else if (distTo[neighbor] == distTo[vertex.id] + edge.distance) {
                    edgeTo[neighbor].add(edge);
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] != MAX_VALUE;
        }

        public Set<Edge> computeAllShortestPathEdges(int vertex) {
            Set<Edge> shortestPathEdges = new HashSet<>();
            boolean[] visited = new boolean[distTo.length];
            computeAllShortestPathEdges(shortestPathEdges, visited, vertex);
            return shortestPathEdges;
        }

        private void computeAllShortestPathEdges(Set<Edge> shortestPathEdges, boolean[] visited, int vertex) {
            if (visited[vertex]) {
                return;
            }
            visited[vertex] = true;
            for (Edge edge : edgeTo[vertex]) {
                shortestPathEdges.add(edge);
                computeAllShortestPathEdges(shortestPathEdges, visited, edge.vertex1);
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
