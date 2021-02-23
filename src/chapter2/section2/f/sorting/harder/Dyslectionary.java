package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/02/21.
 */
public class Dyslectionary {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String word = FastReader.getLine();

        int group = 1;
        int maxLength = word.length();
        List<String> words = new ArrayList<>();
        String firstReverseWord = new StringBuilder(word).reverse().toString();
        words.add(firstReverseWord);

        while (word != null) {
            word = FastReader.getLine();

            if (word == null || word.isEmpty()) {
                if (group > 1) {
                    outputWriter.printLine();
                }

                Collections.sort(words);
                printDyslectionary(words, maxLength, outputWriter);
                words = new ArrayList<>();
                maxLength = 0;
                group++;
            } else {
                maxLength = Math.max(maxLength, word.length());
                String reverseWord = new StringBuilder(word).reverse().toString();
                words.add(reverseWord);
            }
        }

        outputWriter.flush();
        outputWriter.close();
    }

    private static void printDyslectionary(List<String> words, int maxLength, OutputWriter outputWriter) {
        for (String word : words) {
            String originalWord = new StringBuilder(word).reverse().toString();
            outputWriter.printLine(String.format("%" + maxLength + "s", originalWord));
        }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
