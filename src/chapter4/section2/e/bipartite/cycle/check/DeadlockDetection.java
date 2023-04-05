package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/04/23.
 */
@SuppressWarnings("unchecked")
public class DeadlockDetection {

    private static class Cycle implements Comparable<Cycle> {
        String finalCycle;

        public Cycle(List<Integer> cycle, char[] vertexIDToName) {
            StringBuilder cycleValues = new StringBuilder();
            for (int vertexID : cycle) {
                cycleValues.append(vertexIDToName[vertexID]);
            }

            char lowestChar = 'Z';
            int lowestValueIndex = 0;
            for (int i = 0; i < cycleValues.length(); i++) {
                if (cycleValues.charAt(i) < lowestChar) {
                    lowestChar = cycleValues.charAt(i);
                    lowestValueIndex = i;
                }
            }
            finalCycle = cycleValues.substring(lowestValueIndex) + cycleValues.substring(0, lowestValueIndex + 1);
        }

        @Override
        public int compareTo(Cycle other) {
            return Integer.compare(finalCycle.length(), other.finalCycle.length());
        }
    }

    private static final int MAX_VERTICES = 52;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int processes = FastReader.nextInt();
            int resources = FastReader.nextInt();
            int edges = FastReader.nextInt();
            Map<Character, Integer> vertexNameToIDMap = new HashMap<>();
            char[] vertexIDToName = new char[MAX_VERTICES];

            List<Integer>[] adjacencyList = new ArrayList[MAX_VERTICES];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < edges; i++) {
                String[] edge = FastReader.next().split("-");
                char vertexName1 = edge[0].charAt(0);
                char vertexName2 = edge[1].charAt(0);
                int vertexID1 = getVertexID(vertexName1, vertexNameToIDMap, vertexIDToName);
                int vertexID2 = getVertexID(vertexName2, vertexNameToIDMap, vertexIDToName);
                adjacencyList[vertexID1].add(vertexID2);
            }

            AllCyclesDirectedGraph allCycles = new AllCyclesDirectedGraph(adjacencyList, vertexIDToName);
            List<Cycle> cycles = allCycles.cycles;
            Collections.sort(cycles);

            if (t > 0) {
                outputWriter.printLine();
            }

            if (!cycles.isEmpty()) {
                outputWriter.printLine("YES");
                for (Cycle cycle : cycles) {
                    String values = cycle.finalCycle;
                    outputWriter.print(values.charAt(0));

                    for (int i = 1; i < values.length(); i++) {
                        char vertexName = values.charAt(i);
                        outputWriter.print("-" + vertexName);
                    }
                    outputWriter.printLine();
                }
            } else {
                outputWriter.printLine("NO");
            }
        }
        outputWriter.flush();
    }

    private static int getVertexID(char vertexName, Map<Character, Integer> vertexNameToIDMap, char[] vertexIDToName) {
        if (!vertexNameToIDMap.containsKey(vertexName)) {
            int vertexID = vertexNameToIDMap.size();
            vertexNameToIDMap.put(vertexName, vertexID);
            vertexIDToName[vertexID] = vertexName;
        }
        return vertexNameToIDMap.get(vertexName);
    }

    private static class AllCyclesDirectedGraph {
        private final boolean[] visited;
        private final int[] edgeTo;
        private final List<Cycle> cycles;
        private final boolean[] onStack;

        public AllCyclesDirectedGraph(List<Integer>[] adjacent, char[] vertexIDToName) {
            onStack = new boolean[adjacent.length];
            edgeTo = new int[adjacent.length];
            visited = new boolean[adjacent.length];
            cycles = new ArrayList<>();

            for (int vertex = 0; vertex < adjacent.length; vertex++) {
                if (!visited[vertex]) {
                    dfs(adjacent, vertex, vertexIDToName);
                }
            }
        }

        private void dfs(List<Integer>[] adjacent, int vertex, char[] vertexIDToName) {
            onStack[vertex] = true;
            visited[vertex] = true;

            for (int neighbor : adjacent[vertex]) {
                if (!visited[neighbor]) {
                    edgeTo[neighbor] = vertex;
                    dfs(adjacent, neighbor, vertexIDToName);
                } else if (onStack[neighbor]) {
                    List<Integer> cycle = new ArrayList<>();

                    for (int currentVertex = vertex; currentVertex != neighbor; currentVertex = edgeTo[currentVertex]) {
                        cycle.add(currentVertex);
                    }
                    cycle.add(neighbor);
                    Collections.reverse(cycle);
                    cycles.add(new Cycle(cycle, vertexIDToName));
                }
            }
            onStack[vertex] = false;
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
