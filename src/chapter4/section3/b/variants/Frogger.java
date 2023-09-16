package chapter4.section3.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/09/23.
 */
public class Frogger {

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;
        double cost;

        Edge(int vertex1, int vertex2, double cost) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.cost = cost;
        }

        public int other(int vertex) {
            return vertex == vertex1 ? vertex2 : vertex1;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(cost, other.cost);
        }
    }

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int scenarioID = 1;
        int stones = FastReader.nextInt();
        while (stones != 0) {
            Coordinate[] coordinates = new Coordinate[stones];
            for (int i = 0; i < coordinates.length; i++) {
                coordinates[i] = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            }

            double frogDistance = computeFrogDistance(coordinates);
            outputWriter.printLine(String.format("Scenario #%d", scenarioID));
            outputWriter.printLine(String.format("Frog Distance = %.3f\n", frogDistance));

            scenarioID++;
            stones = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeFrogDistance(Coordinate[] coordinates) {
        List<Edge> edges = computeEdges(coordinates);
        List<Edge>[] minimumSpanningTree = KruskalMinimumSpanningTree.getMinimumSpanningTree(edges, coordinates.length);
        boolean[] visited = new boolean[coordinates.length];
        return computeMaxJump(minimumSpanningTree, visited, 0);
    }

    private static List<Edge> computeEdges(Coordinate[] coordinates) {
        List<Edge> edges = new ArrayList<>();
        for (int coordinateID1 = 0; coordinateID1 < coordinates.length; coordinateID1++) {
            Coordinate coordinate1 = coordinates[coordinateID1];

            for (int coordinateID2 = coordinateID1 + 1; coordinateID2 < coordinates.length; coordinateID2++) {
                Coordinate coordinate2 = coordinates[coordinateID2];
                double distance = distanceBetweenPoints(coordinate1.x, coordinate1.y, coordinate2.x, coordinate2.y);
                edges.add(new Edge(coordinateID1, coordinateID2, distance));
            }
        }
        return edges;
    }

    public static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private static double computeMaxJump(List<Edge>[] minimumSpanningTree, boolean[] visited, int currentStone) {
        visited[currentStone] = true;
        if (currentStone == 1) {
            return 0;
        }

        double maxJump = -1;

        for (Edge edge : minimumSpanningTree[currentStone]) {
            int otherStone = edge.other(currentStone);
            if (!visited[otherStone]) {
                double distance = computeMaxJump(minimumSpanningTree, visited, otherStone);
                if (distance != -1) {
                    double maxDistance = Math.max(distance, edge.cost);
                    maxJump = Math.max(maxJump, maxDistance);
                }
            }
        }
        return maxJump;
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

        @SuppressWarnings("unchecked")
        private static List<Edge>[] getMinimumSpanningTree(List<Edge> edges, int totalVertices) {
            List<Edge>[] minimumSpanningTree = (List<Edge>[]) new ArrayList[totalVertices + 1];

            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(totalVertices);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);

                    if (minimumSpanningTree[edge.vertex1] == null) {
                        minimumSpanningTree[edge.vertex1] = new ArrayList<>();
                    }
                    if (minimumSpanningTree[edge.vertex2] == null) {
                        minimumSpanningTree[edge.vertex2] = new ArrayList<>();
                    }
                    minimumSpanningTree[edge.vertex1].add(edge);
                    minimumSpanningTree[edge.vertex2].add(edge);
                }

                if (unionFind.components == 1) {
                    break;
                }
            }
            return minimumSpanningTree;
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
