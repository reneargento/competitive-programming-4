package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 19/01/24.
 */
@SuppressWarnings("unchecked")
public class AirportSetup {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int cities = FastReader.nextInt();
            int routes = FastReader.nextInt();
            int citiesWithAirport = FastReader.nextInt();

            List<Integer>[] adjacencyList = new List[cities];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            boolean[] hasAirport = new boolean[cities];
            for (int i = 0; i < citiesWithAirport; i++) {
                hasAirport[FastReader.nextInt() - 1] = true;
            }

            for (int i = 0; i < routes; i++) {
                int cityName1 = FastReader.nextInt() - 1;
                int cityName2 = FastReader.nextInt() - 1;
                adjacencyList[cityName1].add(cityName2);
                adjacencyList[cityName2].add(cityName1);
            }
            int demands = FastReader.nextInt();

            outputWriter.printLine(String.format("Case %d:", t));
            for (int i = 0; i < demands; i++) {
                int cityName1 = FastReader.nextInt() - 1;
                int cityName2 = FastReader.nextInt() - 1;
                int airportsRequired = computeAirportsRequired(adjacencyList, hasAirport, cityName1, cityName2);
                outputWriter.printLine(airportsRequired);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static int computeAirportsRequired(List<Integer>[] adjacencyList, boolean[] hasAirport,
                                               int cityName1, int cityName2) {
        if (cityName1 == cityName2) {
            return 0;
        }
        boolean[] visited = new boolean[adjacencyList.length];
        int[] parentInPath = new int[adjacencyList.length];
        Arrays.fill(parentInPath, -1);

        Deque<Integer> deque = new ArrayDeque<>();
        deque.offerFirst(cityName1);
        visited[cityName1] = true;

        while (!deque.isEmpty()) {
            int cityName = deque.pollFirst();

            if (cityName == cityName2) {
                return computeAirportsRequiredWithPath(parentInPath, hasAirport, cityName2);
            }

            for (int neighborName : adjacencyList[cityName]) {
                if (!visited[neighborName]) {
                    visited[neighborName] = true;
                    if (hasAirport[neighborName]) {
                        deque.offerFirst(neighborName);
                    } else {
                        deque.offerLast(neighborName);
                    }
                    parentInPath[neighborName] = cityName;
                }
            }
        }
        return -1;
    }

    private static int computeAirportsRequiredWithPath(int[] parentInPath, boolean[] hasAirport, int cityName2) {
        int airportsRequired = 0;
        int currentCity = cityName2;

        while (currentCity != -1) {
            if (!hasAirport[currentCity]) {
                airportsRequired++;
            }
            currentCity = parentInPath[currentCity];
        }
        return airportsRequired;
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
