package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/12/23.
 */
@SuppressWarnings("unchecked")
public class OnAverageTheyePurple {

    private static class State {
        int nodeID;
        int steps;

        public State(int nodeID, int steps) {
            this.nodeID = nodeID;
            this.steps = steps;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodes = FastReader.nextInt();
        int edges = FastReader.nextInt();

        List<Integer>[] adjacencyList = new List[nodes];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges; i++) {
            int nodeID1 = FastReader.nextInt() - 1;
            int nodeID2 = FastReader.nextInt() - 1;
            adjacencyList[nodeID1].add(nodeID2);
            adjacencyList[nodeID2].add(nodeID1);
        }

        int minimumColorChanges = computeMinimumColorChanges(adjacencyList);
        outputWriter.printLine(minimumColorChanges);
        outputWriter.flush();
    }

    private static int computeMinimumColorChanges(List<Integer>[] adjacencyList) {
        Queue<State> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        queue.offer(new State(0, 0));
        visited[0] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (state.nodeID == adjacencyList.length - 1) {
                return state.steps - 1;
            }

            for (int neighborID : adjacencyList[state.nodeID]) {
                if (!visited[neighborID]) {
                    visited[neighborID] = true;
                    queue.offer(new State(neighborID, state.steps + 1));
                }
            }
        }
        return -1;
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
