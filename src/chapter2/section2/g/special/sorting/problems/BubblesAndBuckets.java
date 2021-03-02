package chapter2.section2.g.special.sorting.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/02/21.
 */
public class BubblesAndBuckets {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int numbers = FastReader.nextInt();

        while (numbers != 0) {
            int[] array = new int[numbers];

            for (int i = 0; i < array.length; i++) {
                array[i] = FastReader.nextInt();
            }

            long inversions = countInversions(array);
            if (inversions % 2 == 1) {
                System.out.println("Marcelo");
            } else {
                System.out.println("Carlos");
            }
            numbers = FastReader.nextInt();
        }
    }

    private static long countInversions(int[] array) {
        long inversions = 0;

        Map<Integer, Integer> positionsMap = createPositionsMap(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = positionsMap.get(array[i]);
        }

        FenwickTreeRangeSum fenwickTree = new FenwickTreeRangeSum(array.length);

        for (int index = array.length - 1; index >= 0; index--) {
            inversions += fenwickTree.rangeSumQuery(array[index] - 1);
            fenwickTree.update(array[index], 1);
        }
        return inversions;
    }

    private static Map<Integer, Integer> createPositionsMap(int[] array) {
        int[] arrayCopy = array.clone();
        Arrays.sort(arrayCopy);

        Map<Integer, Integer> positionsMap = new HashMap<>();
        for (int i = 0; i < arrayCopy.length; i++) {
            positionsMap.put(arrayCopy[i], i + 1);
        }
        return positionsMap;
    }

    private static class FenwickTreeRangeSum {
        private long[] fenwickTree;

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
}
