package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/04/23.
 */
@SuppressWarnings("unchecked")
public class DovesAndBombs {

    private static class Station implements Comparable<Station> {
        int number;
        int pigeonValue;

        public Station(int number, int pigeonValue) {
            this.number = number;
            this.pigeonValue = pigeonValue;
        }

        @Override
        public int compareTo(Station other) {
            if (pigeonValue != other.pigeonValue) {
                return Integer.compare(other.pigeonValue, pigeonValue);
            }
            return Integer.compare(number, other.number);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int railwayStations = FastReader.nextInt();
        int targets = FastReader.nextInt();

        while (railwayStations != 0 && targets != 0) {
            List<Integer>[] adjacencyList = new List[railwayStations];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int station1 = FastReader.nextInt();
            int station2 = FastReader.nextInt();

            while (station1 != -1) {
                adjacencyList[station1].add(station2);
                adjacencyList[station2].add(station1);
                station1 = FastReader.nextInt();
                station2 = FastReader.nextInt();
            }

            List<Station> stationsToBomb = computeStationsToBomb(adjacencyList, targets);
            for (int i = 0; i < targets; i++) {
                Station station = stationsToBomb.get(i);
                outputWriter.printLine(station.number + " " + station.pigeonValue);
            }
            outputWriter.printLine();

            railwayStations = FastReader.nextInt();
            targets = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Station> computeStationsToBomb(List<Integer>[] adjacencyList, int targets) {
        List<Station> stationsToBomb = new ArrayList<>();

        ArticulationPoints.computeArticulationPoints(adjacencyList);
        for (int stationNumber : ArticulationPoints.articulationPoints) {
            int pigeonValue = 0;
            boolean[] visited = new boolean[adjacencyList.length];
            visited[stationNumber] = true;

            for (int neighbor : adjacencyList[stationNumber]) {
                if (!visited[neighbor]) {
                    pigeonValue++;
                    depthFirstSearch(adjacencyList, visited, neighbor);
                }
            }
            stationsToBomb.add(new Station(stationNumber, pigeonValue));
        }
        Collections.sort(stationsToBomb);

        for (int stationNumber = 0; stationsToBomb.size() < targets; stationNumber++) {
            if (!ArticulationPoints.articulationPoints.contains(stationNumber)) {
                stationsToBomb.add(new Station(stationNumber, 1));
            }
        }
        return stationsToBomb;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int stationNumber) {
        visited[stationNumber] = true;
        for (int neighbor : adjacencyList[stationNumber]) {
            if (!visited[neighbor]) {
                depthFirstSearch(adjacencyList, visited, neighbor);
            }
        }
    }

    private static class ArticulationPoints {
        private static int[] time;
        private static int[] low;
        private static int[] parent;
        private static int count;
        public static Set<Integer> articulationPoints;

        public static void computeArticulationPoints(List<Integer>[] adjacencyList) {
            time = new int[adjacencyList.length];
            low = new int[adjacencyList.length];
            parent = new int[adjacencyList.length];
            count = 0;
            articulationPoints = new HashSet<>();

            Arrays.fill(time, -1);
            Arrays.fill(low, -1);
            Arrays.fill(parent, -1);

            for (int vertex = 0; vertex < adjacencyList.length; vertex++) {
                if (time[vertex] == -1) {
                    dfs(adjacencyList, vertex);
                }
            }
        }

        private static void dfs(List<Integer>[] adjacencyList, int vertex) {
            time[vertex] = count;
            low[vertex] = count;
            count++;

            int children = 0;

            for (int neighbor : adjacencyList[vertex]) {
                if (time[neighbor] == -1) {
                    parent[neighbor] = vertex;
                    children++;

                    dfs(adjacencyList, neighbor);

                    low[vertex] = Math.min(low[vertex], low[neighbor]);

                    if (parent[vertex] == -1 && children > 1) {
                        articulationPoints.add(vertex);
                    } else if (parent[vertex] != -1 && low[neighbor] >= time[vertex]) {
                        articulationPoints.add(vertex);
                    }
                } else if (parent[vertex] != neighbor) {
                    low[vertex] = Math.min(low[vertex], time[neighbor]);
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
