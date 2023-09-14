package chapter4.section3.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/09/23.
 */
@SuppressWarnings("unchecked")
public class NatureReserve {

    private static class Edge implements Comparable<Edge> {
        private final int vertexID;
        private final int weight;

        public Edge(int vertexID, int weight) {
            this.vertexID = vertexID;
            this.weight = weight;
        }

        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int stations = FastReader.nextInt();
            int channels = FastReader.nextInt();
            long programSize = FastReader.nextInt();

            int initialStationsNumber = FastReader.nextInt();
            Set<Integer> initialStations = new HashSet<>();

            List<Edge>[] adjacent = new List[stations];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new ArrayList<>();
            }

            for (int i = 0; i < initialStationsNumber; i++) {
                int stationID = FastReader.nextInt() - 1;
                initialStations.add(stationID);
            }
            for (int i = 0; i < channels; i++) {
                int station1ID = FastReader.nextInt() - 1;
                int station2ID = FastReader.nextInt() - 1;
                int energyRequired = FastReader.nextInt();

                Edge edge1 = new Edge(station1ID, energyRequired);
                Edge edge2 = new Edge(station2ID, energyRequired);
                adjacent[station1ID].add(edge2);
                adjacent[station2ID].add(edge1);
            }

            int channelsToActivate = stations - initialStationsNumber;
            long transferCost = channelsToActivate * programSize;

            PrimMinimumSpanningTree primMST = new PrimMinimumSpanningTree(adjacent, initialStations);
            long minimumEnergyNeeded = primMST.weight + transferCost;
            outputWriter.printLine(minimumEnergyNeeded);
        }
        outputWriter.flush();
    }

    private static class PrimMinimumSpanningTree {

        private final boolean[] marked; // true if vertex is on the minimum spanning tree
        private final PriorityQueue<Edge> priorityQueue; // eligible crossing edges
        public long weight;

        public PrimMinimumSpanningTree(List<Edge>[] adjacencyList, Set<Integer> initialStations) {
            int verticesNumber = adjacencyList.length;
            int verticesProcessed = 0;
            marked = new boolean[verticesNumber];
            priorityQueue = new PriorityQueue<>();

            // Initialize priority queue
            for (int station : initialStations) {
                process(adjacencyList, station);
                verticesProcessed++;
            }

            while (!priorityQueue.isEmpty() && verticesNumber != verticesProcessed) {
                Edge edge = priorityQueue.poll(); // Add closest vertex to the minimum spanning tree
                if (marked[edge.vertexID]) {
                    continue;
                }

                weight += edge.weight;
                verticesProcessed++;

                process(adjacencyList, edge.vertexID);
            }
        }

        private void process(List<Edge>[] adjacencyList, int vertex) {
            marked[vertex] = true;

            for (Edge edge : adjacencyList[vertex]) {
                if (!marked[edge.vertexID]) {
                    priorityQueue.offer(new Edge(edge.vertexID, edge.weight));
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
            while (!tokenizer.hasMoreTokens() ) {
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
