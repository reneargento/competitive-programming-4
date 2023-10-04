package chapter4.section3.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/09/23.
 */
@SuppressWarnings("unchecked")
public class SimonTheSpider {

    private static class Edge implements Comparable<Edge> {
        int id;
        int vertex1;
        int vertex2;
        int weight;

        public Edge(int id, int vertex1, int vertex2, int weight) {
            this.id = id;
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
    }

    private static final long INFINITE = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int nodes = Integer.parseInt(data[0]);
            int links = Integer.parseInt(data[1]);

            List<Edge>[] originalAdjacencyList = new List[nodes];
            for (int i = 0; i < originalAdjacencyList.length; i++) {
                originalAdjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < links; i++) {
                int nodeID1 = FastReader.nextInt() - 1;
                int nodeID2 = FastReader.nextInt() - 1;
                int length = FastReader.nextInt();
                Edge edge = new Edge(i, nodeID1, nodeID2, length);
                originalAdjacencyList[nodeID1].add(edge);
            }

            new NextBestMST(originalAdjacencyList);
            long minimumLength = NextBestMST.computeNextBestMST();
            if (minimumLength != INFINITE) {
                outputWriter.printLine(minimumLength);
            } else {
                outputWriter.printLine("disconnected");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static class NextBestMST {

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

            public int count() {
                return components;
            }

            public boolean connected(int site1, int site2) {
                return find(site1) == find(site2);
            }

            // O(inverse Ackermann function)
            public int find(int site) {
                if (site == leaders[site]) {
                    return site;
                }
                return leaders[site] = find(leaders[site]);
            }

            // O(inverse Ackermann function)
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

        private static final int MAX_PATH = 21;
        private static List<Edge>[] adjacencyList;
        private static List<Edge> edges;
        private static int[][] dpMaxWeights;
        private static int[][] vertexIDsAbove;
        private static int[] distances;
        private static int verticesNumber;

        public NextBestMST(List<Edge>[] originalAdjacencyList) {
            verticesNumber = originalAdjacencyList.length + 1;
            adjacencyList = new List[verticesNumber];

            dpMaxWeights = new int[verticesNumber][MAX_PATH];
            vertexIDsAbove = new int[verticesNumber][MAX_PATH];
            distances = new int[verticesNumber];

            for (int vertexID = 1; vertexID < verticesNumber; vertexID++) {
                adjacencyList[vertexID] = new ArrayList<>();
                Arrays.fill(vertexIDsAbove[vertexID], -1);

                for (int distance = 0; distance < MAX_PATH; distance++) {
                    dpMaxWeights[vertexID][distance] = 0;
                }
            }

            for (int distance = 0; distance < MAX_PATH; distance++) {
                dpMaxWeights[0][distance] = 0;
            }
            computeEdges(originalAdjacencyList);
        }

        public static long computeNextBestMST() {
            long nextBestMSTWeight = Long.MAX_VALUE;
            long mstWeight = 0;
            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(verticesNumber);
            boolean[] edgeIDIsInMST = new boolean[edges.size()];
            Edge highestCostEdgeInMST = null;

            // Kruskal's algorithm
            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    adjacencyList[edge.vertex1].add(edge);
                    adjacencyList[edge.vertex2].add(new Edge(edge.id, edge.vertex2, edge.vertex1, edge.weight));
                    edgeIDIsInMST[edge.id] = true;
                    mstWeight += edge.weight;
                    unionFind.union(edge.vertex1, edge.vertex2);
                    highestCostEdgeInMST = edge;
                }
            }
            if (unionFind.components != 2) {
                // No MST exists
                return INFINITE;
            }

            // Pre-computation
            depthFirstSearch(1, 0, 0);

            for (int distance = 1; distance < MAX_PATH; distance++) {
                for (int vertexID = 1; vertexID < verticesNumber; vertexID++) {
                    if (vertexIDsAbove[vertexID][distance - 1] != -1) {
                        int vertexIDAbove = vertexIDsAbove[vertexID][distance - 1];
                        vertexIDsAbove[vertexID][distance] = vertexIDsAbove[vertexIDAbove][distance - 1];
                        dpMaxWeights[vertexID][distance] =
                                Math.max(dpMaxWeights[vertexID][distance - 1], dpMaxWeights[vertexIDAbove][distance - 1]);
                    }
                }
            }

            boolean hasNextBestMST = false;
            // Try to add each edge not in the MST
            for (Edge edge : edges) {
                if (!edgeIDIsInMST[edge.id]) {
                    int highestWeightInAncestorPath = lca(edge.vertex1, edge.vertex2);

                    if (nextBestMSTWeight > mstWeight - edge.weight - highestWeightInAncestorPath) {
                        nextBestMSTWeight = mstWeight - edge.weight - highestWeightInAncestorPath;
                        hasNextBestMST = true;
                    }
                }
            }

            if (!hasNextBestMST) {
                nextBestMSTWeight = mstWeight - 2L * highestCostEdgeInMST.weight;
            }
            return nextBestMSTWeight;
        }

        private static void computeEdges(List<Edge>[] adjacencyList) {
            edges = new ArrayList<>();
            int edgeID = 0;

            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                for (Edge edge : adjacencyList[vertexID]) {
                    edges.add(new Edge(edgeID, vertexID + 1, edge.vertex2 + 1, edge.weight));
                    edgeID++;
                }
            }
        }

        private static void depthFirstSearch(int vertexID, int parentID, int weight) {
            distances[vertexID] = distances[parentID] + 1;
            vertexIDsAbove[vertexID][0] = parentID;
            dpMaxWeights[vertexID][0] = weight;

            for (Edge edge : adjacencyList[vertexID]) {
                if (edge.vertex2 != parentID) {
                    depthFirstSearch(edge.vertex2, vertexID, edge.weight);
                }
            }
        }

        // Computes the highest edge weights on the paths from vertexID1 and vertexID2 to their LCA
        private static int lca(int vertexID1, int vertexID2) {
            int highestWeightInAncestorPath = -2;
            if (distances[vertexID1] < distances[vertexID2]) {
                int aux = vertexID2;
                vertexID2 = vertexID1;
                vertexID1 = aux;
            }

            for (int i = MAX_PATH - 1; i >= 0; i--) {
                if (distances[vertexID1] - distances[vertexID2] >= (1 << i)) {
                    highestWeightInAncestorPath =
                            Math.max(highestWeightInAncestorPath, dpMaxWeights[vertexID1][i]);
                    vertexID1 = vertexIDsAbove[vertexID1][i];
                }
            }

            if (vertexID1 == vertexID2) {
                return highestWeightInAncestorPath;
            }

            for (int i = MAX_PATH - 1; i >= 0; i--) {
                if (vertexIDsAbove[vertexID1][i] != -1
                        && vertexIDsAbove[vertexID2][i] != -1
                        && vertexIDsAbove[vertexID1][i] != vertexIDsAbove[vertexID2][i]) {
                    int highestWeightDP = Math.max(dpMaxWeights[vertexID1][i], dpMaxWeights[vertexID2][i]);
                    highestWeightInAncestorPath = Math.max(highestWeightInAncestorPath, highestWeightDP);
                    vertexID1 = vertexIDsAbove[vertexID1][i];
                    vertexID2 = vertexIDsAbove[vertexID2][i];
                }
            }
            int highestRootWeight = Math.max(dpMaxWeights[vertexID1][0], dpMaxWeights[vertexID2][0]);
            return Math.max(highestWeightInAncestorPath, highestRootWeight);
        }
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
