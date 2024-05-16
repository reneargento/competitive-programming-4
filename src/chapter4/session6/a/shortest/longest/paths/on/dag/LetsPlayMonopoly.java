package chapter4.session6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/05/24.
 */
@SuppressWarnings("unchecked")
public class LetsPlayMonopoly {

    private static class Node {
        int type;
        int primaryValue;
        int secondaryValue;

        public Node(int type, int primaryValue, int secondaryValue) {
            this.type = type;
            this.primaryValue = primaryValue;
            this.secondaryValue = secondaryValue;
        }
    }

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

    private static class Result {
        long alobMoney;
        long biceMoney;

        public Result(long alobMoney, long biceMoney) {
            this.alobMoney = alobMoney;
            this.biceMoney = biceMoney;
        }
    }

    private static final int PROPERTY = 0;
    private static final int SALARY = 1;
    private static final int TAX = 2;
    private static final long INFINITE = 100000000000000L;

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodesNumber = inputReader.nextInt();
        Node[] nodes = new Node[nodesNumber];
        int edges = inputReader.nextInt();
        int turns = inputReader.nextInt(); // Not used
        int alobStartNode = inputReader.nextInt() - 1;
        int biceStartNode = inputReader.nextInt() - 1;

        List<Edge>[] adjacent = new List[nodesNumber];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        List<Edge> edgeList = new ArrayList<>();

        for (int e = 0; e < edges; e++) {
            int nodeID1 = inputReader.nextInt() - 1;
            int nodeID2 = inputReader.nextInt() - 1;
            Edge edge = new Edge(nodeID1, nodeID2, 0);
            adjacent[nodeID1].add(edge);
            edgeList.add(edge);
        }

        for (int i = 0; i < nodes.length; i++) {
            int type = getTypeID(inputReader.next());
            int primaryValue = inputReader.nextInt();
            int secondaryValue = 0;
            if (type == 0) {
                secondaryValue = inputReader.nextInt();
            }
            nodes[i] = new Node(type, primaryValue, secondaryValue);
        }

        for (Edge edge : edgeList) {
            if (nodes[edge.vertex2].type == SALARY) {
                edge.weight = -nodes[edge.vertex2].primaryValue;
            } else if (nodes[edge.vertex2].type == TAX) {
                edge.weight = nodes[edge.vertex2].primaryValue;
            }
        }

        Result result = computeEndgame(adjacent, alobStartNode, biceStartNode);
        outputWriter.printLine(String.format("%d %d", result.alobMoney, result.biceMoney));
        outputWriter.flush();
    }

    private static Result computeEndgame(List<Edge>[] adjacent, int alobStartNode, int biceStartNode) {
        DAGLongestPathWeighted longestPathAlob = new DAGLongestPathWeighted(adjacent, alobStartNode);
        DAGLongestPathWeighted longestPathBice = new DAGLongestPathWeighted(adjacent, biceStartNode);
        return new Result(longestPathAlob.distToEndGame(), longestPathBice.distToEndGame());
    }

    private static int getTypeID(String typeValue) {
        switch (typeValue) {
            case "PROPERTY": return PROPERTY;
            case "SALARY": return SALARY;
            default: return TAX;
        }
    }

    private static class DAGLongestPathWeighted {
        private final long[] distTo;
        private long maxMoney = -INFINITE;

        public DAGLongestPathWeighted(List<Edge>[] adjacent, int source) {
            distTo = new long[adjacent.length];
            Arrays.fill(distTo, INFINITE);
            distTo[source] = 0;

            int[] topological = topologicalSort(adjacent);
            for (int vertex : topological) {
                relax(adjacent, vertex);
            }
        }

        private void relax(List<Edge>[] adjacent, int vertex) {
            if (adjacent[vertex].isEmpty()) {
                maxMoney = Math.max(maxMoney, -distTo[vertex]);
                return;
            }

            for (Edge edge : adjacent[vertex]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex] + edge.weight) {
                    distTo[neighbor] = distTo[vertex] + edge.weight;
                }
            }
        }

        public long distToEndGame() {
            return maxMoney;
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
