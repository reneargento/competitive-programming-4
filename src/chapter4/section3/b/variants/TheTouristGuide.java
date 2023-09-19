package chapter4.section3.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/09/23.
 */
public class TheTouristGuide {

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;
        int cost;

        Edge(int vertex1, int vertex2, int cost) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.cost = cost;
        }

        public int other(int vertex) {
            return vertex == vertex1 ? vertex2 : vertex1;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(other.cost, cost);
        }
    }

    private static final int REACHED = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int roads = FastReader.nextInt();
        int scenarioID = 1;

        while (cities != 0 || roads != 0) {
            List<Edge> edges = new ArrayList<>();

            for (int i = 0; i < roads; i++) {
                int cityID1 = FastReader.nextInt() - 1;
                int cityID2 = FastReader.nextInt() - 1;
                int capacity = FastReader.nextInt() - 1;
                edges.add(new Edge(cityID1, cityID2, capacity));
            }
            int startCity = FastReader.nextInt() - 1;
            int destinationCity = FastReader.nextInt() - 1;
            int tourists = FastReader.nextInt();

            int minimumTrips = computeMinimumTrips(edges, cities, startCity, destinationCity, tourists);
            outputWriter.printLine(String.format("Scenario #%d", scenarioID));
            outputWriter.printLine(String.format("Minimum Number of Trips = %d\n", minimumTrips));

            scenarioID++;
            cities = FastReader.nextInt();
            roads = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumTrips(List<Edge> edges, int cities, int startCity, int destinationCity,
                                           int tourists) {
        List<Edge>[] maximumSpanningTree = KruskalMinimumSpanningTree.getMaximumSpanningTree(edges, cities);
        boolean[] visited = new boolean[cities];
        double minimumEdge = getMinimumEdge(maximumSpanningTree, visited, destinationCity, startCity);
        double tripsNeeded = tourists / minimumEdge;
        return (int) Math.ceil(tripsNeeded);
    }

    private static int getMinimumEdge(List<Edge>[] mst, boolean[] visited, int destinationCity, int currentCity) {
        visited[currentCity] = true;
        if (currentCity == destinationCity) {
            return REACHED;
        }

        int minimumEdge = Integer.MAX_VALUE;
        for (Edge edge : mst[currentCity]) {
            int otherCity = edge.other(currentCity);
            if (!visited[otherCity]) {
                int currentMinimumEdge = getMinimumEdge(mst, visited, destinationCity, otherCity);
                if (currentMinimumEdge != Integer.MAX_VALUE) {
                    minimumEdge = Math.min(minimumEdge, currentMinimumEdge);
                    minimumEdge = Math.min(minimumEdge, edge.cost);
                }
            }
        }
        return minimumEdge;
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
        private static List<Edge>[] getMaximumSpanningTree(List<Edge> edges, int totalVertices) {
            List<Edge>[] maximumSpanningTree = (List<Edge>[]) new ArrayList[totalVertices];

            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(totalVertices);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);

                    if (maximumSpanningTree[edge.vertex1] == null) {
                        maximumSpanningTree[edge.vertex1] = new ArrayList<>();
                    }
                    if (maximumSpanningTree[edge.vertex2] == null) {
                        maximumSpanningTree[edge.vertex2] = new ArrayList<>();
                    }
                    maximumSpanningTree[edge.vertex1].add(edge);
                    maximumSpanningTree[edge.vertex2].add(edge);
                }

                if (unionFind.components == 1) {
                    break;
                }
            }
            return maximumSpanningTree;
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
