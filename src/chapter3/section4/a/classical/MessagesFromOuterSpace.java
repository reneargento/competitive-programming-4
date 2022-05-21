package chapter3.section4.a.classical;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 20/05/22.
 */
public class MessagesFromOuterSpace {

    private static class Word implements Comparable<Word> {
        int start;
        int end;

        public Word(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Word other) {
            return Integer.compare(end, other.end);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<String> dictionary = new ArrayList<>();

        String line = FastReader.getLine();
        while (!line.equals("#")) {
            dictionary.add(line);
            line = FastReader.getLine();
        }

        line = FastReader.getLine();
        StringBuilder message = new StringBuilder();
        while (!line.equals("#")) {
            message.append(line);

            if (message.charAt(message.length() - 1) == '|') {
                int maximumSubstrings = countMaximumSubstrings(dictionary, message.substring(0, message.length() - 1));
                outputWriter.printLine(maximumSubstrings);
                message = new StringBuilder();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countMaximumSubstrings(List<String> dictionary, String message) {
        int maximumSubstrings = 0;
        List<Word> wordsFound = new ArrayList<>();

        for (String word : dictionary) {
            int index = message.indexOf(word);
            while (index != -1) {
                wordsFound.add(new Word(index, index + word.length() - 1));
                index = message.indexOf(word, index + 1);
            }
        }
        Collections.sort(wordsFound);

        int end = -1;
        for (Word word : wordsFound) {
            if (word.start > end) {
                end = word.end;
                maximumSubstrings++;
            }
        }
        return maximumSubstrings;
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
