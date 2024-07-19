package chapter4.session6.d.tree;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Rene Argento on 18/07/24.
 */
public class TreesOnTheLevel {

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node() {
            value = UNDEFINED;
        }
    }

    private static final int UNDEFINED = -1;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            Node root = new Node();
            boolean isValidTree = true;
            boolean inputFinished = false;

            while (line != null) {
                if (inputFinished) {
                    root = new Node();
                    isValidTree = true;
                    inputFinished = false;
                }

                List<String> tokens = getWords(line);
                for (String nodeValue : tokens) {
                    if (nodeValue.equals("()")) {
                        inputFinished = true;
                        break;
                    }

                    String[] data = nodeValue.substring(1, nodeValue.length() - 1).split(",");
                    int value = Integer.parseInt(data[0]);
                    String path;
                    if (data.length > 1) {
                        path = data[1];
                    } else {
                        path = "";
                    }

                    boolean buildOk = addNode(root, value, path);
                    if (!buildOk) {
                        isValidTree = false;
                    }
                }

                if (inputFinished) {
                    if (!isValidTree) {
                        outputWriter.printLine("not complete");
                    } else {
                        List<Integer> levelOrderTraversal = computeLevelOrderTraversal(root);
                        if (levelOrderTraversal == null) {
                            outputWriter.printLine("not complete");
                        } else {
                            outputWriter.print(levelOrderTraversal.get(0));
                            for (int i = 1; i < levelOrderTraversal.size(); i++) {
                                outputWriter.print(" " + levelOrderTraversal.get(i));
                            }
                            outputWriter.printLine();
                        }
                    }
                }
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static boolean addNode(Node node, int value, String path) {
        for (char step : path.toCharArray()) {
            if (step == 'L') {
                if (node.left == null) {
                    node.left = new Node();
                }
                node = node.left;
            } else {
                if (node.right == null) {
                    node.right = new Node();
                }
                node = node.right;
            }
        }

        if (node.value != UNDEFINED) {
            return false;
        }
        node.value = value;
        return true;
    }

    private static List<Integer> computeLevelOrderTraversal(Node root) {
        List<Integer> levelOrderTraversal = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.value == UNDEFINED) {
                return null;
            }
            levelOrderTraversal.add(node.value);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return levelOrderTraversal;
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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
