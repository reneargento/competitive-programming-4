package chapter6.section7;

import java.util.*;

/**
 * Created by Rene Argento on 01/10/26.
 */
public class Exercise1SuffixTree {

    public static void main() {
        String string1 = "forgeeksskeegfor";
        int lps1 = longestPalindromicSubstringLength(string1);
        System.out.println("Longest Palindromic Substring length: " + lps1);
        System.out.println("Expected: 10");

        String string2 = "bananas";
        int lps2 = longestPalindromicSubstringLength(string2);
        System.out.println("\nLongest Palindromic Substring length: " + lps2);
        System.out.println("Expected: 5");
    }

    private static int longestPalindromicSubstringLength(String string) {
        if (string.isEmpty()) {
            return 0;
        }
        int longestLength = 1;
        int n = string.length();
        int reverseStartIndex = n + 1;
        String reverse = new StringBuilder(string).reverse().toString();

        SuffixTree suffixTree = new SuffixTree();
        suffixTree.add(string);
        suffixTree.add(reverse);

        suffixTree.prepareLcp();

        // Odd-length palindromes
        for (int center = 0; center < n; center++) {
            int low = 0;
            int high = Math.min(center, n - 1 - center);

            while (low <= high) {
                int radius = (low + high) >>> 1;
                int left = center - radius;
                int right = center + radius;

                int reversePosition = reverseStartIndex + (n - 1 - right);
                int requiredLength = 2 * radius + 1;

                int lcp = suffixTree.lcp(left, reversePosition);
                if (lcp >= requiredLength) {
                    longestLength = Math.max(longestLength, requiredLength);
                    low = radius + 1;
                } else {
                    high = radius - 1;
                }
            }
        }

        // Even-length palindromes
        for (int center = 1; center < n; center++) {
            int low = 1;
            int high = Math.min(center, n - center);

            while (low <= high) {
                int radius = (low + high) >>> 1;
                int left = center - radius;
                int right = center + radius - 1;

                int reversePosition = reverseStartIndex + (n - 1 - right);
                int requiredLength = 2 * radius;

                int lcp = suffixTree.lcp(left, reversePosition);
                if (lcp >= requiredLength) {
                    longestLength = Math.max(longestLength, requiredLength);
                    low = radius + 1;
                } else {
                    high = radius - 1;
                }
            }
        }
        return longestLength;
    }

    private static class SuffixTree {
        private static final int FIRST_TERMINAL = 65536;

        private static class Node {
            int start;
            int[] end;
            Node suffixLink;
            Map<Integer, Node> children = new HashMap<>();
            int suffixStart = -1;

            Node(int start, int[] end) {
                this.start = start;
                this.end = end;
            }

            int edgeLength() {
                return end[0] - start + 1;
            }
        }

        private final List<Integer> text = new ArrayList<>();
        private final Node root;

        private Node activeNode;
        private int activeEdge;
        private int activeLength;

        private int remainingSuffixCount;

        private int nextTerminal = FIRST_TERMINAL;

        /*
         * LCP / Euler tour / RMQ.
         */
        private boolean lcpReady;
        private int[] firstOccurrence;
        private int[] eulerDepth;
        private int[] log;
        private int[][] sparseTable;

        private final IdentityHashMap<Node, Integer> nodeIds = new IdentityHashMap<>();

        private int nextNodeId;

        SuffixTree() {
            root = new Node(-1, new int[]{ -1 });
            root.suffixLink = root;
            activeNode = root;
        }

        private void add(String string) {
            int[] currentLeafEnd = new int[]{ -1 };
            for (int i = 0; i < string.length(); i++) {
                extend(string.charAt(i), currentLeafEnd);
            }

            extend(nextTerminal++, currentLeafEnd);
            lcpReady = false;
        }

        private void extend(int symbol, int[] leafEnd) {
            text.add(symbol);
            int position = text.size() - 1;
            leafEnd[0] = position;
            remainingSuffixCount++;
            Node lastCreatedInternalNode = null;

            while (remainingSuffixCount > 0) {
                if (activeLength == 0) {
                    activeEdge = position;
                }
                int activeEdgeSymbol = text.get(activeEdge);

                Node next = activeNode.children.get(activeEdgeSymbol);

                if (next == null) {
                    Node leaf = new Node(position, leafEnd);
                    leaf.suffixStart = position - remainingSuffixCount + 1;
                    activeNode.children.put(activeEdgeSymbol, leaf);

                    if (lastCreatedInternalNode != null) {
                        lastCreatedInternalNode.suffixLink = activeNode;
                        lastCreatedInternalNode = null;
                    }
                } else {
                    if (walkDown(next)) {
                        continue;
                    }

                    int nextSymbol = text.get(next.start + activeLength);
                    if (nextSymbol == symbol) {
                        activeLength++;
                        if (lastCreatedInternalNode != null) {
                            lastCreatedInternalNode.suffixLink = activeNode;
                        }
                        break;
                    }

                    int[] splitEnd = { next.start + activeLength - 1 };
                    Node split = new Node(next.start, splitEnd);
                    activeNode.children.put(activeEdgeSymbol, split);

                    Node leaf = new Node(position, leafEnd);
                    leaf.suffixStart = position - remainingSuffixCount + 1;

                    split.children.put(symbol, leaf);
                    next.start += activeLength;
                    split.children.put(text.get(next.start), next);

                    if (lastCreatedInternalNode != null) {
                        lastCreatedInternalNode.suffixLink = split;
                    }
                    lastCreatedInternalNode = split;
                }

                remainingSuffixCount--;

                if (activeNode == root && activeLength > 0) {
                    activeLength--;
                    activeEdge = position - remainingSuffixCount + 1;
                } else if (activeNode != root) {
                    activeNode = activeNode.suffixLink;
                }
            }
        }

        private boolean walkDown(Node next) {
            if (activeLength >= next.edgeLength()) {
                activeEdge = next.start + next.edgeLength();
                activeLength -= next.edgeLength();
                activeNode = next;
                return true;
            }
            return false;
        }

        /*
         * =========================================================
         * LCP
         * =========================================================
         */
        private void prepareLcp() {
            if (lcpReady) {
                return;
            }

            int size = text.size();
            firstOccurrence = new int[size];
            Arrays.fill(firstOccurrence, -1);

            List<Integer> euler = new ArrayList<>();
            List<Integer> depths = new ArrayList<>();

            nodeIds.clear();
            nextNodeId = 0;

            dfsEuler(root, 0, euler, depths);
            eulerDepth = new int[depths.size()];
            for (int i = 0; i < depths.size(); i++) {
                eulerDepth[i] = depths.get(i);
            }
            buildSparseTable();
            lcpReady = true;
        }

        private void dfsEuler(Node node, int depth, List<Integer> euler, List<Integer> depths) {
            Integer id = nodeIds.get(node);
            if (id == null) {
                id = nextNodeId++;
                nodeIds.put(node, id);
            }

            /*
             * A leaf corresponds to a suffix.
             * Record the first Euler occurrence of that leaf.
             */
            if (node.suffixStart >= 0
                    && firstOccurrence[node.suffixStart] == -1) {
                firstOccurrence[node.suffixStart] = euler.size();
            }

            euler.add(id);
            depths.add(depth);

            for (Node child : node.children.values()) {
                dfsEuler(child, depth + child.edgeLength(), euler, depths);
                euler.add(id);
                depths.add(depth);
            }
        }

        private void buildSparseTable() {
            int n = eulerDepth.length;
            log = new int[n + 1];

            for (int i = 2; i <= n; i++) {
                log[i] = log[i / 2] + 1;
            }
            int levels = log[n] + 1;
            sparseTable = new int[levels][n];
            System.arraycopy(eulerDepth, 0, sparseTable[0], 0, n);

            for (int k = 1; k < levels; k++) {
                int length = 1 << k;
                int half = length >> 1;

                for (int i = 0; i + length <= n; i++) {
                    sparseTable[k][i] = Math.min(sparseTable[k - 1][i], sparseTable[k - 1][i + half]);
                }
            }
        }

        /**
         * LCP of suffixes starting at positions a and b in the combined text.
         */
        private int lcp(int a, int b) {
            if (a == b) {
                return text.size() - a;
            }

            prepareLcp();
            int ea = firstOccurrence[a];
            int eb = firstOccurrence[b];

            if (ea < 0 || eb < 0) {
                throw new IllegalArgumentException("Position is not a suffix start: " + a + ", " + b);
            }

            if (ea > eb) {
                int temp = ea;
                ea = eb;
                eb = temp;
            }

            int length = eb - ea + 1;
            int k = log[length];
            return Math.min(sparseTable[k][ea], sparseTable[k][eb - (1 << k) + 1]);
        }
    }
}
