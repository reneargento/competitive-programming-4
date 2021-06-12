package chapter2.section3.i.order.statistics.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/06/21.
 */
public class BabyNames {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        AVLTree<String, Integer> maleNames = new AVLTree<>();
        AVLTree<String, Integer> femaleNames = new AVLTree<>();

        while (true) {
            String line = inputReader.readLine();
            String[] data = line.split(" ");
            char command = data[0].charAt(0);

            if (command == '1') {
                String name = data[1];
                char genderSuitability = data[2].charAt(0);

                if (genderSuitability == '1') {
                    maleNames.put(name, 0);
                } else {
                    femaleNames.put(name, 0);
                }
            } else if (command == '2') {
                String name = data[1];
                maleNames.delete(name);
                femaleNames.delete(name);
            } else if (command == '3') {
               String start = data[1];
               String end = data[2];
               int numberOfNames;

               char genderSuitability = data[3].charAt(0);
               if (genderSuitability == '0') {
                   numberOfNames = computeNamesInInterval(maleNames, start, end);
                   numberOfNames += computeNamesInInterval(femaleNames, start, end);
               } else if (genderSuitability == '1') {
                   numberOfNames = computeNamesInInterval(maleNames, start, end);
               } else {
                   numberOfNames = computeNamesInInterval(femaleNames, start, end);
               }
               outputWriter.printLine(numberOfNames);
            }  else {
                break;
            }
        }
        outputWriter.flush();
    }

    private static int computeNamesInInterval(AVLTree<String, Integer> names, String start, String end) {
        return names.rank(end) - names.rank(start);
    }

    private static class AVLTree<Key extends Comparable<Key>, Value> {
        private class Node {
            Key key;
            Value value;
            Node left, right;

            int height;
            int size;

            Node(Key key, Value value, int size, int height) {
                this.key = key;
                this.value = value;

                this.size = size;
                this.height = height;
            }
        }

        private Node root;

        public int size() {
            return size(root);
        }

        private int size(Node node) {
            if (node == null) {
                return 0;
            }

            return node.size;
        }

        public int height() {
            return height(root);
        }

        private int height(Node node) {
            if (node == null) {
                return -1;
            }

            return node.height;
        }

        public boolean isEmpty() {
            return size(root) == 0;
        }

        private Node rotateLeft(Node node) {
            if (node == null || node.right == null) {
                return node;
            }

            Node newRoot = node.right;

            node.right = newRoot.left;
            newRoot.left = node;

            node.height = 1 + Math.max(height(node.left), height(node.right));
            newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));

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

            node.height = 1 + Math.max(height(node.left), height(node.right));
            newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));

            newRoot.size = node.size;
            node.size = size(node.left) + 1 + size(node.right);

            return newRoot;
        }

        public void put(Key key, Value value) {
            if (key == null) {
                return;
            }
            root = put(root, key, value);
        }

        private Node put(Node node, Key key, Value value) {
            if (node == null) {
                return new Node(key, value, 1, 0);
            }
            int compare = key.compareTo(node.key);

            if (compare < 0) {
                node.left = put(node.left, key, value);
            } else if (compare > 0) {
                node.right = put(node.right, key, value);
            } else {
                node.value = value;
            }

            node.height = 1 + Math.max(height(node.left), height(node.right));
            node.size = size(node.left) + 1 + size(node.right);

            return balance(node);
        }

        private Node balance(Node node) {
            if (balanceFactor(node) < -1) {
                //right-left case
                if (balanceFactor(node.right) > 0) {
                    node.right = rotateRight(node.right);
                }
                node = rotateLeft(node);
            }

            if (balanceFactor(node) > 1) {
                //left-right case
                if (balanceFactor(node.left) < 0) {
                    node.left = rotateLeft(node.left);
                }
                node = rotateRight(node);
            }

            return node;
        }

        /**
         * Returns the balance factor of the subtree. The balance factor is defined
         * as the difference in height of the left subtree and right subtree, in
         * this order. Therefore, a subtree with a balance factor of -1, 0 or 1 has
         * the AVL property since the heights of the two child subtrees differ by at
         * most one.
         */
        private int balanceFactor(Node node) {
            if (node == null) {
                return 0;
            }
            return height(node.left) - height(node.right);
        }

        private Node min(Node node) {
            if (node.left == null) {
                return node;
            }

            return min(node.left);
        }

        public int rank(Key key) {
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
                return node.right;
            }

            node.left = deleteMin(node.left);

            node.size = size(node.left) + 1 + size(node.right);
            node.height = 1 + Math.max(height(node.left), height(node.right));
            return balance(node);
        }

        public void delete(Key key) {
            if (isEmpty()) {
                return;
            }
            root = delete(root, key);
        }

        private Node delete(Node node, Key key) {
            if (node == null) {
                return null;
            }

            int compare = key.compareTo(node.key);

            if (compare < 0) {
                node.left = delete(node.left, key);
            } else if (compare > 0) {
                node.right = delete(node.right, key);
            } else {
                if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                } else {
                    Node aux = min(node.right);
                    node.key = aux.key;
                    node.value = aux.value;
                    node.right = deleteMin(node.right);
                }
            }

            node.size = size(node.left) + 1 + size(node.right);
            node.height = 1 + Math.max(height(node.left), height(node.right));
            return balance(node);
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

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

        private boolean isEndOfLine(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return c == '\n' || c == -1;
        }

        private interface SpaceCharFilter {
            boolean isSpaceChar(int ch);
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