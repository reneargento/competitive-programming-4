package chapter6.section6;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/09/2026.
 */
public class AnimalClassification {

    private static final long PRIME_NUMBER1 = 1000000007L;
    private static final long PRIME_NUMBER2 = 1000000009L;
    private static final int ALPHABET_SIZE = 256;

    private static class DoubleHash {
        long hash1;
        long hash2;

        public DoubleHash(long hash1, long hash2) {
            this.hash1 = hash1;
            this.hash2 = hash2;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            DoubleHash that = (DoubleHash) o;
            return hash1 == that.hash1 && hash2 == that.hash2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(hash1, hash2);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int animals = FastReader.nextInt();
        String tree1 = FastReader.getLine();
        String tree2 = FastReader.getLine();

        int commonGroups = countCommonGroups(animals, tree1, tree2);
        outputWriter.printLine(commonGroups);
        outputWriter.flush();
    }

    private static int countCommonGroups(int animals, String tree1, String tree2) {
        long[] hashes1 = computeHash(animals, PRIME_NUMBER1);
        long[] hashes2 = computeHash(animals, PRIME_NUMBER2);

        Set<DoubleHash> hashSets = new HashSet<>();
        computeAndCountCommonGroups(animals, hashes1, hashes2, hashSets, tree1, false);
        return computeAndCountCommonGroups(animals, hashes1, hashes2, hashSets, tree2, true);
    }

    private static int computeAndCountCommonGroups(int animals, long[] hashes1, long[] hashes2,
                                                   Set<DoubleHash> hashSets, String tree, boolean isSecondTree) {
        int commonGroups = 0;
        Deque<DoubleHash> stackHashes = new ArrayDeque<>();
        Deque<Integer> stackUnionSize = new ArrayDeque<>();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < tree.length(); i++) {
            char symbol = tree.charAt(i);

            if (Character.isDigit(symbol)) {
                number = new StringBuilder();
                number.append(symbol);

                while (i + 1 < tree.length()
                        && Character.isDigit(tree.charAt(i + 1))) {
                    i++;
                    number.append(tree.charAt(i));
                }
            } else if (symbol == '(') {
                stackUnionSize.push(1);
            } else {
                if (!number.isEmpty()) {
                    int numberValue = Integer.parseInt(number.toString());
                    long hash1 = hashes1[numberValue];
                    long hash2 = hashes2[numberValue];
                    stackHashes.push(new DoubleHash(hash1, hash2));
                    number = new StringBuilder();
                }

                if (symbol == ',') {
                    int hashesToUnite = stackUnionSize.pop();
                    stackUnionSize.push(hashesToUnite + 1);
                } else {
                    int setSize = stackUnionSize.pop();
                    DoubleHash hashSum = new DoubleHash(0, 0);
                    for (int element = 0; element < setSize; element++) {
                        DoubleHash doubleHash = stackHashes.pop();
                        hashSum.hash1 += doubleHash.hash1;
                        hashSum.hash1 %= PRIME_NUMBER1;
                        hashSum.hash2 += doubleHash.hash2;
                        hashSum.hash2 %= PRIME_NUMBER2;
                    }
                    stackHashes.push(hashSum);

                    if (isSecondTree) {
                        if (hashSets.contains(hashSum)) {
                            commonGroups++;
                        }
                    } else {
                        hashSets.add(hashSum);
                    }
                }
            }
        }
        return commonGroups + animals;
    }

    private static long[] computeHash(int length, long primeNumber) {
        long[] powers = new long[length + 1];
        powers[0] = 1;
        for (int i = 1; i < powers.length; i++) {
            powers[i] = (powers[i - 1] * ALPHABET_SIZE) % primeNumber;
        }
        return powers;
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