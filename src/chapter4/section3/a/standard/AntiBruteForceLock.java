package chapter4.section3.a.standard;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/08/23.
 */
public class AntiBruteForceLock {

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
            String[] keys = new String[FastReader.nextInt()];
            for (int k = 0; k < keys.length; k++) {
                keys[k] = FastReader.next();
            }
            int minimumRolls = computeMinimumRolls(keys);
            outputWriter.printLine(minimumRolls);
        }
        outputWriter.flush();
    }

    private static int computeMinimumRolls(String[] keys) {
        List<Edge> edges = new ArrayList<>();
        for (int keyID1 = 0; keyID1 < keys.length; keyID1++) {
            for (int keyID2 = keyID1 + 1; keyID2 < keys.length; keyID2++) {
                int cost = computeKeyChangeCost(keys[keyID1], keys[keyID2]);
                edges.add(new Edge(keyID1, keyID2, cost));
            }
        }
        int firstKeyCost = computeFirstKeyCost(keys);
        return firstKeyCost + KruskalMinimumSpanningTree.getMinimumSpanningTreeCost(edges, keys.length);
    }

    private static int computeFirstKeyCost(String[] keys) {
        int firstKeyCost = Integer.MAX_VALUE;
        String startState = "0000";

        for (String key : keys) {
            int cost = computeKeyChangeCost(startState, key);
            firstKeyCost = Math.min(firstKeyCost, cost);
        }
        return firstKeyCost;
    }

    private static int computeKeyChangeCost(String key1, String key2) {
        int keyChangeCost = 0;

        for (int digit = 0; digit < 4; digit++) {
            int digitValue1 = Character.getNumericValue(key1.charAt(digit));
            int digitValue2 = Character.getNumericValue(key2.charAt(digit));
            keyChangeCost += computeRollCost(digitValue1, digitValue2);
        }
        return keyChangeCost;
    }

    private static int computeRollCost(int currentValue, int nextValue) {
        if (nextValue == currentValue) {
            return 0;
        }
        int valueRollingUp;
        int valueRollingDown;

        if (nextValue > currentValue) {
            valueRollingUp = nextValue - currentValue;
            valueRollingDown = currentValue + (10 - nextValue);
        } else {
            valueRollingUp = (10 - currentValue) + nextValue;
            valueRollingDown = currentValue - nextValue;
        }
        return Math.min(valueRollingUp, valueRollingDown);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
