package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/08/21.
 */
public class WorstWeatherEver {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int testCase = 1;

        while (true) {
            int yearsNumber = FastReader.nextInt();
            int[] years = new int[yearsNumber];
            long[] rain = new long[yearsNumber];

            if (yearsNumber == 0) {
                break;
            }

            for (int i = 0; i < yearsNumber; i++) {
                years[i] = FastReader.nextInt();
                rain[i] = FastReader.nextInt();
            }
            SegmentTree segmentTree = new SegmentTree(rain);

            if (testCase > 1) {
                outputWriter.printLine();
            }

            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int year1 = FastReader.nextInt();
                int year2 = FastReader.nextInt();
                String result = queryYears(segmentTree, years, rain, year1, year2);
                outputWriter.printLine(result);
            }
            testCase++;
        }
        outputWriter.flush();
    }

    private static String queryYears(SegmentTree segmentTree, int[] years, long[] rain, int year1, int year2) {
        int index1 = binarySearch(years, year1, true);
        int index2 = binarySearch(years, year2, false);
        boolean hasAllYearsData = false;
        boolean hasIntermediateYears = true;

        if (index1 == rain.length || years[years.length - 1] == year1 || years[0] >= year2) {
            return "maybe";
        }

        if (index2 == rain.length) {
            index2--;
        } else {
            int numberOfYears = Math.abs(year2 - year1);
            if (index2 - index1 == numberOfYears) {
                hasAllYearsData = true;
            }
        }

        long rainOnYear1 = rain[index1];
        long rainOnYear2 = rain[index2];
        boolean year1IsKnown = false;
        boolean year2IsKnown = false;

        if (years[index1] == year1) {
            index1++;
            year1IsKnown = true;
        }
        if (years[index2] == year2) {
            index2--;
            year2IsKnown = true;
        }

        if (index1 > index2) {
            hasIntermediateYears = false;
        }

        long maxRainOnIntermediateYears = 0;
        if (hasIntermediateYears) {
            maxRainOnIntermediateYears = segmentTree.rangeMaxQuery(index1, index2);
        }

        if ((year1IsKnown && year2IsKnown
                && rainOnYear2 > rainOnYear1)
                || (hasIntermediateYears && year1IsKnown && maxRainOnIntermediateYears >= rainOnYear1)
                || (hasIntermediateYears && year2IsKnown && maxRainOnIntermediateYears >= rainOnYear2)) {
            return "false";
        } else if (year1IsKnown && year2IsKnown
                && rainOnYear1 >= rainOnYear2
                && hasAllYearsData) {
            return "true";
        } else {
            return "maybe";
        }
    }

    private static int binarySearch(int[] years, int year, boolean upperBound) {
        int low = 0;
        int high = years.length - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (years[middle] == year) {
                return middle;
            } else if (years[middle] < year) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }

        if (upperBound) {
            return low;
        }
        return high;
    }

    private static class SegmentTree {
        // The Node class represents a partition range of the array.
        private static class Node {
            long sum;
            long max;

            // Value that will be propagated lazily
            Long pendingValue = null;
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
                heap[index].sum = array[left];
                heap[index].max = array[left];
            } else {
                // Build children
                build(2 * index, left, size / 2);
                build(2 * index + 1, left + size / 2, size - size / 2);

                heap[index].sum = heap[2 * index].sum + heap[2 * index + 1].sum;
                heap[index].max = Math.max(heap[2 * index].max, heap[2 * index + 1].max);
            }
        }

        public long rangeMaxQuery(int left, int right) {
            return rangeMaxQuery(1, left, right);
        }

        private long rangeMaxQuery(int index, int left, int right) {
            Node node = heap[index];

            // If you did a range update that contained this node, you can infer the Max value without going down the tree
            if (node.pendingValue != null && contains(node.left, node.right, left, right)) {
                return node.pendingValue;
            }

            if (contains(left, right, node.left, node.right)) {
                return heap[index].max;
            }

            if (intersects(left, right, node.left, node.right)) {
                propagate(index);
                long leftMax = rangeMaxQuery(2 * index, left, right);
                long rightMax = rangeMaxQuery(2 * index + 1, left, right);

                return Math.max(leftMax, rightMax);
            }
            return Long.MIN_VALUE;
        }

        public void update(int left, int right, long value) {
            update(1, left, right, value);
        }

        private void update(int index, int left, int right, long value) {
            // The Node of the heap tree represents a range of the array with bounds: [node.left, node.right]
            Node node = heap[index];

            if (contains(left, right, node.left, node.right)) {
                change(node, value);
            }

            if (node.size() == 1) {
                return;
            }

            if (intersects(left, right, node.left, node.right)) {
                propagate(index);

                update(2 * index, left, right, value);
                update(2 * index + 1, left, right, value);

                node.sum = heap[2 * index].sum + heap[2 * index + 1].sum;
                node.max = Math.max(heap[2 * index].max, heap[2 * index + 1].max);
            }
        }

        // Propagate temporal values to children
        private void propagate(int index) {
            Node node = heap[index];

            if (node.pendingValue != null) {
                change(heap[2 * index], node.pendingValue);
                change(heap[2 * index + 1], node.pendingValue);
                node.pendingValue = null; // Unset the pending propagation value
            }
        }

        // Save the temporal values that will be propagated lazily
        private void change(Node node, long value) {
            node.pendingValue = value;
            node.sum = node.size() * value;
            node.max = value;
            array[node.left] = value;
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
