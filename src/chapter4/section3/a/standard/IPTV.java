package chapter4.section3.a.standard;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/08/23.
 */
public class IPTV {

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
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int cities = FastReader.nextInt();
            int links = FastReader.nextInt();
            Map<String, Integer> cityNameToIDMap = new HashMap<>();

            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < links; i++) {
                String cityName1 = FastReader.next();
                String cityName2 = FastReader.next();
                int cost = FastReader.nextInt();

                int cityID1 = getCityID(cityNameToIDMap, cityName1);
                int cityID2 = getCityID(cityNameToIDMap, cityName2);
                edges.add(new Edge(cityID1, cityID2, cost));
            }

            int transmissionCost = KruskalMinimumSpanningTree.getMinimumSpanningTreeCost(edges, cities);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(transmissionCost);
        }
        outputWriter.flush();
    }

    private static int getCityID(Map<String, Integer> cityNameToIDMap, String cityName) {
        if (!cityNameToIDMap.containsKey(cityName)) {
            cityNameToIDMap.put(cityName, cityNameToIDMap.size());
        }
        return cityNameToIDMap.get(cityName);
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

        private static int getMinimumSpanningTreeCost(List<Edge> edges, int totalVertices) {
            int cost = 0;
            Collections.sort(edges);
            UnionFind unionFind = new UnionFind(totalVertices);

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
