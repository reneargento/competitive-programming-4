package chapter4.section6.d.tree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/07/24.
 */
@SuppressWarnings("unchecked")
public class FrozenRoseHeads {

    private static class Pipe {
        int nextNodeId;
        int effort;

        public Pipe(int nextNodeId, int effort) {
            this.nextNodeId = nextNodeId;
            this.effort = effort;
        }
    }

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int nodes = Integer.parseInt(data[0]);
            int centralNode = Integer.parseInt(data[1]) - 1;

            List<Pipe>[] adjacencyList = new List[nodes];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < nodes - 1; i++) {
                int nodeId1 = FastReader.nextInt() - 1;
                int nodeId2 = FastReader.nextInt() - 1;
                int effort = FastReader.nextInt();

                Pipe pipe1 = new Pipe(nodeId2, effort);
                Pipe pipe2 = new Pipe(nodeId1, effort);
                adjacencyList[nodeId1].add(pipe1);
                adjacencyList[nodeId2].add(pipe2);
            }

            int minimumValvesCloseEffort = computeMinimumValvesCloseEffort(adjacencyList, centralNode);
            outputWriter.printLine(minimumValvesCloseEffort);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumValvesCloseEffort(List<Pipe>[] adjacencyList, int centralNodeId) {
        boolean[] visited = new boolean[adjacencyList.length];
        visited[centralNodeId] = true;
        return computeMinimumValvesCloseEffort(adjacencyList, visited, centralNodeId, INFINITE);
    }

    private static int computeMinimumValvesCloseEffort(List<Pipe>[] adjacencyList, boolean[] visited, int nodeId,
                                                       int effortCurrentNode) {
        boolean visitedChildren = false;
        int effortClosingChildren = 0;
        for (Pipe pipe : adjacencyList[nodeId]) {
            if (!visited[pipe.nextNodeId]) {
                visitedChildren = true;
                visited[pipe.nextNodeId] = true;
                effortClosingChildren += computeMinimumValvesCloseEffort(adjacencyList, visited, pipe.nextNodeId,
                        pipe.effort);
            }
        }

        if (!visitedChildren) {
            return effortCurrentNode;
        }
        return Math.min(effortCurrentNode, effortClosingChildren);
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
