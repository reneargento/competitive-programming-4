package chapter6.section5;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/09/2026.
 */
public class ShortestNames {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] names = new String[FastReader.nextInt()];
            for (int i = 0; i < names.length; i++) {
                names[i] = FastReader.next();
            }
            int shortestCharactersNeeded = computeShortestCharacters(names);
            outputWriter.printLine(shortestCharactersNeeded);
        }
        outputWriter.flush();
    }

    private static int computeShortestCharacters(String[] names) {
        Trie trie = new Trie();
        for (String name : names) {
            trie.add(name);
        }

        int shortestCharactersNeeded = 0;
        for (String name : names) {
            int longestSharedPrefix = trie.longestSharedPrefixOf(name);
            shortestCharactersNeeded += (longestSharedPrefix + 1);
        }
        return shortestCharactersNeeded;
    }

    private static class Trie {

        private static class Node {
            private final Map<Character, Node> next = new HashMap<>();
            private boolean isKey;
            private int size;
        }

        private Node root = new Node();

        public boolean contains(String key) {
            Node node = getNode(root, key, 0);
            return node != null && node.isKey;
        }

        private Node getNode(Node node, String key, int digit) {
            if (node == null) {
                return null;
            }

            if (digit == key.length()) {
                return node;
            }

            char nextChar = key.charAt(digit);
            return getNode(node.next.get(nextChar), key, digit + 1);
        }

        public void add(String key) {
            if (contains(key)) {
                return;
            }
            root = add(root, key, 0);
        }

        private Node add(Node node, String key, int digit) {
            if (node == null) {
                node = new Node();
            }
            node.size++;

            if (digit == key.length()) {
                node.isKey = true;
                return node;
            }

            char nextChar = key.charAt(digit);

            Node nextNode = add(node.next.get(nextChar), key, digit + 1);
            node.next.put(nextChar, nextNode);
            return node;
        }

        public int longestSharedPrefixOf(String query) {
            return search(root, query, 0, 0);
        }

        private int search(Node node, String query, int digit, int length) {
            if (digit == query.length()) {
                return length;
            }

            char nextChar = query.charAt(digit);
            Node nextNode = node.next.get(nextChar);
            if (nextNode.size == 1) {
                return length;
            }
            return search(nextNode, query, digit + 1, length + 1);
        }
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