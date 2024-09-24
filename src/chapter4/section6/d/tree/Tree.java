package chapter4.section6.d.tree;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 18/07/24.
 */
public class Tree {

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static class PostorderIndex {
        int value;

        public PostorderIndex(int value) {
            this.value = value;
        }
    }

    private static class Result {
        int totalSum;
        int leafValue;

        public Result(int totalSum, int leafValue) {
            this.totalSum = totalSum;
            this.leafValue = leafValue;
        }
    }

    private static final int INFINITE = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] inorder = line.split(" ");
            String[] postorder = FastReader.getLine().split(" ");

            int leastLeafValue = computeLeastLeafValue(inorder, postorder);
            outputWriter.printLine(leastLeafValue);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeLeastLeafValue(String[] inorder, String[] postorder) {
        Node root = buildTree(inorder, postorder);
        return computeLeastLeafValue(root, 0).leafValue;
    }

    private static Node buildTree(String[] inorder, String[] postorder) {
        Map<Integer, Integer> nodeToIndexInorderMap = new HashMap<>();
        for (int index = 0; index < inorder.length; index++) {
            nodeToIndexInorderMap.put(Integer.parseInt(inorder[index]), index);
        }
        PostorderIndex postorderIndex = new PostorderIndex(postorder.length - 1);
        return buildTree(postorder, nodeToIndexInorderMap, postorderIndex, 0, inorder.length - 1);
    }

    private static Node buildTree(String[] postorder, Map<Integer, Integer> nodeToIndexInorderMap,
                                  PostorderIndex postorderIndex, int inorderStartIndex, int inorderEndIndex) {
        if (inorderStartIndex > inorderEndIndex) {
            return null;
        }

        int currentValue = Integer.parseInt(postorder[postorderIndex.value]);
        postorderIndex.value--;
        Node node = new Node(currentValue);

        // If node has no children, return
        if (inorderStartIndex == inorderEndIndex) {
            return node;
        }

        int inorderIndex = nodeToIndexInorderMap.get(currentValue);
        node.right = buildTree(postorder, nodeToIndexInorderMap, postorderIndex, inorderIndex + 1, inorderEndIndex);
        node.left = buildTree(postorder, nodeToIndexInorderMap, postorderIndex, inorderStartIndex, inorderIndex - 1);
        return node;
    }

    private static Result computeLeastLeafValue(Node node, int currentSum) {
        if (node.left == null && node.right == null) {
            return new Result(currentSum + node.value, node.value);
        }

        Result leftResult = new Result(INFINITE, INFINITE);
        Result rightResult  = new Result(INFINITE, INFINITE);
        if (node.left != null) {
            leftResult = computeLeastLeafValue(node.left, currentSum + node.value);
        }
        if (node.right != null) {
            rightResult = computeLeastLeafValue(node.right, currentSum + node.value);
        }

        if (leftResult.totalSum < rightResult.totalSum
                || (leftResult.totalSum == rightResult.totalSum
                    && leftResult.leafValue < rightResult.leafValue)) {
            return leftResult;
        } else {
            return rightResult;
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void flush() {
            writer.flush();
        }
    }
}
