package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/01/24.
 */
@SuppressWarnings("unchecked")
public class FireStation {

    private static class Edge {
        int intersectionID2;
        int length;

        public Edge(int intersectionID2, int length) {
            this.intersectionID2 = intersectionID2;
            this.length = length;
        }
    }

    private static class Vertex implements Comparable<Vertex> {
        int intersectionID;
        long distance;

        public Vertex(int intersectionID, long distance) {
            this.intersectionID = intersectionID;
            this.distance = distance;
        }

        @Override
        public int compareTo(Vertex other) {
            return Long.compare(distance, other.distance);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            int fireStations = FastReader.nextInt();
            Set<Integer> fireStationIDs = new HashSet<>();
            List<Edge>[] adjacencyList = new List[FastReader.nextInt()];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int f = 0; f < fireStations; f++) {
                int fireStationID = FastReader.nextInt() - 1;
                fireStationIDs.add(fireStationID);
            }

            String line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");
                int intersectionID1 = Integer.parseInt(data[0]) - 1;
                int intersectionID2 = Integer.parseInt(data[1]) - 1;
                int length = Integer.parseInt(data[2]);
                adjacencyList[intersectionID1].add(new Edge(intersectionID2, length));
                adjacencyList[intersectionID2].add(new Edge(intersectionID1, length));

                line = FastReader.getLine();
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            int intersectionToBuild = computeBestLocation(adjacencyList, fireStationIDs);
            outputWriter.printLine(intersectionToBuild);
        }
        outputWriter.flush();
    }

    private static int computeBestLocation(List<Edge>[] adjacencyList, Set<Integer> fireStationIDs) {
        int bestIntersectionID = 0;
        long bestDistance = Long.MAX_VALUE;
        long[] distances = new long[adjacencyList.length];

        for (int intersectionID = 0; intersectionID < adjacencyList.length; intersectionID++) {
            if (fireStationIDs.contains(intersectionID)) {
                continue;
            }
            long furthestDistance = computeLongestDistance(adjacencyList, distances, fireStationIDs, intersectionID);
            if (furthestDistance < bestDistance) {
                bestDistance = furthestDistance;
                bestIntersectionID = intersectionID;
            }
        }
        return bestIntersectionID + 1;
    }

    private static long computeLongestDistance(List<Edge>[] adjacencyList, long[] distances,
                                               Set<Integer> fireStationIDs, int candidateID) {
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(adjacencyList.length);
        Arrays.fill(distances, Long.MAX_VALUE);

        for (int fireStationID : fireStationIDs) {
            priorityQueue.offer(new Vertex(fireStationID, 0));
            distances[fireStationID] = 0;
        }
        priorityQueue.offer(new Vertex(candidateID, 0));
        distances[candidateID] = 0;

        while (!priorityQueue.isEmpty()) {
            Vertex vertex = priorityQueue.poll();

            for (Edge edge : adjacencyList[vertex.intersectionID]) {
                if (distances[edge.intersectionID2] > distances[vertex.intersectionID] + edge.length) {
                    distances[edge.intersectionID2] = distances[vertex.intersectionID] + edge.length;
                    priorityQueue.offer(new Vertex(edge.intersectionID2, distances[edge.intersectionID2]));
                }
            }
        }

        long furthestDistance = -1;
        for (long distance : distances) {
            if (distance > furthestDistance) {
                furthestDistance = distance;
            }
        }
        return furthestDistance;
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
