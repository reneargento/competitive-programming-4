package chapter4.section3.a.standard;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/08/23.
 */
// Incremental Minimum Spanning Tree in O(V * E) where V is the number of fields and E is the number of weeks.
@SuppressWarnings("unchecked")
public class TrailMaintenance {

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;
        int cost;

        Edge(int vertex1, int vertex2, int cost) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(cost, other.cost);
        }

        private int otherVertex(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            }
            return vertex1;
        }
    }

    private static class UnionFind {
        private final int[] leaders;
        private final int[] ranks;
        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            components = size;

            for (int i = 0; i < size; i++) {
                leaders[i] = i;
                ranks[i] = 0;
            }
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }
            return leaders[site] = find(leaders[site]);
        }

        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);
            if (leader1 == leader2) {
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
            } else {
                leaders[leader1] = leader2;
                ranks[leader2]++;
            }
            components--;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int fields = FastReader.nextInt();
        int weeks = FastReader.nextInt();

        int minimumTotalLength = 0;
        boolean hasMST = false;

        UnionFind unionFind = new UnionFind(fields);
        List<Edge>[] adjacencyList = new ArrayList[fields];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < weeks; i++) {
            int fieldID1 = FastReader.nextInt() - 1;
            int fieldID2 = FastReader.nextInt() - 1;
            int length = FastReader.nextInt();
            Edge edge = new Edge(fieldID1, fieldID2, length);

            if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                unionFind.union(edge.vertex1, edge.vertex2);
            }
            if (unionFind.components == 1) {
                hasMST = true;
            }

            Edge highestEdgeCost = getHighestEdgeCostInPath(adjacencyList, fieldID1, fieldID2);

            if (highestEdgeCost == null || highestEdgeCost.cost > length) {
                if (highestEdgeCost != null) {
                    int difference = highestEdgeCost.cost - length;
                    minimumTotalLength -= difference;
                    adjacencyList[highestEdgeCost.vertex1].remove(highestEdgeCost);
                    adjacencyList[highestEdgeCost.vertex2].remove(highestEdgeCost);
                } else {
                    minimumTotalLength += length;
                }

                adjacencyList[fieldID1].add(edge);
                adjacencyList[fieldID2].add(edge);
            }

            if (hasMST) {
                outputWriter.printLine(minimumTotalLength);
            } else {
                outputWriter.printLine("-1");
            }
        }
        outputWriter.flush();
    }

    private static Edge getHighestEdgeCostInPath(List<Edge>[] adjacencyList, int fieldID1, int fieldID2) {
        Edge highestEdgeCost = null;
        Deque<Edge> edgesInPathStack = new ArrayDeque<>();
        boolean[] visited = new boolean[adjacencyList.length];

        boolean hasPath = getEdgesInPath(adjacencyList, edgesInPathStack, visited, fieldID2, fieldID1);
        if (hasPath) {
            for (Edge edge : edgesInPathStack) {
                if (highestEdgeCost == null || edge.cost > highestEdgeCost.cost) {
                    highestEdgeCost = edge;
                }
            }
        }
        return highestEdgeCost;
    }

    private static boolean getEdgesInPath(List<Edge>[] adjacencyList, Deque<Edge> edgesInPathStack,
                                          boolean[] visited, int targetFieldID, int currentFieldID) {
        visited[currentFieldID] = true;

        for (Edge edge : adjacencyList[currentFieldID]) {
            int otherVertex = edge.otherVertex(currentFieldID);
            if (visited[otherVertex]) {
                continue;
            }

            edgesInPathStack.push(edge);
            if (otherVertex == targetFieldID) {
                return true;
            }

            boolean hasPath = getEdgesInPath(adjacencyList, edgesInPathStack, visited, targetFieldID, otherVertex);
            if (hasPath) {
                return true;
            } else {
                edgesInPathStack.pop();
            }
        }
        return false;
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
