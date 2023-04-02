package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/03/23.
 */
@SuppressWarnings("unchecked")
public class RunningMoM {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int flights = FastReader.nextInt();
        Map<String, Integer> cityNameToIDMap = new HashMap<>();
        Set<Integer> safeCityIDs = new HashSet<>();

        List<Integer>[] adjacencyList = new ArrayList[flights];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < flights; i++) {
            String cityName1 = FastReader.next();
            String cityName2 = FastReader.next();

            int cityID1 = getCityID(cityNameToIDMap, cityName1);
            int cityID2 = getCityID(cityNameToIDMap, cityName2);
            adjacencyList[cityID1].add(cityID2);
        }

        String cityName = FastReader.getLine();
        while (cityName != null) {
            int cityID = getCityID(cityNameToIDMap, cityName);
            boolean hasCycle = safeCityIDs.contains(cityID)
                    || hasCycle(adjacencyList, safeCityIDs, cityID);

            outputWriter.print(cityName + " ");
            outputWriter.printLine(hasCycle ? "safe" : "trapped");
            cityName = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean hasCycle(List<Integer>[] adjacencyList, Set<Integer> safeCityIDs, int cityID) {
        HasCycleDirectedGraph cycle = new HasCycleDirectedGraph(adjacencyList, cityID);
        if (cycle.hasCycle()) {
            safeCityIDs.addAll(cycle.cycle);
        }
        return cycle.hasCycle();
    }

    private static int getCityID(Map<String, Integer> cityNameToIDMap, String cityName) {
        if (!cityNameToIDMap.containsKey(cityName)) {
            cityNameToIDMap.put(cityName, cityNameToIDMap.size());
        }
        return cityNameToIDMap.get(cityName);
    }

    private static class HasCycleDirectedGraph {
        private final boolean[] visited;
        private final int[] edgeTo;
        private Deque<Integer> cycle;
        private final boolean[] onStack;

        public HasCycleDirectedGraph(List<Integer>[] adjacent, int vertex) {
            onStack = new boolean[adjacent.length];
            edgeTo = new int[adjacent.length];
            visited = new boolean[adjacent.length];

            dfs(adjacent, vertex);
        }

        private void dfs(List<Integer>[] adjacent, int vertex) {
            onStack[vertex] = true;
            visited[vertex] = true;

            for (int neighbor : adjacent[vertex]) {
                if (hasCycle()) {
                    return;
                } else if (!visited[neighbor]) {
                    edgeTo[neighbor] = vertex;
                    dfs(adjacent, neighbor);
                } else if (onStack[neighbor]) {
                    cycle = new ArrayDeque<>();

                    for (int currentVertex = vertex; currentVertex != neighbor; currentVertex = edgeTo[currentVertex]) {
                        cycle.push(currentVertex);
                    }
                    cycle.push(neighbor);
                    cycle.push(vertex);
                }
            }
            onStack[vertex] = false;
        }

        public boolean hasCycle() {
            return cycle != null;
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
