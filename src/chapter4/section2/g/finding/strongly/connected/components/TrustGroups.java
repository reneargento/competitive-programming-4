package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 19/04/23.
 */
@SuppressWarnings("unchecked")
public class TrustGroups {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int namesNumber = FastReader.nextInt();
        int trustRelations = FastReader.nextInt();

        while (namesNumber != 0 || trustRelations != 0) {
            List<Integer>[] adjacencyList = new List[namesNumber];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            Map<String, Integer> nameToIDMap = new HashMap<>();

            for (int nameID = 0; nameID < namesNumber; nameID++) {
                String name = FastReader.getLine();
                nameToIDMap.put(name, nameID);
            }

            for (int i = 0; i < trustRelations; i++) {
                String personName1 = FastReader.getLine();
                String personName2 = FastReader.getLine();
                int personID1 = nameToIDMap.get(personName1);
                int personID2 = nameToIDMap.get(personName2);
                adjacencyList[personID1].add(personID2);
            }

            StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(adjacencyList);
            outputWriter.printLine(stronglyConnectedComponents.sccCount);

            namesNumber = FastReader.nextInt();
            trustRelations = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static class StronglyConnectedComponents {
        private int sccCount;

        public int count() {
            return sccCount;
        }

        public StronglyConnectedComponents(List<Integer>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];

            List<Integer>[] inverseEdges = invertGraphEdges(adjacent);
            int[] decreasingFinishingTimes = computeDecreasingFinishingTimes(inverseEdges);

            for (int vertex : decreasingFinishingTimes) {
                if (!visited[vertex]) {
                    depthFirstSearch(vertex, adjacent, null, visited, false);
                    sccCount++;
                }
            }
        }

        private int[] computeDecreasingFinishingTimes(List<Integer>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];
            Stack<Integer> finishTimes = new Stack<>();

            for (int i = 0; i < visited.length; i++) {
                if (!visited[i]) {
                    depthFirstSearch(i, adjacent, finishTimes, visited, true);
                }
            }

            int[] decreasingFinishingTimes = new int[finishTimes.size()];
            int arrayIndex = 0;
            while (!finishTimes.isEmpty()) {
                decreasingFinishingTimes[arrayIndex++] = finishTimes.pop();
            }
            return decreasingFinishingTimes;
        }

        private void depthFirstSearch(int sourceVertex, List<Integer>[] adjacent, Stack<Integer> finishTimes,
                                      boolean[] visited, boolean getFinishTimes) {
            visited[sourceVertex] = true;

            if (adjacent[sourceVertex] != null) {
                for (int neighbor : adjacent[sourceVertex]) {
                    if (!visited[neighbor]) {
                        depthFirstSearch(neighbor, adjacent, finishTimes, visited, getFinishTimes);
                    }
                }
            }

            if (getFinishTimes) {
                finishTimes.push(sourceVertex);
            }
        }

        private List<Integer>[] invertGraphEdges(List<Integer>[] adjacent) {
            List<Integer>[] inverseEdges = new ArrayList[adjacent.length];
            for (int i = 0; i < inverseEdges.length; i++) {
                inverseEdges[i] = new ArrayList<>();
            }

            for (int i = 0; i < adjacent.length; i++) {
                List<Integer> neighbors = adjacent[i];

                if (neighbors != null) {
                    for (int neighbor : adjacent[i]) {
                        inverseEdges[neighbor].add(i);
                    }
                }
            }
            return inverseEdges;
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
