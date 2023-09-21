package chapter4.section3.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/09/23.
 */
public class ConnectTheCampus {

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

        String line = FastReader.getLine();
        while (line != null) {
            // Required I/O handling due to empty lines in the input
            if (line.isEmpty()) {
                line = FastReader.getLine();
                continue;
            }

            int buildings = Integer.parseInt(line);
            Coordinate[] coordinates = new Coordinate[buildings];
            for (int i = 0; i < coordinates.length; i++) {
                coordinates[i] = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            }

            List<Edge> existingCables = new ArrayList<>();
            int existingCablesNumber = FastReader.nextInt();
            for (int i = 0; i < existingCablesNumber; i++) {
                int buildingID1 = FastReader.nextInt() - 1;
                int buildingID2 = FastReader.nextInt() - 1;
                existingCables.add(new Edge(buildingID1, buildingID2, 0));
            }

            double newCablesLength = computeNewCablesLength(coordinates, existingCables);
            outputWriter.printLine(String.format("%.2f", newCablesLength));

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double computeNewCablesLength(Coordinate[] coordinates, List<Edge> existingCables) {
        computeExistingCableLengths(coordinates, existingCables);
        List<Edge> edges = computeEdges(coordinates);
        return KruskalMinimumSpanningTree.getMinimumSpanningTreeCost(edges, coordinates.length, existingCables);
    }

    private static void computeExistingCableLengths(Coordinate[] coordinates, List<Edge> existingCables) {
        for (Edge edge : existingCables) {
            Coordinate coordinate1 = coordinates[edge.vertex1];
            Coordinate coordinate2 = coordinates[edge.vertex2];

            edge.cost = distanceBetweenPoints(coordinate1.x, coordinate1.y, coordinate2.x, coordinate2.y);
        }
    }

    private static List<Edge> computeEdges(Coordinate[] coordinates) {
        List<Edge> edges = new ArrayList<>();

        for (int buildingID1 = 0; buildingID1 < coordinates.length; buildingID1++) {
            Coordinate coordinate1 = coordinates[buildingID1];

            for (int buildingID2 = buildingID1 + 1; buildingID2 < coordinates.length; buildingID2++) {
                Coordinate coordinate2 = coordinates[buildingID2];
                double distance = distanceBetweenPoints(coordinate1.x, coordinate1.y, coordinate2.x, coordinate2.y);
                edges.add(new Edge(buildingID1, buildingID2, distance));
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

        private static double getMinimumSpanningTreeCost(List<Edge> edges, int totalVertices,
                                                         List<Edge> existingEdges) {
            double cost = 0;
            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(totalVertices);

            for (Edge edge : existingEdges) {
                unionFind.union(edge.vertex1, edge.vertex2);
            }

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
