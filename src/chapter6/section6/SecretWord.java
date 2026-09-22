package chapter6.section6;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/09/2026.
 */
public class SecretWord {

    private static final long PRIME_NUMBER = 1000000007L;
    private static final int ALPHABET_SIZE = 256;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.getLine();
            String secretWord = computeSecretWord(string);
            outputWriter.printLine(secretWord);
        }
        outputWriter.flush();
    }

    private static String computeSecretWord(String string) {
        long[] prefixHashString = computePrefixHash(string);
        long[] prefixHashReverseString = computePrefixHash(new StringBuilder(string).reverse().toString());
        long[] powers = computePowers(string.length() + 1);
        int maximumLength = 0;

        for (int start = 0; start < string.length(); start++) {
            int low = 0;
            int high = string.length() - start;

            while (low < high) {
                int middle = (low + high + 1) >>> 1;

                long hash1 = prefixHashString[middle];
                long hash2 = getHash(prefixHashReverseString, powers, start, start + middle);

                if (hash1 == hash2) {
                    low = middle;
                } else {
                    high = middle - 1;
                }
            }
            maximumLength = Math.max(maximumLength, low);
        }
        return new StringBuilder(string.substring(0, maximumLength)).reverse().toString();
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