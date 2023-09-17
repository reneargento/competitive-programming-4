package chapter4.section3.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/09/23.
 */
public class HeavyCargo {

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

    private static final int GOAL_REACHED = 10001;
    private static final int INFINITE = 10002;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int roads = FastReader.nextInt();
        int scenarioID = 1;

        while (cities != 0 || roads != 0) {
            Map<String, Integer> cityNameToIDMap = new HashMap<>();

            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < roads; i++) {
                String cityName1 = FastReader.next();
                String cityName2 = FastReader.next();
                int weightLimit = FastReader.nextInt();

                int cityID1 = getCityIDFromName(cityNameToIDMap, cityName1);
                int cityID2 = getCityIDFromName(cityNameToIDMap, cityName2);
                edges.add(new Edge(cityID1, cityID2, weightLimit));
            }

            String startCity = FastReader.next();
            String destinationCity = FastReader.next();
            int startCityID = cityNameToIDMap.get(startCity);
            int destinationCityID = cityNameToIDMap.get(destinationCity);

            int maxWeight = computeMaxWeight(edges, cities, startCityID, destinationCityID);
            outputWriter.printLine(String.format("Scenario #%d", scenarioID));
            outputWriter.printLine(String.format("%d tons\n", maxWeight));

            scenarioID++;
            cities = FastReader.nextInt();
            roads = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int getCityIDFromName(Map<String, Integer> cityNameToIDMap, String cityName) {
        if (!cityNameToIDMap.containsKey(cityName)) {
            cityNameToIDMap.put(cityName, cityNameToIDMap.size());
        }
        return cityNameToIDMap.get(cityName);
    }

    private static int computeMaxWeight(List<Edge> edges, int citiesNumber, int startCityID, int destinationCityID) {
        List<Edge>[] maximumSpanningTree = KruskalMinimumSpanningTree.getMaximumSpanningTree(edges, citiesNumber);
        boolean[] visited = new boolean[citiesNumber];
        return computeMaxWeight(maximumSpanningTree, visited, destinationCityID, startCityID);
    }

    private static int computeMaxWeight(List<Edge>[] maximumSpanningTree, boolean[] visited, int destinationCityID,
                                        int currentCityID) {
        visited[currentCityID] = true;
        if (currentCityID == destinationCityID) {
            return GOAL_REACHED;
        }

        int maxWeight = INFINITE;
        for (Edge edge : maximumSpanningTree[currentCityID]) {
            int otherCityID = edge.other(currentCityID);
            if (!visited[otherCityID]) {
                int weight = computeMaxWeight(maximumSpanningTree, visited, destinationCityID, otherCityID);
                if (weight != INFINITE) {
                    int currentMaxWeight = Math.min(edge.cost, weight);
                    maxWeight = Math.min(maxWeight, currentMaxWeight);
                }
            }
        }
        return maxWeight;
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
            List<Edge>[] minimumSpanningTree = (List<Edge>[]) new ArrayList[totalVertices + 1];

            Collections.sort(edges);
            KruskalMinimumSpanningTree.UnionFind unionFind = new KruskalMinimumSpanningTree.UnionFind(totalVertices);

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
