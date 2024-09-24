package chapter4.section6.d.tree;

import java.io.*;

/**
 * Created by Rene Argento on 30/06/24.
 */
public class TreeRecovery {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            String preorder = data[0];
            String inorder = data[1];

            String postorder = computePostorder(preorder, inorder);
            outputWriter.printLine(postorder);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computePostorder(String preorder, String inorder) {
        StringBuilder postorder = new StringBuilder();
        computePostorder(preorder, inorder, postorder, 0, 0, preorder.length());
        return postorder.toString();
    }

    private static void computePostorder(String preorder, String inorder, StringBuilder postorder,
                                         int preorderStartIndex, int inorderStartIndex, int endIndex) {
        char firstNode = preorder.charAt(preorderStartIndex);
        int firstNodeIndex = findIndex(inorder, inorderStartIndex, endIndex, firstNode);
        int leftSubtreeSize = firstNodeIndex - inorderStartIndex;

        if (leftSubtreeSize != 0) {
            // Recurse to left subtree
            computePostorder(preorder, inorder, postorder, preorderStartIndex + 1, inorderStartIndex,
                    firstNodeIndex);
        }
        if (firstNodeIndex != endIndex - 1) {
            // Recurse to right subtree
            int preorderStartIndexRight = preorderStartIndex + leftSubtreeSize + 1;
            computePostorder(preorder, inorder, postorder, preorderStartIndexRight, firstNodeIndex + 1,
                    endIndex);
        }
        postorder.append(firstNode);
    }

    private static int findIndex(String inorder, int inorderStartIndex, int endIndex, char node) {
        for (int index = inorderStartIndex; index < endIndex; index++) {
            if (inorder.charAt(index) == node) {
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
