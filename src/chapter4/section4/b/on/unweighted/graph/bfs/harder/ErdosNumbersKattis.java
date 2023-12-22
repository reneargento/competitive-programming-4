package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/12/23.
 */
public class ErdosNumbersKattis {

    private static final String ERDOS_NAME = "PAUL_ERDOS";
    private static final int INFINITE = Integer.MAX_VALUE;
    private static final String INFINITE_RESULT = "no-connection";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Integer> nameToIDMap = new HashMap<>();
        List<List<Integer>> adjacencyList = new ArrayList<>();
        List<String> authorsToCompute = new ArrayList<>();

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            String authorName = data[0];
            authorsToCompute.add(authorName);

            int scientistID1 = getScientistID(nameToIDMap, authorName);
            for (String coauthorName : data) {
                int scientistID2 = getScientistID(nameToIDMap, coauthorName);
                connectScientists(adjacencyList, scientistID1, scientistID2);
            }
            line = FastReader.getLine();
        }

        int[] erdosNumbers = new int[adjacencyList.size()];
        Arrays.fill(erdosNumbers, INFINITE);

        if (nameToIDMap.containsKey(ERDOS_NAME)) {
            int erdosID = nameToIDMap.get(ERDOS_NAME);
            computeErdosNumbers(adjacencyList, erdosNumbers, erdosID);
        }

        for (String authorName : authorsToCompute) {
            outputWriter.print(authorName + " ");

            int scientistID = nameToIDMap.get(authorName);
            if (erdosNumbers[scientistID] != INFINITE) {
                outputWriter.printLine(erdosNumbers[scientistID]);
            } else {
                outputWriter.printLine(INFINITE_RESULT);
            }
        }
        outputWriter.flush();
    }

    private static void connectScientists(List<List<Integer>> adjacencyList, int scientistID1, int scientistID2) {
        if (adjacencyList.size() <= scientistID1) {
            adjacencyList.add(new ArrayList<>());
        }
        if (adjacencyList.size() <= scientistID2) {
            adjacencyList.add(new ArrayList<>());
        }
        adjacencyList.get(scientistID1).add(scientistID2);
        adjacencyList.get(scientistID2).add(scientistID1);
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

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
