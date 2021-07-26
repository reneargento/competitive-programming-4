package chapter2.section4.b.union.find.disjoint.sets;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/07/21.
 */
public class CorporativeNetwork {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int enterprises = FastReader.nextInt();
            String commandType = FastReader.next();
            UnionFind unionFind = new UnionFind(enterprises);

            while (!commandType.equals("O")) {
                if (commandType.equals("E")) {
                    int enterprise = FastReader.nextInt() - 1;
                    long pathLength = unionFind.length(enterprise);
                    outputWriter.printLine(pathLength);
                } else {
                    int serviceCenter = FastReader.nextInt() - 1;
                    int enterprise = FastReader.nextInt() - 1;
                    unionFind.union(serviceCenter, enterprise);
                }
                commandType = FastReader.next();
            }
        }
        outputWriter.flush();
    }

    private static class UnionFind {
        private final int[] leaders;
        private final long[] lengths;
        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            lengths = new long[size];
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

            int newLeader = find(leaders[site]);
            lengths[site] += lengths[leaders[site]];
            leaders[site] = newLeader;
            return leaders[site];
        }

        public void union(int site1, int site2) {
            leaders[site1] = site2;
            lengths[site1] = Math.abs(site1 - site2) % 1000;
            components--;
        }

        public long length(int set) {
            find(set);
            return lengths[set];
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
