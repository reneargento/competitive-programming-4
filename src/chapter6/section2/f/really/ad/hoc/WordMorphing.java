package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 03/07/2026.
 */
public class WordMorphing {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int wordsNumber = Integer.parseInt(data[0]);
            Set<String> words = new HashSet<>();
            String originalWord = FastReader.getLine();

            for (int i = 0; i < wordsNumber - 1; i++) {
                String word = FastReader.getLine();
                words.add(word);
            }
            printWordMorphing(words, originalWord, outputWriter);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printWordMorphing(Set<String> words, String originalWord, OutputWriter outputWriter) {
        outputWriter.printLine(originalWord);
        String currentWord = originalWord;

        while (!words.isEmpty()) {
            String nextWord = getNextWord(words, currentWord);
            outputWriter.printLine(nextWord);
            words.remove(nextWord);
            currentWord =  nextWord;
        }
    }

    private static String getNextWord(Set<String> words, String currentWord) {
        for (String word : words) {
            int differences = 0;
            for (int i = 0; i < word.length(); i++) {
                if (currentWord.charAt(i) != word.charAt(i)) {
                    differences++;
                    if (differences == 2) {
                        break;
                    }
                }
            }

            if (differences == 1) {
                return word;
            }
        }
        return null;
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