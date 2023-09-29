package chapter4.section3.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 19/09/23.
 */
public class FiretrucksAreRed {

    private static class Edge {
        int vertex1;
        int vertex2;
        int number;

        Edge(int vertex1, int vertex2, int number) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.number = number;
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

        int individuals = FastReader.nextInt();

        Map<Integer, Integer> numberToIndividualMap = new HashMap<>();
        UnionFind unionFind = new UnionFind(individuals);
        List<Edge> proof = new ArrayList<>();

        for (int individualID = 0; individualID < individuals; individualID++) {
            int numbers = FastReader.nextInt();
            for (int n = 0; n < numbers; n++) {
                int number = FastReader.nextInt();

                if (numberToIndividualMap.containsKey(number)) {
                    int otherIndividualID = numberToIndividualMap.get(number);

                    if (!unionFind.connected(individualID, otherIndividualID)) {
                        unionFind.union(individualID, otherIndividualID);
                        proof.add(new Edge(individualID, otherIndividualID, number));
                    }
                } else {
                    numberToIndividualMap.put(number, individualID);
                }
            }
        }

        if (unionFind.components == 1) {
            for (Edge edge : proof) {
                outputWriter.printLine(String.format("%d %d %d", edge.vertex1 + 1, edge.vertex2 + 1, edge.number));
            }
        } else {
            outputWriter.printLine("impossible");
        }
        outputWriter.flush();
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
