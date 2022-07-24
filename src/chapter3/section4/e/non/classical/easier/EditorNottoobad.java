package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/07/22.
 */
public class EditorNottoobad {

    private static class Word implements Comparable<Word> {
        String value;
        int prefixSize;

        public Word(String value, int prefixSize) {
            this.value = value;
            this.prefixSize = prefixSize;
        }

        @Override
        public int compareTo(Word other) {
            if (prefixSize != other.prefixSize) {
                return Integer.compare(other.prefixSize, prefixSize);
            }
            return value.compareTo(other.value);
        }
    }

    private static class Result {
        int presses;
        List<String> wordList;

        public Result(int presses, List<String> wordList) {
            this.presses = presses;
            this.wordList = wordList;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] words = new String[FastReader.nextInt() - 1];
            String firstWord = FastReader.next();
            for (int i = 0; i < words.length; i++) {
                words[i] = FastReader.next();
            }

            Result result = computeBestOrder(words, firstWord);
            outputWriter.printLine(result.presses);
            for (String word : result.wordList) {
                outputWriter.printLine(word);
            }
        }
        outputWriter.flush();
    }

    private static Result computeBestOrder(String[] words, String firstWord) {
        int presses = firstWord.length();
        List<String> wordList = new ArrayList<>();
        wordList.add(firstWord);

        Word[] sortedWords = new Word[words.length];
        for (int i = 0; i < words.length; i++) {
            int prefixSize = computePrefixCharacters(words[i], firstWord);
            sortedWords[i] = new Word(words[i], prefixSize);
        }
        Arrays.sort(sortedWords);

        for (Word word : sortedWords) {
            String previousWord = wordList.get(wordList.size() - 1);
            int prefixWithPreviousWord = computePrefixCharacters(word.value, previousWord);
            presses += (word.value.length() - prefixWithPreviousWord);
            wordList.add(word.value);
        }
        return new Result(presses, wordList);
    }

    private static int computePrefixCharacters(String word1, String word2) {
        int prefixSize = 0;
        int minLength = Math.min(word1.length(), word2.length());

        for (int i = 0; i < minLength; i++) {
            if (word1.charAt(i) == word2.charAt(i)) {
                prefixSize++;
            } else {
                break;
            }
        }
        return prefixSize;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
