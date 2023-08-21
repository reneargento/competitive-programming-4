package chapter4.section3.a.standard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/08/23.
 */
public class CommunicationsSatellite {

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;
        double length;

        Edge(int vertex1, int vertex2, double length) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.length = length;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(length, other.length);
        }
    }

    private static class DishAntenna {
        int xLocation;
        int yLocation;
        int radius;

        public DishAntenna(int xLocation, int yLocation, int radius) {
            this.xLocation = xLocation;
            this.yLocation = yLocation;
            this.radius = radius;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        DishAntenna[] dishAntennas = new DishAntenna[FastReader.nextInt()];
        for (int i = 0; i < dishAntennas.length; i++) {
            dishAntennas[i] = new DishAntenna(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
        }
        double minimumDistance = computeMinimumDistance(dishAntennas);
        outputWriter.printLine(String.format("%.8f", minimumDistance));
        outputWriter.flush();
    }

    private static double computeMinimumDistance(DishAntenna[] dishAntennas) {
        List<Edge> edges = computeEdges(dishAntennas);
        return KruskalMinimumSpanningTree.getMinimumLength(dishAntennas.length, edges);
    }

    private static List<Edge> computeEdges(DishAntenna[] dishAntennas) {
        List<Edge> edges = new ArrayList<>();

        for (int antenna1ID = 0; antenna1ID < dishAntennas.length; antenna1ID++) {
            for (int antenna2ID = antenna1ID + 1; antenna2ID < dishAntennas.length; antenna2ID++) {
                DishAntenna antenna1 = dishAntennas[antenna1ID];
                DishAntenna antenna2 = dishAntennas[antenna2ID];

                double pointsDistance = distanceBetweenPoints(antenna1.xLocation, antenna1.yLocation,
                        antenna2.xLocation, antenna2.yLocation);
                double circleDistance = getCircleDistance(pointsDistance, antenna1.radius, antenna2.radius);
                edges.add(new Edge(antenna1ID, antenna2ID, circleDistance));
            }
        }
        return edges;
    }

    public static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private static double getCircleDistance(double pointsDistance, double radius1, double radius2) {
        return pointsDistance - radius1 - radius2;
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

        private static double getMinimumLength(int dishAntennas, List<Edge> edges) {
            double minimumLength = 0;

            Collections.sort(edges);
            KruskalMinimumSpanningTree.UnionFind unionFind = new KruskalMinimumSpanningTree.UnionFind(dishAntennas);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    minimumLength += edge.length;
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
