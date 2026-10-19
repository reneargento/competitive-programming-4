package chapter6.section7.palindrome.checking;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/10/2026.
 */
public class Abnormal89s {

    private static final long PRIME_NUMBER = 1000000007L;
    private static final int ALPHABET_SIZE = 256;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.getLine();
            String result = classifyString(string);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String classifyString(String string) {
        String reverseString = new StringBuilder(string).reverse().toString();
        long[] prefixHash = computePrefixHash(string);
        long[] powers = computePowers(string.length());
        long[] prefixHash2 = computePrefixHash(reverseString);
        long[] powers2 = computePowers(reverseString.length());

        for (int i = 0; i < string.length() - 1; i++) {
            if (isPalindrome(prefixHash, powers, prefixHash2, powers2, string.length(), 0, i)
                    && isPalindrome(prefixHash, powers, prefixHash2, powers2, string.length(), i + 1, string.length() - 1)) {
                return "alindrome";
            }
        }
        if (isPalindrome(prefixHash, powers, prefixHash2, powers2, string.length(), 0, string.length() - 1)) {
            return "palindrome";
        }
        return "simple";
    }

    private static boolean isPalindrome(long[] prefixHash, long[] powers, long[] prefixHash2, long[] powers2,
                                        int stringLength, int startIndex, int endIndex) {
        int startIndex2 = stringLength - 1 - endIndex;
        int endIndex2 = stringLength - 1 - startIndex;

        long hash1 = getHash(prefixHash, powers, startIndex, endIndex);
        long hash2 = getHash(prefixHash2, powers2, startIndex2, endIndex2);
        return hash1 == hash2;
    }

    private static long[] computePrefixHash(String string) {
        long[] prefixHash = new long[string.length() + 1];
        for (int i = 0; i < string.length(); i++) {
            prefixHash[i + 1] = (prefixHash[i] * ALPHABET_SIZE + string.charAt(i)) % PRIME_NUMBER;
        }
        return prefixHash;
    }

    private static long[] computePowers(int length) {
        long[] powers = new long[length + 1];
        powers[0] = 1;
        for (int i = 1; i < powers.length; i++) {
            powers[i] = (powers[i - 1] * ALPHABET_SIZE) % PRIME_NUMBER;
        }
        return powers;
    }

    private static long getHash(long[] prefixHash, long[] powers, int left, int right) {
        return (prefixHash[right + 1]
                - prefixHash[left] * powers[right - left + 1] % PRIME_NUMBER
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