package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/04/23.
 */
@SuppressWarnings("unchecked")
public class FaultyRobot {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
        int edges = FastReader.nextInt();
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        int[] forcedNeighbors = new int[adjacencyList.length];
        Arrays.fill(forcedNeighbors, -1);

        for (int i = 0; i < edges; i++) {
            int node1 = FastReader.nextInt();
            int node2 = FastReader.nextInt() - 1;

            int absoluteNode1 = Math.abs(node1) - 1;
            if (node1 < 0) {
                forcedNeighbors[absoluteNode1] = node2;
            }
            adjacencyList[absoluteNode1].add(node2);
        }

        int restNodes = countRestNodes(adjacencyList, forcedNeighbors);
        outputWriter.printLine(restNodes);
        outputWriter.flush();
    }

    private static int countRestNodes(List<Integer>[] adjacencyList, int[] forcedNeighbor) {
        Set<Integer> restNodes = new HashSet<>();
        boolean[] visited = new boolean[adjacencyList.length];
        countRestNodes(adjacencyList, visited, forcedNeighbor, restNodes, false, 0);
        return restNodes.size();
    }

    private static void countRestNodes(List<Integer>[] adjacencyList, boolean[] visited, int[] forcedNeighbors,
                                       Set<Integer> restNodes, boolean bugOccurred, int nodeID) {
        visited[nodeID] = true;
        int forcedNeighbor = forcedNeighbors[nodeID];
        if (forcedNeighbor == -1) {
            restNodes.add(nodeID);
            if (bugOccurred || adjacencyList[nodeID].isEmpty()) {
                return;
            }
        }

        if (forcedNeighbors[nodeID] != -1) {
            if (!visited[forcedNeighbor]) {
                countRestNodes(adjacencyList, visited, forcedNeighbors, restNodes, bugOccurred, forcedNeighbor);
            }
        }
        if (!bugOccurred) {
            for (int neighborID : adjacencyList[nodeID]) {
                if (neighborID == forcedNeighbor) {
                    continue;
                }
                countRestNodes(adjacencyList, visited, forcedNeighbors, restNodes, true, neighborID);
            }
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
