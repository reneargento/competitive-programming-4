package chapter4.session6.e.bipartite.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/08/24.
 */
public class FlippingCards {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int cards = FastReader.nextInt();
            UnionFind unionFind = new UnionFind(cards * 2);

            for (int i = 0; i < cards; i++) {
                int cardId1 = FastReader.nextInt() - 1;
                int cardId2 = FastReader.nextInt() - 1;
                unionFind.union(cardId1, cardId2);
            }
            outputWriter.printLine(isPossible(unionFind, cards) ? "possible" : "impossible");
        }
        outputWriter.flush();
    }

    private static boolean isPossible(UnionFind unionFind, int cards) {
        for (int cardId = 0; cardId < cards * 2; cardId++) {
            int leaderId = unionFind.find(cardId);
            if (unionFind.cycles[leaderId] > 1) {
                return false;
            }
        }
        return true;
    }

    private static class UnionFind {
        private final int[] leaders;
        private final int[] ranks;
        private final int[] sizes;
        private int components;
        private final int[] cycles;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            sizes = new int[size];
            cycles = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                sizes[i] = 1;
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        // O(α(n)), where α is the inverse Ackermann function.
        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }
            return leaders[site] = find(leaders[site]);
        }

        // O(α(n)), where α is the inverse Ackermann function.
        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                cycles[leader1]++;
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
                cycles[leader2] += cycles[leader1];
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
                sizes[leader1] += sizes[leader2];
                cycles[leader1] += cycles[leader2];
            } else {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
                cycles[leader2] += cycles[leader1];
                ranks[leader2]++;
            }
            components--;
        }

        public int size(int set) {
            int leader = find(set);
            return sizes[leader];
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
