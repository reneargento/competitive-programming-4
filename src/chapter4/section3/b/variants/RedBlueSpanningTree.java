package chapter4.section3.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/09/23.
 */
public class RedBlueSpanningTree {

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

        int nodes = FastReader.nextInt();
        int edgesNumber = FastReader.nextInt();
        int blueEdges = FastReader.nextInt();

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < edgesNumber; i++) {
            char color = FastReader.next().charAt(0);
            int nodeID1 = FastReader.nextInt() - 1;
            int nodeID2 = FastReader.nextInt() - 1;
            int cost = color == 'B' ? 1 : 0;

            edges.add(new Edge(nodeID1, nodeID2, cost));
        }

        if (canBuildBlueST(nodes, edges, blueEdges)) {
            outputWriter.printLine("1");
        } else {
            outputWriter.printLine("0");
        }
        outputWriter.flush();
    }

    private static boolean canBuildBlueST(int nodes, List<Edge> edges, int blueEdges) {
        Collections.sort(edges);
        int minSTCost = KruskalMinimumSpanningTree.getSpanningTreeCost(edges, nodes);

        edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return Integer.compare(edge2.cost, edge1.cost);
            }
        });
        int maxSTCost = KruskalMinimumSpanningTree.getSpanningTreeCost(edges, nodes);
        return minSTCost <= blueEdges && blueEdges <= maxSTCost;
    }

    private static class KruskalMinimumSpanningTree {

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

        private static int getSpanningTreeCost(List<Edge> edges, int totalVertices) {
            int cost = 0;
            UnionFind unionFind = new UnionFind(totalVertices);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    cost += edge.cost;
                }

                if (unionFind.components == 1) {
                    break;
                }
            }
            return cost;
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
