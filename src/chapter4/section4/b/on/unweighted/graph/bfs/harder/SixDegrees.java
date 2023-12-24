package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/12/23.
 */
@SuppressWarnings("unchecked")
public class SixDegrees {

    private static class State {
        int deviceID;
        int intermediateDevices;

        public State(int deviceID, int intermediateDevices) {
            this.deviceID = deviceID;
            this.intermediateDevices = intermediateDevices;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Map<String, Integer> deviceNameToID = new HashMap<>();
            List<Integer>[] adjacencyList = new List[3001];
            int maxDeviceID = -1;

            int connections = FastReader.nextInt();
            for (int i = 0; i < connections; i++) {
                int deviceID1 = getDeviceID(deviceNameToID, FastReader.next());
                int deviceID2 = getDeviceID(deviceNameToID, FastReader.next());

                if (adjacencyList[deviceID1] == null) {
                    adjacencyList[deviceID1] = new ArrayList<>();
                }
                if (adjacencyList[deviceID2] == null) {
                    adjacencyList[deviceID2] = new ArrayList<>();
                }
                adjacencyList[deviceID1].add(deviceID2);
                adjacencyList[deviceID2].add(deviceID1);

                maxDeviceID = Math.max(maxDeviceID, deviceID1);
                maxDeviceID = Math.max(maxDeviceID, deviceID2);
            }

            String result = evaluatePlan(adjacencyList, maxDeviceID);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String evaluatePlan(List<Integer>[] adjacencyList, int maxDeviceID) {
        int devicesToDisconnect = 0;
        double maxAllowedDevices = (maxDeviceID + 1) * 0.05;

        for (int deviceID = 0; deviceID <= maxDeviceID; deviceID++) {
            if (shouldDisconnect(adjacencyList, maxDeviceID, deviceID)) {
                devicesToDisconnect++;
            }
        }
        return devicesToDisconnect <= maxAllowedDevices ? "YES" : "NO";
    }

    private static boolean shouldDisconnect(List<Integer>[] adjacencyList, int maxDeviceID, int sourceDeviceID) {
        Queue<State> queue = new LinkedList<>();
        boolean[] visited = new boolean[maxDeviceID + 1];

        queue.offer(new State(sourceDeviceID, 0));
        visited[sourceDeviceID] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (state.intermediateDevices == 7) {
                return true;
            }

            for (int neighborDeviceID : adjacencyList[state.deviceID]) {
                if (!visited[neighborDeviceID]) {
                    visited[neighborDeviceID] = true;
                    State nextState = new State(neighborDeviceID, state.intermediateDevices + 1);
                    queue.offer(nextState);
                }
            }
        }
        return false;
    }

    private static int getDeviceID(Map<String, Integer> deviceNameToID, String deviceName) {
        if (!deviceNameToID.containsKey(deviceName)) {
            deviceNameToID.put(deviceName, deviceNameToID.size());
        }
        return deviceNameToID.get(deviceName);
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
