package chapter4.section3.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/09/23.
 */
public class ArcticNetwork {

    private static class Coordinate  {
        int x;
        int y;

        public Coordinate(int x, int y) {
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
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int satelliteChannels = FastReader.nextInt();
            Coordinate[] coordinates = new Coordinate[FastReader.nextInt()];
            for (int i = 0; i < coordinates.length; i++) {
                coordinates[i] = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            }

            double minimumDistance = computeMinimumDistance(coordinates, satelliteChannels);
            outputWriter.printLine(String.format("%.2f", minimumDistance));
        }
        outputWriter.flush();
    }

    private static double computeMinimumDistance(Coordinate[] coordinates, int satelliteChannels) {
        List<Edge> edges = computeEdges(coordinates);
        return KruskalMinimumSpanningTree.getMinimumDistance(edges, coordinates.length, satelliteChannels);
    }

    private static List<Edge> computeEdges(Coordinate[] coordinates) {
        List<Edge> edges = new ArrayList<>();

        for (int outpostID1 = 0; outpostID1 < coordinates.length; outpostID1++) {
            Coordinate coordinate1 = coordinates[outpostID1];

            for (int outpostID2 = outpostID1 + 1; outpostID2 < coordinates.length; outpostID2++) {
                Coordinate coordinate2 = coordinates[outpostID2];
                double distance = distanceBetweenPoints(coordinate1.x, coordinate1.y, coordinate2.x, coordinate2.y);
                edges.add(new Edge(outpostID1, outpostID2, distance));
            }
        }
        return edges;
    }

    public static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
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

        private static double getMinimumDistance(List<Edge> edges, int totalVertices, int totalGroups) {
            double minimumDistance = 0;

            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(totalVertices);

            for (Edge edge : edges) {
                if (unionFind.components == totalGroups) {
                    break;
                }

                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    minimumDistance = Math.max(minimumDistance, edge.cost);
                }
            }
            return minimumDistance;
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
