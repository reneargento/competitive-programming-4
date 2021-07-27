package chapter2.section4.b.union.find.disjoint.sets;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/07/21.
 */
public class AlmostUnionFind {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int components = Integer.parseInt(data[0]);
            int commands = Integer.parseInt(data[1]);

            UnionFind unionFind = new UnionFind(components + 1);
            for (int i = 0; i < commands; i++) {
                int type = FastReader.nextInt();
                if (type == 3) {
                    int site = FastReader.nextInt();
                    SiteData siteData = unionFind.getSiteData(site);
                    outputWriter.printLine(siteData.size + " " + siteData.sum);
                } else {
                    int site1 = FastReader.nextInt();
                    int site2 = FastReader.nextInt();

                    if (type == 1) {
                        unionFind.union(site1, site2);
                    } else {
                        unionFind.move(site1, site2);
                    }
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static class SiteData {
        int size;
        long sum;

        public SiteData(int size, long sum) {
            this.size = size;
            this.sum = sum;
        }
    }

    @SuppressWarnings("unchecked")
    private static class UnionFind {
        private final int[] leaders;
        private final int[] sizes;
        private int components;
        private final long[] sums;
        private final List<Integer>[] sitesPerGroup;

        public UnionFind(int size) {
            leaders = new int[size];
            sizes = new int[size];
            components = size;
            sums = new long[size];
            sitesPerGroup = new List[size];

            for(int i = 1; i < size; i++) {
                leaders[i]  = i;
                sizes[i] = 1;
                sums[i] = i;
                sitesPerGroup[i] = new ArrayList<>();
                sitesPerGroup[i].add(i);
            }
        }

        public int count() {
            return components;
        }

        public int find(int site) {
            return leaders[site];
        }

        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (sizes[leader1] <= sizes[leader2]) {
                uniteSites(leader1, leader2);
            } else {
                uniteSites(leader2, leader1);
            }
            components--;
        }

        private void uniteSites(int leader1, int leader2) {
            for (int site : sitesPerGroup[leader1]) {
                if (leaders[site] == leader1) {
                    sitesPerGroup[leader2].add(site);
                    leaders[site] = leader2;
                }
            }

            sizes[leader2] += sizes[leader1];
            sums[leader2] += sums[leader1];
        }

        public void move(int site, int newSite) {
            int leader1 = find(site);
            int leader2 = find(newSite);

            if (leader1 == leader2) {
                return;
            }

            sizes[leader1]--;
            sums[leader1] -= site;
            sizes[leader2]++;
            sums[leader2] += site;

            leaders[site] = leader2;
            sitesPerGroup[leader2].add(site);
        }

        public SiteData getSiteData(int site) {
            int leader = find(site);
            return new SiteData(sizes[leader], sums[leader]);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
