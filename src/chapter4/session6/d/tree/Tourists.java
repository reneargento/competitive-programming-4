package chapter4.session6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/07/24.
 */
// LCA implementation based on https://noiref.codecla.ws/ds/#sparse
@SuppressWarnings("unchecked")
public class Tourists {

    private static class Edge {
        int vertexId;
        int weight;

        public Edge(int vertexId, int weight) {
            this.vertexId = vertexId;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Edge>[] adjacencyList = new List[inputReader.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < adjacencyList.length - 1; i++) {
            int attractionId1 = inputReader.nextInt() - 1;
            int attractionId2 = inputReader.nextInt() - 1;
            adjacencyList[attractionId1].add(new Edge(attractionId2, 1));
            adjacencyList[attractionId2].add(new Edge(attractionId1, 1));
        }

        long pathsSum = computePathsSum(adjacencyList);
        outputWriter.printLine(pathsSum);
        outputWriter.flush();
    }

    private static long computePathsSum(List<Edge>[] adjacencyList) {
        long pathsSum = 0;
        LCA lca = new LCA(adjacencyList, 0);

        for (int vertexId1 = 1; vertexId1 <= adjacencyList.length / 2; vertexId1++) {
            int multiplier = 2;
            for (int vertexId2 = vertexId1 * multiplier; vertexId2 <= adjacencyList.length; multiplier++,
                    vertexId2 = vertexId1 * multiplier) {
                pathsSum += lca.computeDistance(vertexId1 - 1, vertexId2 - 1) + 1;
            }
        }
        return pathsSum;
    }

    private static class LCA {
        private final int LOG_N = 21;
        int[][] ancestor;
        int[] heights;           // number of edges from root
        int[] shortestDistances; // sum of edge weights from root
        boolean[] visited;

        LCA(List<Edge>[] adjacencyList, int rootVertexId) {
            ancestor = new int[LOG_N + 1][adjacencyList.length];
            heights = new int[adjacencyList.length];
            shortestDistances = new int[adjacencyList.length];
            visited = new boolean[adjacencyList.length];

            computeShortestDistancesFromRoot(adjacencyList, rootVertexId);
        }

        public int computeLCA(int vertexId1, int vertexId2) {
            if (heights[vertexId1] > heights[vertexId2]) {
                int aux = vertexId1;
                vertexId1 = vertexId2;
                vertexId2 = aux;
            }

            // Advance vertexId2 by height[vertexId2] - height[vertexId1] levels
            int levels = heights[vertexId2] - heights[vertexId1];
            for (int height = 0; height < LOG_N; height++) {
                if ((levels & (1 << height)) > 0) {
                    vertexId2 = ancestor[height][vertexId2];
                }
            }

            if (vertexId1 == vertexId2) {
                return vertexId1;
            }

            for (int height = LOG_N - 1; height >= 0; height--) {
                if (ancestor[height][vertexId1] != ancestor[height][vertexId2]) {
                    vertexId1 = ancestor[height][vertexId1];
                    vertexId2 = ancestor[height][vertexId2];
                }
            }
            return ancestor[0][vertexId1];
        }

        public int computeDistance(int vertexId1, int vertexId2) {
            int lca = computeLCA(vertexId1, vertexId2);
            return shortestDistances[vertexId1] + shortestDistances[vertexId2]
                    - 2 * shortestDistances[lca];
        }

        private void computeShortestDistancesFromRoot(List<Edge>[] adjacencyList, int rootVertexId) {
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(rootVertexId);
            visited[rootVertexId] = true;

            while (!queue.isEmpty()) {
                int currentVertexId = queue.poll();

                for (int height = 0; height < ancestor.length - 1; height++) {
                    int parentId = ancestor[height][currentVertexId];
                    ancestor[height + 1][currentVertexId] = ancestor[height][parentId];
                }

                for (Edge edge : adjacencyList[currentVertexId]) {
                    int neighborId = edge.vertexId;
                    if (visited[neighborId]) {
                        continue;
                    }

                    visited[neighborId] = true;
                    ancestor[0][neighborId] = currentVertexId;
                    shortestDistances[neighborId] = shortestDistances[currentVertexId] + edge.weight;
                    heights[neighborId] = heights[currentVertexId] + 1;
                    queue.offer(neighborId);
                }
            }
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
