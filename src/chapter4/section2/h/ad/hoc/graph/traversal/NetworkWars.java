package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/05/23.
 */
@SuppressWarnings("unchecked")
public class NetworkWars {

    private static final String ANNIHILATED_RESULT = "Both annihilated in node ";
    private static final String PASKILL_TRAPPED_RESULT = "Paskill trapped in node ";
    private static final String LISPER_TRAPPED_RESULT = "Lisper trapped in node ";
    private static final String LISPER_DESTROYED_RESULT = "Lisper destroyed in node ";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (!line.equals("#")) {
            List<Integer>[] adjacencyList = new List[26];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int separatorIndex = line.indexOf('.');
            String firstPart = line.substring(0, separatorIndex);
            String secondPart = line.substring(separatorIndex + 2);

            String[] nodeConnections = firstPart.split(";");
            for (String nodeConnection : nodeConnections) {
                char nodeName = nodeConnection.charAt(0);
                int nodeID = getNodeID(nodeName);

                for (int j = 2; j < nodeConnection.length(); j++) {
                    char otherNodeName = nodeConnection.charAt(j);
                    int otherNodeID = getNodeID(otherNodeName);
                    adjacencyList[nodeID].add(otherNodeID);
                    adjacencyList[otherNodeID].add(nodeID);
                }
            }

            for (List<Integer> list : adjacencyList) {
                Collections.sort(list);
            }

            char paskillNodeName = secondPart.charAt(0);
            char lisperNodeName = secondPart.charAt(2);
            int paskillNodeID = getNodeID(paskillNodeName);
            int lisperNodeID = getNodeID(lisperNodeName);

            List<String> events = computeEvents(adjacencyList, paskillNodeID, lisperNodeID);
            outputWriter.print(events.get(0));
            for (int i = 1; i < events.size(); i++) {
                outputWriter.print(" " + events.get(i));
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> computeEvents(List<Integer>[] adjacencyList, int paskillNodeID, int lisperNodeID) {
        List<String> results = new ArrayList<>();
        boolean[] paskillVisited = new boolean[adjacencyList.length];
        boolean[] lisperVisited = new boolean[adjacencyList.length];

        Queue<Integer> paskillQueue = new LinkedList<>();
        Queue<Integer> lisperQueue = new LinkedList<>();
        paskillQueue.offer(paskillNodeID);
        lisperQueue.offer(lisperNodeID);

        paskillVisited[paskillNodeID] = true;
        lisperVisited[lisperNodeID] = true;

        if (paskillNodeID == lisperNodeID) {
            char nodeName = getNodeName(paskillNodeID);
            results.add(ANNIHILATED_RESULT + nodeName);
            return results;
        }

        while (!paskillQueue.isEmpty() || !lisperQueue.isEmpty()) {
            boolean isPaskillTrapped = false;
            boolean isLisperTrapped = false;
            int nextPaskillID = -1;

            if (!paskillQueue.isEmpty()) {
                paskillNodeID = paskillQueue.poll();
                int nextNodeID = getNextNodeID(adjacencyList, paskillNodeID, lisperVisited, paskillVisited, true,
                        0, adjacencyList[paskillNodeID].size(), 1);
                if (nextNodeID != -1) {
                    paskillQueue.offer(nextNodeID);
                    nextPaskillID = nextNodeID;
                    paskillVisited[nextPaskillID] = true;
                } else {
                    isPaskillTrapped = true;
                    char nodeName = getNodeName(paskillNodeID);
                    results.add(PASKILL_TRAPPED_RESULT + nodeName);
                }
            }

            if (!lisperQueue.isEmpty()) {
                lisperNodeID = lisperQueue.poll();
                int nextNodeID = getNextNodeID(adjacencyList, lisperNodeID, lisperVisited, null, false,
                        adjacencyList[lisperNodeID].size() - 1, -1, -1);
                if (nextNodeID != -1) {
                    if (nextNodeID == nextPaskillID
                            || (isPaskillTrapped && nextNodeID == paskillNodeID)) {
                        char nodeName = getNodeName(nextNodeID);
                        results.add(ANNIHILATED_RESULT + nodeName);
                        return results;
                    } else if (paskillVisited[nextNodeID]) {
                        char nodeName = getNodeName(nextNodeID);
                        results.add(LISPER_DESTROYED_RESULT + nodeName);
                        return results;
                    }
                    lisperQueue.offer(nextNodeID);
                    lisperVisited[nextNodeID] = true;
                } else {
                    isLisperTrapped = true;
                    char lisperNodeName = getNodeName(lisperNodeID);
                    results.add(LISPER_TRAPPED_RESULT + lisperNodeName);
                }
            }

            if (isPaskillTrapped || isLisperTrapped) {
                break;
            }
        }
        return results;
    }

    private static int getNextNodeID(List<Integer>[] adjacencyList, int nodeID, boolean[] visited1, boolean[] visited2,
                                     boolean searchAscending, int startIndex, int endIndex, int increment) {
        for (int i = startIndex; i != endIndex; i += increment) {
            int neighborID = adjacencyList[nodeID].get(i);
            if (searchAscending && neighborID > nodeID && !visited1[neighborID] && !visited2[neighborID]) {
                return neighborID;
            }
            if (!searchAscending && neighborID < nodeID && !visited1[neighborID]) {
                return neighborID;
            }
        }

        if (searchAscending) {
            for (int neighborID : adjacencyList[nodeID]) {
                if (!visited1[neighborID] && !visited2[neighborID]) {
                    return neighborID;
                }
            }
        } else {
            for (int i = adjacencyList[nodeID].size() - 1; i >= 0; i--) {
                int neighborID = adjacencyList[nodeID].get(i);
                if (!visited1[neighborID]) {
                    return neighborID;
                }
            }
        }
        return -1;
    }

    private static int getNodeID(char nodeName) {
        return nodeName - 'A';
    }

    private static char getNodeName(int nodeID) {
        return (char) ('A' + nodeID);
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
