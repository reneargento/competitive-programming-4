package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/06/24.
 */
@SuppressWarnings("unchecked")
public class SingleSourceShortestPathSafePath {

    private static class Edge {
        int otherJunctionId;
        int time;

        public Edge(int otherJunctionId, int time) {
            this.otherJunctionId = otherJunctionId;
            this.time = time;
        }
    }

    private static final int INFINITE = 1000000000;

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.nextInt();

        for (int t = 0; t < tests; t++) {
            List<Edge>[] adjacent = new List[inputReader.nextInt()];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new ArrayList<>();
            }

            for (int junctionId = 0; junctionId < adjacent.length; junctionId++) {
                int edges = inputReader.nextInt();
                for (int e = 0; e < edges; e++) {
                    Edge edge = new Edge(inputReader.nextInt(), inputReader.nextInt());
                    adjacent[junctionId].add(edge);
                }
            }

            int queries = inputReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int start = inputReader.nextInt();
                int end = inputReader.nextInt();
                int maxJunctions = inputReader.nextInt();

                int minimumTime = computeMinimumTime(adjacent, start, end, maxJunctions);
                if (t > 0) {
                    outputWriter.printLine();
                }
                outputWriter.printLine(minimumTime);
            }
        }
        outputWriter.flush();
    }

    private static int computeMinimumTime(List<Edge>[] adjacent, int start, int end, int maxJunctions) {
        // dp[junction id][junctions available] = minimum time
        int[][] dp = new int[adjacent.length][maxJunctions];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        int minimumTime = computeMinimumTime(adjacent, end, dp, start, maxJunctions - 1);
        if (minimumTime == INFINITE) {
            return -1;
        }
        return minimumTime;
    }

    private static int computeMinimumTime(List<Edge>[] adjacent, int end, int[][] dp, int junctionId,
                                          int junctionsAvailable) {
        if (junctionId == end) {
            return 0;
        }
        if (junctionsAvailable == 0) {
            return INFINITE;
        }
        if (dp[junctionId][junctionsAvailable] != -1) {
            return dp[junctionId][junctionsAvailable];
        }

        int minimumTime = INFINITE;
        for (Edge edge : adjacent[junctionId]) {
            int candidateTime = edge.time +
                    computeMinimumTime(adjacent, end, dp, edge.otherJunctionId, junctionsAvailable - 1);
            minimumTime = Math.min(minimumTime, candidateTime);
        }

        dp[junctionId][junctionsAvailable] = minimumTime;
        return dp[junctionId][junctionsAvailable];
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String next() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public String nextLine() throws IOException {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
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
