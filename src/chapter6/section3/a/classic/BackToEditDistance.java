package chapter6.section3.a.classic;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/07/2026.
 */
public class BackToEditDistance {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int permutationSizes = FastReader.nextInt();
            int[] permutation1 = readPermutation(permutationSizes);
            int[] permutation2 = readPermutation(permutationSizes);

            int operationsRequired = computeOperations(permutation1, permutation2);
            outputWriter.printLine(String.format("Case %d: %d", t, operationsRequired));
        }
        outputWriter.flush();
    }

    private static int computeOperations(int[] permutation1, int[] permutation2) {
        int lcsLength = computeLCSDistinctValues(permutation1, permutation2);
        return (permutation1.length - lcsLength) * 2;
    }

    private static int computeLCSDistinctValues(int[] permutation1, int[] permutation2) {
        Map<Integer, Integer> valueToIndexMap = computeValueToIndexMap(permutation2);
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < permutation1.length; i++) {
            int value = permutation1[i];
            Integer string2Index = valueToIndexMap.get(value);
            if (string2Index != null) {
                indexes.add(string2Index);
            }
        }

        Integer[] indexesArray = indexes.toArray(new Integer[0]);
        return longestIncreasingSubsequence(indexesArray);
    }

    private static Map<Integer, Integer> computeValueToIndexMap(int[] permutation) {
        Map<Integer, Integer> valueToIndexMap = new HashMap<>();
        for (int i = 0; i < permutation.length; i++) {
            int value = permutation[i];
            valueToIndexMap.put(value, i);
        }
        return valueToIndexMap;
    }

    private static int longestIncreasingSubsequence(Integer[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] endIndexes = new int[array.length];
        int length = 1;

        for (int i = 1; i < array.length; i++) {
            // Case 1 - smallest end element
            if (array[i] <= array[endIndexes[0]]) {
                endIndexes[0] = i;
            } else if (array[i] > array[endIndexes[length - 1]]) {
                // Case 2 - highest end element - extends longest increasing subsequence
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(array, endIndexes, 0, length - 1, array[i]);
                endIndexes[indexToReplace] = i;
            }
        }
        return length;
    }

    private static int ceilIndex(Integer[] array, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (array[endIndexes[middle]] >= key) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
    }

    private static int[] readPermutation(int length) throws IOException{
        int[] permutation = new int[length];
        for (int i = 0; i < length; i++) {
            permutation[i] = FastReader.nextInt();
        }
        return permutation;
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

        public void flush() {
            writer.flush();
        }
    }
}