package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/12/23.
 */
@SuppressWarnings("unchecked")
public class WeShipCheap {

    private static final int MAX_CITIES = 676; // 26 * 26

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int caseID = 1;

        while (line != null) {
            int links = Integer.parseInt(line);
            Map<String, Integer> cityNameToIDMap = new HashMap<>();
            Map<Integer, String> cityIDToNameMap = new HashMap<>();

            List<Integer>[] adjacencyList = new List[MAX_CITIES];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < links; i++) {
                String[] data = FastReader.getLine().split(" ");
                int cityID1 = getCityID(data[0], cityNameToIDMap, cityIDToNameMap);
                int cityID2 = getCityID(data[1], cityNameToIDMap, cityIDToNameMap);
                adjacencyList[cityID1].add(cityID2);
                adjacencyList[cityID2].add(cityID1);
            }

            String sourceCityName = FastReader.next();
            String destinationCityName = FastReader.next();

            int sourceCityID = getCityID(sourceCityName, cityNameToIDMap, cityIDToNameMap);
            int destinationCityID = getCityID(destinationCityName, cityNameToIDMap, cityIDToNameMap);

            if (caseID > 1) {
                outputWriter.printLine();
            }

            List<String> route = computeRoute(adjacencyList, cityIDToNameMap, sourceCityID, destinationCityID);
            if (route != null) {
                for (int r = 0; r < route.size() - 1; r++) {
                    outputWriter.printLine(route.get(r) + " " + route.get(r + 1));
                }
            } else {
                outputWriter.printLine("No route");
            }

            caseID++;
            line = FastReader.getLine();
            if (line != null) {
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static int getCityID(String cityName, Map<String, Integer> cityNameToIDMap,
                                 Map<Integer, String> cityIDToNameMap) {
        if (!cityNameToIDMap.containsKey(cityName)) {
            int cityID = cityIDToNameMap.size();
            cityNameToIDMap.put(cityName, cityID);
            cityIDToNameMap.put(cityID, cityName);
        }
        return cityNameToIDMap.get(cityName);
    }

    private static List<String> computeRoute(List<Integer>[] adjacencyList, Map<Integer, String> cityIDToNameMap,
                                             int sourceCityID, int destinationCityID) {
        int[] parent = new int[adjacencyList.length];
        boolean[] visited = new boolean[adjacencyList.length];
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(sourceCityID);
        visited[sourceCityID] = true;

        while (!queue.isEmpty()) {
            int cityID = queue.poll();
            if (cityID == destinationCityID) {
                break;
            }

            for (int neighbor : adjacencyList[cityID]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = cityID;
                    queue.offer(neighbor);
                }
            }
        }

        if (parent[destinationCityID] == -1) {
            return null;
        }
        return createRoute(destinationCityID, parent, cityIDToNameMap);
    }

    private static List<String> createRoute(int destinationCityID, int[] parent, Map<Integer, String> cityIDToNameMap) {
        List<String> route = new ArrayList<>();
        Deque<String> cityStack = new ArrayDeque<>();

        for (int cityID = destinationCityID; cityID != -1; cityID = parent[cityID]) {
            String cityName = cityIDToNameMap.get(cityID);
            cityStack.push(cityName);
        }

        while (!cityStack.isEmpty()) {
            route.add(cityStack.pop());
        }
        return route;
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
