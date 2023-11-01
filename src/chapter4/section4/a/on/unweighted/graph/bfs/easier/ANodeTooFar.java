package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/10/23.
 */
public class ANodeTooFar {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int connections = FastReader.nextInt();
        int caseID = 1;

        while (connections != 0) {
            Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
            Set<Integer> validIDs = new HashSet<>();

            for (int i = 0; i < connections; i++) {
                int nodeID1 = FastReader.nextInt() - 1;
                int nodeID2 = FastReader.nextInt() - 1;
                addConnection(adjacencyList, nodeID1, nodeID2);
                addConnection(adjacencyList, nodeID2, nodeID1);

                validIDs.add(nodeID1);
                validIDs.add(nodeID2);
            }

            Map<Integer, Map<Integer, Integer>> distances = computeDistances(adjacencyList, validIDs);

            int startNodeID = FastReader.nextInt();
            int ttlValue = FastReader.nextInt();
            while (startNodeID != 0) {
                int unreachable = computeUnreachable(distances, validIDs, startNodeID - 1, ttlValue);
                outputWriter.printLine(String.format("Case %d: %d nodes not reachable from node %d with TTL = %d.",
                        caseID, unreachable, startNodeID, ttlValue));

                startNodeID = FastReader.nextInt();
                ttlValue = FastReader.nextInt();
                caseID++;
            }

            String line = FastReader.getLine();
            while (line == null || line.trim().isEmpty()) {
                if (line == null) {
                    break;
                } else {
                    line = FastReader.getLine();
                }
            }

            if (line == null) {
                break;
            }
            connections = Integer.parseInt(line);
        }
        outputWriter.flush();
    }

    private static void addConnection(Map<Integer, List<Integer>> adjacencyList, int nodeID1, int nodeID2) {
        if (!adjacencyList.containsKey(nodeID1)) {
            adjacencyList.put(nodeID1, new ArrayList<>());
        }
        adjacencyList.get(nodeID1).add(nodeID2);
    }

    private static Map<Integer, Map<Integer, Integer>> computeDistances(Map<Integer, List<Integer>> adjacencyList,
                                                                        Set<Integer> validIDs) {
        Map<Integer, Map<Integer, Integer>> distances = new HashMap<>();
        for (int nodeID : adjacencyList.keySet()) {
            distances.put(nodeID, new HashMap<>());
        }

        for (int nodeID : validIDs) {
            Set<Integer> visited = new HashSet<>();
            visited.add(nodeID);
            distances.get(nodeID).put(nodeID, 0);
            breadthFirstSearch(adjacencyList, distances, visited, nodeID);
        }
        return distances;
    }

    private static void breadthFirstSearch(Map<Integer, List<Integer>> adjacencyList,
                                           Map<Integer, Map<Integer, Integer>> distances, Set<Integer> visited,
                                           int sourceID) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(sourceID);

        while (!queue.isEmpty()) {
            int nodeID = queue.poll();
            int parentDistance = distances.get(sourceID).get(nodeID);

            for (int neighborID : adjacencyList.get(nodeID)) {
                if (!visited.contains(neighborID)) {
                    visited.add(neighborID);

                    distances.get(sourceID).put(neighborID, parentDistance + 1);
                    queue.offer(neighborID);
                }
            }
        }
    }

    private static int computeUnreachable(Map<Integer, Map<Integer, Integer>> distances, Set<Integer> validIDs,
                                          int sourceNodeID, int ttlValue) {
        if (!distances.containsKey(sourceNodeID)) {
            return distances.size();
        }

        int unreachable = 0;
        for (int nodeID : validIDs) {
            if (distances.get(sourceNodeID).containsKey(nodeID)
                    && distances.get(sourceNodeID).get(nodeID) > ttlValue) {
                unreachable++;
            }
        }
        int nodesNotConnected = validIDs.size() - distances.get(sourceNodeID).size();
        return unreachable + nodesNotConnected;
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