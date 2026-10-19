package chapter4.section6.c.converting.general.graph.to.dag;

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
        int junctions = adjacent.length;
        int[] previous = new int[junctions];
        int[] current = new int[junctions];

        Arrays.fill(previous, INFINITE);
        previous[end] = 0;

        for (int k = 1; k < maxJunctions; k++) {
            System.arraycopy(previous, 0, current, 0, junctions);
            boolean changed = false;

            for (int junctionId = 0; junctionId < junctions; junctionId++) {
                for (Edge edge : adjacent[junctionId]) {
                    int neighborJunctionId = edge.otherJunctionId;

                    if (previous[neighborJunctionId] != INFINITE) {
                        int candidateTime = previous[neighborJunctionId] + edge.time;
                        if (candidateTime < current[junctionId]) {
                            current[junctionId] = candidateTime;
                            changed = true;
                        }
                    }
                }
            }

            int[] temp = previous;
            previous = current;
            current = temp;

            if (!changed) {
                break;
            }
        }
        return previous[start] == INFINITE ? -1 : previous[start];
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

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
