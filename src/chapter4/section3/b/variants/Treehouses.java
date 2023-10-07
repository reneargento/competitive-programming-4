package chapter4.section3.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/09/23.
 */
public class Treehouses {

    private static class Coordinate {
        double x;
        double y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;
        double cost;

        Edge(int vertex1, int vertex2, double cost) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(cost, other.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Coordinate[] treehouses = new Coordinate[FastReader.nextInt()];
        int nearOpenLandEdge = FastReader.nextInt();
        int cablesInPlace = FastReader.nextInt();
        List<Edge> existingEdges = new ArrayList<>();

        for (int i = 0; i < treehouses.length; i++) {
            treehouses[i] = new Coordinate(FastReader.nextDouble(), FastReader.nextDouble());
        }
        for (int i = 0; i < cablesInPlace; i++) {
            int treehouseID1 = FastReader.nextInt() - 1;
            int treehouseID2 = FastReader.nextInt() - 1;
            existingEdges.add(new Edge(treehouseID1, treehouseID2, 0));
        }

        double minimumCableLength = computeMinimumCableLength(treehouses, nearOpenLandEdge, existingEdges);
        outputWriter.printLine(String.format("%.6f", minimumCableLength));
        outputWriter.flush();
    }

    private static double computeMinimumCableLength(Coordinate[] treehouses, int nearOpenLandEdge,
                                                    List<Edge> existingEdges) {
        List<Edge> edges = computeEdges(treehouses);
        return KruskalMinimumSpanningTree.getMinimumSpanningTreeLength(treehouses.length, nearOpenLandEdge,
                existingEdges, edges);
    }

    private static List<Edge> computeEdges(Coordinate[] coordinates) {
        List<Edge> edges = new ArrayList<>();

        for (int treehouseID1 = 0; treehouseID1 < coordinates.length; treehouseID1++) {
            Coordinate coordinate1 = coordinates[treehouseID1];

            for (int treehouseID2 = treehouseID1 + 1; treehouseID2 < coordinates.length; treehouseID2++) {
                Coordinate coordinate2 = coordinates[treehouseID2];
                double distance = distanceBetweenPoints(coordinate1.x, coordinate1.y, coordinate2.x, coordinate2.y);
                edges.add(new Edge(treehouseID1, treehouseID2, distance));
            }
        }
        return edges;
    }

    public static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
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

        private static double getMinimumSpanningTreeLength(int totalVertices, int nearOpenLandEdge,
                                                           List<Edge> existingEdges, List<Edge> edges) {
            double minimumLength = 0;
            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(totalVertices);

            for (Edge edge : existingEdges) {
                unionFind.union(edge.vertex1, edge.vertex2);
            }
            for (int i = 0; i < nearOpenLandEdge - 1; i++) {
                unionFind.union(i, i + 1);
            }

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    minimumLength += edge.cost;
                }

                if (unionFind.components == 1) {
                    break;
                }
            }
            return minimumLength;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
