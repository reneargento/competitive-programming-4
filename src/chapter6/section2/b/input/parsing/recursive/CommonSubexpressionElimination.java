package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/05/26.
 */
public class CommonSubexpressionElimination {

    private static class Node {
        String name;
        Node left, right;
        int canonicalId;
    }

    private static class Key {
        String fName;
        int left, right;

        Key(String fName, int left, int right) {
            this.fName = fName;
            this.left = left;
            this.right = right;
        }

        public boolean equals(Object o) {
            Key k = (Key) o;
            return left == k.left && right == k.right && fName.equals(k.fName);
        }

        public int hashCode() {
            return fName.hashCode() * 31 * 31 + left * 31 + right;
        }
    }

    private static int parsePosition;
    private static int nextCanonicalId;
    private static int nextId;

    public static void main(String[] args) throws Exception {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String expression = FastReader.getLine();
            parsePosition = 0;

            // Step 1: parse
            Node root = parse(expression);

            // Step 2: canonical IDs
            Map<Key, Integer> keyToCanonicalIdMap = new HashMap<>();
            nextCanonicalId = 1;
            computeCanonicalIds(keyToCanonicalIdMap, root);

            // Step 3: output
            Map<Integer, Integer> canonicalIdToIdMap = new HashMap<>();
            nextId = 1;
            String result = print(canonicalIdToIdMap, root);

            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static Node parse(String expression) {
        int start = parsePosition;
        while (parsePosition < expression.length()
                && expression.charAt(parsePosition) != '('
                && expression.charAt(parsePosition) != ','
                && expression.charAt(parsePosition) != ')') {
            parsePosition++;
        }

        Node node = new Node();
        node.name = expression.substring(start, parsePosition);

        if (parsePosition < expression.length() && expression.charAt(parsePosition) == '(') {
            parsePosition++; // '('
            node.left = parse(expression);
            parsePosition++; // ','
            node.right = parse(expression);
            parsePosition++; // ')'
        }
        return node;
    }

    private static int computeCanonicalIds(Map<Key, Integer> keyToCanonicalIdMap, Node node) {
        if (node.canonicalId != 0) {
            return node.canonicalId;
        }

        if (node.left == null) {
            Key key = new Key(node.name, 0, 0);
            node.canonicalId = keyToCanonicalIdMap.computeIfAbsent(key, k -> nextCanonicalId++);
            return node.canonicalId;
        }

        int left = computeCanonicalIds(keyToCanonicalIdMap, node.left);
        int right = computeCanonicalIds(keyToCanonicalIdMap, node.right);
        Key key = new Key(node.name, left, right);
        node.canonicalId = keyToCanonicalIdMap.computeIfAbsent(key, k -> nextCanonicalId++);
        return node.canonicalId;
    }

    private static String print(Map<Integer, Integer> canonicalIdToIdMap, Node node) {
        int canonicalId = node.canonicalId;
        if (canonicalIdToIdMap.containsKey(canonicalId)) {
            return String.valueOf(canonicalIdToIdMap.get(canonicalId));
        }

        int id = nextId++;
        canonicalIdToIdMap.put(canonicalId, id);
        if (node.left == null) {
            return node.name;
        }
        return node.name + "(" + print(canonicalIdToIdMap, node.left) + "," + print(canonicalIdToIdMap, node.right) + ")";
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
