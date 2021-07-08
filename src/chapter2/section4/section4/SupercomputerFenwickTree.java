package chapter2.section4.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/07/21.
 */
public class SupercomputerFenwickTree {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int bitsInMemory = FastReader.nextInt();
        int queries = FastReader.nextInt();

        FenwickTreeRangeSum fenwickTree = new FenwickTreeRangeSum(bitsInMemory + 1);

        for (int q = 0; q < queries; q++) {
            String queryType = FastReader.next();
            if (queryType.equals("F")) {
                int bitIndex = FastReader.nextInt();
                long bitValue = fenwickTree.rangeSumQuery(bitIndex, bitIndex);
                int newBitValue = bitValue == 0 ? 1 : -1;
                fenwickTree.update(bitIndex, newBitValue);
            } else {
                int left = FastReader.nextInt();
                int right = FastReader.nextInt();
                long bitsSet = fenwickTree.rangeSumQuery(left, right);
                outputWriter.printLine(bitsSet);
            }
        }
        outputWriter.flush();
    }

    private static class FenwickTreeRangeSum {
        private final long[] fenwickTree;

        // Create empty fenwick tree
        FenwickTreeRangeSum(int size) {
            fenwickTree = new long[size + 1];
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
