package chapter4.section6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/07/24.
 */
public class PreInAndPost {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int nodes = FastReader.nextInt();
            Map<Character, Integer> nodeValueToIdMap = new HashMap<>();
            Character[] nodeIdToValue = new Character[nodes];

            String preorderValues = FastReader.next();
            String inorderValues = FastReader.next();

            int[] preorder = getNodeIds(nodeValueToIdMap, nodeIdToValue, preorderValues);
            int[] inorder = getNodeIds(nodeValueToIdMap, nodeIdToValue, inorderValues);

            List<Integer> postorder = computePostorder(preorder, inorder);
            for (int nodeId : postorder) {
                outputWriter.print(nodeIdToValue[nodeId]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static int[] getNodeIds(Map<Character, Integer> nodeValueToIdMap, Character[] nodeIdToValue, String values) {
        int[] nodeIds = new int[values.length()];

        for (int i = 0; i < values.length(); i++) {
            char value = values.charAt(i);

            if (!nodeValueToIdMap.containsKey(value)) {
                int nodeId = nodeValueToIdMap.size();
                nodeValueToIdMap.put(value, nodeId);
                nodeIdToValue[nodeId] = value;
            }
            nodeIds[i] = nodeValueToIdMap.get(value);
        }
        return nodeIds;
    }

    private static List<Integer> computePostorder(int[] preorder, int[] inorder) {
        List<Integer> postorder = new ArrayList<>();
        computePostorder(preorder, inorder, postorder, 0, 0, preorder.length);
        return postorder;
    }

    private static void computePostorder(int[] preorder, int[] inorder, List<Integer> postorder,
                                         int preorderStartIndex, int inorderStartIndex, int endIndex) {
        int firstNode = preorder[preorderStartIndex];
        int firstNodeIndex = findNodeIndex(inorder, inorderStartIndex, endIndex, firstNode);
        int leftSubtreeSize = firstNodeIndex - inorderStartIndex;

        if (leftSubtreeSize != 0) {
            computePostorder(preorder, inorder, postorder, preorderStartIndex + 1, inorderStartIndex,
                    firstNodeIndex);
        }
        if (firstNodeIndex != endIndex - 1) {
            computePostorder(preorder, inorder, postorder, preorderStartIndex + leftSubtreeSize + 1,
                    firstNodeIndex + 1, endIndex);
        }
        postorder.add(firstNode);
    }

    private static int findNodeIndex(int[] inorder, int inorderStartIndex, int endIndex, int node) {
        for (int index = inorderStartIndex; index < endIndex; index++) {
            if (inorder[index] == node) {
                return index;
            }
        }
        return -1;
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
