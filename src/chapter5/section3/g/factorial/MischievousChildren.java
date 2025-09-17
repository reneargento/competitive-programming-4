package chapter5.section3.g.factorial;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/09/25.
 */
public class MischievousChildren {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        BigInteger[] factorials = computeFactorials();

        for (int t = 1; t <= tests; t++) {
            String word = FastReader.getLine();
            BigInteger permutations = computePermutations(factorials, word);
            outputWriter.printLine(String.format("Data set %d: %s", t, permutations.toString()));
        }
        outputWriter.flush();
    }

    private static BigInteger computePermutations(BigInteger[] factorials, String word) {
        Map<Character, Integer> characterFrequencies = new HashMap<>();
        for (char character : word.toCharArray()) {
            if (!characterFrequencies.containsKey(character)) {
                characterFrequencies.put(character, 0);
            }
            int frequency = characterFrequencies.get(character);
            characterFrequencies.put(character, frequency + 1);
        }

        BigInteger permutations = factorials[word.length()];
        for (int frequency : characterFrequencies.values()) {
            permutations = permutations.divide(factorials[frequency]);
        }
        return permutations;
    }

    private static BigInteger[] computeFactorials() {
        BigInteger[] factorials = new BigInteger[21];
        factorials[0] = BigInteger.ONE;

        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1].multiply(BigInteger.valueOf(i));
        }
        return factorials;
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
