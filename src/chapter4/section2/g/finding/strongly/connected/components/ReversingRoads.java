package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/04/23.
 */
@SuppressWarnings("unchecked")
public class ReversingRoads {

    private static class Road {
        int locationID1;
        int locationID2;

        public Road(int locationID1, int locationID2) {
            this.locationID1 = locationID1;
            this.locationID2 = locationID2;
        }
    }

    private static class Result {
        boolean valid;
        Road roadToSwitch;

        public Result(boolean valid, Road roadToSwitch) {
            this.valid = valid;
            this.roadToSwitch = roadToSwitch;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseID = 1;
        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int locations = Integer.parseInt(data[0]);
            int roadsNumber = Integer.parseInt(data[1]);
            Road[] roads = new Road[roadsNumber];

            List<Integer>[] adjacencyList = new List[locations];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < roadsNumber; i++) {
                int locationID1 = FastReader.nextInt();
                int locationID2 = FastReader.nextInt();
                adjacencyList[locationID1].add(locationID2);
                roads[i] = new Road(locationID1, locationID2);
            }

            Result result = analyzeProposal(adjacencyList, roads);
            outputWriter.print(String.format("Case %d: ", caseID));
            if (result.valid) {
                outputWriter.printLine("valid");
            } else if (result.roadToSwitch != null) {
                outputWriter.printLine(String.format("%d %d", result.roadToSwitch.locationID1,
                        result.roadToSwitch.locationID2));
            } else {
                outputWriter.printLine("invalid");
            }
            caseID++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result analyzeProposal(List<Integer>[] adjacencyList, Road[] roads) {
        StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(adjacencyList);
        if (stronglyConnectedComponents.sccCount == 1) {
            return new Result(true, null);
        }

        for (Road road : roads) {
            Integer locationID1 = road.locationID1;
            Integer locationID2 = road.locationID2;
            adjacencyList[locationID1].remove(locationID2);
            adjacencyList[locationID2].add(locationID1);

            stronglyConnectedComponents = new StronglyConnectedComponents(adjacencyList);
            if (stronglyConnectedComponents.sccCount == 1) {
                return new Result(false, road);
            }

            adjacencyList[locationID2].remove(locationID1);
            adjacencyList[locationID1].add(locationID2);
        }
        return new Result(false, null);
    }

    private static class StronglyConnectedComponents {
        private final int[] dfsLow;
        private final int[] dfsNumber;
        private final boolean[] visited;
        private final Deque<Integer> stack;
        private int sccCount;
        private int dfsNumberCounter;
        private static final int UNVISITED = -1;

        public StronglyConnectedComponents(List<Integer>[] adjacencyList) {
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

                    if (vertexInStack == vertexID) {
                        break;
                    }
                }
                sccCount++;
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
