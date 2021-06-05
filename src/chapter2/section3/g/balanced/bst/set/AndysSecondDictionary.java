package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Rene Argento on 05/06/21.
 */
public class AndysSecondDictionary {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        Set<String> wordsSet = new TreeSet<>();
        boolean isContinuingWord = false;
        StringBuilder wordSplitInLines = new StringBuilder();

        while (line != null) {
            line = line.replaceAll("[^a-zA-Z- ]", " ");

            if (!line.isEmpty()) {
                List<String> wordsInLine = getWords(line);
                int startIndex = 0;

                if (isContinuingWord) {
                    String firstWord = wordsInLine.get(0);
                    startIndex++;

                    int firstWordLength = firstWord.length();
                    if (wordsInLine.size() > 1 || firstWord.charAt(firstWordLength - 1) != '-') {
                        wordSplitInLines.append(firstWord);
                        wordsSet.add(wordSplitInLines.toString().toLowerCase());
                        wordSplitInLines = new StringBuilder();
                        isContinuingWord = false;
                    } else {
                        wordSplitInLines.append(firstWord, 0, firstWordLength - 1);
                    }
                }

                for (int i = startIndex; i < wordsInLine.size(); i++) {
                    String word = wordsInLine.get(i);

                    if (i == wordsInLine.size() - 1 && word.charAt(word.length() - 1) == '-') {
                        isContinuingWord = true;
                        wordSplitInLines.append(word, 0, word.length() - 1);
                        break;
                    }
                    wordsSet.add(word.toLowerCase());
                }
            }
            line = FastReader.getLine();
        }

        for (String word : wordsSet) {
            outputWriter.printLine(word);
        }
        outputWriter.flush();
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
