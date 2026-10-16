package chapter6.section7.anagram;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/10/2026.
 */
public class AnagramsII {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            String[] vocabulary = new String[FastReader.nextInt()];
            for (int i = 0; i < vocabulary.length; i++) {
                vocabulary[i] = FastReader.next();
            }
            int[][] frequencies = precomputeAllFrequencies(vocabulary);

            if (t > 0) {
                outputWriter.printLine();
            }
            String testWord = FastReader.getLine();
            while (!testWord.equals("END")) {
                List<String> anagrams = computeAnagrams(vocabulary, frequencies, testWord);
                outputWriter.printLine("Anagrams for: " + testWord);

                if (anagrams.isEmpty()) {
                    outputWriter.printLine("No anagrams for: " + testWord);
                } else {
                    for (int i = 0; i < anagrams.size(); i++) {
                        outputWriter.printLine(String.format("%3d) %s", i + 1, anagrams.get(i)));
                    }
                }
                testWord = FastReader.getLine();
            }
            FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> computeAnagrams(String[] vocabulary, int[][] frequencies, String testWord) {
        List<String> anagrams = new ArrayList<>();
        int[] testWordFrequencies = computeFrequency(testWord);

        for (int i = 0; i < frequencies.length; i++) {
            if (areEqualFrequencies(frequencies[i], testWordFrequencies)) {
                anagrams.add(vocabulary[i]);
            }
        }
        return anagrams;
    }

    private static int[][] precomputeAllFrequencies(String[] words) {
        int[][] frequencies = new int[words.length][256];
        for (int i = 0; i < words.length; i++) {
            frequencies[i] = computeFrequency(words[i]);
        }
        return frequencies;
    }

    private static int[] computeFrequency(String word) {
        int[] frequencies = new int[256];
        for (int i = 0; i < word.length(); i++) {
            frequencies[word.charAt(i)]++;
        }
        return frequencies;
    }

    private static boolean areEqualFrequencies(int[] frequencies1, int[] frequencies2) {
        for (int i = 0; i < frequencies1.length; i++) {
            if (frequencies1[i] != frequencies2[i]) {
                return false;
            }
        }
        return true;
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