package chapter2.section4.b.union.find.disjoint.sets;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 30/07/21.
 */
public class Chatter {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int people = Integer.parseInt(data[0]);
            int r = Integer.parseInt(data[1]);
            int a = Integer.parseInt(data[2]);
            int b = Integer.parseInt(data[3]);
            int c = Integer.parseInt(data[4]);

            UnionFind unionFind = new UnionFind(people);

            for (int i = 0; i < people; i++) {
                while (true) {
                    r = (r * a + b) % c;
                    int person1 = r % people;
                    r = (r * a + b) % c;
                    int person2 = r % people;

                    if (person1 != person2) {
                        unionFind.union(person1, person2);
                        break;
                    }
                }
            }
            printGroupSizes(unionFind, people, outputWriter);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printGroupSizes(UnionFind unionFind, int people, OutputWriter outputWriter) {
        List<Integer> groupSizes = new ArrayList<>();
        for (int p = 0; p < people; p++) {
            if (p == unionFind.find(p)) {
                groupSizes.add(unionFind.size(p));
            }
        }
        groupSizes.sort(Collections.reverseOrder());

        outputWriter.print(unionFind.components + " ");
        for (int i = 0; i < groupSizes.size(); i++) {
            int count = 1;
            int size = groupSizes.get(i);
            while (i < groupSizes.size() - 1 && size == groupSizes.get(i + 1)) {
                count++;
                i++;
            }

            outputWriter.print(size);
            if (count > 1) {
                outputWriter.print("x" + count);
            }

            if (i != groupSizes.size() - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.printLine();
    }

    private static class UnionFind {
        private final int[] leaders;
        private final int[] ranks;
        private final int[] sizes;
        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            sizes = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                ranks[i] = 0;
                sizes[i] = 1;
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
                sizes[leader2] += sizes[leader1];
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
                sizes[leader1] += sizes[leader2];
            } else {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
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

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
