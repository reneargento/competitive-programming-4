package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/07/21.
 */
public class FenwickTree {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int arraySize = FastReader.nextInt();
        int operations = FastReader.nextInt();
        FenwickTreeRangeSum fenwickTree = new FenwickTreeRangeSum(arraySize);

        for (int i = 0; i < operations; i++) {
            String operation = FastReader.next();
            int index = FastReader.nextInt();

            if (operation.equals("+")) {
                int value = FastReader.nextInt();
                fenwickTree.update(index + 1, value);
            } else {
                if (index == 0) {
                    outputWriter.printLine("0");
                } else {
                    long sum = fenwickTree.rangeSumQuery(index);
                    outputWriter.printLine(sum);
                }
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
