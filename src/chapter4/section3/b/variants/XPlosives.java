package chapter4.section3.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 14/09/23.
 */
public class XPlosives {

    private static class BindingPair {
        int compoundID1;
        int compoundID2;

        public BindingPair(int compoundID1, int compoundID2) {
            this.compoundID1 = compoundID1;
            this.compoundID2 = compoundID2;
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
            int maxCompoundID = 0;
            List<BindingPair> bindingPairs = new ArrayList<>();

            while (!line.equals("-1")) {
                String[] data = line.split(" ");
                int compoundID1 = Integer.parseInt(data[0]);
                int compoundID2 = Integer.parseInt(data[1]);
                bindingPairs.add(new BindingPair(compoundID1, compoundID2));

                maxCompoundID = Math.max(maxCompoundID, compoundID1);
                maxCompoundID = Math.max(maxCompoundID, compoundID2);
                line = FastReader.getLine();
            }

            int refusals = countRefusals(bindingPairs, maxCompoundID);
            outputWriter.printLine(refusals);

            line = FastReader.getLine();
            if (line != null && line.isEmpty()) {
                line = FastReader.getLine();
            }
        }

        outputWriter.flush();
    }

    private static int countRefusals(List<BindingPair> bindingPairs, int maxCompoundID) {
        int refusals = 0;

        UnionFind unionFind = new UnionFind(maxCompoundID + 1);
        for (BindingPair pair : bindingPairs) {
            if (unionFind.connected(pair.compoundID1, pair.compoundID2)) {
                refusals++;
            } else {
                unionFind.union(pair.compoundID1, pair.compoundID2);
            }
        }
        return refusals;
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

        public void flush() {
            writer.flush();
        }
    }
}
