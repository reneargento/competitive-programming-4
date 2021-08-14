package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/08/21.
 */
public class IntervalProduct {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int elementsNumber = Integer.parseInt(data[0]);
            int rounds = Integer.parseInt(data[1]);

            long[] elements = new long[elementsNumber + 1];
            for (int i = 1; i < elements.length; i++) {
                elements[i] = FastReader.nextInt();
            }

            SegmentTree segmentTree = new SegmentTree(elements);
            for (int r = 0; r < rounds; r++) {
                String command = FastReader.next();
                int parameter1 = FastReader.nextInt();
                int parameter2 = FastReader.nextInt();

                if (command.equals("C")) {
                    segmentTree.update(parameter1, parameter1, parameter2);
                } else {
                    long product = segmentTree.rangeProductQuery(parameter1, parameter2);
                    if (product < 0) {
                        outputWriter.print("-");
                    } else if (product > 0) {
                        outputWriter.print("+");
                    } else {
                        outputWriter.print("0");
                    }
                }
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static class SegmentTree {
        // The Node class represents a partition range of the array.
        private static class Node {
            long product;

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
                heap[index].product = getSign(array[left]);
            } else {
                // Build children
                build(2 * index, left, size / 2);
                build(2 * index + 1, left + size / 2, size - size / 2);

                heap[index].product = heap[2 * index].product * heap[2 * index + 1].product;
            }
        }

        public long rangeProductQuery(int left, int right) {
            return rangeProductQuery(1, left, right);
        }

        private long rangeProductQuery(int index, int left, int right) {
            Node node = heap[index];

            if (contains(left, right, node.left, node.right)) {
                return heap[index].product;
            }

            if (intersects(left, right, node.left, node.right)) {
                long leftSum = rangeProductQuery(2 * index, left, right);
                long rightSum = rangeProductQuery(2 * index + 1, left, right);

                return leftSum * rightSum;
            }
            return 1;
        }

        public void update(int left, int right, long value) {
            update(1, left, right, getSign(value));
        }

        private void update(int index, int left, int right, long value) {
            Node node = heap[index];

            if (contains(left, right, node.left, node.right)) {
                change(node, value);
            }

            if (node.size() == 1) {
                return;
            }

            if (intersects(left, right, node.left, node.right)) {
                update(2 * index, left, right, value);
                update(2 * index + 1, left, right, value);

                node.product = heap[2 * index].product * heap[2 * index + 1].product;
            }
        }

        private void change(Node node, long value) {
            node.product = value;
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

        private int getSign(long value) {
            if (value > 0) {
                return 1;
            } else if (value < 0) {
                return -1;
            }
            return 0;
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
