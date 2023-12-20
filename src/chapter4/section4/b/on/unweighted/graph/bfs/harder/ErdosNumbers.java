package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/12/23.
 */
public class ErdosNumbers {

    private static final String ERDOS_NAME = "Erdos, P.";
    private static final int INFINITE = Integer.MAX_VALUE;
    private static final String INFINITE_RESULT = "infinity";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int scenarios = FastReader.nextInt();
        for (int s = 1; s <= scenarios; s++) {
            int papers = FastReader.nextInt();
            int names = FastReader.nextInt();
            Map<String, Integer> nameToIDMap = new HashMap<>();
            List<List<Integer>> adjacencyList = new ArrayList<>();

            for (int i = 0; i < papers; i++) {
                String paper = FastReader.getLine();
                List<Integer> scientistIDs = parsePaper(nameToIDMap, paper);
                connectScientists(adjacencyList, scientistIDs);
            }

            int[] erdosNumbers = new int[adjacencyList.size()];
            Arrays.fill(erdosNumbers, INFINITE);

            if (nameToIDMap.containsKey(ERDOS_NAME)) {
                int erdosID = nameToIDMap.get(ERDOS_NAME);
                computeErdosNumbers(adjacencyList, erdosNumbers, erdosID);
            }

            outputWriter.printLine(String.format("Scenario %s", s));
            for (int i = 0; i < names; i++) {
                String name = FastReader.getLine();
                outputWriter.print(name + " ");

                if (nameToIDMap.containsKey(name)) {
                    int scientistID = nameToIDMap.get(name);
                    if (erdosNumbers[scientistID] != INFINITE) {
                        outputWriter.printLine(erdosNumbers[scientistID]);
                    } else {
                        outputWriter.printLine(INFINITE_RESULT);
                    }
                } else {
                    outputWriter.printLine(INFINITE_RESULT);
                }
            }
        }
        outputWriter.flush();
    }

    private static List<Integer> parsePaper(Map<String, Integer> nameToIDMap, String paper) {
        List<Integer> scientistIDs = new ArrayList<>();

        boolean shouldSplit = false;
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < paper.length(); i++) {
            char symbol = paper.charAt(i);
            if (symbol == ':') {
                int scientistID = getScientistID(nameToIDMap, name.toString());
                scientistIDs.add(scientistID);
                break;
            } else if (symbol == ',') {
                if (shouldSplit) {
                    int scientistID = getScientistID(nameToIDMap, name.toString());
                    name = new StringBuilder();
                    scientistIDs.add(scientistID);
                    i++;
                    shouldSplit = false;
                } else {
                    name.append(symbol);
                    shouldSplit = true;
                }
            } else {
                name.append(symbol);
            }
        }
        return scientistIDs;
    }

    private static void connectScientists(List<List<Integer>> adjacencyList, List<Integer> scientistIDs) {
        for (int i = 0; i < scientistIDs.size(); i++) {
            int scientistID1 = scientistIDs.get(i);
            if (adjacencyList.size() <= scientistID1) {
                adjacencyList.add(new ArrayList<>());
            }

            for (int j = i + 1; j < scientistIDs.size(); j++) {
                int scientistID2 = scientistIDs.get(j);
                if (adjacencyList.size() <= scientistID2) {
                    adjacencyList.add(new ArrayList<>());
                }

                adjacencyList.get(scientistID1).add(scientistID2);
                adjacencyList.get(scientistID2).add(scientistID1);
            }
        }
    }

    private static void computeErdosNumbers(List<List<Integer>> adjacencyList, int[] erdosNumbers, int erdosID) {
        boolean[] visited = new boolean[adjacencyList.size()];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(erdosID);
        visited[erdosID] = true;
        erdosNumbers[erdosID] = 0;

        while (!queue.isEmpty()) {
            int scientistID = queue.poll();

            for (int coauthorID : adjacencyList.get(scientistID)) {
                if (!visited[coauthorID]) {
                    visited[coauthorID] = true;
                    erdosNumbers[coauthorID] = erdosNumbers[scientistID] + 1;
                    queue.offer(coauthorID);
                }
            }
        }
    }

    private static int getScientistID(Map<String, Integer> nameToIDMap, String name) {
        if (!nameToIDMap.containsKey(name)) {
            nameToIDMap.put(name, nameToIDMap.size());
        }
        return nameToIDMap.get(name);
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