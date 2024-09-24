package chapter4.section6.d.tree;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 02/07/24.
 */
public class BinarySearchTree {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        List<Integer> preorderList = new ArrayList<>();
        List<Integer> inorderList = new ArrayList<>();
        while (line != null) {
            int key = Integer.parseInt(line);
            preorderList.add(key);
            inorderList.add(key);
            line = FastReader.getLine();
        }

        Integer[] preorder = preorderList.toArray(new Integer[0]);
        Integer[] inorder = inorderList.toArray(new Integer[0]);
        Arrays.sort(inorder);
        List<Integer> postorder = computePostorder(preorder, inorder);

        for (Integer key : postorder) {
            outputWriter.printLine(key);
        }
        outputWriter.flush();
    }

    public static List<Integer> computePostorder(Integer[] preorder, Integer[] inorder) {
        List<Integer> postorder = new ArrayList<>();
        computePostorder(preorder, inorder, postorder, 0, 0, preorder.length);
        return postorder;
    }

    private static void computePostorder(Integer[] preorder, Integer[] inorder, List<Integer> postorder,
                                         int preorderStartIndex, int inorderStartIndex, int endIndex) {
        int firstNode = preorder[preorderStartIndex];
        int firstNodeIndex = findNodeIndex(inorder, inorderStartIndex, endIndex, firstNode);
        int leftSubtreeSize = firstNodeIndex - inorderStartIndex;

        if (leftSubtreeSize != 0) {
            // Recurse to left subtree
            computePostorder(preorder, inorder, postorder, preorderStartIndex + 1, inorderStartIndex,
                    firstNodeIndex);
        }
        if (firstNodeIndex != endIndex - 1) {
            // Recurse to right subtree
            computePostorder(preorder, inorder, postorder, preorderStartIndex + leftSubtreeSize + 1,
                    firstNodeIndex + 1, endIndex);
        }
        postorder.add(firstNode);
    }

    private static int findNodeIndex(Integer[] inorder, int inorderStartIndex, int endIndex, int node) {
        for (int index = inorderStartIndex; index < endIndex; index++) {
            if (inorder[index] == node) {
                return index;
            }
        }
        return -1;
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
