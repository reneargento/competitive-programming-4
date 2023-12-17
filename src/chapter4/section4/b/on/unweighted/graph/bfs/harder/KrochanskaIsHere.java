package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 15/12/23.
 */
@SuppressWarnings("unchecked")
public class KrochanskaIsHere {

    private static class State {
        int stationID;
        int distance;

        public State(int stationID, int distance) {
            this.stationID = stationID;
            this.distance = distance;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int stations = FastReader.nextInt();
            int lines = FastReader.nextInt();

            List<Integer>[] adjacencyList = new List[stations + 1];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int[] stationLines = new int[adjacencyList.length];

            for (int i = 0; i < lines; i++) {
                int previousStation;
                int station = FastReader.nextInt();
                stationLines[station]++;

                while (station != 0) {
                    previousStation = station;
                    station = FastReader.nextInt();
                    stationLines[station]++;

                    if (station != 0) {
                        adjacencyList[previousStation].add(station);
                        adjacencyList[station].add(previousStation);
                    }
                }
            }

            int krochanskaStation = computeKrochanskaStation(adjacencyList, stationLines);
            outputWriter.printLine(String.format("Krochanska is in: %d", krochanskaStation));
        }
        outputWriter.flush();
    }

    private static int computeKrochanskaStation(List<Integer>[] adjacencyList, int[] stationLines) {
        Set<Integer> importantStations = new HashSet<>();
        for (int stationID = 1; stationID < adjacencyList.length; stationID++) {
            if (stationLines[stationID] > 1) {
                importantStations.add(stationID);
            }
        }

        double minimumAverageDistance = Double.POSITIVE_INFINITY;
        int krochanskaStation = -1;

        for (int station : importantStations) {
            double averageDistance = computeAverageDistance(adjacencyList, importantStations, station);
            if (averageDistance < minimumAverageDistance) {
                minimumAverageDistance = averageDistance;
                krochanskaStation = station;
            }
        }
        return krochanskaStation;
    }

    private static double computeAverageDistance(List<Integer>[] adjacencyList, Set<Integer> importantStations,
                                                 int startStation) {
        int totalDistance = 0;
        boolean[] visited = new boolean[adjacencyList.length];
        int importantStationsVisited = 1;

        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(startStation, 0));
        visited[startStation] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            int stationID = state.stationID;

            for (int neighborID : adjacencyList[stationID]) {
                if (!visited[neighborID]) {
                    int nextDistance = state.distance + 1;
                    if (importantStations.contains(neighborID)) {
                        totalDistance += nextDistance;
                        importantStationsVisited++;
                    }

                    queue.offer(new State(neighborID, nextDistance));
                    visited[neighborID] = true;
                }
            }

            if (importantStationsVisited == importantStations.size()) {
                break;
            }
        }
        return totalDistance / (double) importantStations.size();
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
