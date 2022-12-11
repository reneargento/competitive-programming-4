package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/12/22.
 */
public class CyclesEasy {

    private static final int MOD = 9901;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int nodes = FastReader.nextInt();
            int forbiddenEdges = FastReader.nextInt();
            boolean[][] isForbidden = new boolean[nodes][nodes];

            for (int i = 0; i < forbiddenEdges; i++) {
                int node1 = FastReader.nextInt() - 1;
                int node2 = FastReader.nextInt() - 1;
                isForbidden[node1][node2] = true;
                isForbidden[node2][node1] = true;
            }
            long hamiltonianCycles = (computeHamiltonianCycles(nodes, isForbidden) / 2) % MOD;
            outputWriter.printLine(String.format("Case #%d: %d", t, hamiltonianCycles));
        }
        outputWriter.flush();
    }

    private static long computeHamiltonianCycles(int nodes, boolean[][] isForbidden) {
        int bitmaskSize = (1 << nodes);
        long[][] dp = new long[nodes][bitmaskSize];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }

        int bitmask = 1;
        return computeHamiltonianCycles(nodes, isForbidden, dp, 0, bitmask);
    }

    private static long computeHamiltonianCycles(int nodes, boolean[][] isForbidden, long[][] dp, int currentNodeId,
                                                 int bitmask) {
        if (bitmask == dp[0].length - 1) {
            if (isForbidden[currentNodeId][0]) {
                return 0;
            }
            return 1;
        }
        if (dp[currentNodeId][bitmask] != -1) {
            return dp[currentNodeId][bitmask];
        }

        long cycles = 0;
        for (int nodeId = 1; nodeId < nodes; nodeId++) {
            if ((bitmask & (1 << nodeId)) == 0 && !isForbidden[currentNodeId][nodeId]) {
                int nextBitmask = bitmask | (1 << nodeId);
                cycles += computeHamiltonianCycles(nodes, isForbidden, dp, nodeId, nextBitmask);
            }
        }
        dp[currentNodeId][bitmask] = cycles;
        return dp[currentNodeId][bitmask];
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
