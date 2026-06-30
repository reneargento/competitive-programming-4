package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/06/2026.
 */
public class SpellChecker {

    private static class DictionaryWord implements Comparable<DictionaryWord> {
        String value;
        int index;

        public DictionaryWord(String value, int index) {
            this.value = value;
            this.index = index;
        }

        @Override
        public int compareTo(DictionaryWord other) {
            return Integer.compare(index, other.index);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            Map<String, DictionaryWord> dictionary = new HashMap<>();
            String dictionaryWord = FastReader.getLine();
            int index = 0;

            while (!dictionaryWord.equals("#")) {
                dictionary.put(dictionaryWord, new DictionaryWord(dictionaryWord, index));
                index++;
                dictionaryWord = FastReader.getLine();
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            String wordToCheck = FastReader.getLine();
            while (!wordToCheck.equals("#")) {
                if (dictionary.containsKey(wordToCheck)) {
                    outputWriter.printLine(wordToCheck + " is correct");
                } else {
                    List<DictionaryWord> replacements = findReplacements(dictionary, wordToCheck);
                    outputWriter.print(wordToCheck + ":");
                    for (DictionaryWord replacement : replacements) {
                        outputWriter.print(" " + replacement.value);
                    }
                    outputWriter.printLine();
                }
                wordToCheck = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static List<DictionaryWord> findReplacements(Map<String, DictionaryWord> dictionary, String wordToCheck) {
        Set<DictionaryWord> replacements = new HashSet<>();

        // Insert
        for (int i = 0; i <= wordToCheck.length(); i++) {
            for (char character = 'a'; character <= 'z'; character++) {
                String insertedWord = wordToCheck.substring(0, i) + character;
                if (i < wordToCheck.length()) {
                    insertedWord += wordToCheck.substring(i);
                }
                if (dictionary.containsKey(insertedWord)) {
                    replacements.add(dictionary.get(insertedWord));
                }
            }
        }

        // Replace
        for (int i = 0; i < wordToCheck.length(); i++) {
            for (char character = 'a'; character <= 'z'; character++) {
                String replacedWord = wordToCheck.substring(0, i) + character;
                if (i < wordToCheck.length() - 1) {
                    replacedWord += wordToCheck.substring(i + 1);
                }

                if (dictionary.containsKey(replacedWord)) {
                    replacements.add(dictionary.get(replacedWord));
                }
            }
        }

        // Remove
        for (int i = 0; i < wordToCheck.length(); i++) {
            String removedWord = wordToCheck.substring(0, i);
            if (i < wordToCheck.length() - 1) {
                removedWord += wordToCheck.substring(i + 1);
            }

            if (dictionary.containsKey(removedWord)) {
                replacements.add(dictionary.get(removedWord));
            }
        }

        List<DictionaryWord> replacementList = new ArrayList<>(replacements);
        Collections.sort(replacementList);
        return replacementList;
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