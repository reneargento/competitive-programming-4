package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/02/22.
 */
public class TheProblemOfTheCrazyLinguist {

    private static final double[] probabilityTable = {
            12.53, 1.42, 4.68, 5.86, 13.68, 0.69, 1.01, 0.70, 6.25, 0.44, 0.00, 4.97, 3.15,
            6.71, 8.68, 2.51, 0.88, 6.87, 7.98, 4.63, 3.93, 0.90, 0.02, 0.22, 0.90, 0.52
    };

    private static final char[] oddChars = {
            'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'
    };

    private static final char[] evenChars = { 'a', 'e', 'i', 'o', 'u' };

    private static class WordResult {
        double totalSBC;
        double totalWords;

        double averageSBC() {
            return totalSBC / totalWords;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String word = FastReader.next();
            boolean isAboveOrEqualAverageSBC = isAboveOrEqualAverageSBC(word);
            outputWriter.printLine(isAboveOrEqualAverageSBC ? "above or equal" : "below");
        }
        outputWriter.flush();
    }

    private static boolean isAboveOrEqualAverageSBC(String word) {
        char[] currentWord = new char[word.length()];
        currentWord[0] = word.charAt(0);
        WordResult wordResult = new WordResult();

        int[] characterFrequency = new int[26];
        int firstCharacterIndex = getCharacterIndex(word.charAt(0));
        characterFrequency[firstCharacterIndex]++;

        computeSBCs(currentWord, 1, wordResult, characterFrequency);

        double wordSBC = computeSBC(word.toCharArray());
        return wordSBC >= wordResult.averageSBC();
    }

    private static void computeSBCs(char[] word, int index, WordResult wordResult, int[] characterFrequency) {
        if (index == word.length) {
            double sbc = computeSBC(word);
            wordResult.totalWords++;
            wordResult.totalSBC += sbc;
            return;
        }

        if ((index + 1) % 2 == 0) {
            for (char evenChar : evenChars) {
                processNextCharacter(word, index, wordResult, characterFrequency, evenChar);
            }
        } else {
            for (char oddChar : oddChars) {
                processNextCharacter(word, index, wordResult, characterFrequency, oddChar);
            }
        }
    }

    private static void processNextCharacter(char[] word, int index, WordResult wordResult,
                                             int[] characterFrequency, char character) {
        int characterIndex = getCharacterIndex(character);

        if (characterFrequency[characterIndex] < 2) {
            characterFrequency[characterIndex]++;
            word[index] = character;
            computeSBCs(word, index + 1, wordResult, characterFrequency);
            characterFrequency[characterIndex]--;
        }
    }

    private static double computeSBC(char[] word) {
        double sbc = 0;
        for (int i = 0; i < word.length; i++) {
            int index = getCharacterIndex(word[i]);
            sbc += (i + 1) * probabilityTable[index];
        }
        return sbc;
    }

    private static int getCharacterIndex(char character) {
        return character - 'a';
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
