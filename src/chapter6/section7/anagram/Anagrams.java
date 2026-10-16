package chapter6.section7.anagram;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 15/10/2026.
 */
public class Anagrams {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            String phrase = FastReader.getLine();
            List<String> phrases = new ArrayList<>();

            while (phrase != null && !phrase.isEmpty()) {
                phrases.add(phrase);
                phrase = FastReader.getLine();
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            List<String> anagramPairs = computeAnagramPairs(phrases);
            for (String anagramPair : anagramPairs) {
                outputWriter.printLine(anagramPair);
            }
        }
        outputWriter.flush();
    }

    private static List<String> computeAnagramPairs(List<String> phrases) {
        Set<String> anagramPairsSet = new HashSet<>();

        for (int i = 0; i < phrases.size(); i++) {
            String phrase1 = phrases.get(i);
            int[] frequencies1 = computeFrequencies(phrase1);

            for (int j = i + 1; j < phrases.size(); j++) {
                String phrase2 = phrases.get(j);
                int[] frequencies2 = computeFrequencies(phrase2);
                if (Arrays.equals(frequencies1, frequencies2)) {
                    String anagramPair;
                    if (phrase1.compareTo(phrase2) < 0) {
                        anagramPair = phrase1 + " = " + phrase2;
                    } else {
                        anagramPair = phrase2 + " = " + phrase1;
                    }
                    anagramPairsSet.add(anagramPair);
                }
            }
        }

        List<String> anagramPairs = new ArrayList<>(anagramPairsSet);
        Collections.sort(anagramPairs);
        return anagramPairs;
    }

    private static int[] computeFrequencies(String phrase) {
        int[] frequencies = new int[256];
        for (char character : phrase.toCharArray()) {
            if (character == ' ') {
                continue;
            }
            frequencies[character]++;
        }
        return frequencies;
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