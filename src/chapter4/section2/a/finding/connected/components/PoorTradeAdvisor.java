package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/02/23.
 */
@SuppressWarnings("unchecked")
public class PoorTradeAdvisor {

    private static class Road {
        int cityID1;
        int cityID2;
        int ppa;

        public Road(int cityID1, int cityID2, int ppa) {
            this.cityID1 = cityID1;
            this.cityID2 = cityID2;
            this.ppa = ppa;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int roadsNumber = FastReader.nextInt();

        while (cities != 0 || roadsNumber != 0) {
            int highestPPA = Integer.MIN_VALUE;
            Road[] roads = new Road[roadsNumber];
            for (int i = 0; i < roads.length; i++) {
                roads[i] = new Road(FastReader.nextInt() - 1, FastReader.nextInt() - 1, FastReader.nextInt());
                highestPPA = Math.max(highestPPA, roads[i].ppa);
            }

            List<Integer>[] adjacencyList = computeGraph(cities, roads, highestPPA);
            int maximumCities = computeMaximumCitiesWithHighPPA(adjacencyList);
            outputWriter.printLine(maximumCities);

            cities = FastReader.nextInt();
            roadsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer>[] computeGraph(int cities, Road[] roads, int highestPPA) {
        List<Integer>[] adjacencyList = new List[cities];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (Road road : roads) {
            if (road.ppa == highestPPA) {
                adjacencyList[road.cityID1].add(road.cityID2);
                adjacencyList[road.cityID2].add(road.cityID1);
            }
        }
        return adjacencyList;
    }

    private static int computeMaximumCitiesWithHighPPA(List<Integer>[] adjacencyList) {
        int maximumCities = 0;
        boolean[] visited = new boolean[adjacencyList.length];

        for (int cityID = 0; cityID < adjacencyList.length; cityID++) {
            if (!visited[cityID]) {
                int cities = depthFirstSearch(adjacencyList, visited, cityID);
                maximumCities = Math.max(maximumCities, cities);
            }
        }
        return maximumCities;
    }

    private static int depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int cityID) {
        visited[cityID] = true;
        int cities = 1;

        for (int neighborCityID : adjacencyList[cityID]) {
            if (!visited[neighborCityID]) {
                cities += depthFirstSearch(adjacencyList, visited, neighborCityID);
            }
        }
        return cities;
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
