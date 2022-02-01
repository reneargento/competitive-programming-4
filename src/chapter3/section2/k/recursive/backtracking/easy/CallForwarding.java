package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/02/22.
 */
@SuppressWarnings("unchecked")
public class CallForwarding {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        outputWriter.printLine("CALL FORWARDING OUTPUT");

        for (int t = 1; t <= tests; t++) {
            int source = FastReader.nextInt();

            IntervalBST<Integer>[] intervalBSTs = new IntervalBST[9999];

            while (source != 0) {
                int time = FastReader.nextInt();
                int endTime = time + FastReader.nextInt();
                int target = FastReader.nextInt();

                if (intervalBSTs[source] == null) {
                    intervalBSTs[source] = new IntervalBST<>();
                }
                IntervalBST.Interval interval = new IntervalBST.Interval(time, endTime);
                intervalBSTs[source].put(interval, target);

                source = FastReader.nextInt();
            }

            outputWriter.printLine(String.format("SYSTEM %d", t));
            int callTime = FastReader.nextInt();
            while (callTime != 9000) {
                int extension = FastReader.nextInt();
                Set<Integer> visited = new HashSet<>();
                IntervalBST.Interval queryInterval = new IntervalBST.Interval(callTime, callTime);

                int callResult = processCall(intervalBSTs, queryInterval, extension, visited);
                outputWriter.printLine(String.format("AT %04d CALL TO %04d RINGS %04d", callTime,
                        extension, callResult));

                callTime = FastReader.nextInt();
            }
        }
        outputWriter.printLine("END OF OUTPUT");
        outputWriter.flush();
    }

    private static int processCall(IntervalBST<Integer>[] intervalBSTs, IntervalBST.Interval queryInterval,
                                   int extension, Set<Integer> visited) {
        if (visited.contains(extension)) {
            return 9999;
        }
        visited.add(extension);

        if (intervalBSTs[extension] == null
                || intervalBSTs[extension].getIntersection(queryInterval) == null) {
            return extension;
        }
        IntervalBST.Interval nextInterval = intervalBSTs[extension].getIntersection(queryInterval);
        int nextExtension = intervalBSTs[extension].get(nextInterval);
        return processCall(intervalBSTs, queryInterval, nextExtension, visited);
    }

    private static class IntervalBST<Value> {

        public static class Interval implements Comparable<Interval> {
            double min;
            double max;

            Interval(double min, double max) {
                this.min = min;
                this.max = max;
            }

            public boolean intersects(Interval that) {
                if (this.max < that.min || that.max < this.min) {
                    return false;
                }
                return true;
            }

            @Override
            public int compareTo(Interval that) {
                if (this.min < that.min) {
                    return -1;
                } else if (this.min > that.min) {
                    return 1;
                } else if (this.max < that.max) {
                    return -1;
                } else if (this.max > that.max) {
                    return 1;
                } else {
                    return 0;
                }
            }

            @Override
            public boolean equals(Object other) {
                if (!(other instanceof IntervalBST.Interval)) {
                    return false;
                }

                IntervalBST.Interval otherInterval = (IntervalBST.Interval) other;
                return this.min == otherInterval.min && this.max == otherInterval.max;
            }

            @Override
            public int hashCode() {
                return Double.hashCode(min) * 31 + Double.hashCode(max);
            }
        }

        private Node root;

        private class Node {
            Interval interval;  // key
            Value value;        // associated data
            Node left, right;   // left and right subtrees
            int size;           // size of subtree rooted at this node
            double max;         // max endpoint in subtree rooted at this node

            Node(Interval interval, Value value) {
                this.interval = interval;
                this.value = value;
                this.size = 1;
                this.max = interval.max;
            }
        }

        /***************************************************************************
         *  BST search
         ***************************************************************************/

        public boolean contains(Interval interval) {
            return (get(interval) != null);
        }

        // Return value associated with the given key
        // If no such value, return null
        public Value get(Interval interval) {
            return get(root, interval);
        }

        private Value get(Node node, Interval interval) {
            if (node == null) {
                return null;
            }

            int compare = interval.compareTo(node.interval);

            if (compare < 0) {
                return get(node.left, interval);
            } else if (compare > 0) {
                return get(node.right, interval);
            } else {
                return node.value;
            }
        }

        /***************************************************************************
         *  Insertion
         ***************************************************************************/
        public void put(Interval interval, Value value) {
            root = randomizedInsert(root, interval, value);
        }

        // Make new node the root with uniform probability to keep the BST balanced
        private Node randomizedInsert(Node node, Interval interval, Value value) {
            if (node == null) {
                return new Node(interval, value);
            }

            if (Math.random() * size(node) < 1.0) {
                return rootInsert(node, interval, value);
            }

            int compare = interval.compareTo(node.interval);
            if (compare < 0)  {
                node.left = randomizedInsert(node.left, interval, value);
            } else {
                node.right = randomizedInsert(node.right, interval, value);
            }

            updateSizeAndMax(node);

            return node;
        }

        private Node rootInsert(Node node, Interval interval, Value value) {
            if (node == null) {
                return new Node(interval, value);
            }

            int compare = interval.compareTo(node.interval);
            if (compare < 0) {
                node.left = rootInsert(node.left, interval, value);
                node = rotateRight(node);
            } else {
                node.right = rootInsert(node.right, interval, value);
                node = rotateLeft(node);
            }

            return node;
        }

        /***************************************************************************
         *  Interval searching
         ***************************************************************************/

        // Return an interval in the data structure that intersects the given interval;
        // Return null if no such interval exists
        // Running time is proportional to log N
        public Interval getIntersection(Interval interval) {
            return getIntersection(root, interval);
        }

        // Look in subtree rooted at node
        public Interval getIntersection(Node node, Interval interval) {
            while (node != null) {
                if (interval.intersects(node.interval)) {
                    return node.interval;
                } else if (node.left == null) {
                    node = node.right;
                } else if (node.left.max < interval.min) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return null;
        }

        /***************************************************************************
         *  useful binary search tree functions
         ***************************************************************************/

        // Return number of nodes in subtree rooted at node
        public int size() {
            return size(root);
        }

        private int size(Node node) {
            if (node == null) {
                return 0;
            } else {
                return node.size;
            }
        }

        // Height of tree (empty tree height = 0)
        public int height() {
            return height(root);
        }

        private int height(Node node) {
            if (node == null) {
                return 0;
            }

            return 1 + Math.max(height(node.left), height(node.right));
        }

        /***************************************************************************
         *  helper binary search tree functions
         ***************************************************************************/

        // Update tree information (subtree size and max fields)
        private void updateSizeAndMax(Node node) {
            if (node == null) {
                return;
            }

            node.size = 1 + size(node.left) + size(node.right);
            node.max = max3(node.interval.max, max(node.left), max(node.right));
        }

        private double max(Node node) {
            if (node == null) {
                return Double.MIN_VALUE;
            }

            return node.max;
        }

        // Precondition: intervalAMax is not null
        private double max3(double intervalAMax, double intervalBMax, double intervalCMax) {
            return Math.max(intervalAMax, Math.max(intervalBMax, intervalCMax));
        }

        private Node rotateRight(Node node) {
            Node newRoot = node.left;
            node.left = newRoot.right;
            newRoot.right = node;

            updateSizeAndMax(node);
            updateSizeAndMax(newRoot);

            return newRoot;
        }

        private Node rotateLeft(Node node) {
            Node newRoot = node.right;
            node.right = newRoot.left;
            newRoot.left = node;

            updateSizeAndMax(node);
            updateSizeAndMax(newRoot);

            return newRoot;
        }

        /***************************************************************************
         *  Debugging functions that test the integrity of the interval tree
         ***************************************************************************/

        // Check integrity of subtree size and max fields
        public boolean checkIntegrity() {
            return checkSize() && checkMax();
        }

        // Check integrity of size fields
        private boolean checkSize() {
            return checkSize(root);
        }

        private boolean checkSize(Node root) {
            if (root == null) {
                return true;
            }

            return checkSize(root.left)
                    && checkSize(root.right)
                    && (root.size == 1 + size(root.left) + size(root.right));
        }

        private boolean checkMax() {
            return checkMax(root);
        }

        private boolean checkMax(Node node) {
            if (node == null) {
                return true;
            }

            return node.max == max3(node.interval.max, max(node.left), max(node.right));
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
