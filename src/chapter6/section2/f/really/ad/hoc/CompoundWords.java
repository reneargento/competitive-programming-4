package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/07/2026.
 */
public class CompoundWords {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<String> words = new ArrayList<>();
        String word = FastReader.getLine();
        while (word != null) {
            words.add(word);
            word = FastReader.getLine();
        }

        List<String> compoundWords = computeCompoundWords(words);
        for (String compoundWord : compoundWords) {
            outputWriter.printLine(compoundWord);
        }
        outputWriter.flush();
    }

    private static List<String> computeCompoundWords(List<String> words) {
        List<String> compoundWords = new ArrayList<>();
        Set<String> wordsSet = new HashSet<>(words);

        for (String word : words) {
            for (int i = 1; i < word.length(); i++) {
                String word1 = word.substring(0, i);
                String word2 = word.substring(i);
                if (wordsSet.contains(word1) && wordsSet.contains(word2)) {
                    compoundWords.add(word);
                    break;
                }
            }
        }
        Collections.sort(compoundWords);
        return compoundWords;
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