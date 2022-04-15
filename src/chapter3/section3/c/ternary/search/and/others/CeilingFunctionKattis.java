package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/04/22.
 */
public class CeilingFunctionKattis {

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[][] prototypeLayers = new int[FastReader.nextInt()][FastReader.nextInt()];

        for (int prototype = 0; prototype < prototypeLayers.length; prototype++) {
            for (int layer = 0; layer < prototypeLayers[prototype].length; layer++) {
                prototypeLayers[prototype][layer] = FastReader.nextInt();
            }
        }

        int treeShapes = countTreeShapes(prototypeLayers);
        outputWriter.printLine(treeShapes);
        outputWriter.flush();
    }

    private static int countTreeShapes(int[][] prototypeLayers) {
        Node[] trees = getTrees(prototypeLayers);
        boolean[] visited = new boolean[prototypeLayers.length];
        int treeShapes = 0;

        for (int i = 0; i < trees.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            processShape(trees, i, visited);
            treeShapes++;
        }
        return treeShapes;
    }

    private static Node[] getTrees(int[][] prototypeLayers) {
        Node[] trees = new Node[prototypeLayers.length];

        for (int i = 0; i < prototypeLayers.length; i++) {
            int[] prototype = prototypeLayers[i];
            Node root = null;
            for (int layer : prototype) {
                root = insertNode(root, layer);
            }
            trees[i] = root;
        }
        return trees;
    }

    private static Node insertNode(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.value) {
            node.left = insertNode(node.left, key);
        } else {
            node.right = insertNode(node.right, key);
        }
        return node;
    }

    private static void processShape(Node[] trees, int index, boolean[] visited) {
        Node node = trees[index];
        for (int i = 0; i < trees.length; i++) {
            if (visited[i]) {
                continue;
            }
            if (isEqual(node, trees[i])) {
                visited[i] = true;
            }
        }
    }

    private static boolean isEqual(Node node1, Node node2) {
        if ((node1 == null && node2 != null)
                || (node1 != null && node2 == null)) {
            return false;
        }
        if (node1 == null) {
            return true;
        }

        if ((node1.left == null && node2.left != null)
                || (node1.left != null && node2.left == null)) {
            return false;
        }
        if ((node1.right == null && node2.right != null)
                || (node1.right != null && node2.right == null)) {
            return false;
        }
        return isEqual(node1.left, node2.left) && isEqual(node1.right, node2.right);
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
