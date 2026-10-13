package chapter6.section7.anagram;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 13/10/2026.
 */
public class ReodrnreigLteetrsInWrods {

    private static class Word {
        String originalWord;
        String sortedWord;

        public Word(String originalWord) {
            this.originalWord = originalWord;
            sortedWord = getSortedWord(originalWord);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Word[] dictionary = getWords(FastReader.getLine().split(" "));
            Word[] textWords = getWords(FastReader.getLine().split(" "));

            String[] clearedText = clearText(dictionary, textWords);
            outputWriter.print(clearedText[0]);
            for (int i = 1; i < clearedText.length; i++) {
                outputWriter.print(" " + clearedText[i]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static String[] clearText(Word[] dictionary, Word[] textWords) {
        String[] clearedText = new String[textWords.length];

        for (int i = 0; i < textWords.length; i++) {
            String wordToReplace = getWordToReplace(dictionary, textWords[i]);
            clearedText[i] = wordToReplace;
        }
        return clearedText;
    }

    private static String getWordToReplace(Word[] dictionary, Word textWord) {
        List<String> candidateList = new ArrayList<>();
        for (Word word : dictionary) {
            if (textWord.sortedWord.equals(word.sortedWord)) {
                candidateList.add(word.originalWord);
            }
        }

        if (candidateList.isEmpty()) {
            return textWord.originalWord;
        }
        Collections.sort(candidateList);
        return candidateList.get(0);
    }

    private static String getSortedWord(String word) {
        if (word.length() == 1 || word.length() == 2) {
            return word;
        }

        char[] characters = new char[word.length()];
        char[] innerCharacters = word.substring(1, word.length() - 1).toCharArray();
        Arrays.sort(innerCharacters);

        characters[0] = word.charAt(0);
        characters[word.length() - 1] = word.charAt(word.length() - 1);
        System.arraycopy(innerCharacters, 0, characters, 1, innerCharacters.length);
        return new String(characters);
    }

    private static Word[] getWords(String[] strings) {
        Word[] words = new Word[strings.length];
        for (int i = 0; i < strings.length; i++) {
            words[i] = new Word(strings[i]);
        }
        return words;
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