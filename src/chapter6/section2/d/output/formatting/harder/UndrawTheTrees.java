package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/06/2026.
 */
public class UndrawTheTrees {

    private static class Node {
        char label;
        List<Node> children;

        public Node(char label) {
            this.label = label;
            children = new ArrayList<>();
        }
    }

    private static class ChildRange {
        int start;
        int end;

        public ChildRange(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            Node root = null;

            Map<Integer, Node> indexToNodeMap = new HashMap<>();
            List<Node> parents = new ArrayList<>();
            List<ChildRange> childRangeList = new ArrayList<>();
            while (!line.equals("#")) {
                int rangeStart = -1;
                boolean isLabelRow = false;
                boolean hasAssociatedParent = false;
                Map<Integer, Node> currentIndexToNodeMap = new HashMap<>();

                line = line + " ";
                for (int i = 0; i < line.length(); i++) {
                    char character = line.charAt(i);

                    if (character == ' ') {
                        if (rangeStart != -1) {
                            childRangeList.add(new ChildRange(rangeStart, i - 1));
                            rangeStart = -1;
                            hasAssociatedParent = false;
                        }
                    } else if (character == '-') {
                        if (rangeStart == -1) {
                            rangeStart = i;

                            if (indexToNodeMap.containsKey(i) && parents.contains(indexToNodeMap.get(i))) {
                                hasAssociatedParent = true;
                            }
                        } else if (hasAssociatedParent && indexToNodeMap.containsKey(i)
                                && parents.contains(indexToNodeMap.get(i))) {
                            childRangeList.add(new ChildRange(rangeStart, i - 1));
                            rangeStart = i;
                        }
                    } else if (character == '|') {
                        Node parent = indexToNodeMap.get(i);
                        parents.add(parent);
                    } else {
                        Node node = new Node(character);
                        if (!parents.isEmpty()) {
                            Node parent = getParent(parents, childRangeList, i);
                            parent.children.add(node);
                        } else {
                            root = node;
                        }
                        currentIndexToNodeMap.put(i, node);
                        isLabelRow = true;
                    }
                }

                if (isLabelRow) {
                    indexToNodeMap = currentIndexToNodeMap;
                    parents = new ArrayList<>();
                    childRangeList = new ArrayList<>();
                }
                line = FastReader.getLine();
            }
            outputWriter.print("(");
            printTree(root, outputWriter);
            outputWriter.printLine(")");
        }
        outputWriter.flush();
    }

    private static void printTree(Node node, OutputWriter outputWriter) {
        if (node == null) {
            return;
        }
        outputWriter.print(node.label);
        outputWriter.print("(");
        if (!node.children.isEmpty()) {
            for (Node child : node.children) {
                printTree(child, outputWriter);
            }
        }
        outputWriter.print(")");
    }

    private static Node getParent(List<Node> parents, List<ChildRange> childRangeList, int index) {
        for (int i = 0; i < childRangeList.size(); i++) {
            ChildRange childRange = childRangeList.get(i);
            if (childRange.start <= index && index <= childRange.end) {
                return parents.get(i);
            }
        }
        return null;
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