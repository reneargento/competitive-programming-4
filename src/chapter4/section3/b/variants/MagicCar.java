package chapter4.section3.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/09/23.
 */
public class MagicCar {

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

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int junctions = Integer.parseInt(data[0]);
            int roads = Integer.parseInt(data[1]);

            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < roads; i++) {
                int startJunction = FastReader.nextInt() - 1;
                int endJunction = FastReader.nextInt() - 1;
                int speed = FastReader.nextInt();

                Edge edge = new Edge(startJunction, endJunction, speed);
                edges.add(edge);
            }
            Collections.sort(edges);

            int startEnergy = FastReader.nextInt();
            int stopEnergy = FastReader.nextInt();
            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int sourceJunction = FastReader.nextInt() - 1;
                int destinationJunction = FastReader.nextInt() - 1;

                int minimumEnergyUsed = computeMinimumEnergyUsed(junctions, edges, sourceJunction, destinationJunction);
                minimumEnergyUsed += startEnergy + stopEnergy;
                outputWriter.printLine(minimumEnergyUsed);
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumEnergyUsed(int junctions, List<Edge> edges, int sourceJunction,
                                                int destinationJunction) {
        int energyUsed = Integer.MAX_VALUE;

        for (int edgeID1 = 0; edgeID1 < edges.size(); edgeID1++) {
            UnionFind unionFind = new UnionFind(junctions);

            for (int edgeID2 = edgeID1; edgeID2 < edges.size(); edgeID2++) {
                int junctionID1 = edges.get(edgeID2).vertex1;
                int junctionID2 = edges.get(edgeID2).vertex2;

                if (!unionFind.connected(junctionID1, junctionID2)) {
                    unionFind.union(junctionID1, junctionID2);
                }

                if (unionFind.connected(sourceJunction, destinationJunction)) {
                    int energyNeeded = edges.get(edgeID2).cost - edges.get(edgeID1).cost;
                    energyUsed = Math.min(energyUsed, energyNeeded);
                    break;
                }
            }
        }
        return energyUsed;
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
