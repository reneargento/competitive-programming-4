package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/04/23.
 */
@SuppressWarnings("unchecked")
public class Test {

    private static class ActivitySet implements Comparable<ActivitySet> {
        List<String> activities;

        public ActivitySet(List<String> activities) {
            this.activities = activities;
        }

        @Override
        public int compareTo(ActivitySet other) {
            int minSize = Math.min(activities.size(), other.activities.size());
            for (int i = 0; i < minSize; i++) {
                if (!activities.get(i).equals(other.activities.get(i))) {
                    return activities.get(i).compareTo(other.activities.get(i));
                }
            }
            return Integer.compare(activities.size(), other.activities.size());
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int questions = FastReader.nextInt();
        int caseID = 1;

        while (questions != 0) {
            List<Integer>[] adjacencyList = new List[questions * 5];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            Map<String, Integer> activityNameToIDMap = new HashMap<>();
            String[] activityIDToName = new String[adjacencyList.length];

            for (int q = 0; q < questions; q++) {
                String[] data = FastReader.getLine().split(" ");
                int preferredActivityID = getActivityID(data[5], activityNameToIDMap, activityIDToName);
                for (int i = 0; i < 5; i++) {
                    int activityID = getActivityID(data[i], activityNameToIDMap, activityIDToName);
                    adjacencyList[preferredActivityID].add(activityID);
                }
            }

            List<ActivitySet> activitySets = computeActivitySets(adjacencyList, activityIDToName,
                    activityNameToIDMap.size());
            if (caseID > 1) {
                outputWriter.printLine();
            }
            for (ActivitySet activitySet : activitySets) {
                outputWriter.print(activitySet.activities.get(0));

                for (int i = 1; i < activitySet.activities.size(); i++) {
                    String activity = activitySet.activities.get(i);
                    outputWriter.print(" " + activity);
                }
                outputWriter.printLine();
            }
            caseID++;
            questions = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<ActivitySet> computeActivitySets(List<Integer>[] adjacencyList, String[] activityIDToName,
                                                         int activitiesNumber) {
        List<ActivitySet> activitySets = new ArrayList<>();
        StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(adjacencyList);

        for (List<Integer> activityIDS : stronglyConnectedComponents.getSCCs()) {
            List<String> activities = new ArrayList<>();
            boolean isValidComponent = false;

            for (int activityID : activityIDS) {
                if (activityID < activitiesNumber) {
                    activities.add(activityIDToName[activityID]);
                    isValidComponent = true;
                }
            }

            if (isValidComponent) {
                Collections.sort(activities);
                ActivitySet activitySet = new ActivitySet(activities);
                activitySets.add(activitySet);
            }
        }
        Collections.sort(activitySets);
        return activitySets;
    }

    private static int getActivityID(String activityName, Map<String, Integer> activityNameToIDMap,
                                     String[] activityIDToName) {
        if (!activityNameToIDMap.containsKey(activityName)) {
            int activityID = activityNameToIDMap.size();
            activityNameToIDMap.put(activityName, activityID);
            activityIDToName[activityID] = activityName;
        }
        return activityNameToIDMap.get(activityName);
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
