package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/04/23.
 */
@SuppressWarnings("unchecked")
public class ProvingEquivalences {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int implications = FastReader.nextInt();
            for (int i = 0; i < implications; i++) {
                int statementID1 = FastReader.nextInt() - 1;
                int statementID2 = FastReader.nextInt() - 1;
                adjacencyList[statementID1].add(statementID2);
            }

            int implicationsToProve = computeImplicationsToProve(adjacencyList);
            outputWriter.printLine(implicationsToProve);
        }
        outputWriter.flush();
    }

    private static int computeImplicationsToProve(List<Integer>[] adjacencyList) {
        int totalZeroInDegrees = 0;
        int totalZeroOutDegrees = 0;
        StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(adjacencyList);

        // Edge case
        if (stronglyConnectedComponents.sccCount == 1) {
            return 0;
        }
        int[] sccInDegrees = new int[stronglyConnectedComponents.sccCount];
        int[] sccOutDegrees = new int[stronglyConnectedComponents.sccCount];

        for (int statementID = 0; statementID < adjacencyList.length; statementID++) {
            int sccID1 = stronglyConnectedComponents.sccIds[statementID];
            for (int neighborID : adjacencyList[statementID]) {
                int sccID2 = stronglyConnectedComponents.sccIds[neighborID];
                if (sccID1 == sccID2) {
                    continue;
                }
                sccInDegrees[sccID2]++;
                sccOutDegrees[sccID1]++;
            }
        }

        for (int i = 0; i < sccInDegrees.length; i++) {
            if (sccInDegrees[i] == 0) {
                totalZeroInDegrees++;
            }
            if (sccOutDegrees[i] == 0) {
                totalZeroOutDegrees++;
            }
        }
        return Math.max(totalZeroInDegrees, totalZeroOutDegrees);
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
            visited = new boolean[adjacencyList.length];
            dfsLow = new int[adjacencyList.length];
            dfsNumber = new int[adjacencyList.length];
            sccIds = new int[adjacencyList.length];
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
