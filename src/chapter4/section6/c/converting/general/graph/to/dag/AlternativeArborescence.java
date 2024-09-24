package chapter4.section6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/06/24.
 */
@SuppressWarnings("unchecked")
public class AlternativeArborescence {

    private static final int INFINITE = 10000000;
    private static final int MAX_COLOR = 10;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodes = FastReader.nextInt();

        while (nodes != 0) {
            List<Integer>[] adjacent = new List[nodes];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new ArrayList<>();
            }

            for (int nodeId = 0; nodeId < nodes; nodeId++) {
                String[] data = FastReader.getLine().split(" ");
                for (int i = 1; i < data.length; i++) {
                    int childId = Integer.parseInt(data[i]);
                    adjacent[nodeId].add(childId);
                }
            }

            int minimumColorSum = computeMinimumColorSum(adjacent);
            outputWriter.printLine(minimumColorSum);
            nodes = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumColorSum(List<Integer>[] adjacent) {
        // dp[node id][color]
        int[][] dp = new int[adjacent.length][MAX_COLOR];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        int rootId = findRoot(adjacent);
        int minimumSum = INFINITE;
        for (int color = 1; color < MAX_COLOR; color++) {
            int sumCandidate = computeMinimumColorSum(adjacent, dp, rootId, color);
            minimumSum = Math.min(minimumSum, sumCandidate);
        }
        return minimumSum;
    }

    private static int computeMinimumColorSum(List<Integer>[] adjacent, int[][] dp, int nodeId, int color) {
        if (dp[nodeId][color] != -1) {
            return dp[nodeId][color];
        }

        int minimumSumTotal = color;

        for (int childId : adjacent[nodeId]) {
            int minimumSumChild = INFINITE;

            for (int childColor = 1; childColor < MAX_COLOR; childColor++) {
                if (childColor == color) {
                    continue;
                }
                int sumCandidate = computeMinimumColorSum(adjacent, dp, childId, childColor);
                minimumSumChild = Math.min(minimumSumChild, sumCandidate);
            }
            minimumSumTotal += minimumSumChild;
        }

        dp[nodeId][color] = minimumSumTotal;
        return dp[nodeId][color];
    }

    private static int findRoot(List<Integer>[] adjacent) {
        int[] inDegrees = new int[adjacent.length];
        int[] outDegrees = new int[adjacent.length];

        for (int nodeId = 0; nodeId < adjacent.length; nodeId++) {
            for (int childId : adjacent[nodeId]) {
                outDegrees[nodeId]++;
                inDegrees[childId]++;
            }
        }

        for (int nodeId = 0; nodeId < inDegrees.length; nodeId++) {
            if (inDegrees[nodeId] == 0 && outDegrees[nodeId] > 0) {
                return nodeId;
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
