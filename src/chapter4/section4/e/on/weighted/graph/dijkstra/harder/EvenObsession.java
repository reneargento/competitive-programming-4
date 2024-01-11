package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/01/24.
 */
@SuppressWarnings("unchecked")
public class EvenObsession {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int cities = Integer.parseInt(data[0]);
            int roads = Integer.parseInt(data[1]);

            List<Edge>[] adjacencyList = new List[cities];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int m = 0; m < roads; m++) {
                int cityID1 = FastReader.nextInt() - 1;
                int cityID2 = FastReader.nextInt() - 1;
                int toll = FastReader.nextInt();

                adjacencyList[cityID1].add(new Edge(cityID2, toll));
                adjacencyList[cityID2].add(new Edge(cityID1, toll));
            }

            long minimumEvenToll = computeMinimumEvenToll(adjacencyList);
            outputWriter.printLine(minimumEvenToll);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeMinimumEvenToll(List<Edge>[] adjacencyList) {
        int target = adjacencyList.length - 1;
        Dijkstra dijkstra = new Dijkstra(adjacencyList, 0, target);
        if (dijkstra.hasPathTo(target)) {
            return dijkstra.distTo[target][Dijkstra.EVEN];
        } else {
            return -1;
        }
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
            long totalPaid;
            int tollsPaid;

            public Vertex(int id, long totalPaid, int tollsPaid) {
                this.id = id;
                this.totalPaid = totalPaid;
                this.tollsPaid = tollsPaid;
            }

            @Override
            public int compareTo(Vertex other) {
                return Long.compare(totalPaid, other.totalPaid);
            }
        }

        private final long[][] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;
        private static final int ODD = 0;
        private static final int EVEN = 1;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target) {
            distTo = new long[adjacencyList.length][2];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            for (int i = 0; i < distTo.length; i++) {
                Arrays.fill(distTo[i], MAX_VALUE);
            }
            distTo[source][EVEN] = 0;
            priorityQueue.offer(new Vertex(source, 0, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target && vertex.tollsPaid % 2 == 0) {
                    break;
                }
                relax(adjacencyList, vertex);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            int currentParityIndex = vertex.tollsPaid % 2 == 0 ? EVEN : ODD;
            int nextParityIndex = currentParityIndex == ODD ? EVEN : ODD;

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor][nextParityIndex] > distTo[vertex.id][currentParityIndex] + edge.distance) {
                    distTo[neighbor][nextParityIndex] = distTo[vertex.id][currentParityIndex] + edge.distance;
                    int nextTollsPaid = vertex.tollsPaid + 1;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor][nextParityIndex], nextTollsPaid));
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex][EVEN] != MAX_VALUE;
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
