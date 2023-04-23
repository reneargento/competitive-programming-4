package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/04/23.
 */
@SuppressWarnings("unchecked")
public class CantinaOfBabel {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int characters = FastReader.nextInt();
        Map<String, List<Integer>> languagesToIDsMap = new HashMap<>();

        List<String>[] languagesUnderstood = new List[characters];
        for (int i = 0; i < languagesUnderstood.length; i++) {
            languagesUnderstood[i] = new ArrayList<>();
        }

        for (int characterID = 0; characterID < characters; characterID++) {
            String[] data = FastReader.getLine().split(" ");

            String spokenLanguage = data[1];
            if (!languagesToIDsMap.containsKey(spokenLanguage)) {
                languagesToIDsMap.put(spokenLanguage, new ArrayList<>());
            }
            languagesToIDsMap.get(spokenLanguage).add(characterID);

            for (int i = 1; i < data.length; i++) {
                languagesUnderstood[characterID].add(data[i]);
            }
        }

        int charactersToLeave = computeCharactersToLeave(characters, languagesToIDsMap, languagesUnderstood);
        outputWriter.printLine(charactersToLeave);
        outputWriter.flush();
    }

    private static int computeCharactersToLeave(int characters, Map<String, List<Integer>> languagesToIDsMap,
                                                List<String>[] languagesUnderstood) {
        List<Integer>[] adjacencyList = createGraph(characters, languagesToIDsMap, languagesUnderstood);
        StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(adjacencyList);

        int maxSCCSize = 0;
        for (int size : stronglyConnectedComponents.componentSizes) {
            maxSCCSize = Math.max(maxSCCSize, size);
        }
        return adjacencyList.length - maxSCCSize;
    }

    private static List<Integer>[] createGraph(int characters, Map<String, List<Integer>> languagesToIDsMap,
                                               List<String>[] languagesUnderstood) {
        List<Integer>[] adjacencyList = new List[characters];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int characterID = 0; characterID < characters; characterID++) {
            for (String language : languagesUnderstood[characterID]) {
                if (!languagesToIDsMap.containsKey(language)) {
                    continue;
                }
                for (int characterToConnectID : languagesToIDsMap.get(language)) {
                    if (characterID != characterToConnectID) {
                        adjacencyList[characterID].add(characterToConnectID);
                    }
                }
            }
        }
        return adjacencyList;
    }

    private static class StronglyConnectedComponents {
        private int sccCount;
        private final int[] componentSizes;

        public int count() {
            return sccCount;
        }

        public StronglyConnectedComponents(List<Integer>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];
            componentSizes = new int[adjacent.length];

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

            if (!getFinishTimes) {
                componentSizes[sccCount]++;
            }

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
