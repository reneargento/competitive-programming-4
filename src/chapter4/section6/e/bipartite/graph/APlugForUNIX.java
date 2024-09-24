package chapter4.section6.e.bipartite.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/08/24.
 */
@SuppressWarnings("unchecked")
public class APlugForUNIX {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int receptaclesNumber = FastReader.nextInt();
            List<Integer>[] adjacencyList = new List[400];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            Map<String, Integer> receptacleNameToIdMap = new HashMap<>();

            for (int i = 0; i < receptaclesNumber; i++) {
                String name = FastReader.next();
                getReceptacleId(receptacleNameToIdMap, name);
            }

            int devicesToPlugNumber = FastReader.nextInt();
            List<String> devicesToPlug = new ArrayList<>();
            for (int i = 0; i < devicesToPlugNumber; i++) {
                FastReader.next(); // unused
                devicesToPlug.add(FastReader.next());
            }

            int adapters = FastReader.nextInt();
            for (int i = 0; i < adapters; i++) {
                String receptacleName = FastReader.next();
                String convertedName = FastReader.next();

                int receptacleId = getReceptacleId(receptacleNameToIdMap, receptacleName);
                int convertedId = getReceptacleId(receptacleNameToIdMap, convertedName);
                adjacencyList[receptacleId].add(convertedId);
            }

            int minimumDevicesNotPlugged = computeMinimumDevicesNotPlugged(adjacencyList, devicesToPlug,
                    receptacleNameToIdMap, receptaclesNumber);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(minimumDevicesNotPlugged);
        }
        outputWriter.flush();
    }

    private static int getReceptacleId(Map<String, Integer> receptacleNameToIdMap, String receptacleName) {
        if (!receptacleNameToIdMap.containsKey(receptacleName)) {
            receptacleNameToIdMap.put(receptacleName, receptacleNameToIdMap.size());
        }
        return receptacleNameToIdMap.get(receptacleName);
    }

    private static int computeMinimumDevicesNotPlugged(List<Integer>[] adjacencyList, List<String> devicesToPlug,
                                                       Map<String, Integer> receptacleNameToIdMap,
                                                       int receptaclesNumber) {
        List<Integer>[] matchAdjacencyList = new List[adjacencyList.length + devicesToPlug.size()];
        for (int i = 0; i < matchAdjacencyList.length; i++) {
            matchAdjacencyList[i] = new ArrayList<>();
        }

        Collections.sort(devicesToPlug);

        for (int deviceId = 0; deviceId < devicesToPlug.size(); deviceId++) {
            int startDeviceId = deviceId + adjacencyList.length;
            int endDeviceId = deviceId + adjacencyList.length + 1;
            String deviceName = devicesToPlug.get(deviceId);

            while (deviceId + 1 < devicesToPlug.size() && devicesToPlug.get(deviceId + 1).equals(deviceName)) {
                endDeviceId = deviceId + 1 + adjacencyList.length + 1;
                deviceId++;
            }
            int receptacleId = getReceptacleId(receptacleNameToIdMap, deviceName);
            addEdges(matchAdjacencyList, adjacencyList, receptaclesNumber, receptacleId, startDeviceId, endDeviceId);
        }

        int maximumCardinality = computeMaximumCardinality(matchAdjacencyList, adjacencyList.length);
        return devicesToPlug.size() - maximumCardinality;
    }

    private static void addEdges(List<Integer>[] matchAdjacencyList, List<Integer>[] adjacencyList,
                                 int receptaclesNumber, int receptacleId, int startDeviceId, int endDeviceId) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[matchAdjacencyList.length];
        visited[receptacleId] = true;
        queue.offer(receptacleId);

        while (!queue.isEmpty()) {
            int currentReceptacleId = queue.poll();
            if (currentReceptacleId < receptaclesNumber) {
                for (int deviceId = startDeviceId; deviceId < endDeviceId; deviceId++) {
                    matchAdjacencyList[deviceId].add(currentReceptacleId);
                }
            }

            for (int neighborId : adjacencyList[currentReceptacleId]) {
                if (!visited[neighborId]) {
                    visited[neighborId] = true;
                    queue.offer(neighborId);
                }
            }
        }
    }

    private static int computeMaximumCardinality(List<Integer>[] adjacencyList, int leftPartitionMaxVertexID) {
        int maximumMatches = 0;
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID = leftPartitionMaxVertexID; vertexID < adjacencyList.length; vertexID++) {
            boolean[] visited = new boolean[adjacencyList.length];
            maximumMatches += tryToMatch(adjacencyList, match, visited, vertexID);
        }
        return maximumMatches;
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, boolean[] visited, int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (match[neighbor] == -1 || tryToMatch(adjacencyList, match, visited, match[neighbor]) == 1) {
                match[neighbor] = vertexID; // flip status
                return 1;
            }
        }
        return 0;
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
