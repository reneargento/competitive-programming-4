package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/12/23.
 */
@SuppressWarnings("unchecked")
public class AllRoadsLeadWhere {

    private static final int MAX_CITIES = 26;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            String[] data = FastReader.getLine().split(" ");
            int roads = Integer.parseInt(data[0]);
            int queries = Integer.parseInt(data[1]);

            Map<Character, Integer> cityNameToIDMap = new HashMap<>();
            Map<Integer, Character> cityIDToNameMap = new HashMap<>();

            List<Integer>[] adjacencyList = new List[MAX_CITIES];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < roads; i++) {
                char cityName1 = FastReader.next().charAt(0);
                char cityName2 = FastReader.next().charAt(0);

                int cityID1 = getCityID(cityName1, cityNameToIDMap, cityIDToNameMap);
                int cityID2 = getCityID(cityName2, cityNameToIDMap, cityIDToNameMap);
                adjacencyList[cityID1].add(cityID2);
                adjacencyList[cityID2].add(cityID1);
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            for (int q = 0; q < queries; q++) {
                char cityName1 = FastReader.next().charAt(0);
                char cityName2 = FastReader.next().charAt(0);

                int cityID1 = getCityID(cityName1, cityNameToIDMap, cityIDToNameMap);
                int cityID2 = getCityID(cityName2, cityNameToIDMap, cityIDToNameMap);

                String route = computeRoute(adjacencyList, cityIDToNameMap, cityID1, cityID2);
                outputWriter.printLine(route);
            }
        }
        outputWriter.flush();
    }

    private static int getCityID(char cityName, Map<Character, Integer> cityNameToIDMap,
                                 Map<Integer, Character> cityIDToNameMap) {
        if (!cityNameToIDMap.containsKey(cityName)) {
            int cityID = cityIDToNameMap.size();
            cityNameToIDMap.put(cityName, cityID);
            cityIDToNameMap.put(cityID, cityName);
        }
        return cityNameToIDMap.get(cityName);
    }

    private static String computeRoute(List<Integer>[] adjacencyList, Map<Integer, Character> cityIDToNameMap,
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
        return createRoute(destinationCityID, parent, cityIDToNameMap);
    }

    private static String createRoute(int destinationCityID, int[] parent, Map<Integer, Character> cityIDToNameMap) {
        StringBuilder route = new StringBuilder();
        Deque<Character> cityStack = new ArrayDeque<>();

        for (int cityID = destinationCityID; cityID != -1; cityID = parent[cityID]) {
            char cityName = cityIDToNameMap.get(cityID);
            cityStack.push(cityName);
        }

        while (!cityStack.isEmpty()) {
            route.append(cityStack.pop());
        }
        return route.toString();
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
