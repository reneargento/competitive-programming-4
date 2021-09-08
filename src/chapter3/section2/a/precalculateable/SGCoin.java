package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/09/21.
 */
public class SGCoin {

    private static class ComputationResult {
        long token;
        long hash;

        public ComputationResult(long token, long hash) {
            this.token = token;
            this.hash = hash;
        }
    }

    private static final String TRANSACTION = "a";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Integer> hashes = getAllHashes();

        int previousHash = FastReader.nextInt();
        ComputationResult result1 = computeTokenAndNewHash(previousHash, hashes);

        ComputationResult result2 = computeTokenAndNewHash(result1.hash, hashes);
        outputWriter.printLine(TRANSACTION + " " + result1.token);
        outputWriter.printLine(TRANSACTION + " " + result2.token);
        outputWriter.flush();
    }

    private static List<Integer> getAllHashes() {
        List<Integer> hashes = new ArrayList<>();
        for (int hash = 10000000; hash <= 990000000; hash += 10000000) {
            hashes.add(hash);
        }
        return hashes;
    }

    private static ComputationResult computeTokenAndNewHash(long previousHash, List<Integer> hashes) {
        for (int hash : hashes) {
            ComputationResult result = hash(previousHash, TRANSACTION, hash);

            if (0 <= result.token && result.token < 1000000000) {
                return result;
            }
        }
        return null;
    }

    private static ComputationResult hash(long previousHash, String transaction, long targetHash) {
        long hashValue = previousHash;
        for (int i = 0; i < transaction.length(); i++) {
            hashValue = (hashValue * 31 + transaction.charAt(i)) % 1000000007;
        }
        long newHash = (hashValue * 7) % 1000000007;
        long token = (targetHash - newHash + 1000000007) % 1000000007;
        return new ComputationResult(token, targetHash);
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
