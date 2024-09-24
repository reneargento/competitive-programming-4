package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/05/24.
 */
public class BowsersPipes {

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
        int entities = inputReader.nextInt();
        boolean[] isCoinRoom = new boolean[entities];
        Edge[] adjacent = new Edge[entities];

        for (int i = 0; i < entities; i++) {
            int value = inputReader.nextInt();
            if (value == -1) {
                isCoinRoom[i] = true;
            } else {
                adjacent[i] = new Edge(i, value, 1);
            }
        }

        new DAGShortestPath(adjacent);
        int[] coinRoomsPerPipe = new int[entities];
        Arrays.fill(coinRoomsPerPipe, -1);

        int luigiPipes = inputReader.nextInt();
        for (int i = 0; i < luigiPipes; i++) {
            int luigiPipe = inputReader.nextInt();
            int coinRoom = computeCoinRoom(adjacent, isCoinRoom, coinRoomsPerPipe, luigiPipe);
            coinRoomsPerPipe[luigiPipe] = coinRoom;

            int pipeToEnter = DAGShortestPath.parent[coinRoom];
            outputWriter.printLine(pipeToEnter);
        }
        outputWriter.flush();
    }

    private static int computeCoinRoom(Edge[] adjacent, boolean[] isCoinRoom, int[] coinRoomsPerPipe, int pipeID) {
        if (coinRoomsPerPipe[pipeID] != -1) {
            return coinRoomsPerPipe[pipeID];
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(pipeID);

        while (!queue.isEmpty()) {
            int roomID = queue.poll();
            if (isCoinRoom[roomID]) {
                return roomID;
            }
            if (coinRoomsPerPipe[roomID] != -1) {
                return coinRoomsPerPipe[roomID];
            }

            Edge edge = adjacent[roomID];
            queue.offer(edge.vertex2);
        }
        return -1;
    }

    private static class DAGShortestPath {
        private static int[] distTo;
        private static int[] parent;

        public DAGShortestPath(Edge[] adjacent) {
            distTo = new int[adjacent.length];
            parent = new int[adjacent.length];
            Arrays.fill(distTo, INFINITE);
            Arrays.fill(parent, -1);

            int[] topological = topologicalSort(adjacent);
            for (int vertex : topological) {
                int parentID = parent[vertex] != -1 ? parent[vertex] : vertex;
                relax(adjacent, vertex, parentID);
            }
        }

        private void relax(Edge[] adjacent, int vertex, int parentID) {
            Edge edge = adjacent[vertex];
            if (edge != null) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex] + edge.weight) {
                    distTo[neighbor] = distTo[vertex] + edge.weight;
                    parent[neighbor] = parentID;
                }
            }
        }

        public static int distTo(int vertex) {
            return distTo[vertex];
        }

        private static int[] topologicalSort(Edge[] adjacencyList) {
            int[] inDegrees = new int[adjacencyList.length];

            for (int i = 0; i < adjacencyList.length; i++) {
                Edge edge = adjacencyList[i];
                if (edge != null) {
                    inDegrees[edge.vertex2]++;
                }
            }

            Queue<Integer> queue = new LinkedList<>();
            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                if (inDegrees[vertexID] == 0) {
                    queue.add(vertexID);
                    distTo[vertexID] = 0;
                }
            }

            int[] topologicalOrder = new int[adjacencyList.length];
            int topologicalOrderIndex = 0;

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();
                topologicalOrder[topologicalOrderIndex++] = currentVertex;

                Edge edge = adjacencyList[currentVertex];
                if (edge != null) {
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
