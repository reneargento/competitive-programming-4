package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/01/24.
 */
@SuppressWarnings("unchecked")
public class ForestFruits {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int clearings = FastReader.nextInt();
        int trails = FastReader.nextInt();
        int[] clearingsWithFruits = new int[FastReader.nextInt()];
        int daysToGrow = FastReader.nextInt();
        int numberOfDays = FastReader.nextInt();

        List<Edge>[] adjacencyList = new List[clearings];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int t = 0; t < trails; t++) {
            int clearingID1 = FastReader.nextInt() - 1;
            int clearingID2 = FastReader.nextInt() - 1;
            int distance = FastReader.nextInt();
            adjacencyList[clearingID1].add(new Edge(clearingID2, distance));
            adjacencyList[clearingID2].add(new Edge(clearingID1, distance));
        }

        for (int i = 0; i < clearingsWithFruits.length; i++) {
            clearingsWithFruits[i] = FastReader.nextInt() - 1;
        }

        long minimumDistance = computeMinimumDistance(adjacencyList, clearingsWithFruits, daysToGrow, numberOfDays);
        outputWriter.printLine(minimumDistance);
        outputWriter.flush();
    }

    private static long computeMinimumDistance(List<Edge>[] adjacencyList, int[] clearingsWithFruits,
                                               int daysToGrow, int numberOfDays) {
        Dijkstra dijkstra = new Dijkstra(adjacencyList, 0);
        List<Long> fruitDistances = computeDistancesToFruits(dijkstra, clearingsWithFruits);

        if (numberOfDays > fruitDistances.size() && daysToGrow > fruitDistances.size()) {
            return -1;
        }

        int lastDistanceIndex;
        if (numberOfDays > daysToGrow) {
            lastDistanceIndex = daysToGrow - 1;
        } else {
            if (numberOfDays <= fruitDistances.size()) {
                lastDistanceIndex = numberOfDays - 1;
            } else {
                lastDistanceIndex = fruitDistances.size() - 1;
            }
        }
        return fruitDistances.get(lastDistanceIndex) * 2;
    }

    private static List<Long> computeDistancesToFruits(Dijkstra dijkstra, int[] clearingsWithFruits) {
        List<Long> distances = new ArrayList<>();
        for (int i = 0; i < clearingsWithFruits.length; i++) {
            int clearingID = clearingsWithFruits[i];
            if (dijkstra.hasPathTo(clearingID)) {
                distances.add(dijkstra.distTo[clearingID]);
            }
        }
        Collections.sort(distances);
        return distances;
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
