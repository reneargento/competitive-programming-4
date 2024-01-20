package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 19/01/24.
 */
@SuppressWarnings("unchecked")
public class FullTank {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int roads = FastReader.nextInt();
        List<Edge>[] adjacencyList = new List[cities];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        int[] fuelPrice = new int[cities];
        for (int i = 0; i < fuelPrice.length; i++) {
            fuelPrice[i] = FastReader.nextInt();
        }

        for (int m = 0; m < roads; m++) {
            int cityID1 = FastReader.nextInt();
            int cityID2 = FastReader.nextInt();
            int length = FastReader.nextInt();
            adjacencyList[cityID1].add(new Edge(cityID2, length));
            adjacencyList[cityID2].add(new Edge(cityID1, length));
        }

        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            int fuelCapacity = FastReader.nextInt();
            int startCity = FastReader.nextInt();
            int goalCity = FastReader.nextInt();

            Dijkstra dijkstra = new Dijkstra(adjacencyList, startCity, goalCity, fuelPrice, fuelCapacity);
            if (dijkstra.hasPathTo(goalCity)) {
                int minimumCost = Integer.MAX_VALUE;
                for (int fuel = 0; fuel < dijkstra.bestCost[0].length; fuel++) {
                    if (dijkstra.bestCost[goalCity][fuel] != Integer.MAX_VALUE) {
                        minimumCost = Math.min(minimumCost, dijkstra.bestCost[goalCity][fuel]);
                    }
                }
                outputWriter.printLine(minimumCost);
            } else {
                outputWriter.printLine("impossible");
            }
        }
        outputWriter.flush();
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
            int totalCost;
            int currentFuel;

            public Vertex(int id, int totalCost, int currentFuel) {
                this.id = id;
                this.totalCost = totalCost;
                this.currentFuel = currentFuel;
            }

            @Override
            public int compareTo(Vertex other) {
                return Integer.compare(totalCost, other.totalCost);
            }
        }

        private final int[][] bestCost;
        private final PriorityQueue<Vertex> priorityQueue;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target, int[] fuelPrice, int fuelCapacity) {
            bestCost = new int[adjacencyList.length][101];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            for (int[] costs : bestCost) {
                Arrays.fill(costs, Integer.MAX_VALUE);
            }
            bestCost[source][0] = 0;
            priorityQueue.offer(new Vertex(source, 0, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target) {
                    break;
                }
                relax(adjacencyList, vertex, fuelPrice, fuelCapacity);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, int[] fuelPrice, int fuelCapacity) {
            if (bestCost[vertex.id][vertex.currentFuel] < vertex.totalCost) {
                return;
            }

            int nextFuel = vertex.currentFuel + 1;
            if (nextFuel <= fuelCapacity) {
                int nextCost = vertex.totalCost + fuelPrice[vertex.id];
                if (bestCost[vertex.id][nextFuel] > nextCost) {
                    bestCost[vertex.id][nextFuel] = nextCost;
                    priorityQueue.offer(new Vertex(vertex.id, nextCost, nextFuel));
                }
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (vertex.currentFuel >= edge.distance) {
                    int newCurrentFuel = vertex.currentFuel - edge.distance;
                    if (bestCost[neighbor][newCurrentFuel] > bestCost[vertex.id][vertex.currentFuel]) {
                        bestCost[neighbor][newCurrentFuel] = bestCost[vertex.id][vertex.currentFuel];
                        priorityQueue.offer(new Vertex(neighbor, bestCost[neighbor][newCurrentFuel], newCurrentFuel));
                    }
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            for (int fuel = 0; fuel < bestCost[0].length; fuel++) {
                if (bestCost[vertex][fuel] != Integer.MAX_VALUE) {
                    return true;
                }
            }
            return false;
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
