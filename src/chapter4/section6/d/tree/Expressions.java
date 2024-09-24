package chapter4.section6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/07/24.
 */
public class Expressions {

    private static class Node {
        char value;
        Node left;
        Node right;

        public Node(char value) {
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String expression = FastReader.getLine();
            String equivalentExpression = computeEquivalentExpression(expression);
            outputWriter.printLine(equivalentExpression);
        }
        outputWriter.flush();
    }

    private static String computeEquivalentExpression(String expression) {
        StringBuilder equivalentExpression = new StringBuilder();

        Node root = buildTree(expression);
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            equivalentExpression.append(node.value);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return equivalentExpression.reverse().toString();
    }

    private static Node buildTree(String expression) {
        Deque<Node> deque = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char value = expression.charAt(i);
            Node node = new Node(value);

            if (Character.isUpperCase(value)) {
                Node rightNode = deque.pop();
                Node leftNode = deque.pop();
                node.right = rightNode;
                node.left = leftNode;
            }
            deque.push(node);
        }
        return deque.peek();
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
