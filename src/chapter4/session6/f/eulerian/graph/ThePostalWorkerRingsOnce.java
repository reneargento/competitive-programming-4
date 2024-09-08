package chapter4.session6.f.eulerian.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/08/24.
 */
@SuppressWarnings("unchecked")
public class ThePostalWorkerRingsOnce {

    private static class Edge {
        int vertex;
        int cost;

        public Edge(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            List<Edge>[] adjacent = new List[26];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new ArrayList<>();
            }
            int initialCost = 0;

            while (!line.equals("deadend")) {
                int nodeId1 = line.charAt(0) - 'a';
                int nodeId2 = line.charAt(line.length() - 1) - 'a';
                int length = line.length();

                Edge edge1 = new Edge(nodeId2, length);
                Edge edge2 = new Edge(nodeId1, length);
                adjacent[nodeId1].add(edge1);
                adjacent[nodeId2].add(edge2);
                initialCost += length;

                line = FastReader.getLine();
            }

            int tourCost = computeTourCost(adjacent, initialCost);
            outputWriter.printLine(tourCost);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeTourCost(List<Edge>[] adjacent, int initialCost) {
        int tourCost = initialCost;
        int edges = 0;

        int startVertex = -1;
        int endVertex = -1;
        boolean hasOddDegree = false;

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            edges += adjacent[vertex].size();
            int vertexDegree = adjacent[vertex].size();

            if (vertexDegree % 2 != 0) {
                hasOddDegree = true;

                if (startVertex == -1) {
                    startVertex = vertex;
                } else if (endVertex == -1) {
                    endVertex = vertex;
                }
            }
        }

        if (edges == 0) {
            return 0;
        }
        if (startVertex == -1) {
            startVertex = nonIsolatedVertex(adjacent);
        }

        if (hasOddDegree) {
            Dijkstra dijkstra = new Dijkstra(adjacent, startVertex, endVertex);
            tourCost += dijkstra.distTo[endVertex];
        }
        return tourCost;
    }

    private static int nonIsolatedVertex(List<Edge>[] adjacent) {
        int nonIsolatedVertex = -1;

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (!adjacent[vertex].isEmpty()) {
                nonIsolatedVertex = vertex;
                break;
            }
        }
        return nonIsolatedVertex;
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

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target) {
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
                relax(adjacencyList, vertex);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            // Optional optimization
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex;

                if (distTo[neighbor] > distTo[vertex.id] + edge.cost) {
                    distTo[neighbor] = distTo[vertex.id] + edge.cost;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                }
            }
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
