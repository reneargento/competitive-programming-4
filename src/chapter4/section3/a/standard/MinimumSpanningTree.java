package chapter4.section3.a.standard;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/07/23.
 */
public class MinimumSpanningTree {

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

    private static class Result {
        List<Edge> edges;
        int cost;

        public Result(List<Edge> edges, int cost) {
            this.edges = edges;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int nodes = FastReader.nextInt();
        int edgesNumber = FastReader.nextInt();

        while (nodes != 0 || edgesNumber != 0) {
            Edge[] edges = new Edge[edgesNumber];
            for (int i = 0; i < edgesNumber; i++) {
                int vertex1 = FastReader.nextInt();
                int vertex2 = FastReader.nextInt();
                int weight = FastReader.nextInt();

                int minimumVertex = Math.min(vertex1, vertex2);
                int maximumVertex = Math.max(vertex1, vertex2);
                edges[i] = new Edge(minimumVertex, maximumVertex, weight);
            }

            Result result = computeMinimumSpanningTree(edges, nodes);
            if (result == null) {
                outputWriter.printLine("Impossible");
            } else {
                outputWriter.printLine(result.cost);
                for (Edge edge : result.edges) {
                    outputWriter.printLine(edge.vertex1 + " " + edge.vertex2);
                }
            }
            nodes = FastReader.nextInt();
            edgesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeMinimumSpanningTree(Edge[] edges, int nodes) {
        Result result = KruskalMinimumSpanningTree.getMinimumSpanningTree(edges, nodes);
        if (result == null) {
            return null;
        }

        result.edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                if (edge1.vertex1 != edge2.vertex1) {
                    return Integer.compare(edge1.vertex1, edge2.vertex1);
                }
                return Integer.compare(edge1.vertex2, edge2.vertex2);
            }
        });
        return result;
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

        private static Result getMinimumSpanningTree(Edge[] edges, int totalVertices) {
            List<Edge> edgesInSpanningTree = new ArrayList<>();
            int cost = 0;

            Arrays.sort(edges);
            UnionFind unionFind = new UnionFind(totalVertices);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    edgesInSpanningTree.add(edge);
                    cost += edge.cost;
                }

                if (unionFind.components == 1) {
                    break;
                }
            }

            if (unionFind.components != 1) {
                return null;
            }
            return new Result(edgesInSpanningTree, cost);
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
