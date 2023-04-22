package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 19/04/23.
 */
@SuppressWarnings("unchecked")
public class CallingCircles {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int people = FastReader.nextInt();
        int phoneCalls = FastReader.nextInt();
        int dataSetNumber = 1;

        while (people != 0 || phoneCalls != 0) {
            List<Integer>[] adjacencyList = new List[people];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            Map<String, Integer> nameToPersonIDMap = new HashMap<>();
            String[] idToName = new String[people];

            for (int i = 0; i < phoneCalls; i++) {
                String personName1 = FastReader.next();
                String personName2 = FastReader.next();

                int personID1 = getPersonID(personName1, nameToPersonIDMap, idToName);
                int personID2 = getPersonID(personName2, nameToPersonIDMap, idToName);
                adjacencyList[personID1].add(personID2);
            }

            if (dataSetNumber > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Calling circles for data set %d:", dataSetNumber));
            StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(adjacencyList);
            for (int sccID = 0; sccID < stronglyConnectedComponents.sccCount; sccID++) {
                boolean isFirstPerson = true;

                for (int personID = 0; personID < people; personID++) {
                    if (stronglyConnectedComponents.sccId[personID] == sccID) {
                        if (!isFirstPerson) {
                            outputWriter.print(", ");
                        }
                        String personName = idToName[personID];
                        outputWriter.print(personName);
                        isFirstPerson = false;
                    }
                }
                outputWriter.printLine();
            }

            dataSetNumber++;
            people = FastReader.nextInt();
            phoneCalls = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int getPersonID(String personName, Map<String, Integer> nameToPersonIDMap, String[] idToName) {
        if (!nameToPersonIDMap.containsKey(personName)) {
            int personID = nameToPersonIDMap.size();
            nameToPersonIDMap.put(personName, personID);
            idToName[personID] = personName;
        }
        return nameToPersonIDMap.get(personName);
    }

    private static class StronglyConnectedComponents {
        private final int[] sccId;
        private int sccCount;

        public int count() {
            return sccCount;
        }

        public StronglyConnectedComponents(List<Integer>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];
            sccId = new int[adjacent.length];

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
                sccId[sourceVertex] = sccCount;
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
