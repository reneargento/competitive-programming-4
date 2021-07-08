package chapter2.section4.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/07/21.
 */
public class JustForSidekicksFenwickTree {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int[] gems = new int[FastReader.nextInt() + 1];
        int queries = FastReader.nextInt();
        long[] gemValues = new long[7];

        for (int i = 1; i < gemValues.length; i++) {
            gemValues[i] = FastReader.nextInt();
        }

        String gemString = FastReader.next();
        for (int i = 1; i < gems.length; i++) {
            gems[i] = Character.getNumericValue(gemString.charAt(i - 1));
        }
        answerQueries(gems, gemValues, queries);
    }

    private static void answerQueries(int[] gems, long[] gemValues, int queries) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);
        FenwickTreeRangeSum fenwickTree = new FenwickTreeRangeSum(gems);

        for (int q = 0; q < queries; q++) {
            int queryType = FastReader.nextInt();
            int parameter1 = FastReader.nextInt();
            int parameter2 = FastReader.nextInt();

            if (queryType == 1) {
                int oldGemType = (int) fenwickTree.rangeSumQueryGemType(parameter1, parameter1);
                fenwickTree.update(parameter1, oldGemType, parameter2);
            } else if (queryType == 2) {
                gemValues[parameter1] = parameter2;
            } else {
                long totalValue = fenwickTree.rangeSumQuery(parameter1, parameter2, gemValues);
                outputWriter.printLine(totalValue);
            }
        }
        outputWriter.flush();
    }

    private static class FenwickTreeRangeSum {
        private int[][] fenwickTree;

        // Create fenwick tree with values
        FenwickTreeRangeSum(int[] values) {
            build(values);
        }

        private void build(int[] values) {
            int size = values.length - 1; // values[0] should always be 0
            fenwickTree = new int[size + 1][7];
            for (int i = 1; i <= size; i++) {
                fenwickTree[i][values[i]]++;

                if (i + lsOne(i) <= size) { // i has parent
                    for (int g = 1; g < fenwickTree[i].length; g++) {
                        int numberOfGems = fenwickTree[i][g];
                        fenwickTree[i + lsOne(i)][g] += numberOfGems; // Add to that parent
                    }
                }
            }
        }

        // Returns sum from 1 to index
        public long rangeSumQuery(int index, long[] gemValues) {
            long sum = 0;
            while (index > 0) {
                for (int g = 1; g < fenwickTree[index].length; g++) {
                    sum += fenwickTree[index][g] * gemValues[g];
                }
                index -= lsOne(index);
            }
            return sum;
        }

        public long rangeSumQuery(int startIndex, int endIndex, long[] gemValues) {
            return rangeSumQuery(endIndex, gemValues) - rangeSumQuery(startIndex - 1, gemValues);
        }

        public long rangeSumQueryGemType(int index) {
            long sum = 0;
            while (index > 0) {
                for (int g = 1; g < fenwickTree[index].length; g++) {
                    sum += g * fenwickTree[index][g];
                }
                index -= lsOne(index);
            }
            return sum;
        }

        public long rangeSumQueryGemType(int startIndex, int endIndex) {
            return rangeSumQueryGemType(endIndex) - rangeSumQueryGemType(startIndex - 1);
        }

        public void update(int index, int oldGemType, int newGemType) {
            while (index < fenwickTree.length) {
                fenwickTree[index][oldGemType]--;
                fenwickTree[index][newGemType]++;
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
