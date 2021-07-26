package chapter2.section4.b.union.find.disjoint.sets;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/07/21.
 */
public class Ladice {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int items = FastReader.nextInt();
        int drawers = FastReader.nextInt();

        boolean[] taken = new boolean[drawers + 1];
        UnionFind unionFind = new UnionFind(drawers + 1);

        for (int item = 1; item <= items; item++) {
            int drawerA = FastReader.nextInt();
            int drawerB = FastReader.nextInt();
            boolean stored = true;

            if (!taken[drawerA]) {
                taken[drawerA] = true;
                unionFind.union(drawerA, drawerB);
            } else if (!taken[drawerB]) {
                taken[drawerB] = true;
                unionFind.union(drawerB, drawerA);
            } else if (!taken[unionFind.find(drawerA)]) {
                taken[unionFind.find(drawerA)] = true;
                unionFind.union(drawerA, drawerB);
            } else if (!taken[unionFind.find(drawerB)]) {
                taken[unionFind.find(drawerB)] = true;
                unionFind.union(drawerB, drawerA);
            } else {
                stored = false;
            }
            outputWriter.printLine(stored ? "LADICA" : "SMECE");
        }
        outputWriter.flush();
    }

    private static class UnionFind {
        private final int[] leaders;
        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
            }
        }

        public int count() {
            return components;
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
            leaders[leader1] = leader2;
            components--;
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
