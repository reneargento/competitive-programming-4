package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/06/24.
 */
@SuppressWarnings("unchecked")
public class QuantumSuperposition {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Integer>[] adjacent1 = getAdjacentArray(FastReader.nextInt());
        List<Integer>[] adjacent2 = getAdjacentArray(FastReader.nextInt());

        int edges1 = FastReader.nextInt();
        int edges2 = FastReader.nextInt();
        addEdges(adjacent1, edges1);
        addEdges(adjacent2, edges2);

        // dp[vertex id][steps]
        boolean[][] dpUniverse1 = new boolean[adjacent1.length][edges1 + 1];
        boolean[][] dpUniverse2 = new boolean[adjacent2.length][edges2 + 1];

        computeStepsToEnd(adjacent1, dpUniverse1);
        computeStepsToEnd(adjacent2, dpUniverse2);
        Set<Integer> allStepSums = computeAllStepSums(dpUniverse1, dpUniverse2);

        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            int stepsSum = FastReader.nextInt();
            if (allStepSums.contains(stepsSum)) {
                outputWriter.printLine("Yes");
            } else {
                outputWriter.printLine("No");
            }
        }
        outputWriter.flush();
    }

    private static List<Integer>[] getAdjacentArray(int size) {
        List<Integer>[] adjacent = new List[size];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }
        return adjacent;
    }

    private static void addEdges(List<Integer>[] adjacent, int edges) throws IOException {
        for (int i = 0; i < edges; i++) {
            int vertexId1 = FastReader.nextInt() - 1;
            int vertexId2 = FastReader.nextInt() - 1;
            adjacent[vertexId1].add(vertexId2);
        }
    }

    private static void computeStepsToEnd(List<Integer>[] adjacent, boolean[][] dpUniverse) {
        int[] inDegrees = new int[adjacent.length];
        for (int i = 0; i < adjacent.length; i++) {
            for (int neighbor : adjacent[i]) {
                inDegrees[neighbor]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        dpUniverse[0][0] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for (int neighbor : adjacent[currentVertex]) {
                for (int steps = 0; steps < dpUniverse[0].length; steps++) {
                    if (dpUniverse[currentVertex][steps]) {
                        dpUniverse[neighbor][steps + 1] = true;
                    }
                }

                inDegrees[neighbor]--;
                if (inDegrees[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }
    }

    private static Set<Integer> computeAllStepSums(boolean[][] dpUniverse1, boolean[][] dpUniverse2) {
        Set<Integer> allStepSums = new HashSet<>();
        int lastVertexUniverse1 = dpUniverse1.length - 1;
        int lastVertexUniverse2 = dpUniverse2.length - 1;

        for (int stepsUniverse1 = 0; stepsUniverse1 < dpUniverse1[0].length; stepsUniverse1++) {
            for (int stepsUniverse2 = 0; stepsUniverse2 < dpUniverse2[0].length; stepsUniverse2++) {
                if (dpUniverse1[lastVertexUniverse1][stepsUniverse1]
                        && dpUniverse2[lastVertexUniverse2][stepsUniverse2]) {
                    allStepSums.add(stepsUniverse1 + stepsUniverse2);
                }
            }
        }
        return allStepSums;
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
