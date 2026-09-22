package chapter6.section6;

import java.io.*;

/**
 * Created by Rene Argento on 19/09/2026.
 */
public class ExtendToPalindrome {

    private static final long PRIME_NUMBER = 1000000007L;
    private static final int ALPHABET_SIZE = 256;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        while (string != null) {
            String palindrome = computePalindrome(string);
            outputWriter.printLine(palindrome);
            string = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computePalindrome(String string) {
        long[] prefixHashString = computePrefixHash(string);
        long[] prefixHashReverseString = computePrefixHash(new StringBuilder(string).reverse().toString());
        long[] powers = computePowers(string.length() + 1);

        int palindromeStartIndex = 0;
        for (int length = string.length(); length >= 1; length--) {
            int hash1StartIndex = string.length() - length;
            long hash1 = getHash(prefixHashString, powers, hash1StartIndex, string.length());
            long hash2 = prefixHashReverseString[length];

            if (hash1 == hash2) {
                palindromeStartIndex = string.length() - length;
                break;
            }
        }
        StringBuilder missingCharacters = new StringBuilder(string.substring(0, palindromeStartIndex));
        missingCharacters.reverse();
        return string + missingCharacters;
    }

    private static long[] computePrefixHash(String string) {
        long[] prefixHash = new long[string.length() + 1];
        for (int i = 0; i < string.length(); i++) {
            prefixHash[i + 1] = (prefixHash[i] * ALPHABET_SIZE + string.charAt(i)) % PRIME_NUMBER;
        }
        return prefixHash;
    }

    private static long[] computePowers(int length) {
        long[] powers = new long[length];
        powers[0] = 1;
        for (int i = 1; i < powers.length; i++) {
            powers[i] = (powers[i - 1] * ALPHABET_SIZE) % PRIME_NUMBER;
        }
        return powers;
    }

    private static long getHash(long[] prefixHash, long[] powers, int left, int right) {
        return (prefixHash[right]
                - prefixHash[left] * powers[right - left] % PRIME_NUMBER
                + PRIME_NUMBER) % PRIME_NUMBER;
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