package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/01/26.
 */
public class Permutations {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        long[] factorials = computeFactorials();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.next();
            long index = FastReader.nextLong();

            String nthPermutation = computeNthPermutation(string, index, factorials);
            outputWriter.printLine(nthPermutation);
        }
        outputWriter.flush();
    }

    private static String computeNthPermutation(String string, long index, long[] factorials) {
        StringBuilder targetPermutation = new StringBuilder();
        char[] characters = string.toCharArray();
        Arrays.sort(characters);
        Map<Character, Integer> frequencies = computeFrequencies(characters);

        for (int position = 0; position < characters.length; position++) {
            for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
                char character = entry.getKey();
                int frequency = entry.getValue();
                if (frequency == 0) {
                    continue;
                }

                frequencies.put(character, frequency - 1);
                long permutations = computePermutations(frequencies, factorials);

                if (index < permutations) {
                    targetPermutation.append(character);
                    break;
                } else {
                    index -= permutations;
                    frequencies.put(character, frequency);
                }
            }
        }
        return targetPermutation.toString();
    }

    private static Map<Character, Integer> computeFrequencies(char[] characters) {
        Map<Character, Integer> frequencies = new LinkedHashMap<>();
        for (char character : characters) {
            if (!frequencies.containsKey(character)) {
                frequencies.put(character, 0);
            }
            int frequency = frequencies.get(character) + 1;
            frequencies.put(character, frequency);
        }
        return frequencies;
    }

    private static long computePermutations(Map<Character, Integer> frequencies, long[] factorials) {
        int totalFrequencies = 0;
        for (int frequency : frequencies.values()) {
            totalFrequencies += frequency;
        }

        long permutations = factorials[totalFrequencies];
        for (int frequency : frequencies.values()) {
            permutations /= factorials[frequency];
        }
        return permutations;
    }

    private static long[] computeFactorials() {
        long[] factorials = new long[21];
        factorials[0] = 1;

        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1] * i;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
