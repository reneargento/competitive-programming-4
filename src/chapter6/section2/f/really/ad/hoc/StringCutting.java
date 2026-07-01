package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/07/2026.
 */
public class StringCutting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] cuts = new int[FastReader.nextInt()];
            for (int i = 0; i < cuts.length; i++) {
                cuts[i] = FastReader.nextInt();
            }
            String string = FastReader.getLine();
            int totalCutCost = computeTotalCutCost(cuts, string);
            outputWriter.printLine(totalCutCost);
        }
        outputWriter.flush();
    }

    private static int computeTotalCutCost(int[] cuts, String string) {
        int totalCutCost = 0;
        BinarySearchTree<Integer, Integer> binarySearchTree = new BinarySearchTree<>();

        for (int cut :  cuts) {
            int startIndex;
            Integer floorKey = binarySearchTree.floor(cut);
            if (floorKey == null) {
                startIndex = 0;
            } else {
                startIndex = floorKey;
            }

            int endIndex;
            Integer ceilingKey = binarySearchTree.ceiling(cut);
            if (ceilingKey == null) {
                endIndex = string.length() - 1;
            } else {
                endIndex = ceilingKey - 1;
            }

            binarySearchTree.put(cut, 0);
            int cost = computeCutCost(startIndex, endIndex, cut, string);
            totalCutCost += cost;
        }
        return totalCutCost;
    }

    private static int computeCutCost(int startIndex, int endIndex, int cut, String string) {
        int cost = 0;

        Set<Character> charactersInSubstring1 = getCharactersInSubstring(string, startIndex, cut - 1);
        Set<Character> charactersInSubstring2 = getCharactersInSubstring(string, cut, endIndex);
        cost += countCharactersInOnlyOneSet(charactersInSubstring1, charactersInSubstring2);
        cost += countCharactersInOnlyOneSet(charactersInSubstring2, charactersInSubstring1);
        return cost;
    }

    private static Set<Character> getCharactersInSubstring(String string, int startIndex, int endIndex) {
        Set<Character> charactersInSubstring = new HashSet<>();

        for (int i = startIndex; i <= endIndex; i++) {
            charactersInSubstring.add(string.charAt(i));
        }
        return  charactersInSubstring;
    }

    private static int countCharactersInOnlyOneSet(Set<Character> charactersInSubstring1,
                                                   Set<Character> charactersInSubstring2) {
        int count = 0;
        for (Character character : charactersInSubstring1) {
            if (!charactersInSubstring2.contains(character)) {
                count++;
            }
        }
        return count;
    }

    private static class BinarySearchTree<Key extends Comparable<Key>, Value> {

        private class Node {
            private Key key;
            private Value value;

            private Node left;
            private Node right;

            public Node(Key key, Value value) {
                this.key = key;
                this.value = value;
            }
        }

        private Node root;

        public Value get(Key key) {
            if (key == null) {
                return null;
            }
            return get(root, key);
        }

        private Value get(Node node, Key key) {
            if (node == null) {
                return null;
            }

            int compare = key.compareTo(node.key);
            if (compare < 0) {
                return get(node.left, key);
            } else if (compare > 0) {
                return get(node.right, key);
            } else {
                return node.value;
            }
        }

        public void put(Key key, Value value) {
            if (key == null) {
                return;
            }
            root = put(root, key, value);
        }

        private Node put(Node node, Key key, Value value) {
            if (node == null) {
                return new Node(key, value);
            }

            int compare = key.compareTo(node.key);

            if (compare < 0) {
                node.left = put(node.left, key, value);
            } else if (compare > 0) {
                node.right = put(node.right, key, value);
            } else {
                node.value = value;
            }
            return node;
        }

        // Returns the highest key in the symbol table smaller than or equal to key.
        public Key floor(Key key) {
            Node node = floor(root, key);
            if (node == null) {
                return null;
            }
            return node.key;
        }

        private Node floor(Node node, Key key) {
            if (node == null) {
                return null;
            }

            int compare = key.compareTo(node.key);

            if (compare == 0) {
                return node;
            } else if (compare < 0) {
                return floor(node.left, key);
            } else {
                Node rightNode = floor(node.right, key);
                if (rightNode != null) {
                    return rightNode;
                } else {
                    return node;
                }
            }
        }

        // Returns the smallest key in the symbol table greater than or equal to key.
        public Key ceiling(Key key) {
            Node node = ceiling(root, key);
            if (node == null) {
                return null;
            }
            return node.key;
        }

        private Node ceiling(Node node, Key key) {
            if (node == null) {
                return null;
            }

            int compare = key.compareTo(node.key);

            if (compare == 0) {
                return node;
            } else if (compare > 0) {
                return ceiling(node.right, key);
            } else {
                Node leftNode = ceiling(node.left, key);
                if (leftNode != null) {
                    return leftNode;
                } else {
                    return node;
                }
            }
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