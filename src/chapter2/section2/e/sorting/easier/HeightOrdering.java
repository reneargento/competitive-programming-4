package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/02/21.
 */
public class HeightOrdering {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            int[] heights = new int[20];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = FastReader.nextInt();
            }

            int inversions = countInversions(heights);
            System.out.printf("%d %d\n", dataSetNumber, inversions);
        }
    }

    private static int countInversions(int[] array) {
        int inversions = 0;

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
        private int[] fenwickTree;

        FenwickTreeRangeSum(int size) {
            fenwickTree = new int[size + 1];
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
