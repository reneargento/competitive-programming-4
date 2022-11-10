package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/11/22.
 */
public class IncreasingSubsequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int sequenceLength = FastReader.nextInt();

        while (sequenceLength != 0) {
            int[] sequence = new int[sequenceLength];
            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = FastReader.nextInt();
            }
            List<Integer> lis = longestIncreasingSubsequence(sequence);
            outputWriter.print(lis.size());
            for (int value : lis) {
                outputWriter.print(" " + value);
            }
            outputWriter.printLine();

            sequenceLength = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    public static List<Integer> longestIncreasingSubsequence(int[] array) {
        if (array.length == 0) {
            return new ArrayList<>();
        }
        int[] endIndexes = new int[array.length];
        int[] previousIndices = new int[array.length];

        Arrays.fill(previousIndices, -1);
        int length = 1;

        for (int i = 1; i < array.length; i++) {
            // Case 1 - smallest end element
            if (array[i] <= array[endIndexes[0]]) {
                endIndexes[0] = i;
            } else if (array[i] > array[endIndexes[length - 1]]) {
                // Case 2 - highest end element - extends longest increasing subsequence
                previousIndices[i] = endIndexes[length - 1];
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(array, endIndexes, 0, length - 1, array[i]);
                previousIndices[i] = endIndexes[indexToReplace - 1];
                endIndexes[indexToReplace] = i;
            }
        }
        return getSequence(array, endIndexes, previousIndices, length);
    }

    private static int ceilIndex(int[] array, int[] endIndexes, int low, int high, int key) {
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

    private static List<Integer> getSequence(int[] array, int[] endIndexes, int[] previousIndices, int length) {
        LinkedList<Integer> sequence = new LinkedList<>();

        for (int i = endIndexes[length - 1]; i >= 0; i = previousIndices[i]) {
            sequence.addFirst(array[i]);
        }
        return sequence;
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
