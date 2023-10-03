package chapter4.section3.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/09/23.
 */
public class LandlineTelephoneNetwork {

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
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int buildings = FastReader.nextInt();
        int connections = FastReader.nextInt();
        int insecureBuildingsNumber = FastReader.nextInt();
        Set<Integer> insecureBuildings = new HashSet<>();
        int edgesCostSum = 0;

        for (int i = 0; i < insecureBuildingsNumber; i++) {
            int buildingID = FastReader.nextInt() - 1;
            insecureBuildings.add(buildingID);
        }

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < connections; i++) {
            int buildingID1 = FastReader.nextInt() - 1;
            int buildingID2 = FastReader.nextInt() - 1;
            int cost = FastReader.nextInt();
            edgesCostSum += cost;
            edges.add(new Edge(buildingID1, buildingID2, cost));
        }

        int cheapestNetwork = KruskalMinimumSpanningTree.computeCheapestNetwork(buildings, edges,
                insecureBuildings, edgesCostSum);
        if (cheapestNetwork != -1) {
            outputWriter.printLine(cheapestNetwork);
        } else {
            outputWriter.printLine("impossible");
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
                    sizes[leader2] += sizes[leader1];
                } else if (ranks[leader2] < ranks[leader1]) {
                    leaders[leader2] = leader1;
                    sizes[leader1] += sizes[leader2];
                } else {
                    leaders[leader1] = leader2;
                    sizes[leader2] += sizes[leader1];
                    ranks[leader2]++;
                }
                components--;
            }
        }

        private static int computeCheapestNetwork(int totalVertices, List<Edge> edges, Set<Integer> insecureBuildings,
                                                  int edgesCostSum) {
            // Handle edge case - all buildings are insecure
            if (insecureBuildings.size() == totalVertices) {
                int allConnectedEdgesNumber = totalVertices * (totalVertices - 1) / 2;
                if (edges.size() == allConnectedEdgesNumber) {
                    return edgesCostSum;
                } else {
                    return -1;
                }
            }

            int cost = 0;
            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(totalVertices);

            for (Edge edge : edges) {
                if (insecureBuildings.contains(edge.vertex1) && insecureBuildings.contains(edge.vertex2)) {
                    continue;
                }
                int leader1 = unionFind.find(edge.vertex1);
                int leader2 = unionFind.find(edge.vertex2);

                if (insecureBuildings.contains(edge.vertex1) && unionFind.sizes[leader1] > 1) {
                    continue;
                }
                if (insecureBuildings.contains(edge.vertex2) && unionFind.sizes[leader2] > 1) {
                    continue;
                }

                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    cost += edge.cost;
                }
            }

            if (unionFind.components == 1) {
                return cost;
            }
            return -1;
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
