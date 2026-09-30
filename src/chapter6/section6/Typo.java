package chapter6.section6;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/09/2026.
 */
public class Typo {

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

    private static final long PRIME_NUMBER1 = 1000000007L;
    private static final long PRIME_NUMBER2 = 1000000009L;
    private static final int ALPHABET_SIZE = 256;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String[] words = new String[FastReader.nextInt()];

        int maxLength = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = FastReader.getLine();
            maxLength = Math.max(maxLength, words[i].length());
        }

        List<String> typos = computeTypos(words, maxLength);
        if (typos.isEmpty()) {
            outputWriter.printLine("NO TYPOS");
        } else {
            for (String typo : typos) {
                outputWriter.printLine(typo);
            }
        }
        outputWriter.flush();
    }

    private static List<String> computeTypos(String[] words, int maxLength) {
        List<String> typos = new ArrayList<>();
        long[] powers1 = computePowers(maxLength, PRIME_NUMBER1);
        long[] powers2 = computePowers(maxLength, PRIME_NUMBER2);

        Set<DoubleHash> hashSet = new HashSet<>();
        for (String word : words) {
            long[] prefixHash1 = computePrefixHash(word, PRIME_NUMBER1);
            long[] prefixHash2 = computePrefixHash(word, PRIME_NUMBER2);
            DoubleHash doubleHash = new DoubleHash(prefixHash1[word.length()], prefixHash2[word.length()]);
            hashSet.add(doubleHash);
        }

        for (String word : words) {
            long[] prefixHash1 = computePrefixHash(word, PRIME_NUMBER1);
            long[] prefixHash2 = computePrefixHash(word, PRIME_NUMBER2);

            for (int i = 0; i < word.length(); i++) {
                long hash1 = getHashWithoutLetter(prefixHash1, powers1, word.length(), i, PRIME_NUMBER1);
                long hash2 = getHashWithoutLetter(prefixHash2, powers2, word.length(), i, PRIME_NUMBER2);
                DoubleHash doubleHash = new DoubleHash(hash1, hash2);

                if (hashSet.contains(doubleHash)) {
                    typos.add(word);
                    break;
                }
            }
        }
        return typos;
    }

    private static long[] computePowers(int length, long primeNumber) {
        long[] powers = new long[length + 1];
        powers[0] = 1;
        for (int i = 1; i < powers.length; i++) {
            powers[i] = (powers[i - 1] * ALPHABET_SIZE) % primeNumber;
        }
        return powers;
    }

    private static long[] computePrefixHash(String string, long primeNumber) {
        long[] prefixHash = new long[string.length() + 1];
        for (int i = 0; i < string.length(); i++) {
            prefixHash[i + 1] = (prefixHash[i] * ALPHABET_SIZE + string.charAt(i)) % primeNumber;
        }
        return prefixHash;
    }

    private static long getHash(long[] prefixHash, long[] powers, int left, int right, long primeNumber) {
        return (prefixHash[right]
                - prefixHash[left] * powers[right - left] % primeNumber
                + primeNumber) % primeNumber;
    }

    private static long getHashWithoutLetter(long[] prefixHash, long[] powers, int wordLength, int indexToRemove,
                                             long primeNumber) {
        long hashLeft = getHash(prefixHash, powers, 0, indexToRemove, primeNumber);
        long hashRight = getHash(prefixHash, powers, indexToRemove + 1, wordLength, primeNumber);
        int powerIndex = wordLength - indexToRemove - 1;
        long hashCombined;
        if (indexToRemove < wordLength - 1) {
            hashCombined = ((hashLeft * powers[powerIndex]) % primeNumber + hashRight) % primeNumber;
        } else {
            hashCombined = hashLeft;
        }
        return hashCombined;
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