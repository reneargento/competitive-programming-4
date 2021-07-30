package chapter2.section4.b.union.find.disjoint.sets;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/07/21.
 */
public class MoneyMatters {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int friends = FastReader.nextInt();
            int friendships = FastReader.nextInt();

            int[] money = new int[friends];
            for (int i = 0; i < money.length; i++) {
                money[i] = FastReader.nextInt();
            }

            UnionFind unionFind = new UnionFind(friends, money);
            for (int i = 0; i < friendships; i++) {
                unionFind.union(FastReader.nextInt(), FastReader.nextInt());
            }
            outputWriter.printLine(isPossible(unionFind, friends) ? "POSSIBLE" : "IMPOSSIBLE");
        }
        outputWriter.flush();
    }

    private static boolean isPossible(UnionFind unionFind, int friends) {
        for (int i = 0; i < friends; i++) {
            if (unionFind.money(i) != 0) {
                return false;
            }
        }
        return true;
    }

    private static class UnionFind {
        private final int[] leaders;
        private final int[] ranks;
        private final int[] money;
        private int components;

        public UnionFind(int size, int[] money) {
            leaders = new int[size];
            ranks = new int[size];
            this.money = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                ranks[i] = 0;
                this.money[i] = money[i];
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
                money[leader2] += money[leader1];
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
                money[leader1] += money[leader2];
            } else {
                leaders[leader1] = leader2;
                money[leader2] += money[leader1];
                ranks[leader2]++;
            }
            components--;
        }

        public int money(int set) {
            int leader = find(set);
            return money[leader];
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
