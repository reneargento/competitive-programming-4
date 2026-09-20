package chapter6.section5;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/09/2026.
 */
public class TypePrinter {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] words = new String[inputReader.nextInt()];
        int totalWordLength = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = inputReader.nextLine();
            totalWordLength += words[i].length();
        }
        computeMinimumOperations(words, totalWordLength, outputWriter);
        outputWriter.flush();
    }

    private static void computeMinimumOperations(String[] words, int totalWordLength, OutputWriter outputWriter) {
        Trie trie = new Trie(totalWordLength);
        String longestWord = "";
        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
            trie.add(word);
        }

        markLongestWordPath(longestWord, trie);

        int minimumOperationsNumber =
                2 * (trie.nodeCount - 1)
                        + words.length
                        - longestWord.length();

        outputWriter.printLine(minimumOperationsNumber);
        computeOperationsNeeded(trie, 0, outputWriter);
    }

    private static void markLongestWordPath(String longestWord, Trie trie) {
        int nodeId = 0;
        trie.isInLongestWordPath[nodeId] = true;

        for (int i = 0; i < longestWord.length(); i++) {
            nodeId = trie.findChildId(nodeId, longestWord.charAt(i));
            trie.isInLongestWordPath[nodeId] = true;
        }
    }

    private static void computeOperationsNeeded(Trie trie, int nodeId, OutputWriter outputWriter) {
        if (trie.isKey[nodeId]) {
            outputWriter.printLine('P');
        }

        int longestWordChildId = -1;
        int childId = trie.firstChild[nodeId];

        while (childId != -1) {
            if (trie.isInLongestWordPath[childId]) {
                longestWordChildId = childId;
            } else {
                outputWriter.printLine(trie.character[childId]);
                computeOperationsNeeded(trie, childId, outputWriter);
                outputWriter.printLine('-');
            }
            childId = trie.nextSibling[childId];
        }

        if (longestWordChildId != -1) {
            outputWriter.printLine(trie.character[longestWordChildId]);
            computeOperationsNeeded(trie, longestWordChildId, outputWriter);
        }
    }

    private static class Trie {

        /*
         * Each trie node is represented by one position in these arrays.
         *
         * firstChild[node] = first child of this node
         * nextSibling[node] = next child having the same parent
         * character[node] = character from parent to this node
         */
        private final int[] firstChild;
        private final int[] nextSibling;
        private final char[] character;

        private final boolean[] isKey;
        private final boolean[] isInLongestWordPath;

        private int nodeCount = 1;

        public Trie(int totalWordLength) {
            int maximumNodes = totalWordLength + 1;

            firstChild = new int[maximumNodes];
            nextSibling = new int[maximumNodes];
            character = new char[maximumNodes];
            isKey = new boolean[maximumNodes];
            isInLongestWordPath = new boolean[maximumNodes];

            Arrays.fill(firstChild, -1);
            Arrays.fill(nextSibling, -1);
        }

        public void add(String word) {
            int nodeId = 0;

            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                int childId = findChildId(nodeId, c);

                if (childId == -1) {
                    childId = nodeCount++;
                    character[childId] = c;
                    nextSibling[childId] = firstChild[nodeId];
                    firstChild[nodeId] = childId;
                }
                nodeId = childId;
            }
            isKey[nodeId] = true;
        }

        private int findChildId(int node, char targetCharacter) {
            int childId = firstChild[node];

            while (childId != -1) {
                if (character[childId] == targetCharacter) {
                    return childId;
                }
                childId = nextSibling[childId];
            }
            return -1;
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];

        private int curChar;
        private int snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);

                if (snumChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = snext();
            }
            int result = 0;
            do {
                result = result * 10 + c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return result * sign;
        }

        public String nextLine() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder result = new StringBuilder();
            do {
                result.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));

            return result.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void printLine(char c) {
            writer.println(c);
        }

        public void printLine(int value) {
            writer.println(value);
        }

        public void flush() {
            writer.flush();
        }
    }
}