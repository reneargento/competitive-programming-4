package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/01/24.
 */
@SuppressWarnings("unchecked")
public class Detour {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Edge>[] adjacencyList = new List[FastReader.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        int roads = FastReader.nextInt();

        for (int r = 0; r < roads; r++) {
            int intersectionID1 = FastReader.nextInt();
            int intersectionID2 = FastReader.nextInt();
            int distance = FastReader.nextInt();
            adjacencyList[intersectionID1].add(new Edge(intersectionID1, intersectionID2, distance));
            adjacencyList[intersectionID2].add(new Edge(intersectionID2, intersectionID1, distance));
        }

        List<Edge> path = computePath(adjacencyList);
        if (path == null) {
            outputWriter.printLine("impossible");
        } else {
            outputWriter.print(path.size() + 1);
            Edge startEdge = path.get(0);
            outputWriter.print(String.format(" %d %d", startEdge.vertex1, startEdge.vertex2));

            for (int i = 1; i < path.size(); i++) {
                outputWriter.print(" " + path.get(i).vertex2);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static List<Edge> computePath(List<Edge>[] adjacencyList) {
        int amsterdamID = 1;
        Dijkstra dijkstra = new Dijkstra(adjacencyList, amsterdamID, -1, null);
        Dijkstra dijkstraSkippingEdges = new Dijkstra(adjacencyList, 0, amsterdamID, dijkstra.edgeTo);
        return dijkstraSkippingEdges.pathTo(amsterdamID);
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

        private final Edge[] edgeTo;
        private final long[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target, Edge[] edgesToSkip) {
            edgeTo = new Edge[adjacencyList.length];
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
                relax(adjacencyList, vertex, edgesToSkip);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, Edge[] edgesToSkip) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;
                if (edgesToSkip != null && (edgesToSkip[vertex.id].vertex1 == neighbor)) {
                    continue;
                }

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    edgeTo[neighbor] = edge;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] != MAX_VALUE;
        }

        public List<Edge> pathTo(int vertex) {
            if (!hasPathTo(vertex)) {
                return null;
            }

            List<Edge> path = new ArrayList<>();
            for (Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
                path.add(edge);
            }
            Collections.reverse(path);
            return path;
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
