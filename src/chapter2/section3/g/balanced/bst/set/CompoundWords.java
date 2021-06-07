package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/06/21.
 */
public class CompoundWords {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        List<String> allWords = new ArrayList<>();

        while (line != null) {
            String[] words = line.split(" ");
            for (String word : words) {
                allWords.add(word);
            }
            line = FastReader.getLine();
        }

        Set<String> compoundWords = computeAllCompoundWords(allWords);
        for (String compoundWord : compoundWords) {
            outputWriter.printLine(compoundWord);
        }
        outputWriter.flush();
    }

    private static Set<String> computeAllCompoundWords(List<String> allWords) {
        Set<String> compoundWords = new TreeSet<>();

        for (int i = 0; i < allWords.size(); i++) {
            for (int j = 0; j < allWords.size(); j++) {
                if (i != j) {
                    String compoundWord = allWords.get(i) + allWords.get(j);
                    compoundWords.add(compoundWord);
                }
            }
        }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
