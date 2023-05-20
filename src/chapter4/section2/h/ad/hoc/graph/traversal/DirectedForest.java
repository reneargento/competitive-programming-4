package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 17/05/23.
 */
@SuppressWarnings("unchecked")
public class DirectedForest {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.readInt();

        for (int t = 1; t <= tests; t++) {
            List<Integer>[] adjacencyList = new List[inputReader.readInt()];
            int edges = inputReader.readInt();
            int[] inDegrees = new int[adjacencyList.length];

            for (int i = 0; i < edges; i++) {
                int node1 = inputReader.readInt() - 1;
                int node2 = inputReader.readInt() - 1;

                if (adjacencyList[node1] == null) {
                    adjacencyList[node1] = new ArrayList<>();
                }
                adjacencyList[node1].add(node2);
                inDegrees[node2]++;
            }

            int sets = computeLongestPathLength(adjacencyList, inDegrees);
            outputWriter.printLine(String.format("Case %d: %d", t, sets));
        }
        outputWriter.flush();
    }

    private static int computeLongestPathLength(List<Integer>[] adjacencyList, int[] inDegrees) {
        int longestPathLength = 0;
        int[] pathLengths = new int[adjacencyList.length];

        Queue<Integer> queue = new LinkedList<>();
        for (int nodeID = 0; nodeID < adjacencyList.length; nodeID++) {
            if (inDegrees[nodeID] == 0) {
                if (adjacencyList[nodeID] != null) {
                    queue.add(nodeID);
                }
                pathLengths[nodeID] = 1;
                longestPathLength = 1;
            }
        }

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for (int neighborID : adjacencyList[currentVertex]) {
                inDegrees[neighborID]--;

                if (inDegrees[neighborID] == 0) {
                    int newPathLength = pathLengths[currentVertex] + 1;
                    pathLengths[neighborID] = newPathLength;
                    longestPathLength = Math.max(longestPathLength, newPathLength);

                    if (adjacencyList[neighborID] != null) {
                        queue.add(neighborID);
                    }
                }
            }
        }
        return longestPathLength;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        // Returns null on EOF
        public String readLine() {
            int c = read();
            if (c == -1) {
                return null;
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == -1;
        }

        public String next() {
            return readString();
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
