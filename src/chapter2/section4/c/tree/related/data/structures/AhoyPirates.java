package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/07/21.
 */
public class AhoyPirates {
    private static final int SET = 1;
    private static final int CLEAR = 2;
    private static final int INVERSE = 3;
    private static final int DO_NOTHING = 0;

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] piratesDescription = new int[1024002];

        for (int t = 1; t <= tests; t++) {
            outputWriter.printLine("Case " + t + ":");
            int inputPairs = FastReader.nextInt();
            int piratesNumber = 0;

            for (int i = 0; i < inputPairs; i++) {
                int sets = FastReader.nextInt();
                String pirates = FastReader.next();

                for (int s = 0; s < sets; s++) {
                    for (int p = 0; p < pirates.length(); p++) {
                        piratesDescription[piratesNumber++] = Character.getNumericValue(pirates.charAt(p));
                    }
                }
            }
            processQueries(piratesDescription, piratesNumber, outputWriter);
        }
        outputWriter.flush();
    }

    private static void processQueries(int[] piratesDescription, int piratesNumber,
                                       OutputWriter outputWriter) throws IOException {
        SegmentTree segmentTree = new SegmentTree(piratesDescription, piratesNumber);

        int queries = FastReader.nextInt();
        int godQuestions = 0;

        for (int q = 0; q < queries; q++) {
            String type = FastReader.next();
            int left = FastReader.nextInt();
            int right = FastReader.nextInt();

            if (type.equals("F")) {
                segmentTree.update(left, right, SET);
            } else if (type.equals("E")) {
                segmentTree.update(left, right, CLEAR);
            } else if (type.equals("I")) {
                segmentTree.update(left, right, INVERSE);
            } else {
                godQuestions++;
                long buccaneerPirates = segmentTree.rangeSumQuery(left, right);
                outputWriter.printLine("Q" + godQuestions + ": " + buccaneerPirates);
            }
        }
    }

    private static class SegmentTree {
        // The Node class represents a partition range of the array.
        private final int[] segmentTree;
        private final int[] pendingValues;
        private final int[] leftNodes;
        private final int[] rightNodes;
        private final int[] array;

        public SegmentTree(int[] array, int arraySize) {
            this.array = Arrays.copyOf(array, arraySize);
            int size = arraySize << 2;
            segmentTree = new int[size];
            pendingValues = new int[size];
            leftNodes = new int[size];
            rightNodes = new int[size];
            build(1, 0, arraySize);
        }

        public int size() {
            return array.length;
        }

        // Initialize the Nodes of the Segment tree
        private void build(int index, int left, int size) {
            leftNodes[index] = left;
            rightNodes[index] = left + size - 1;

            if (size == 1) {
                segmentTree[index] = array[left];
            } else {
                // Build children
                int leftChildIndex = index << 1;
                int childSize = size >> 1;
                build(leftChildIndex, left, childSize);
                build(leftChildIndex + 1, left + childSize, size - childSize);

                segmentTree[index] = segmentTree[leftChildIndex] + segmentTree[leftChildIndex + 1];
            }
        }

        public long rangeSumQuery(int left, int right) {
            return rangeSumQuery(1, left, right);
        }

        private long rangeSumQuery(int index, int left, int right) {
            // If you did a range update that contained this node, you can infer the Sum without going down the tree
            if (pendingValues[index] != DO_NOTHING && contains(leftNodes[index], rightNodes[index], left, right)) {
                if (pendingValues[index] == SET) {
                    return right - left + 1;
                }
                if (pendingValues[index] == CLEAR) {
                    return 0;
                }
            }

            if (contains(left, right, leftNodes[index], rightNodes[index])) {
                propagate(index);
                return segmentTree[index];
            }

            if (intersects(left, right, leftNodes[index], rightNodes[index])) {
                propagate(index);
                long leftSum = rangeSumQuery(index << 1, left, right);
                long rightSum = rangeSumQuery((index << 1) + 1, left, right);
                return leftSum + rightSum;
            }
            return 0;
        }

        public void update(int left, int right, int updateType) {
            update(1, left, right, updateType);
        }

        private void update(int index, int left, int right, int updateType) {
            propagate(index);

            // The Node of the heap tree represents a range of the array with bounds: [node.left, node.right]
            if (contains(left, right, leftNodes[index], rightNodes[index])) {
                pendingValues[index] = computePendingValue(index, updateType);
                change(index, pendingValues[index]);
                return;
            }

            if (nodeSize(index) == 1) {
                return;
            }

            if (intersects(left, right, leftNodes[index], rightNodes[index])) {
                int leftChildIndex = index << 1;
                update(leftChildIndex, left, right, updateType);
                update(leftChildIndex + 1, left, right, updateType);

                segmentTree[index] = segmentTree[leftChildIndex] + segmentTree[leftChildIndex + 1];
            }
        }

        // Propagate temporal values to children
        private void propagate(int index) {
            if (pendingValues[index] != DO_NOTHING) {
                change(index, pendingValues[index]);
                pendingValues[index] = DO_NOTHING;
            }
        }

        private int computePendingValue(int index, int updateType) {
            if (pendingValues[index] == DO_NOTHING
                    || updateType == SET
                    || updateType == CLEAR) {
                return updateType;
            }

            // Process INVERSE update
            if (pendingValues[index] == SET) {
                return CLEAR;
            } else if (pendingValues[index] == CLEAR) {
                return SET;
            } else {
                return DO_NOTHING;
            }
        }

        // Update node values
        private void change(int index, int updateType) {
            int updatedValue = computeUpdatedValue(index, updateType);
            segmentTree[index] = updatedValue;
            array[leftNodes[index]] = updatedValue;

            if (nodeSize(index) == 1) {
                pendingValues[index] = DO_NOTHING;
                return;
            }
            if (pendingValues[index] != DO_NOTHING) {
                int leftChildIndex = index << 1;
                propagateValueToChild(index, leftChildIndex);

                propagateValueToChild(index, leftChildIndex + 1);
                pendingValues[index] = DO_NOTHING; // Unset the pending propagation values
            }
        }

        private void propagateValueToChild(int parentIndex, int childIndex) {
            int childPendingValue = computePendingValue(childIndex, pendingValues[parentIndex]);
            pendingValues[childIndex] = childPendingValue;
        }

        private int computeUpdatedValue(int index, int updateType) {
            int nodeSize = rightNodes[index] - leftNodes[index] + 1;
            if (updateType == SET) {
                return nodeSize(index);
            } else if (updateType == CLEAR) {
                return 0;
            } else {
                return nodeSize - segmentTree[index];
            }
        }

        private int nodeSize(int index) {
            return rightNodes[index] - leftNodes[index] + 1;
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

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
