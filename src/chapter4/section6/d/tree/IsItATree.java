package chapter4.section6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/07/24.
 */
public class IsItATree {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodeId1 = FastReader.nextInt();
        int nodeId2 = FastReader.nextInt();
        int caseId = 1;

        while (nodeId1 >= 0 && nodeId2 >= 0) {
            Map<Integer, List<Integer>> adjacent = new HashMap<>();
            Map<Integer, Integer> inDegrees = new HashMap<>();
            boolean hasSelfLoop = false;

            while (nodeId1 != 0 && nodeId2 != 0) {
                if (!adjacent.containsKey(nodeId1)) {
                    adjacent.put(nodeId1, new ArrayList<>());
                    inDegrees.put(nodeId1, 0);
                }
                if (!inDegrees.containsKey(nodeId2)) {
                    adjacent.put(nodeId2, new ArrayList<>());
                    inDegrees.put(nodeId2, 0);
                }

                if (nodeId1 != nodeId2) {
                    adjacent.get(nodeId1).add(nodeId2);
                    inDegrees.put(nodeId2, inDegrees.get(nodeId2) + 1);
                } else {
                    hasSelfLoop = true;
                }

                nodeId1 = FastReader.nextInt();
                nodeId2 = FastReader.nextInt();
            }

            boolean isItATree = !hasSelfLoop && isItATree(adjacent, inDegrees);
            outputWriter.print(String.format("Case %d is ", caseId));
            outputWriter.printLine(isItATree ? "a tree." : "not a tree.");

            caseId++;
            nodeId1 = FastReader.nextInt();
            nodeId2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean isItATree(Map<Integer, List<Integer>> adjacent, Map<Integer, Integer> inDegrees) {
        if (inDegrees.isEmpty()) {
            return true;
        }
        HasCycleDirectedGraph directedGraph = new HasCycleDirectedGraph(adjacent);
        if (directedGraph.hasCycle()) {
            return false;
        }

        boolean hasNodeWithZeroInDegree = false;
        for (int nodeId : inDegrees.keySet()) {
            int inDegree = inDegrees.get(nodeId);
            if (inDegree == 0) {
                if (hasNodeWithZeroInDegree) {
                    return false;
                }
                hasNodeWithZeroInDegree = true;
            } else if (inDegree > 1) {
                return false;
            }
        }
        return hasNodeWithZeroInDegree;
    }

    private static class HasCycleDirectedGraph {
        private final Set<Integer> visited;
        private final Set<Integer> onStack;
        private boolean hasCycle;

        public HasCycleDirectedGraph(Map<Integer, List<Integer>> adjacent) {
            onStack = new HashSet<>();
            visited = new HashSet<>();

            for (int vertex : adjacent.keySet()) {
                if (!visited.contains(vertex)) {
                    dfs(adjacent, vertex);
                }
            }
        }

        private void dfs(Map<Integer, List<Integer>> adjacent, int vertex) {
            onStack.add(vertex);
            visited.add(vertex);

            if (!adjacent.containsKey(vertex)) {
                return;
            }

            for (int neighbor : adjacent.get(vertex)) {
                if (hasCycle()) {
                    return;
                } else if (!visited.contains(neighbor)) {
                    dfs(adjacent, neighbor);
                } else if (onStack.contains(neighbor)) {
                    hasCycle = true;
                }
            }
            onStack.remove(vertex);
        }

        public boolean hasCycle() {
            return hasCycle;
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
