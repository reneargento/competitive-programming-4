package chapter4.section3.a.standard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/08/23.
 */
public class JurassicJigsaw {

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;
        int unlikeliness;

        Edge(int vertex1, int vertex2, int unlikeliness) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.unlikeliness = unlikeliness;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(unlikeliness, other.unlikeliness);
        }
    }

    private static class Result {
        List<Edge> edges;
        int minimalUnlikeliness;

        public Result(List<Edge> edges, int minimalUnlikeliness) {
            this.edges = edges;
            this.minimalUnlikeliness = minimalUnlikeliness;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String[] dnaSamples = new String[FastReader.nextInt()];
        FastReader.nextInt();

        for (int i = 0; i < dnaSamples.length; i++) {
            dnaSamples[i] = FastReader.next();
        }

        Result result = analyzeSamples(dnaSamples);
        outputWriter.printLine(result.minimalUnlikeliness);
        for (Edge edge : result.edges) {
            outputWriter.printLine(String.format("%d %d", edge.vertex1, edge.vertex2));
        }
        outputWriter.flush();
    }

    private static Result analyzeSamples(String[] dnaSamples) {
        List<Edge> edges = computeEdges(dnaSamples);
        return KruskalMinimumSpanningTree.computeMinimumSpanningTree(dnaSamples.length, edges);
    }

    private static List<Edge> computeEdges(String[] dnaSamples) {
        List<Edge> edges = new ArrayList<>();
        for (int sampleID1 = 0; sampleID1 < dnaSamples.length; sampleID1++) {
            for (int sampleID2 = sampleID1 + 1; sampleID2 < dnaSamples.length; sampleID2++) {
                String sample1 = dnaSamples[sampleID1];
                String sample2 = dnaSamples[sampleID2];

                int unlikeliness = 0;
                for (int i = 0; i < sample1.length(); i++) {
                    if (sample1.charAt(i) != sample2.charAt(i)) {
                        unlikeliness++;
                    }
                }
                edges.add(new Edge(sampleID1, sampleID2, unlikeliness));
            }
        }
        return edges;
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

        private static Result computeMinimumSpanningTree(int numberOfSamples, List<Edge> edges) {
            List<Edge> selectedEdges = new ArrayList<>();
            int minimumUnlikeliness = 0;

            Collections.sort(edges);
            KruskalMinimumSpanningTree.UnionFind unionFind = new KruskalMinimumSpanningTree.UnionFind(numberOfSamples);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    minimumUnlikeliness += edge.unlikeliness;
                    selectedEdges.add(edge);
                }

                if (unionFind.components == 1) {
                    break;
                }
            }
            return new Result(selectedEdges, minimumUnlikeliness);
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
