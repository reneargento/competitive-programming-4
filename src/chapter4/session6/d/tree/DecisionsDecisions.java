package chapter4.session6.d.tree;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/07/24.
 */
public class DecisionsDecisions {

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static final int UNDEFINED = -1;
    private static final int BOTH_ZERO_ONE = 3;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int levels = FastReader.nextInt();
        int valuesNumber = (int) Math.pow(2, levels);
        int[] values = new int[valuesNumber];

        for (int i = 0; i < values.length; i++) {
            values[i] = FastReader.nextInt();
        }

        int minBDDVertices = computeMinBDDVertices(values, levels);
        outputWriter.printLine(minBDDVertices);
        outputWriter.flush();
    }

    private static int computeMinBDDVertices(int[] values, int levels) {
        Node root = buildTree(values, levels);
        compressTree(root);
        return countNodes(root);
    }

    private static Node buildTree(int[] values, int levels) {
        Node root = new Node(UNDEFINED);

        for (int index = 0; index < values.length; index++) {
            String bits = getReverseBinaryRepresentation(index, levels);
            buildTree(values[index], bits, 0, root);
        }
        return root;
    }

    private static void buildTree(int value, String bits, int bitIndex, Node node) {
        if (bitIndex == bits.length()) {
            node.value = value;
            return;
        }

        if (bits.charAt(bitIndex) == '0') {
            if (node.left == null) {
                node.left = new Node(UNDEFINED);
            }
            buildTree(value, bits, bitIndex + 1, node.left);
        } else {
            if (node.right == null) {
                node.right = new Node(UNDEFINED);
            }
            buildTree(value, bits, bitIndex + 1, node.right);
        }
    }

    private static String getReverseBinaryRepresentation(int value, int length) {
        StringBuilder bits = new StringBuilder();

        do {
            bits.append(value % 2);
            value /= 2;
        } while (value > 0);

        while (bits.length() != length) {
            bits.append("0");
        }
        return bits.toString();
    }

    private static int compressTree(Node node) {
        if (node.left == null && node.right == null) {
            return node.value;
        }

        int valueFromLeft = UNDEFINED;
        int valueFromRight = UNDEFINED;
        if (node.left != null) {
            valueFromLeft = compressTree(node.left);
        }
        if (node.right != null) {
            valueFromRight = compressTree(node.right);
        }

        if (valueFromLeft == valueFromRight && valueFromLeft != BOTH_ZERO_ONE) {
            node.left = null;
            node.right = null;
            node.value = valueFromLeft;
            return valueFromLeft;
        }
        return BOTH_ZERO_ONE;
    }

    private static int countNodes(Node node) {
        if (node.left == null && node.right == null) {
            return 1;
        }

        int count = 1;
        if (node.left != null) {
            count += countNodes(node.left);
        }
        if (node.right != null) {
            count += countNodes(node.right);
        }
        return count;
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
