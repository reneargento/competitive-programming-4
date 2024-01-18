package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 15/01/24.
 */
@SuppressWarnings("unchecked")
public class RoughRoads {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dataSet = 1;

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int junctions = Integer.parseInt(data[0]);
            int roads = Integer.parseInt(data[1]);
            List<Edge>[] adjacencyList = new List[junctions];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int houseID = junctions - 1;

            for (int r = 0; r < roads; r++) {
                int junctionID1 = FastReader.nextInt();
                int junctionID2 = FastReader.nextInt();
                int length = FastReader.nextInt();
                adjacencyList[junctionID1].add(new Edge(junctionID2, length));
                adjacencyList[junctionID2].add(new Edge(junctionID1, length));
            }

            Dijkstra dijkstra = new Dijkstra(adjacencyList, 0);
            outputWriter.printLine(String.format("Set #%d", dataSet));
            if (dijkstra.distTo[houseID][1] != Dijkstra.MAX_VALUE) {
                outputWriter.printLine(dijkstra.distTo[houseID][1]);
            } else {
                outputWriter.printLine("?");
            }

            dataSet++;
            line = FastReader.getLine();
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
            int parity;

            public Vertex(int id, long distance, int parity) {
                this.id = id;
                this.distance = distance;
                this.parity = parity;
            }

            @Override
            public int compareTo(Vertex other) {
                return Long.compare(distance, other.distance);
            }
        }

        private final long[][] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private static final long MAX_VALUE = 10000000000000000L;

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            distTo = new long[adjacencyList.length][2];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            for (int i = 0; i < distTo.length; i++) {
                Arrays.fill(distTo[i], MAX_VALUE);
            }
            priorityQueue.offer(new Vertex(source, 0, 1));
            distTo[source][1] = 0;

            while (!priorityQueue.isEmpty()) {
                relax(adjacencyList, priorityQueue.poll());
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            int nextParity = vertex.parity ^ 1;

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;
                long candidateDistance = distTo[vertex.id][vertex.parity] + edge.distance;

                if (distTo[neighbor][nextParity] > candidateDistance) {
                    distTo[neighbor][nextParity] = candidateDistance;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor][nextParity], nextParity));
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
