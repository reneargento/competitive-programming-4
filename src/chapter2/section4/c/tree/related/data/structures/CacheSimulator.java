package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/07/21.
 */
// Based on the discussion in https://apps.topcoder.com/forums/?module=Thread&threadID=607602&start=0
// and https://github.com/morris821028/UVa/blob/master/volume114/11423%20-%20Cache%20Simulator.cpp
public class CacheSimulator {
    private static final int MAXIMUM_MEMORY_REFERENCE = 10000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int caches = FastReader.nextInt();

        int[] cacheSizes = new int[caches];
        int[] cacheMisses = new int[caches];
        int[] lastTimeAccess = new int[1 << 24];
        FenwickTreeRangeSum fenwickTree = new FenwickTreeRangeSum(MAXIMUM_MEMORY_REFERENCE);
        int time = 0;

        for (int i = 0; i < caches; i++) {
            cacheSizes[i] = FastReader.nextInt();
        }

        String line = FastReader.getLine();
        while (!line.equals("END")) {
            String[] values = line.split(" ");
            if (values[0].equals("RANGE")) {
                int startIndex = Integer.parseInt(values[1]) + 1;
                int constant = Integer.parseInt(values[2]);
                int endMultiplier = Integer.parseInt(values[3]);

                for (int i = 0; i < endMultiplier; i++) {
                    int address = startIndex + constant * i;
                    processCommand(address, fenwickTree, cacheSizes, cacheMisses, lastTimeAccess, time);
                    time++;
                }
            } else if (values[0].equals("ADDR")) {
                int address = Integer.parseInt(values[1]) + 1;
                processCommand(address, fenwickTree, cacheSizes, cacheMisses, lastTimeAccess, time);
                time++;
            } else {
                for (int i = 0; i < cacheMisses.length; i++) {
                    outputWriter.print(cacheMisses[i]);

                    if (i != cacheMisses.length - 1) {
                        outputWriter.print(" ");
                    }
                }
                outputWriter.printLine();
                cacheMisses = new int[caches];
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void processCommand(int address, FenwickTreeRangeSum fenwickTree, int[] cacheSizes,
                                       int[] cacheMisses, int[] lastTimeAccess, int time) {
        int lastAccessTime = lastTimeAccess[address];
        if (lastAccessTime == 0) {
            for (int i = 0; i < cacheMisses.length; i++) {
                cacheMisses[i]++;
            }
        } else {
            long uniqueElements = fenwickTree.rangeSumQuery(lastAccessTime, time);
            for (int i = 0; i < cacheSizes.length; i++) {
                if (uniqueElements > cacheSizes[i]) {
                    cacheMisses[i]++;
                } else {
                    break;
                }
            }
            fenwickTree.update(lastAccessTime, -1);
        }
        lastTimeAccess[address] = time + 1;
        fenwickTree.update(lastTimeAccess[address], 1);
    }

    private static class FenwickTreeRangeSum {
        private long[] fenwickTree;

        // Create empty fenwick tree
        FenwickTreeRangeSum(int size) {
            fenwickTree = new long[size + 1];
        }

        private void build(long[] values) {
            int size = values.length - 1; // values[0] should always be 0
            fenwickTree = new long[size + 1];
            for (int i = 1; i <= size; i++) {
                fenwickTree[i] += values[i];

                if (i + lsOne(i) <= size) { // i has parent
                    fenwickTree[i + lsOne(i)] += fenwickTree[i]; // Add to that parent
                }
            }
        }

        // Returns sum from 1 to index
        public long rangeSumQuery(int index) {
            long sum = 0;
            while (index > 0) {
                sum += fenwickTree[index];
                index -= lsOne(index);
            }
            return sum;
        }

        public long rangeSumQuery(int startIndex, int endIndex) {
            return rangeSumQuery(endIndex) - rangeSumQuery(startIndex - 1);
        }

        // Updates the value of element on index by value (can be positive/increment or negative/decrement)
        public void update(int index, long value) {
            while (index < fenwickTree.length) {
                fenwickTree[index] += value;
                index += lsOne(index);
            }
        }

        private int lsOne(int value) {
            return value & (-value);
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
