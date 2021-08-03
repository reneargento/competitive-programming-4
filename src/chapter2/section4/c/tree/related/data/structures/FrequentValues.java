package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/08/21.
 */
public class FrequentValues {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int integersNumber = FastReader.nextInt();

        while (integersNumber != 0) {
            int queries = FastReader.nextInt();
            int[] integers = new int[integersNumber];
            int[] start = new int[integersNumber];
            int[] end = new int[integersNumber];

            for (int i = 0; i < integers.length; i++) {
                integers[i] = FastReader.nextInt();
            }

            long[] frequencies = computeFrequenciesAndBoundaries(integers, start, end);
            SegmentTree segmentTree = new SegmentTree(frequencies);

            for (int q = 0; q < queries; q++) {
                int left = FastReader.nextInt() - 1;
                int right = FastReader.nextInt() - 1;

                if (integers[left] == integers[right]) {
                    int maxFrequency = right - left + 1;
                    outputWriter.printLine(maxFrequency);
                } else {
                    int firstNumberFrequency = 0;
                    int lastNumberFrequency = 0;

                    if (start[left] < left) {
                        firstNumberFrequency = end[left] - left + 1;
                        left = end[left] + 1;
                    }
                    if (end[right] > right) {
                        lastNumberFrequency = right - start[right] + 1;
                        right = start[right] - 1;
                    }
                    long maxFrequencyMiddle = segmentTree.rangeMaxQuery(left, right);

                    long maxFrequency = Math.max(firstNumberFrequency, lastNumberFrequency);
                    maxFrequency = Math.max(maxFrequency, maxFrequencyMiddle);
                    outputWriter.printLine(maxFrequency);
                }
            }
            integersNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long[] computeFrequenciesAndBoundaries(int[] integers, int[] start, int[] end) {
        long[] frequencies = new long[integers.length];

        for (int i = 0; i < integers.length; i++) {
            int frequency = 1;
            long number = integers[i];
            int j;

            for (j = i + 1; j < integers.length && integers[j] == number; j++) {
                frequency++;
            }

            int startIndex = i;
            int endIndex = j - 1;

            for (int k = i; k < j; k++) {
                frequencies[k] = frequency;
                start[k] = startIndex;
                end[k] = endIndex;
            }
            i = endIndex;
        }
        return frequencies;
    }

    private static class SegmentTree {
        private static class Node {
            long max;

            int left;
            int right;

            int size() {
                return right - left + 1;
            }
        }

        private final Node[] heap;
        private final long[] array;

        public SegmentTree(long[] array) {
            this.array = Arrays.copyOf(array, array.length);
            // The max size of this array is about 2 * 2 ^ (log2(n) + 1)
            int size = (int) (2 * Math.pow(2.0, Math.floor((Math.log(array.length) / Math.log(2.0)) + 1)));
            heap = new Node[size];
            build(1, 0, array.length);
        }

        public int size() {
            return array.length;
        }

        // Initialize the Nodes of the Segment tree
        private void build(int index, int left, int size) {
            heap[index] = new Node();
            heap[index].left = left;
            heap[index].right = left + size - 1;

            if (size == 1) {
                heap[index].max = array[left];
            } else {
                // Build children
                build(2 * index, left, size / 2);
                build(2 * index + 1, left + size / 2, size - size / 2);

                heap[index].max = Math.max(heap[2 * index].max, heap[2 * index + 1].max);
            }
        }

        public long rangeMaxQuery(int left, int right) {
            if (left > right) {
                return 0;
            }
            return rangeMaxQuery(1, left, right);
        }

        private long rangeMaxQuery(int index, int left, int right) {
            Node node = heap[index];

            if (contains(left, right, node.left, node.right)) {
                return heap[index].max;
            }

            if (intersects(left, right, node.left, node.right)) {
                long leftMax = rangeMaxQuery(2 * index, left, right);
                long rightMax = rangeMaxQuery(2 * index + 1, left, right);

                return Math.max(leftMax, rightMax);
            }
            return Long.MIN_VALUE;
        }

        // Check if range1 contains range2
        private boolean contains(int left1, int right1, int left2, int right2) {
            return left2 >= left1 && right2 <= right1;
        }

        // Check inclusive intersection, test if range1[left1, right1] intersects range2[left2, right2]
        private boolean intersects(int left1, int right1, int left2, int right2) {
            return left1 <= left2 && right1 >= left2   //  (.[..)..] or (.[...]..)
                    || left1 >= left2 && left1 <= right2; // [.(..]..) or [..(..)..]
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
