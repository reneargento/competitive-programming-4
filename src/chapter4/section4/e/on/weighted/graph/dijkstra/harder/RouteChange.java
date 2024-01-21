package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/01/24.
 */
@SuppressWarnings("unchecked")
public class RouteChange {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int roads = FastReader.nextInt();
        int citiesInServiceRoute = FastReader.nextInt();
        int repairCity = FastReader.nextInt();

        while (cities != 0 || roads != 0 || citiesInServiceRoute != 0 || repairCity != 0) {
            int[] nextCityCosts = new int[cities];

            List<Edge>[] adjacencyList = new List[cities];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int m = 0; m < roads; m++) {
                int cityID1 = FastReader.nextInt();
                int cityID2 = FastReader.nextInt();
                int tollCost = FastReader.nextInt();
                adjacencyList[cityID1].add(new Edge(cityID2, tollCost));
                adjacencyList[cityID2].add(new Edge(cityID1, tollCost));

                int minCityID = Math.min(cityID1, cityID2);
                int maxCityID = Math.max(cityID1, cityID2);
                if (maxCityID == minCityID + 1) {
                    nextCityCosts[minCityID] = tollCost;
                }
            }

            int targetCityID = citiesInServiceRoute - 1;
            int[] serviceRouteCosts = getServiceRouteCosts(nextCityCosts, targetCityID);
            Dijkstra dijkstra = new Dijkstra(adjacencyList, repairCity, targetCityID);

            int minCost = Integer.MAX_VALUE;
            for (int cityID = 0; cityID <= targetCityID; cityID++) {
                int cost = dijkstra.distTo[cityID] + serviceRouteCosts[cityID];
                minCost = Math.min(minCost, cost);
            }
            outputWriter.printLine(minCost);

            cities = FastReader.nextInt();
            roads = FastReader.nextInt();
            citiesInServiceRoute = FastReader.nextInt();
            repairCity = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] getServiceRouteCosts(int[] nextCityCosts, int targetCityID) {
        int[] serviceRouteCosts = new int[targetCityID + 1];
        serviceRouteCosts[targetCityID] = 0;
        for (int cityID = targetCityID - 1; cityID >= 0; cityID--) {
            serviceRouteCosts[cityID] = nextCityCosts[cityID] + serviceRouteCosts[cityID + 1];
        }
        return serviceRouteCosts;
    }

    private static class Edge {
        private final int vertex2;
        private final int distance;

        public Edge(int vertex2, int distance) {
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
        private static final int MAX_VALUE = 100000000;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target) {
            distTo = new int[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target) {
                    break;
                }
                relax(adjacencyList, vertex, target);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, int target) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    if (neighbor > target) {
                        priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                    }
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
