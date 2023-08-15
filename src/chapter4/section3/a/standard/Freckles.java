package chapter4.section3.a.standard;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/08/23.
 */
public class Freckles {

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

    private static class Coordinate {
        double xCoordinate;
        double yCoordinate;

        public Coordinate(double xCoordinate, double yCoordinate) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int frecklesNumber = FastReader.nextInt();
            Coordinate[] coordinates = new Coordinate[frecklesNumber];
            for (int i = 0; i < frecklesNumber; i++) {
                double xCoordinate = FastReader.nextDouble();
                double yCoordinate = FastReader.nextDouble();
                coordinates[i] = new Coordinate(xCoordinate, yCoordinate);
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            double minimumLength = computeMinimumLength(coordinates);
            outputWriter.printLine(String.format("%.2f", minimumLength));
        }
        outputWriter.flush();
    }

    private static double computeMinimumLength(Coordinate[] coordinates) {
        List<Edge> edges = computeEdges(coordinates);
        return KruskalMinimumSpanningTree.getMinimumSpanningTreeCost(edges, coordinates.length);
    }

    private static List<Edge> computeEdges(Coordinate[] coordinates) {
        List<Edge> edges = new ArrayList<>();
        for (int coordinateID1 = 0; coordinateID1 < coordinates.length; coordinateID1++) {
            for (int coordinateID2 = coordinateID1 + 1; coordinateID2 < coordinates.length; coordinateID2++) {
                double cost = distanceBetweenCoordinates(coordinates[coordinateID1], coordinates[coordinateID2]);
                edges.add(new Edge(coordinateID1, coordinateID2, cost));
            }
        }
        return edges;
    }

    public static double distanceBetweenCoordinates(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.pow(coordinate1.xCoordinate - coordinate2.xCoordinate, 2)
                + Math.pow(coordinate1.yCoordinate - coordinate2.yCoordinate, 2);
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

        private static double getMinimumSpanningTreeCost(List<Edge> edges, int totalVertices) {
            double cost = 0;
            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(totalVertices);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    cost += Math.sqrt(edge.cost);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}