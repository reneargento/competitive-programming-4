package chapter4.section3.a.standard;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/08/23.
 */
public class Svemir {

    private static class Coordinate {
        int id;
        int x;
        int y;
        int z;

        public Coordinate(int id, int x, int y, int z) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

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
        Coordinate[] coordinates = new Coordinate[FastReader.nextInt()];

        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(i, FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
        }

        long minimumTunnelCost = computeMinimumTunnelCost(coordinates);
        outputWriter.printLine(minimumTunnelCost);
        outputWriter.flush();
    }

    private static long computeMinimumTunnelCost(Coordinate[] coordinates) {
        List<Edge> edges = computeEdges(coordinates);
        return KruskalMinimumSpanningTree.getMinimumCost(coordinates.length, edges);
    }

    private static List<Edge> computeEdges(Coordinate[] coordinates) {
        Arrays.sort(coordinates, new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate coordinate1, Coordinate coordinate2) {
                return Integer.compare(coordinate1.x, coordinate2.x);
            }
        });
        List<Edge> edgesSortedByX = getEdges(coordinates, true, false);

        Arrays.sort(coordinates, new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate coordinate1, Coordinate coordinate2) {
                return Integer.compare(coordinate1.y, coordinate2.y);
            }
        });
        List<Edge> edgesSortedByY = getEdges(coordinates, false, true);

        Arrays.sort(coordinates, new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate coordinate1, Coordinate coordinate2) {
                return Integer.compare(coordinate1.z, coordinate2.z);
            }
        });
        List<Edge> edgesSortedByZ = getEdges(coordinates, false, false);

        List<Edge> edges = new ArrayList<>(edgesSortedByX);
        edges.addAll(edgesSortedByY);
        edges.addAll(edgesSortedByZ);
        return edges;
    }

    private static List<Edge> getEdges(Coordinate[] coordinates, boolean compareX, boolean compareY) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < coordinates.length - 1; i++) {
            int cost;

            if (compareX) {
                cost = Math.abs(coordinates[i].x - coordinates[i + 1].x);
            } else if (compareY) {
                cost = Math.abs(coordinates[i].y - coordinates[i + 1].y);
            } else {
                cost = Math.abs(coordinates[i].z - coordinates[i + 1].z);
            }
            edges.add(new Edge(coordinates[i].id, coordinates[i + 1].id, cost));
        }
        return edges;
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

        private static long getMinimumCost(int coordinates, List<Edge> edges) {
            long minimumCost = 0;

            Collections.sort(edges);
            KruskalMinimumSpanningTree.UnionFind unionFind = new KruskalMinimumSpanningTree.UnionFind(coordinates);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    minimumCost += edge.cost;
                }

                if (unionFind.components == 1) {
                    break;
                }
            }
            return minimumCost;
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
