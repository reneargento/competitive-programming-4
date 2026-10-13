package chapter6.section7.anagram;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 13/10/2026.
 */
public class Ananagrams {

    private static class Word {
        String originalWord;
        String sortedWord;

        public Word(String originalWord) {
            this.originalWord = originalWord;
            char[] characters = originalWord.toUpperCase().toCharArray();
            Arrays.sort(characters);
            sortedWord = new String(characters);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Word> words = new ArrayList<>();
        String line = FastReader.getLine();
        while (!line.equals("#")) {
            String[] wordsInLine = line.split("\\s+");
            for (String word : wordsInLine) {
                words.add(new Word(word));
            }
            line = FastReader.getLine();
        }

        List<String> ananagrams = computeAnanagrams(words);
        for (String ananagram : ananagrams) {
            outputWriter.printLine(ananagram);
        }
        outputWriter.flush();
    }

    private static List<String> computeAnanagrams(List<Word> words) {
        List<String> ananagrams = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            boolean isAnagram = false;
            String sortedWord = words.get(i).sortedWord;

            for (int j = 0; j < words.size(); j++) {
                if (i == j) {
                    continue;
                }
                if (sortedWord.equals(words.get(j).sortedWord)) {
                    isAnagram = true;
                    break;
                }
            }
            if (!isAnagram) {
                ananagrams.add(words.get(i).originalWord);
            }
        }
        Collections.sort(ananagrams);
        return ananagrams;
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