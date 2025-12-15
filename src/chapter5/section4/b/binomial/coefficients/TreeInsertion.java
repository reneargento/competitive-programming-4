package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 10/12/25.
 */
public class TreeInsertion {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int values = FastReader.nextInt();

        while (values != 0) {
            BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
            for (int i = 0; i < values; i++) {
                binarySearchTree.put(FastReader.nextInt());
            }

            BigInteger totalPermutations = binarySearchTree.countPermutations(binarySearchTree.root);
            outputWriter.printLine(totalPermutations);
            values = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static BigInteger binomialCoefficient(int totalNumbers, int numbersToChoose) {
        BigInteger result = BigInteger.ONE;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result.multiply(BigInteger.valueOf(totalNumbers - i))
                    .divide(BigInteger.valueOf(i + 1));
        }
        return result;
    }

    private static class Node<Key extends Comparable<Key>> {
        private Key key;

        private Node<Key> left;
        private Node<Key> right;

        private int size; //# of nodes in subtree rooted here

        public Node(Key key, int size) {
            this.key = key;
            this.size = size;
        }
    }

    private static class BinarySearchTree<Key extends Comparable<Key>> {
        private Node<Key> root;

        private int size(Node<Key> node) {
            if (node == null) {
                return 0;
            }
            return node.size;
        }

        private BigInteger countPermutations(Node<Key> node) {
            if (node == null) {
                return BigInteger.ONE;
            }

            int childrenSize = size(node.left) + size(node.right);
            BigInteger permutations = binomialCoefficient(childrenSize, size(node.right));
            BigInteger permutationsLeft = countPermutations(node.left);
            BigInteger permutationsRight = countPermutations(node.right);

            permutations = permutations.multiply(permutationsLeft);
            permutations = permutations.multiply(permutationsRight);
            return permutations;
        }

        private void put(Key key) {
            root = put(root, key);
        }

        private Node<Key> put(Node<Key> node, Key key) {
            if (node == null) {
                return new Node<>(key, 1);
            }

            int compare = key.compareTo(node.key);

            if (compare < 0) {
                node.left = put(node.left, key);
            } else {
                node.right = put(node.right, key);
            }

            node.size = size(node.left) + 1 + size(node.right);
            return node;
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

        public void flush() {
            writer.flush();
        }
    }
}
