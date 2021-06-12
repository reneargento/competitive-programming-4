package chapter2.section3.i.order.statistics.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/06/21.
 */
public class LuckyNumber {

    private static class Pair {
        int number1;
        int number2;

        public Pair(int number1, int number2) {
            this.number1 = number1;
            this.number2 = number2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        RedBlackBST<Integer, Integer> luckyNumbersTree = generateInitialSet();
        List<Integer> luckyNumbersList = generateLuckyNumbers(luckyNumbersTree);
        Set<Integer> luckyNumbersSet = new HashSet<>(luckyNumbersList);
        String line = FastReader.getLine();

        while (line != null) {
            int targetNumber = Integer.parseInt(line);
            Pair pair = twoSum(targetNumber, luckyNumbersList, luckyNumbersSet, luckyNumbersTree);

            outputWriter.print(targetNumber);
            if (pair != null) {
                outputWriter.printLine(String.format(" is the sum of %d and %d.", pair.number1, pair.number2));
            } else {
                outputWriter.printLine(" is not the sum of two luckies!");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> generateLuckyNumbers(RedBlackBST<Integer, Integer> luckyNumbers) {
        for (int i = 2; i < luckyNumbers.size(); i++) {
            int sieve = luckyNumbers.select(i);
            applySieve(luckyNumbers, sieve);
        }

        luckyNumbers.delete(0);
        List<Integer> luckyNumbersList = new ArrayList<>();
        for (int key : luckyNumbers.keys()) {
            luckyNumbersList.add(key);
        }
        return luckyNumbersList;
    }

    private static RedBlackBST<Integer, Integer> generateInitialSet() {
        RedBlackBST<Integer, Integer> numbers = new RedBlackBST<>();

        numbers.put(0, 0);
        for (int number = 1; number <= 2000001; number += 2) {
            numbers.put(number, 0);
        }
        return numbers;
    }

    private static void applySieve(RedBlackBST<Integer, Integer> luckyNumbers, int sieve) {
        for (int index = sieve; index < luckyNumbers.size(); index += (sieve - 1)) {
            int key = luckyNumbers.select(index);
            luckyNumbers.delete(key);
        }
    }

    private static Pair twoSum(int targetNumber, List<Integer> luckyNumbers, Set<Integer> luckyNumbersSet,
                               RedBlackBST<Integer, Integer> luckyNumbersTree) {
        if ((targetNumber & 1) == 1) {
            return null;
        }
        int rank = luckyNumbersTree.rank(targetNumber / 2);

        for (int i = rank; i >= 0; i--) {
            int number = luckyNumbers.get(i);
            int complementNumber = targetNumber - number;

            if (luckyNumbersSet.contains(complementNumber)) {
                int minNumber = Math.min(number, complementNumber);
                int maxNumber = Math.max(number, complementNumber);
                return new Pair(minNumber, maxNumber);
            }
        }
        return null;
    }

    private static class RedBlackBST<Key extends Comparable<Key>, Value> {
        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private class Node {
            private Key key;
            private Value value;
            private Node left, right;

            boolean color;
            int size;

            Node(Key key, Value value, int size, boolean color) {
                this.key = key;
                this.value = value;

                this.size = size;
                this.color = color;
            }
        }

        private Node root;

        private int size() {
            return size(root);
        }

        private int size(Node node) {
            if (node == null) {
                return 0;
            }
            return node.size;
        }

        private boolean isEmpty() {
            return size(root) == 0;
        }

        private boolean isRed(Node node) {
            if (node == null) {
                return false;
            }
            return node.color == RED;
        }

        private Node rotateLeft(Node node) {
            if (node == null || node.right == null) {
                return node;
            }
            Node newRoot = node.right;

            node.right = newRoot.left;
            newRoot.left = node;

            newRoot.color = node.color;
            node.color = RED;

            newRoot.size = node.size;
            node.size = size(node.left) + 1 + size(node.right);

            return newRoot;
        }

        private Node rotateRight(Node node) {
            if (node == null || node.left == null) {
                return node;
            }
            Node newRoot = node.left;

            node.left = newRoot.right;
            newRoot.right = node;

            newRoot.color = node.color;
            node.color = RED;

            newRoot.size = node.size;
            node.size = size(node.left) + 1 + size(node.right);

            return newRoot;
        }

        private void flipColors(Node node) {
            if (node == null || node.left == null || node.right == null) {
                return;
            }

            //The root must have opposite color of its two children
            if ((isRed(node) && !isRed(node.left) && !isRed(node.right))
                    || (!isRed(node) && isRed(node.left) && isRed(node.right))) {
                node.color = !node.color;
                node.left.color = !node.left.color;
                node.right.color = !node.right.color;
            }
        }

        private void put(Key key, Value value) {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }
            if (value == null) {
                delete(key);
                return;
            }

            root = put(root, key, value);
            root.color = BLACK;
        }

        private Node put(Node node, Key key, Value value) {
            if (node == null) {
                return new Node(key, value, 1, RED);
            }

            int compare = key.compareTo(node.key);

            if (compare < 0) {
                node.left = put(node.left, key, value);
            } else if (compare > 0) {
                node.right = put(node.right, key, value);
            } else {
                node.value = value;
            }

            if (isRed(node.right) && !isRed(node.left)) {
                node = rotateLeft(node);
            }
            if (isRed(node.left) && isRed(node.left.left)) {
                node = rotateRight(node);
            }
            if (isRed(node.left) && isRed(node.right)) {
                flipColors(node);
            }

            node.size = size(node.left) + 1 + size(node.right);
            return node;
        }

        private Key min() {
            if (root == null) {
                throw new NoSuchElementException("Empty binary search tree");
            }
            return min(root).key;
        }

        private Node min(Node node) {
            if (node.left == null) {
                return node;
            }
            return min(node.left);
        }

        private Key max() {
            if (root == null) {
                throw new NoSuchElementException("Empty binary search tree");
            }
            return max(root).key;
        }

        private Node max(Node node) {
            if (node.right == null) {
                return node;
            }
            return max(node.right);
        }

        private Key select(long index) {
            if (index >= size()) {
                throw new IllegalArgumentException("Index is higher than tree size");
            }
            return select(root, index).key;
        }

        private Node select(Node node, long index) {
            long leftSubtreeSize = size(node.left);

            if (leftSubtreeSize == index) {
                return node;
            } else if (leftSubtreeSize > index) {
                return select(node.left, index);
            } else {
                return select(node.right, index - leftSubtreeSize - 1);
            }
        }

        private int rank(Key key) {
            return rank(root, key);
        }

        private int rank(Node node, Key key) {
            if (node == null) {
                return 0;
            }

            //Returns the number of keys less than node.key in the subtree rooted at node
            int compare = key.compareTo(node.key);
            if (compare < 0) {
                return rank(node.left, key);
            } else if (compare > 0) {
                return size(node.left) + 1 + rank(node.right, key);
            } else {
                return size(node.left);
            }
        }

        private Node deleteMin(Node node) {
            if (node.left == null) {
                return null;
            }

            if (!isRed(node.left) && !isRed(node.left.left)) {
                node = moveRedLeft(node);
            }

            node.left = deleteMin(node.left);
            return balance(node);
        }

        private void delete(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }

            if (isEmpty()) {
                return;
            }

            if (!isRed(root.left) && !isRed(root.right)) {
                root.color = RED;
            }

            root = delete(root, key);

            if (!isEmpty()) {
                root.color = BLACK;
            }
        }

        private Node delete(Node node, Key key) {
            if (node == null) {
                return null;
            }

            if (key.compareTo(node.key) < 0) {
                if (!isRed(node.left) && node.left != null && !isRed(node.left.left)) {
                    node = moveRedLeft(node);
                }

                node.left = delete(node.left, key);
            } else {
                if (isRed(node.left)) {
                    node = rotateRight(node);
                }

                if (key.compareTo(node.key) == 0 && node.right == null) {
                    return null;
                }

                if (!isRed(node.right) && node.right != null && !isRed(node.right.left)) {
                    node = moveRedRight(node);
                }

                if (key.compareTo(node.key) == 0) {
                    Node aux = min(node.right);
                    node.key = aux.key;
                    node.value = aux.value;
                    node.right = deleteMin(node.right);
                } else {
                    node.right = delete(node.right, key);
                }
            }
            return balance(node);
        }

        private Node moveRedLeft(Node node) {
            //Assuming that node is red and both node.left and node.left.left are black,
            // make node.left or one of its children red
            flipColors(node);

            if (node.right != null && isRed(node.right.left)) {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
                flipColors(node);
            }
            return node;
        }

        private Node moveRedRight(Node node) {
            //Assuming that node is red and both node.right and node.right.left are black,
            // make node.right or one of its children red
            flipColors(node);

            if (node.left != null && isRed(node.left.left)) {
                node = rotateRight(node);
                flipColors(node);
            }
            return node;
        }

        private Node balance(Node node) {
            if (node == null) {
                return null;
            }

            if (isRed(node.right) && !isRed(node.left)) {
                node = rotateLeft(node);
            }

            if (isRed(node.left) && isRed(node.left.left)) {
                node = rotateRight(node);
            }

            if (isRed(node.left) && isRed(node.right)) {
                flipColors(node);
            }

            node.size = size(node.left) + 1 + size(node.right);
            return node;
        }

        private Iterable<Key> keys() {
            return keys(min(), max());
        }

        private Iterable<Key> keys(Key low, Key high) {
            if (low == null)  {
                throw new IllegalArgumentException("First argument to keys() cannot be null");
            }
            if (high == null) {
                throw new IllegalArgumentException("Second argument to keys() cannot be null");
            }

            Queue<Key> queue = new LinkedList<>();
            keys(root, queue, low, high);
            return queue;
        }

        private void keys(Node node, Queue<Key> queue, Key low, Key high) {
            if (node == null) {
                return;
            }

            int compareLow = low.compareTo(node.key);
            int compareHigh = high.compareTo(node.key);

            if (compareLow < 0) {
                keys(node.left, queue, low, high);
            }

            if (compareLow <= 0 && compareHigh >= 0) {
                queue.offer(node.key);
            }

            if (compareHigh > 0) {
                keys(node.right, queue, low, high);
            }
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
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
