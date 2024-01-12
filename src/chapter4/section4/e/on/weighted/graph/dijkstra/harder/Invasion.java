package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/01/24.
 */
@SuppressWarnings("unchecked")
public class Invasion {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int towns = FastReader.nextInt();
        int roads = FastReader.nextInt();
        int bases = FastReader.nextInt();
        int safeDistance = FastReader.nextInt();

        while (towns != 0 || roads != 0 || bases != 0 || safeDistance != 0) {
            List<Edge>[] adjacencyList = new List[towns];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int m = 0; m < roads; m++) {
                int townID1 = FastReader.nextInt() - 1;
                int townID2 = FastReader.nextInt() - 1;
                int distance = FastReader.nextInt();
                adjacencyList[townID1].add(new Edge(townID2, distance));
                adjacencyList[townID2].add(new Edge(townID1, distance));
            }

            Dijkstra dijkstra = new Dijkstra(adjacencyList);
            Set<Integer> unsafeTowns = new HashSet<>();
            for (int b = 0; b < bases; b++) {
                int baseTown = FastReader.nextInt() - 1;
                dijkstra.addSource(adjacencyList, baseTown, safeDistance);

                unsafeTowns.addAll(dijkstra.unsafeTowns);
                int safeTowns = towns - unsafeTowns.size();
                outputWriter.printLine(safeTowns);
            }
            outputWriter.printLine();

            towns = FastReader.nextInt();
            roads = FastReader.nextInt();
            bases = FastReader.nextInt();
            safeDistance = FastReader.nextInt();
        }
        outputWriter.flush();
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
        List<Integer> unsafeTowns;

        public Dijkstra(List<Edge>[] adjacencyList) {
            distTo = new long[adjacencyList.length];
            priorityQueue = new PriorityQueue<>();
            Arrays.fill(distTo, MAX_VALUE);
        }

        private void addSource(List<Edge>[] adjacencyList, int source, int safeDistance) {
            unsafeTowns = new ArrayList<>();
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                relax(adjacencyList, vertex, safeDistance);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, int safeDistance) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }
            unsafeTowns.add(vertex.id);

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;
                long newDistance = distTo[vertex.id] + edge.distance;

                if (newDistance < safeDistance && distTo[neighbor] > newDistance) {
                    distTo[neighbor] = newDistance;
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
