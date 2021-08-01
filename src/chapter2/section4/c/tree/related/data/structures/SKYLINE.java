package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/07/21.
 */
public class SKYLINE {

    private static class BuildingData {
        int left;
        int right;
        int height;

        public BuildingData(int left, int right, int height) {
            this.left = left;
            this.right = right;
            this.height = height;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            BuildingData[] buildings = new BuildingData[FastReader.nextInt()];
            int minLeft = Integer.MAX_VALUE;
            int maxRight = Integer.MIN_VALUE;

            for (int i = 0; i < buildings.length; i++) {
                int left = FastReader.nextInt();
                int right = FastReader.nextInt() - 1;
                int height = FastReader.nextInt();
                buildings[i] = new BuildingData(left, right, height);

                minLeft = Math.min(minLeft, left);
                maxRight = Math.max(maxRight, right);
            }

            long[] initArray = new long[maxRight - minLeft + 1];
            long totalOverlaps = countOverlaps(buildings, initArray, minLeft);
            outputWriter.printLine(totalOverlaps);
        }
        outputWriter.flush();
    }

    private static long countOverlaps(BuildingData[] buildings, long[] initArray, int minLeft) {
        long totalOverlaps = 0;
        SegmentTree segmentTree = new SegmentTree(initArray);

        for (BuildingData building : buildings) {
            totalOverlaps += segmentTree.update(building.left - minLeft, building.right - minLeft, building.height);
        }
        return totalOverlaps;
    }

    private static class SegmentTree {
        // The Node class represents a partition range of the array.
        private static class Node {
            // long min;
            long minHeight;
            long maxHeight;

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

            if (size > 1) {
                // Build children
                build(2 * index, left, size / 2);
                build(2 * index + 1, left + size / 2, size - size / 2);
            }
        }

        public int update(int left, int right, long height) {
            return update(1, left, right, height);
        }

        private int update(int index, int left, int right, long height) {
            int overlaps = 0;
            Node node = heap[index];

            if (contains(left, right, node.left, node.right)) {
                if (node.minHeight > height) {
                    return 0;
                }
                if (node.maxHeight <= height) {
                    overlaps = node.right - node.left + 1;
                    change(node, height);
                    return overlaps;
                }
            }

            if (intersects(left, right, node.left, node.right)) {
                propagate(index);

                overlaps += update(2 * index, left, right, height);
                overlaps += update(2 * index + 1, left, right, height);

                node.minHeight = Math.min(heap[2 * index].minHeight, heap[2 * index + 1].minHeight);
                node.maxHeight = Math.max(heap[2 * index].maxHeight, heap[2 * index + 1].maxHeight);
            }
            return overlaps;
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
        private void change(Node node, long height) {
            node.pendingValue = height;
            node.minHeight = height;
            node.maxHeight = height;
            array[node.left] = height;
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
