package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/07/21.
 */
public class Potentiometers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int potmetersNumber = FastReader.nextInt();
        int caseNumber = 1;

        while (potmetersNumber != 0) {
            if (caseNumber > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Case %d:", caseNumber));

            long[] potmeters = new long[potmetersNumber + 1];
            for (int i = 1; i < potmeters.length; i++) {
                potmeters[i] = FastReader.nextInt();
            }
            processActions(potmeters, outputWriter);

            potmetersNumber = FastReader.nextInt();
            caseNumber++;
        }
        outputWriter.flush();
    }

    private static void processActions(long[] potmeters, OutputWriter outputWriter) throws IOException {
        FenwickTreeRangeSum fenwickTree = new FenwickTreeRangeSum(potmeters);

        String line = FastReader.getLine();
        while (!line.equals("END")) {
            String[] values = line.split(" ");
            int potmeter = Integer.parseInt(values[1]);
            int secondValue = Integer.parseInt(values[2]);

            if (values[0].equals("S")) {
                long currentResistance = fenwickTree.rangeSumQuery(potmeter, potmeter);
                long valueToAdd = secondValue - currentResistance;
                fenwickTree.update(potmeter, valueToAdd);
            } else {
                long totalResistance = fenwickTree.rangeSumQuery(potmeter, secondValue);
                outputWriter.printLine(totalResistance);
            }
            line = FastReader.getLine();
        }
    }

    private static class FenwickTreeRangeSum {
        private long[] fenwickTree;

        // Create fenwick tree with values
        FenwickTreeRangeSum(long[] values) {
            build(values);
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
