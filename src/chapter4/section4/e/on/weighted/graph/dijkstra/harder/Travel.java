package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/01/24.
 */
@SuppressWarnings("unchecked")
public class Travel {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cityNames = FastReader.nextInt();

        while (cityNames != 0) {
            List<Edge>[] adjacencyList = new List[cityNames];
            Set<Integer>[] timesPerCityID = new Set[adjacencyList.length];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
                timesPerCityID[i] = new HashSet<>();
            }

            Map<String, Integer> cityNameToIDMap = new HashMap<>();
            for (int i = 0; i < cityNames; i++) {
                String cityName = FastReader.next();
                computeCityID(cityNameToIDMap, cityName);
            }

            int trains = FastReader.nextInt();
            for (int t = 0; t < trains; t++) {
                int line = FastReader.nextInt();
                int previousCityID = -1;
                int previousTime = -1;

                for (int l = 0; l < line; l++) {
                    int time = FastReader.nextInt();
                    int cityID = cityNameToIDMap.get(FastReader.next());
                    timesPerCityID[cityID].add(time);

                    if (l > 0) {
                        adjacencyList[previousCityID].add(new Edge(cityID, previousTime, time));
                    }
                    previousCityID = cityID;
                    previousTime = time;
                }
            }
            int earliestStartTime = FastReader.nextInt();
            int startCityID = cityNameToIDMap.get(FastReader.next());
            int destinationCityID = cityNameToIDMap.get(FastReader.next());

            Dijkstra dijkstra = new Dijkstra(adjacencyList, startCityID, destinationCityID, earliestStartTime,
                    timesPerCityID[startCityID]);
            if (dijkstra.hasPathTo(destinationCityID)) {
                int bestStartTime = dijkstra.bestStartTime[destinationCityID];
                int bestArrivalTime = dijkstra.bestArrivalTime[destinationCityID];
                outputWriter.printLine(String.format("%04d %04d", bestStartTime, bestArrivalTime));
            } else {
                outputWriter.printLine("No connection");
            }
            cityNames = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void computeCityID(Map<String, Integer> cityNameToIDMap, String cityName) {
        if (!cityNameToIDMap.containsKey(cityName)) {
            cityNameToIDMap.put(cityName, cityNameToIDMap.size());
        }
    }

    private static class Edge {
        private final int vertex2;
        private final int boardingTime;
        private final int arrivalTime;

        public Edge(int vertex2, int boardingTime, int arrivalTime) {
            this.vertex2 = vertex2;
            this.boardingTime = boardingTime;
            this.arrivalTime = arrivalTime;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            int startTime;
            int currentTime;

            public Vertex(int id, int startTime, int currentTime) {
                this.id = id;
                this.startTime = startTime;
                this.currentTime = currentTime;
            }

            @Override
            public int compareTo(Vertex other) {
                if (currentTime != other.currentTime) {
                    return Integer.compare(currentTime, other.currentTime);
                }
                return Integer.compare(other.startTime, startTime);
            }
        }

        private final int[] bestStartTime;
        private final int[] bestArrivalTime;
        private final PriorityQueue<Vertex> priorityQueue;
        private final int MAX_VALUE = Integer.MAX_VALUE;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int destination, int earliestStartTime,
                        Set<Integer> startTimes) {
            bestStartTime = new int[adjacencyList.length];
            bestArrivalTime = new int[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(bestArrivalTime, MAX_VALUE);
            for (int startTime : startTimes) {
                if (startTime < earliestStartTime) {
                    continue;
                }
                if (startTime < bestArrivalTime[source]) {
                    bestArrivalTime[source] = startTime;
                    bestStartTime[source] = startTime;
                }
                priorityQueue.offer(new Vertex(source, startTime, startTime));
            }

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == destination) {
                    break;
                }
                relax(adjacencyList, vertex);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;
                if (edge.boardingTime < vertex.currentTime) {
                    continue;
                }

                if (bestArrivalTime[neighbor] > edge.arrivalTime
                        || (bestArrivalTime[neighbor] == edge.arrivalTime && bestStartTime[neighbor] < vertex.startTime)) {
                    bestArrivalTime[neighbor] = edge.arrivalTime;
                    bestStartTime[neighbor] = vertex.startTime;
                }
                priorityQueue.offer(new Vertex(neighbor, vertex.startTime, edge.arrivalTime));
            }
        }

        public boolean hasPathTo(int vertex) {
            return bestArrivalTime[vertex] != MAX_VALUE;
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
