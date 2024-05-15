package chapter4.session6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/05/24.
 */
@SuppressWarnings("unchecked")
public class ExcavatorExpedition {

    private static class Edge {
        int vertex1;
        int vertex2;
        int weight;

        Edge(int vertex1, int vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private static final int INFINITE = 1000000000;

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        boolean[] isConstruction = new boolean[inputReader.nextInt()];
        int connections = inputReader.nextInt();
        String locations = inputReader.next();
        int targetID = isConstruction.length - 1;

        for (int i = 0; i < locations.length(); i++) {
            isConstruction[i] = locations.charAt(i) == 'X';
        }

        List<Edge>[] adjacent = new List[isConstruction.length];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int i = 0; i < connections; i++) {
            int locationID1 = inputReader.nextInt();
            int locationID2 = inputReader.nextInt();
            int weight;
            if (locationID2 == targetID) {
                weight = 0;
            } else if (isConstruction[locationID2]) {
                weight = -1;
            } else {
                weight = 1;
            }
            adjacent[locationID1].add(new Edge(locationID1, locationID2, weight));
        }

        DAGLongestPathWeighted longestPath = new DAGLongestPathWeighted(adjacent, 0);
        outputWriter.printLine(longestPath.distTo(targetID));
        outputWriter.flush();
    }

    private static class DAGLongestPathWeighted {
        private static int[] distTo;

        public DAGLongestPathWeighted(List<Edge>[] adjacent, int source) {
            distTo = new int[adjacent.length];
            Arrays.fill(distTo, INFINITE);
            distTo[source] = 0;

            int[] topological = topologicalSort(adjacent);
            for (int vertex : topological) {
                relax(adjacent, vertex);
            }
        }

        private void relax(List<Edge>[] adjacent, int vertex) {
            for (Edge edge : adjacent[vertex]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex] + edge.weight) {
                    distTo[neighbor] = distTo[vertex] + edge.weight;
                }
            }
        }

        public int distTo(int vertex) {
            return -distTo[vertex];
        }

        private int[] topologicalSort(List<Edge>[] adjacencyList) {
            int[] inDegrees = new int[adjacencyList.length];

            for (int i = 0; i < adjacencyList.length; i++) {
                for (Edge edge : adjacencyList[i]) {
                    inDegrees[edge.vertex2]++;
                }
            }

            Queue<Integer> queue = new LinkedList<>();
            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                if (inDegrees[vertexID] == 0) {
                    queue.add(vertexID);
                }
            }

            int[] topologicalOrder = new int[adjacencyList.length];
            int topologicalOrderIndex = 0;

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();
                topologicalOrder[topologicalOrderIndex++] = currentVertex;

                for (Edge edge : adjacencyList[currentVertex]) {
                    int neighbor = edge.vertex2;
                    inDegrees[neighbor]--;

                    if (inDegrees[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
            return topologicalOrder;
        }
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
