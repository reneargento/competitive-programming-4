package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 13/04/23.
 */
@SuppressWarnings("unchecked")
public class Intercept {

    private static final double EPSILON = .000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Edge>[] adjacencyList = new List[FastReader.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        int links = FastReader.nextInt();

        for (int i = 0; i < links; i++) {
            int stationID1 = FastReader.nextInt();
            int stationID2 = FastReader.nextInt();
            long distance = FastReader.nextInt();
            adjacencyList[stationID1].add(new Edge(stationID2, distance));
        }
        int source = FastReader.nextInt();
        int destination = FastReader.nextInt();

        List<Integer> interceptStations = computeInterceptStations(adjacencyList, source, destination);
        outputWriter.print(interceptStations.get(0));
        for (int i = 1; i < interceptStations.size(); i++) {
            outputWriter.print(" " + interceptStations.get(i));
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static List<Integer> computeInterceptStations(List<Edge>[] adjacencyList, int source, int destination) {
        Dijkstra dijkstra = new Dijkstra(adjacencyList, source, destination);
        List<Integer>[] parentsDAG = dijkstra.parents;

        List<Integer> topologicalSort = computeTopologicalSort(adjacencyList, parentsDAG);
        return computeInterceptStations(parentsDAG, topologicalSort, destination);
    }

    private static List<Integer> computeTopologicalSort(List<Edge>[] adjacencyList, List<Integer>[] parentsDAG) {
        List<Integer> topologicalSort = new ArrayList<>();
        int[] inDegree = new int[adjacencyList.length];

        Queue<Integer> queue = new LinkedList<>();
        for (int stationID = 0; stationID < adjacencyList.length; stationID++) {
            for (Integer parent : parentsDAG[stationID]) {
                inDegree[parent]++;
            }
        }

        for (int stationID = 0; stationID < adjacencyList.length; stationID++) {
            if (inDegree[stationID] == 0) {
                queue.offer(stationID);
            }
        }

        while (!queue.isEmpty()) {
            int stationID = queue.poll();
            topologicalSort.add(stationID);

            for (Integer parent : parentsDAG[stationID]) {
                inDegree[parent]--;
                if (inDegree[parent] == 0) {
                    queue.offer(parent);
                }
            }
        }
        return topologicalSort;
    }

    private static List<Integer> computeInterceptStations(List<Integer>[] parentsDAG, List<Integer> topologicalSort,
                                                          int destination) {
        List<Integer> interceptStations = new ArrayList<>();
        double[] flow = new double[parentsDAG.length];
        flow[destination] = 1;

        for (int stationID : topologicalSort) {
            double flowValue = flow[stationID] / parentsDAG[stationID].size();
            for (Integer parent : parentsDAG[stationID]) {
                flow[parent] += flowValue;
            }
        }

        for (int stationID = 0; stationID < parentsDAG.length; stationID++) {
            double difference = 1.0 - flow[stationID];
            if (Math.abs(difference) < EPSILON) {
                interceptStations.add(stationID);
            }
        }
        return interceptStations;
    }

    private static class Dijkstra {
        private final long[] distTo;
        private final List<Integer>[] parents;
        private final PriorityQueue<Vertex> priorityQueue;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int destination) {
            distTo = new long[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);
            parents = new List[adjacencyList.length];
            long maxValue = 10000000000000000L;

            for (int vertex = 0; vertex < adjacencyList.length; vertex++) {
                distTo[vertex] = maxValue;
                parents[vertex] = new ArrayList<>();
            }
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                if (priorityQueue.peek().id == destination) {
                    break;
                }
                relax(adjacencyList, priorityQueue.poll(), destination);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, int destination) {
            if (vertex.id == destination) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.otherVertex;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));

                    parents[neighbor] = new ArrayList<>();
                    parents[neighbor].add(vertex.id);
                } else if (distTo[neighbor] == distTo[vertex.id] + edge.distance) {
                    parents[neighbor].add(vertex.id);
                }
            }
        }
    }

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

    private static class Edge {
        private final int otherVertex;
        private final long distance;

        public Edge(int otherVertex, long distance) {
            this.otherVertex = otherVertex;
            this.distance = distance;
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
