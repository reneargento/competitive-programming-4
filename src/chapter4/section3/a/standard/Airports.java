package chapter4.section3.a.standard;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/08/23.
 */
public class Airports {

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
        int minimumCost;
        int airportsBuilt;

        public Result(int minimumCost, int airportsBuilt) {
            this.minimumCost = minimumCost;
            this.airportsBuilt = airportsBuilt;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.readInt();

        for (int t = 1; t <= tests; t++) {
            int locations = inputReader.readInt();
            int roads = inputReader.readInt();
            int airportCost = inputReader.readInt();

            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < roads; i++) {
                edges.add(new Edge(inputReader.readInt() - 1, inputReader.readInt() - 1, inputReader.readInt()));
            }

            Result result = computeMinimumCost(locations, edges, airportCost);
            outputWriter.printLine(String.format("Case #%d: %d %d", t, result.minimumCost, result.airportsBuilt));
        }
        outputWriter.flush();
    }

    private static Result computeMinimumCost(int locations, List<Edge> edges, int airportCost) {
        return KruskalMinimumSpanningTree.getMinimumSpanningTreeCost(edges, locations, airportCost);
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

        private static Result getMinimumSpanningTreeCost(List<Edge> edges, int totalVertices, int airportCost) {
            int minimumCost = 0;

            Collections.sort(edges);
            KruskalMinimumSpanningTree.UnionFind unionFind = new KruskalMinimumSpanningTree.UnionFind(totalVertices);

            for (Edge edge : edges) {
                if (edge.cost >= airportCost) {
                    continue;
                }
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    minimumCost += edge.cost;
                }

                if (unionFind.components == 1) {
                    break;
                }
            }

            int airportsNumber = unionFind.components;
            minimumCost += airportsNumber * airportCost;
            return new Result(minimumCost, airportsNumber);
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }
        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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
