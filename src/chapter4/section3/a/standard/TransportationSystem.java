package chapter4.section3.a.standard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/07/23.
 */
public class TransportationSystem {

    private static class Coordinate {
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

    private static class Result {
        int numberOfStates;
        int minimumExtensionRoads;
        int minimumExtensionRailroads;

        public Result(int numberOfStates, int minimumExtensionRoads, int minimumExtensionRailroads) {
            this.numberOfStates = numberOfStates;
            this.minimumExtensionRoads = minimumExtensionRoads;
            this.minimumExtensionRailroads = minimumExtensionRailroads;
        }
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

        public static Result getMinimumSpanningTreeCost(List<Edge> edges, int totalVertices, int distanceThreshold) {
            int numberOfStates = 1;
            double minimumExtensionRoads = 0;
            double minimumExtensionRailroads = 0;

            Collections.sort(edges);
            KruskalMinimumSpanningTree.UnionFind unionFind = new KruskalMinimumSpanningTree.UnionFind(totalVertices + 1);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);

                    if (edge.cost <= distanceThreshold) {
                        minimumExtensionRoads += edge.cost;
                    } else {
                        minimumExtensionRailroads += edge.cost;
                        numberOfStates++;
                    }
                }

                if (unionFind.components == 1) {
                    break;
                }
            }

            int roadsExtensionInt = (int) Math.round(minimumExtensionRoads);
            int railroadsExtensionInt = (int) Math.round(minimumExtensionRailroads);
            return new Result(numberOfStates, roadsExtensionInt, railroadsExtensionInt);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            Coordinate[] coordinates = new Coordinate[FastReader.nextInt()];
            int distanceThreshold = FastReader.nextInt();

            for (int cityID = 0; cityID < coordinates.length; cityID++) {
                coordinates[cityID] = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            }

            Result result = computeExtensions(coordinates, distanceThreshold);
            outputWriter.printLine(String.format("Case #%d: %d %d %d", t, result.numberOfStates,
                    result.minimumExtensionRoads, result.minimumExtensionRailroads));
        }
        outputWriter.flush();
    }

    private static Result computeExtensions(Coordinate[] coordinates, int distanceThreshold) {
        List<Edge> edges = computeEdges(coordinates);
        return KruskalMinimumSpanningTree.getMinimumSpanningTreeCost(edges, coordinates.length, distanceThreshold);
    }

    private static List<Edge> computeEdges(Coordinate[] coordinates) {
        List<Edge> edges = new ArrayList<>();

        for (int vertexID1 = 0; vertexID1 < coordinates.length; vertexID1++) {
            Coordinate coordinate1 = coordinates[vertexID1];
            for (int vertexID2 = vertexID1 + 1; vertexID2 < coordinates.length; vertexID2++) {
                Coordinate coordinate2 = coordinates[vertexID2];
                double cost = distanceBetweenPoints(coordinate1.x, coordinate1.y, coordinate2.x, coordinate2.y);
                edges.add(new Edge(vertexID1, vertexID2, cost));
            }
        }
        return edges;
    }

    public static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
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
