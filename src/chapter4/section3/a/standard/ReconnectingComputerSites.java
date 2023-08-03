package chapter4.section3.a.standard;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/07/23.
 */
public class ReconnectingComputerSites {

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

        int caseId = 1;
        String line = FastReader.getLine();

        while (line != null) {
            if (line.isEmpty()) {
                line = FastReader.getLine();
            }
            int sitesNumber = Integer.parseInt(line);
            int originalCost = 0;

            for (int i = 0; i < sitesNumber - 1; i++) {
                FastReader.nextInt();
                FastReader.nextInt();
                originalCost += FastReader.nextInt();
            }

            int additionalLinesNumber = FastReader.nextInt();
            List<Edge> newLines = new ArrayList<>();
            for (int i = 0; i < additionalLinesNumber; i++) {
                newLines.add(new Edge(FastReader.nextInt() - 1, FastReader.nextInt() - 1, FastReader.nextInt()));
            }

            int originalLinesNumber = FastReader.nextInt();
            List<Edge> originalLines = new ArrayList<>();
            for (int i = 0; i < originalLinesNumber; i++) {
                originalLines.add(new Edge(FastReader.nextInt() - 1, FastReader.nextInt() - 1, FastReader.nextInt()));
            }

            int newCost = computeNewCost(sitesNumber, newLines, originalLines);

            if (caseId > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(originalCost);
            outputWriter.printLine(newCost);

            line = FastReader.getLine();
            caseId++;
        }
        outputWriter.flush();
    }

    private static int computeNewCost(int sitesNumber, List<Edge> newLines, List<Edge> originalLines) {
        newLines.addAll(originalLines);
        return KruskalMinimumSpanningTree.getMinimumSpanningTreeCost(newLines, sitesNumber);
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
