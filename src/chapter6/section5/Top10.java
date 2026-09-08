package chapter6.section5;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/09/2026.
 */
public class Top10 {

    private static class Word implements Comparable<Word> {
        String value;
        int label;

        public Word(String value, int label) {
            this.value = value;
            this.label = label;
        }

        @Override
        public int compareTo(Word other) {
            if (value.length() != other.value.length()) {
                if (value.length() < other.value.length()) {
                    return -1;
                } else {
                    return 1;
                }
            }
            if (!value.equals(other.value)) {
                return value.compareTo(other.value);
            }
            return Integer.compare(label, other.label);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Word[] dictionary = new Word[FastReader.nextInt()];

        for (int i = 0; i < dictionary.length; i++) {
            dictionary[i] = new Word(FastReader.next(), i + 1);
        }

        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            String query = FastReader.next();
            List<Word> wordsFound = processQuery(dictionary, query);

            if (wordsFound.isEmpty()) {
                outputWriter.printLine("-1");
            } else {
                int endIndex = Math.min(wordsFound.size(), 10);
                for (int i = 0; i < endIndex; i++) {
                    if (i != 0) {
                        outputWriter.print(" ");
                    }
                    outputWriter.print(wordsFound.get(i).label);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static List<Word> processQuery(Word[] dictionary, String query) {
        List<Word> wordsFound = new ArrayList<>();
        for (Word word : dictionary) {
            if (word.value.contains(query)) {
                wordsFound.add(word);
            }
        }
        Collections.sort(wordsFound);
        return wordsFound;
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