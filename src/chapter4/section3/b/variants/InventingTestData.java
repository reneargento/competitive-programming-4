package chapter4.section3.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/09/23.
 */
public class InventingTestData {

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

    private static class UnionFind {
        private final int[] leaders;
        private final int[] ranks;
        private final long[] sizes;
        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            sizes = new long[size];
            components = size;

            for (int i = 0; i < size; i++) {
                leaders[i] = i;
                sizes[i] = 1;
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
                sizes[leader2] += sizes[leader1];
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
                sizes[leader1] += sizes[leader2];
            } else {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
                ranks[leader2]++;
            }
            components--;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int nodes = FastReader.nextInt();
            List<Edge> edges = new ArrayList<>();

            for (int i = 0; i < nodes - 1; i++) {
                int nodeID1 = FastReader.nextInt() - 1;
                int nodeID2 = FastReader.nextInt() - 1;
                int weight = FastReader.nextInt();
                edges.add(new Edge(nodeID1, nodeID2, weight));
            }

            long minimumWeight = computeMinimumWeight(edges, nodes);
            outputWriter.printLine(minimumWeight);
        }
        outputWriter.flush();
    }

    private static long computeMinimumWeight(List<Edge> edges, int nodes) {
        long minimumWeight = 0;

        Collections.sort(edges);
        UnionFind unionFind = new UnionFind(nodes);

        for (Edge edge : edges) {
            if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                int leader1 = unionFind.find(edge.vertex1);
                int leader2 = unionFind.find(edge.vertex2);

                int nextLowestCost = edge.cost + 1;
                long totalSize = unionFind.sizes[leader1] * unionFind.sizes[leader2] - 1;
                minimumWeight += edge.cost;
                minimumWeight += totalSize * nextLowestCost;

                unionFind.union(edge.vertex1, edge.vertex2);
            }

            if (unionFind.components == 1) {
                break;
            }
        }
        return minimumWeight;
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
