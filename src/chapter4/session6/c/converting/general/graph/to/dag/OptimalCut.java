package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/06/24.
 */
public class OptimalCut {

    private static final int NEGATIVE_INFINITE = -10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int height = FastReader.nextInt();
        int[] powerOf2 = getPower2Table();

        while (height != -1) {
            int maxNodesInCut = FastReader.nextInt();
            int nodes = powerOf2[height + 1] - 1;

            int[] weights = new int[nodes];
            for (int i = 0; i < weights.length; i++) {
                weights[i] = FastReader.nextInt();
            }

            int optimalCutWeight = computeOptimalCutWeight(weights, height, maxNodesInCut, powerOf2);
            outputWriter.printLine(optimalCutWeight);
            height = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeOptimalCutWeight(int[] weights, int maxHeight, int maxNodesInCut, int[] powerOf2) {
        // dp[node index][nodes available to add] = max weight
        int[][] dp = new int[weights.length][maxNodesInCut + 1];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeOptimalCutWeight(weights, maxHeight, dp, powerOf2, 0, maxNodesInCut, 0);
    }

    private static int computeOptimalCutWeight(int[] weights, int maxHeight, int[][] dp, int[] powerOf2,
                                               int nodeIndex, int nodesAvailable, int height) {
        if (dp[nodeIndex][nodesAvailable] != -1) {
            return dp[nodeIndex][nodesAvailable];
        }

        int heightAboveLeaves = maxHeight - height;
        if (heightAboveLeaves == 0) {
            // Leaf reached
            return weights[nodeIndex];
        }

        int leftChildIndex = nodeIndex + 1;
        int rightChildIndex = nodeIndex + powerOf2[heightAboveLeaves];

        // Add root
        int weightAddingRoot = weights[nodeIndex];

        // Do not add root
        int weightNotAddingRoot = NEGATIVE_INFINITE;
        for (int nodesAvailableLeft = 1; nodesAvailableLeft < nodesAvailable; nodesAvailableLeft++) {
            int weightRecursionLeft = computeOptimalCutWeight(weights, maxHeight, dp, powerOf2, leftChildIndex,
                    nodesAvailableLeft, height + 1);
            int weightRecursionRight = computeOptimalCutWeight(weights, maxHeight, dp, powerOf2, rightChildIndex,
                    nodesAvailable - nodesAvailableLeft, height + 1);

            int weightNotAddingRootCandidate = weightRecursionLeft + weightRecursionRight;
            weightNotAddingRoot = Math.max(weightNotAddingRoot, weightNotAddingRootCandidate);
        }

        dp[nodeIndex][nodesAvailable] = Math.max(weightAddingRoot, weightNotAddingRoot);
        return dp[nodeIndex][nodesAvailable];
    }

    // Because Math.pow() can be slow when called multiple times
    private static int[] getPower2Table() {
        int[] powerOf2 = new int[20];
        powerOf2[0] = 1;
        for (int i = 1; i < powerOf2.length; i++) {
            powerOf2[i] = powerOf2[i - 1] * 2;
        }
        return powerOf2;
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
