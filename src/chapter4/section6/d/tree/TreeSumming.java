package chapter4.section6.d.tree;

import java.io.*;

/**
 * Created by Rene Argento on 09/07/24.
 */
public class TreeSumming {

    private static class Node {
        long value;
        Node left;
        Node right;
        int lastIndex;

        public Node(long value, Node left, Node right, int lastIndex) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.lastIndex = lastIndex;
        }
    }

    private static final int NULL = -1000000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            StringBuilder expression = new StringBuilder(line);
            String nextLine = FastReader.getLine();

            while (nextLine != null &&
                    (nextLine.isEmpty()
                            || (!Character.isDigit(nextLine.charAt(0))
                                 && nextLine.charAt(0) != '-'))) {
                expression.append(nextLine);
                nextLine = FastReader.getLine();
            }

            String cleanExpression = expression.toString().replaceAll(" ", "");
            String result = isTherePathWithSum(cleanExpression);

            outputWriter.printLine(result);
            line = nextLine;
        }
        outputWriter.flush();
    }

    private static String isTherePathWithSum(String fullExpression) {
        int startIndex = fullExpression.indexOf('(');
        String numberString = fullExpression.substring(0, startIndex);
        long target = Long.parseLong(numberString);
        String expression = fullExpression.substring(startIndex);

        Node tree = createTree(expression, 0);
        if (tree.left == null && tree.right == null) {
            return "no";
        }
        boolean isTherePathWithSum = isTherePathWithSum(tree, target, 0);
        return isTherePathWithSum ? "yes" : "no";
    }

    private static Node createTree(String expression, int index) {
        if (index >= expression.length()) {
            return null;
        }
        char value = expression.charAt(index);

        if (value == '(') {
            Node node = createTree(expression, index + 1);
            return new Node(node.value, node.left, node.right, node.lastIndex + 1);
        } else if (value == ')') {
            return new Node(NULL, null, null, index + 1);
        } else {
            String valueString = String.valueOf(value);
            StringBuilder number = new StringBuilder(valueString);
            for (int i = index + 1; i < expression.length(); i++) {
                char currentChar = expression.charAt(i);

                if (Character.isDigit(currentChar)) {
                    number.append(Character.getNumericValue(currentChar));
                } else {
                    break;
                }
            }
            long completeValue = Long.parseLong(number.toString());

            Node leftNode = createTree(expression, index + number.length());
            Node rightNode = createTree(expression, leftNode.lastIndex);

            return new Node(completeValue, leftNode, rightNode, rightNode.lastIndex + 1);
        }
    }

    private static boolean isTherePathWithSum(Node node, long target, long currentSum) {
        if (node.left.value == NULL && node.right.value == NULL) {
            return currentSum + node.value == target;
        }

        boolean leftNodeResult = false;
        boolean rightNodeResult = false;

        if (node.left.value != NULL) {
            leftNodeResult = isTherePathWithSum(node.left, target, currentSum + node.value);
        }
        if (!leftNodeResult && node.right.value != NULL) {
            rightNodeResult = isTherePathWithSum(node.right, target, currentSum + node.value);
        }
        return leftNodeResult || rightNodeResult;
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
