package chapter4.section3.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/08/23.
 */
@SuppressWarnings("unchecked")
public class TourBelt {

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
            return Integer.compare(other.cost, cost);
        }

        private int otherVertex(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            }
            return vertex1;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int islands = FastReader.nextInt();
            int edgesNumber = FastReader.nextInt();

            List<Edge> edges = new ArrayList<>();
            List<Edge>[] adjacencyList = new List[islands];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < edgesNumber; i++) {
                int islandID1 = FastReader.nextInt() - 1;
                int islandID2 = FastReader.nextInt() - 1;
                int synergyEffect = FastReader.nextInt();

                Edge edge = new Edge(islandID1, islandID2, synergyEffect);
                edges.add(edge);
                adjacencyList[islandID1].add(edge);
                adjacencyList[islandID2].add(edge);
            }

            int candidateSizesSum = KruskalMinimumSpanningTree.getCandidateSizes(edges, adjacencyList);
            outputWriter.printLine(candidateSizesSum);
        }
        outputWriter.flush();
    }

    private static class KruskalMinimumSpanningTree {

        private static class UnionFind {
            private final int[] leaders;
            private final int[] ranks;
            private final int[] sizes;
            private int components;

            public UnionFind(int size) {
                leaders = new int[size];
                ranks = new int[size];
                sizes = new int[size];

                components = size;

                for (int i = 0; i < size; i++) {
                    leaders[i] = i;
                    sizes[i] = 1;
                }
            }

            public int count() {
                return components;
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

            public boolean union(int site1, int site2) {
                int leader1 = find(site1);
                int leader2 = find(site2);
                if (leader1 == leader2) {
                    return false;
                }

                if (ranks[leader1] < ranks[leader2]) {
                    leaders[leader1] = leader2;
                    sizes[leader2] += sizes[leader1];
                } else if (ranks[leader2] < ranks[leader1]) {
                    leaders[leader2] = leader1;
                    sizes[leader1] += sizes[leader2];
                } else {
                    leaders[leader1] = leader2;
                    ranks[leader2]++;
                    sizes[leader2] += sizes[leader1];
                }
                components--;
                return true;
            }
        }

        private static int getCandidateSizes(List<Edge> edges, List<Edge>[] adjacencyList) {
            int sizes = 0;
            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(adjacencyList.length);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    boolean connected = unionFind.union(edge.vertex1, edge.vertex2);
                    if (connected) {
                        int newLeader = unionFind.find(edge.vertex1);
                        if (isCandidate(adjacencyList, unionFind, newLeader)) {
                            sizes += unionFind.sizes[newLeader];
                        }
                    }
                }

                if (unionFind.components == 1) {
                    break;
                }
            }
            return sizes;
        }

        private static boolean isCandidate(List<Edge>[] adjacencyList, UnionFind unionFind, int leader) {
            int maxBorderEdge = Integer.MIN_VALUE;
            int minInsideEdge = Integer.MAX_VALUE;

            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                int vertexLeader = unionFind.find(vertexID);

                if (vertexLeader == leader) {
                    // Check all neighbors and update min/max inside and border edge values
                    for (Edge edge : adjacencyList[vertexID]) {
                        int neighborVertexID = edge.otherVertex(vertexID);
                        int neighborVertexLeader = unionFind.find(neighborVertexID);

                        if (neighborVertexLeader == vertexLeader) {
                            minInsideEdge = Math.min(minInsideEdge, edge.cost);
                        } else {
                            maxBorderEdge = Math.max(maxBorderEdge, edge.cost);
                        }
                    }
                }
            }
            return minInsideEdge > maxBorderEdge;
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
