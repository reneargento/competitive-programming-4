package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/04/23.
 */
@SuppressWarnings("unchecked")
public class TornToPieces {

    private static final int MAX_STATIONS = 32;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int stations = FastReader.nextInt();
        List<Integer>[] adjacencyList = new List[MAX_STATIONS];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        Map<String, Integer> stationNameToIDMap = new HashMap<>();
        String[] stationIDToName = new String[MAX_STATIONS];

        for (int stationID = 0; stationID < stations; stationID++) {
            String[] stationNames = FastReader.getLine().split(" ");
            int originStationID = getStationID(stationNames[0], stationNameToIDMap, stationIDToName);
            for (int i = 1; i < stationNames.length; i++) {
                int neighborStationID = getStationID(stationNames[i], stationNameToIDMap, stationIDToName);
                adjacencyList[originStationID].add(neighborStationID);
                adjacencyList[neighborStationID].add(originStationID);
            }
        }
        int originStationID = getStationID(FastReader.next(), stationNameToIDMap, stationIDToName);
        int destinationStationID = getStationID(FastReader.next(), stationNameToIDMap, stationIDToName);

        List<String> route = computeRoute(adjacencyList, stationIDToName, originStationID, destinationStationID);
        if (route != null) {
            outputWriter.print(route.get(0));
            for (int i = 1; i < route.size(); i++) {
                outputWriter.print(" " + route.get(i));
            }
            outputWriter.printLine();
        } else {
            outputWriter.printLine("no route found");
        }
        outputWriter.flush();
    }

    private static List<String> computeRoute(List<Integer>[] adjacencyList, String[] stationIDToName,
                                             int originStationID, int destinationStationID) {
        int[] parent = new int[adjacencyList.length];
        boolean[] visited = new boolean[adjacencyList.length];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(originStationID);
        visited[originStationID] = true;

        while (!queue.isEmpty()) {
            int stationID = queue.poll();

            for (int neighborID : adjacencyList[stationID]) {
                if (!visited[neighborID]) {
                    visited[neighborID] = true;
                    parent[neighborID] = stationID;
                    queue.offer(neighborID);
                    if (neighborID == destinationStationID) {
                        return computeRouteNames(parent, stationIDToName, originStationID, destinationStationID);
                    }
                }
            }
        }
        return null;
    }

    private static List<String> computeRouteNames(int[] parent, String[] stationIDToName, int originStationID,
                                                  int destinationStationID) {
        List<String> route = new ArrayList<>();
        int stationID = destinationStationID;

        while (stationID != originStationID) {
            route.add(stationIDToName[stationID]);
            stationID = parent[stationID];
        }
        route.add(stationIDToName[stationID]);
        Collections.reverse(route);
        return route;
    }

    private static int getStationID(String stationName, Map<String, Integer> stationNameToIDMap,
                                    String[] stationIDToName) {
        if (!stationNameToIDMap.containsKey(stationName)) {
            int stationID = stationNameToIDMap.size();
            stationNameToIDMap.put(stationName, stationID);
            stationIDToName[stationID] = stationName;
        }
        return stationNameToIDMap.get(stationName);
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
