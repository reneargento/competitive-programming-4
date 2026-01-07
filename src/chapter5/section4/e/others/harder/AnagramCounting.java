package chapter5.section4.e.others.harder;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 04/01/26.
 */
public class AnagramCounting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger[] factorials = computeFactorials();

        String word = FastReader.getLine();
        while (word != null) {
            BigInteger anagrams = countAnagrams(factorials, word);
            outputWriter.printLine(anagrams);
            word = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger countAnagrams(BigInteger[] factorials, String word) {
        Map<Character, Integer> characterFrequencies = new HashMap<>();

        for (char character : word.toCharArray()) {
            if (!characterFrequencies.containsKey(character)) {
                characterFrequencies.put(character, 0);
            }
            int frequency = characterFrequencies.get(character) + 1;
            characterFrequencies.put(character, frequency);
        }

        BigInteger repeats = BigInteger.ONE;
        for (char character : characterFrequencies.keySet()) {
            int frequency = characterFrequencies.get(character);
            repeats = repeats.multiply(factorials[frequency]);
        }
        return factorials[word.length()].divide(repeats);
    }

    private static BigInteger[] computeFactorials() {
        BigInteger[] factorials = new BigInteger[101];
        factorials[1] = BigInteger.ONE;

        for (int i = 2; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1].multiply(BigInteger.valueOf(i));
        }
        return factorials;
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
