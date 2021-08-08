package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/08/21.
 */
public class Turbo {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] array = new int[FastReader.nextInt()];

        for (int i = 0; i < array.length; i++) {
            array[i] = FastReader.nextInt();
        }

        Map<Integer, Integer> orderMap = buildOrderMap(array.length);
        int maxIndexMovedLeft = (int) Math.ceil(array.length / 2.0);
        Map<Integer, Long> inversionsMapLeft = countInversionsMovingLeft(array, orderMap, maxIndexMovedLeft);
        Map<Integer, Long> inversionsMapRight = countInversionsMovingRight(array, orderMap, maxIndexMovedLeft);

        int startIndex = 1;
        int endIndex = array.length;

        for (int i = 1; i <= array.length; i++) {
            long inversions;
            if (i % 2 == 1) {
                inversions = inversionsMapLeft.get(startIndex);
                startIndex++;
            } else {
                inversions = inversionsMapRight.get(endIndex);
                endIndex--;
            }
            outputWriter.printLine(inversions);
        }
        outputWriter.flush();
    }

    private static Map<Integer, Integer> buildOrderMap(int size) {
        Map<Integer, Integer> orderMap = new HashMap<>();
        int startIndex = 1;
        int endIndex = size;

        for (int i = 1; i <= size; i++) {
            int element;
            if (i % 2 == 1) {
                element = startIndex;
                startIndex++;
            } else {
                element = endIndex;
                endIndex--;
            }
            orderMap.put(element, i);
        }
        return orderMap;
    }

    private static Map<Integer, Long> countInversionsMovingLeft(int[] array, Map<Integer, Integer> orderMap,
                                                                int maxIndexMovedLeft) {
        FenwickTreeRangeSum fenwickTreeLeft = new FenwickTreeRangeSum(array.length + 1);
        Map<Integer, Long> inversionsMap = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            int position = orderMap.get(array[i]);
            long inversions = fenwickTreeLeft.rangeSumQuery(position, array.length);
            if (array[i] <= maxIndexMovedLeft) {
                inversionsMap.put(array[i], inversions);
            }
            fenwickTreeLeft.update(position, 1);
        }
        return inversionsMap;
    }

    private static Map<Integer, Long> countInversionsMovingRight(int[] array, Map<Integer, Integer> orderMap,
                                                                 int maxIndexMovedLeft) {
        FenwickTreeRangeSum fenwickTreeRight = new FenwickTreeRangeSum(array.length + 1);
        Map<Integer, Long> inversionsMap = new HashMap<>();

        for (int i = array.length - 1; i >= 0; i--) {
            int position = orderMap.get(array[i]);
            long inversions = fenwickTreeRight.rangeSumQuery(position, array.length);
            if (array[i] > maxIndexMovedLeft) {
                inversionsMap.put(array[i], inversions);
            }
            fenwickTreeRight.update(position, 1);
        }
        return inversionsMap;
    }

    private static class FenwickTreeRangeSum {
        private final long[] fenwickTree;

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

        int lsOne(int value) {
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
