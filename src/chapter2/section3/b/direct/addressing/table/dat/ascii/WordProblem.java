package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 09/05/21.
 */
public class WordProblem {

    private static class WordData {
        char[] frequency;

        WordData(String word) {
            frequency = new char[26];
            processWord(word);
        }

        private void processWord(String word) {
            for (char letter : word.toCharArray()) {
                if (letter == ' ') {
                    continue;
                }
                int index = (int) letter - 'a';
                frequency[index]++;
            }
        }

        boolean canBeProduced(WordData wordData) {
            for (int i = 0; i < frequency.length; i++) {
                if (frequency[i] > wordData.frequency[i]) {
                    return false;
                }
            }
            return true;
        }

    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String word = FastReader.getLine();
        List<WordData> dictionary = new ArrayList<>();

        while (!word.equals("#")) {
            WordData wordData = new WordData(word);
            dictionary.add(wordData);
            word = FastReader.getLine();
        }

        word = FastReader.getLine();
        while (!word.equals("#")) {
            WordData wordData = new WordData(word);
            int wordsProduced = countWords(dictionary, wordData);
            outputWriter.printLine(wordsProduced);
            word = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countWords(List<WordData> dictionary, WordData wordData) {
        int wordsProduced = 0;

        for (WordData wordInDictionary : dictionary) {
            if (wordInDictionary.canBeProduced(wordData)) {
                wordsProduced++;
            }
        }
        return wordsProduced;
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