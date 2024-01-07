package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/01/24.
 */
@SuppressWarnings("unchecked")
public class BankRobbery {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int sites = Integer.parseInt(data[0]);
            int roads = Integer.parseInt(data[1]);
            int banksNumber = Integer.parseInt(data[2]);
            int policeStationsNumber = Integer.parseInt(data[3]);

            List<Edge>[] adjacencyList = new List[sites];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int m = 0; m < roads; m++) {
                int siteID1 = FastReader.nextInt();
                int siteID2 = FastReader.nextInt();
                int time = FastReader.nextInt();
                adjacencyList[siteID1].add(new Edge(siteID1, siteID2, time));
                adjacencyList[siteID2].add(new Edge(siteID2, siteID1, time));
            }

            int[] banks = new int[banksNumber];
            for (int b = 0; b < banks.length; b++) {
                banks[b] = FastReader.nextInt();
            }

            int[] policeStations = new int[policeStationsNumber];
            for (int p = 0; p < policeStations.length; p++) {
                policeStations[p] = FastReader.nextInt();
            }

            Result result = computeBanksFurthestAway(adjacencyList, banks, policeStations);
            List<Integer> banksFurthestAway = result.banksFurthestAway;
            outputWriter.print(banksFurthestAway.size() + " ");
            if (result.minimumTime == Dijkstra.MAX_VALUE) {
                outputWriter.printLine("*");
            } else {
                outputWriter.printLine(result.minimumTime);
            }
            outputWriter.print(banksFurthestAway.get(0));
            for (int i = 1; i < banksFurthestAway.size(); i++) {
                outputWriter.print(" " + banksFurthestAway.get(i));
            }
            outputWriter.printLine();

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeBanksFurthestAway(List<Edge>[] adjacencyList, int[] banks, int[] policeStations) {
        int furthestTime = 0;
        List<Integer> banksFurthestAway = new ArrayList<>();
        Dijkstra dijkstra = new Dijkstra(adjacencyList, policeStations);

        for (int bank : banks) {
            if (dijkstra.distTo[bank] > furthestTime) {
                furthestTime = dijkstra.distTo[bank];
            }
        }

        for (int bank : banks) {
            if (dijkstra.distTo[bank] == furthestTime) {
                banksFurthestAway.add(bank);
            }
        }
        Collections.sort(banksFurthestAway);
        return new Result(furthestTime, banksFurthestAway);
    }

    private static class Result {
        int minimumTime;
        List<Integer> banksFurthestAway;

        public Result(int minimumTime, List<Integer> banksFurthestAway) {
            this.minimumTime = minimumTime;
            this.banksFurthestAway = banksFurthestAway;
        }
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
            int distance;

            public Vertex(int id, int distance) {
                this.id = id;
                this.distance = distance;
            }

            @Override
            public int compareTo(Vertex other) {
                return Integer.compare(distance, other.distance);
            }
        }

        private final int[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        public static int MAX_VALUE = 1000000000;

        public Dijkstra(List<Edge>[] adjacencyList, int[] sources) {
            distTo = new int[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            for (int source : sources) {
                distTo[source] = 0;
                priorityQueue.offer(new Vertex(source, 0));
            }

            while (!priorityQueue.isEmpty()) {
                relax(adjacencyList, priorityQueue.poll());
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
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
