package chapter6.section7.anagram;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 13/10/2026.
 */
public class WordAmalgamation {

    private static class Word {
        String originalWord;
        String sortedWord;

        public Word(String originalWord) {
            this.originalWord = originalWord;
            sortedWord = getSortedWord(originalWord);
        }
    }

    private static final String SECTION_END = "XXXXXX";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Word> dictionary = new ArrayList<>();
        String line = FastReader.getLine();
        while (!line.equals(SECTION_END)) {
            dictionary.add(new Word(line));
            line = FastReader.getLine();
        }

        String scrambledWord = FastReader.getLine();
        while (!scrambledWord.equals(SECTION_END)) {
            String sortedWord = getSortedWord(scrambledWord);
            List<String> anagrams = computeAnagrams(dictionary, sortedWord);

            if (anagrams.isEmpty()) {
                outputWriter.printLine("NOT A VALID WORD");
            } else {
                for (String anagram : anagrams) {
                    outputWriter.printLine(anagram);
                }
            }
            outputWriter.printLine("******");
            scrambledWord = FastReader.getLine();
        }

        outputWriter.flush();
    }

    private static List<String> computeAnagrams(List<Word> dictionary, String sortedWord) {
        List<String> anagrams = new ArrayList<>();
        for (Word word : dictionary) {
            if (sortedWord.equals(word.sortedWord)) {
                anagrams.add(word.originalWord);
            }
        }
        Collections.sort(anagrams);
        return anagrams;
    }

    private static String getSortedWord(String word) {
        char[] characters = word.toCharArray();
        Arrays.sort(characters);
        return new String(characters);
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