package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/04/23.
 */
@SuppressWarnings("unchecked")
public class LoopyCabDrivers {

    private static class StoreGroup implements Comparable<StoreGroup> {
        List<String> stores;

        public StoreGroup(List<String> stores) {
            this.stores = stores;
        }

        @Override
        public int compareTo(StoreGroup other) {
            int minSize = Math.min(stores.size(), other.stores.size());
            for (int i = 0; i < minSize; i++) {
                if (!stores.get(i).equals(other.stores.get(i))) {
                    return stores.get(i).compareTo(other.stores.get(i));
                }
            }
            return Integer.compare(stores.size(), other.stores.size());
        }
    }

    private static class Result {
        List<StoreGroup> storeGroups;
        Set<Integer> storeIDsToAvoid;

        public Result(List<StoreGroup> storeGroups, Set<Integer> storeIDsToAvoid) {
            this.storeGroups = storeGroups;
            this.storeIDsToAvoid = storeIDsToAvoid;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int streets = FastReader.nextInt();
        List<Integer>[] adjacencyList = new List[streets * 2];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        Map<String, Integer> storeNameToIDMap = new HashMap<>();
        String[] storeIDToName = new String[adjacencyList.length];

        for (int i = 0; i < streets; i++) {
            String storeName1 = FastReader.next();
            String storeName2 = FastReader.next();
            int storeID1 = getStoreID(storeName1, storeNameToIDMap, storeIDToName);
            int storeID2 = getStoreID(storeName2, storeNameToIDMap, storeIDToName);
            adjacencyList[storeID1].add(storeID2);
        }

        Result result = computePossibleStores(adjacencyList, storeIDToName);
        for (StoreGroup storeGroup : result.storeGroups) {
            List<String> stores = storeGroup.stores;
            outputWriter.print("okay");
            for (String storeName : stores) {
                outputWriter.print(" " + storeName);
            }
            outputWriter.printLine();
        }

        List<String> storesToAvoid = new ArrayList<>();
        if (!result.storeIDsToAvoid.isEmpty()) {
            for (int storedID : result.storeIDsToAvoid) {
                if (storedID < storeNameToIDMap.size()) {
                    storesToAvoid.add(storeIDToName[storedID]);
                }
            }
        }
        if (!storesToAvoid.isEmpty()) {
            Collections.sort(storesToAvoid);
            outputWriter.print("avoid");
            for (String storeName : storesToAvoid) {
                outputWriter.print(" " + storeName);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static Result computePossibleStores(List<Integer>[] adjacencyList, String[] storeIDToName) {
        List<StoreGroup> possibleStores = new ArrayList<>();
        StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(adjacencyList);
        List<Integer>[] sccs = stronglyConnectedComponents.getSCCs();
        Set<Integer> storeIDsToAvoid = new HashSet<>();

        for (List<Integer> storeGroupIDs : sccs) {
            if (storeGroupIDs.size() > 1) {
                List<String> storeNames = new ArrayList<>();
                for (int storeID : storeGroupIDs) {
                    storeNames.add(storeIDToName[storeID]);
                }
                Collections.sort(storeNames);
                possibleStores.add(new StoreGroup(storeNames));
            } else {
                storeIDsToAvoid.add(storeGroupIDs.get(0));
            }
        }
        Collections.sort(possibleStores);
        return new Result(possibleStores, storeIDsToAvoid);
    }

    private static int getStoreID(String storeName, Map<String, Integer> storeNameToIDMap, String[] storeIDToName) {
        if (!storeNameToIDMap.containsKey(storeName)) {
            int storeID = storeNameToIDMap.size();
            storeNameToIDMap.put(storeName, storeID);
            storeIDToName[storeID] = storeName;
        }
        return storeNameToIDMap.get(storeName);
    }

    private static class StronglyConnectedComponents {
        private final int[] sccIds;
        private final int[] dfsLow;
        private final int[] dfsNumber;
        private final boolean[] visited;
        private final Deque<Integer> stack;
        private int sccCount;
        private int dfsNumberCounter;
        private static final int UNVISITED = -1;

        public StronglyConnectedComponents(List<Integer>[] adjacencyList) {
            sccIds = new int[adjacencyList.length];
            visited = new boolean[adjacencyList.length];
            dfsLow = new int[adjacencyList.length];
            dfsNumber = new int[adjacencyList.length];
            stack = new ArrayDeque<>();
            Arrays.fill(dfsNumber, UNVISITED);

            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                if (dfsNumber[vertexID] == UNVISITED) {
                    depthFirstSearch(adjacencyList, vertexID);
                }
            }
        }

        private void depthFirstSearch(List<Integer>[] adjacencyList, int vertexID) {
            visited[vertexID] = true;
            dfsLow[vertexID] = dfsNumberCounter;
            dfsNumber[vertexID] = dfsNumberCounter;
            dfsNumberCounter++;
            stack.push(vertexID);

            for (int neighborID: adjacencyList[vertexID]) {
                if (dfsNumber[neighborID] == UNVISITED) {
                    depthFirstSearch(adjacencyList, neighborID);
                }
                if (visited[neighborID]) {
                    dfsLow[vertexID] = Math.min(dfsLow[vertexID], dfsLow[neighborID]);
                }
            }

            if (dfsLow[vertexID] == dfsNumber[vertexID]) {
                while (true) {
                    int vertexInStack = stack.pop();
                    visited[vertexInStack] = false;
                    sccIds[vertexInStack] = sccCount;

                    if (vertexInStack == vertexID) {
                        break;
                    }
                }
                sccCount++;
            }
        }

        private List<Integer>[] getSCCs() {
            List<Integer>[] stronglyConnectedComponents = (List<Integer>[]) new ArrayList[sccCount];
            for (int scc = 0; scc < stronglyConnectedComponents.length; scc++) {
                stronglyConnectedComponents[scc] = new ArrayList<>();
            }

            for (int vertex = 0; vertex < sccIds.length; vertex++) {
                int stronglyConnectedComponentId = sccIds[vertex];
                stronglyConnectedComponents[stronglyConnectedComponentId].add(vertex);
            }
            return stronglyConnectedComponents;
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
