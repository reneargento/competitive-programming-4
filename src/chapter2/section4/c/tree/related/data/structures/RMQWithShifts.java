package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 31/07/21.
 */
public class RMQWithShifts {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        long[] integers = new long[inputReader.readInt()];
        int queries = inputReader.readInt();

        for (int i = 0; i < integers.length; i++) {
            integers[i] = inputReader.readInt();
        }

        SegmentTree segmentTree = new SegmentTree(integers);

        for (int q = 0; q < queries; q++) {
            String line = inputReader.readLine();

            int numbersStartIndex = line.indexOf("(");
            String numbersLine = line.substring(numbersStartIndex + 1, line.length() - 1);
            String[] numbers = numbersLine.split(",");

            if (line.charAt(0) == 'q') {
                int left = Integer.parseInt(numbers[0]) - 1;
                int right = Integer.parseInt(numbers[1]) - 1;
                outputWriter.printLine(segmentTree.rangeMinQuery(left, right));
            } else {
                int previousIndex = Integer.parseInt(numbers[0]) - 1;
                long firstNumber = segmentTree.rangeMinQuery(previousIndex, previousIndex);

                for (int i = 0; i < numbers.length; i++) {
                    if (i == numbers.length - 1) {
                        segmentTree.update(previousIndex, previousIndex, firstNumber);
                        break;
                    }
                    int nextIndex = Integer.parseInt(numbers[i + 1]) - 1;

                    long nextNumber = segmentTree.rangeMinQuery(nextIndex, nextIndex);
                    segmentTree.update(previousIndex, previousIndex, nextNumber);
                    previousIndex = nextIndex;
                }
            }
        }
        outputWriter.flush();
    }

    private static class SegmentTree {
        // The Node class represents a partition range of the array.
        private static class Node {
            long sum;
            long min;

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
                heap[index].min = array[left];
            } else {
                // Build children
                build(2 * index, left, size / 2);
                build(2 * index + 1, left + size / 2, size - size / 2);

                heap[index].sum = heap[2 * index].sum + heap[2 * index + 1].sum;
                heap[index].min = Math.min(heap[2 * index].min, heap[2 * index + 1].min);
            }
        }

        public long rangeMinQuery(int left, int right) {
            return rangeMinQuery(1, left, right);
        }

        private long rangeMinQuery(int index, int left, int right) {
            Node node = heap[index];

            if (contains(left, right, node.left, node.right)) {
                return heap[index].min;
            }

            if (intersects(left, right, node.left, node.right)) {
                long leftMin = rangeMinQuery(2 * index, left, right);
                long rightMin = rangeMinQuery(2 * index + 1, left, right);

                return Math.min(leftMin, rightMin);
            }
            return Long.MAX_VALUE;
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
                update(2 * index, left, right, value);
                update(2 * index + 1, left, right, value);

                node.sum = heap[2 * index].sum + heap[2 * index + 1].sum;
                node.min = Math.min(heap[2 * index].min, heap[2 * index + 1].min);
            }
        }

        private void change(Node node, long value) {
            node.sum = node.size() * value;
            node.min = value;
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

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        // Returns null on EOF
        public String readLine() {
            int c = read();
            if (c == -1) {
                return null;
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == -1;
        }

        public String next() {
            return readString();
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
