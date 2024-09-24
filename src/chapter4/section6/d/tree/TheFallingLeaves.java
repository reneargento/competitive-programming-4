package chapter4.section6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 19/07/24.
 */
public class TheFallingLeaves {

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static class LeafPile implements Comparable<LeafPile> {
        int location;
        int leavesSum;

        public LeafPile(int location, int leavesSum) {
            this.location = location;
            this.leavesSum = leavesSum;
        }

        @Override
        public int compareTo(LeafPile other) {
            return Integer.compare(location, other.location);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseId = 1;
        while (true) {
            List<LeafPile> leavesNumber = computeNumberOfLeaves();
            if (leavesNumber == null) {
                break;
            }

            outputWriter.printLine(String.format("Case %d:", caseId));
            outputWriter.print(leavesNumber.get(0).leavesSum);
            for (int i = 1; i < leavesNumber.size(); i++) {
                outputWriter.print(" " + leavesNumber.get(i).leavesSum);
            }
            outputWriter.printLine();
            outputWriter.printLine();

            caseId++;
        }
        outputWriter.flush();
    }

    private static List<LeafPile> computeNumberOfLeaves() throws IOException {
        Node root = buildTree();
        if (root == null) {
            return null;
        }

        Map<Integer, Integer> leafPilesMap = new HashMap<>();
        sumLeaves(root, leafPilesMap, 0);

        List<LeafPile> leafPilesList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> locationSumEntry : leafPilesMap.entrySet()) {
            leafPilesList.add(new LeafPile(locationSumEntry.getKey(), locationSumEntry.getValue()));
        }
        Collections.sort(leafPilesList);
        return leafPilesList;
    }

    private static Node buildTree() throws IOException {
        int value = FastReader.nextInt();
        if (value == -1) {
            return null;
        }

        Node node = new Node(value);
        node.left = buildTree();
        node.right = buildTree();
        return node;
    }

    private static void sumLeaves(Node node, Map<Integer, Integer> leafPilesMap, int location) {
        if (node == null) {
            return;
        }
        int currentLeaves = leafPilesMap.getOrDefault(location, 0);
        leafPilesMap.put(location, currentLeaves + node.value);

        sumLeaves(node.left, leafPilesMap, location - 1);
        sumLeaves(node.right, leafPilesMap, location + 1);
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
