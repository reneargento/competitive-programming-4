package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/06/21.
 */
public class HardwoodSpeciesKattis {

    private static class TreeFrequency implements Comparable<TreeFrequency> {
        String tree;
        int frequency;

        public TreeFrequency(String tree, int frequency) {
            this.tree = tree;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(TreeFrequency other) {
            return tree.compareTo(other.tree);
        }
    }

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Integer> treesFrequencyMap = new HashMap<>();
        double totalTrees = 0;

        String treeSpecie = inputReader.readLine();
        while (treeSpecie != null){
            int frequency = treesFrequencyMap.getOrDefault(treeSpecie, 0);
            treesFrequencyMap.put(treeSpecie, frequency + 1);

            totalTrees++;
            treeSpecie = inputReader.readLine();
        }

        TreeFrequency[] treeFrequencies = new TreeFrequency[treesFrequencyMap.size()];
        int arrayIndex = 0;
        for (Map.Entry<String, Integer> treeFrequency : treesFrequencyMap.entrySet()) {
            treeFrequencies[arrayIndex++] = new TreeFrequency(treeFrequency.getKey(), treeFrequency.getValue());
        }
        Arrays.sort(treeFrequencies);

        for (TreeFrequency treeFrequency : treeFrequencies) {
            double frequency = treeFrequency.frequency / totalTrees * 100;
            outputWriter.printLine(String.format("%s %.6f", treeFrequency.tree, frequency));
        }
        outputWriter.flush();
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public String readLine() {
            int c = read();
            if (c == -1) {
                return null;
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return c == '\n' || c == -1;
        }

        public String next() {
            return readString();
        }

        private interface SpaceCharFilter {
            boolean isSpaceChar(int ch);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
