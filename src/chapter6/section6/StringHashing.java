package chapter6.section6;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/09/2026.
 */
public class StringHashing {

    private static final BigInteger PRIME_NUMBER = BigInteger.valueOf(1000000000039L);
    private static final BigInteger ALPHABET_SIZE = BigInteger.valueOf(256);

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String string = FastReader.getLine();
        BigInteger[] prefixHash = computePrefixHash(string);
        BigInteger[] powers = computePowers(string.length());

        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            int left = FastReader.nextInt();
            int right = FastReader.nextInt();
            BigInteger hash = getHash(prefixHash, powers, left, right);
            outputWriter.printLine(hash);
        }
        outputWriter.flush();
    }

    private static BigInteger[] computePrefixHash(String string) {
        BigInteger[] prefixHash = new BigInteger[string.length() + 1];
        prefixHash[0] = BigInteger.ZERO;
        for (int i = 0; i < string.length(); i++) {
            prefixHash[i + 1] = prefixHash[i]
                    .multiply(ALPHABET_SIZE).add(BigInteger.valueOf(string.charAt(i))).mod(PRIME_NUMBER);
        }
        return prefixHash;
    }

    private static BigInteger[] computePowers(int length) {
        BigInteger[] powers = new BigInteger[length];
        powers[0] = BigInteger.ONE;
        for (int i = 1; i < powers.length; i++) {
            powers[i] = (powers[i - 1].multiply(ALPHABET_SIZE)).mod(PRIME_NUMBER);
        }
        return powers;
    }

    private static BigInteger getHash(BigInteger[] prefixHash, BigInteger[] powers, int left, int right) {
        return prefixHash[right].subtract(
                (prefixHash[left].multiply(powers[right - left]))).mod(PRIME_NUMBER).
                add(PRIME_NUMBER).mod(PRIME_NUMBER);
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