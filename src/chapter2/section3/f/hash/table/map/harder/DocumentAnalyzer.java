package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/06/21.
 */
public class DocumentAnalyzer {

    private static class Range {
        int start;
        int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int documents = FastReader.nextInt();

        for (int document = 1; document <= documents; document++) {
            String line = FastReader.getLine();
            List<String> documentWords = new ArrayList<>();
            Set<String> uniqueWords = new HashSet<>();

            while (!line.equals("END")) {
                List<String> lineWords = getWords(line);
                documentWords.addAll(lineWords);
                uniqueWords.addAll(lineWords);

                line = FastReader.getLine();
            }
            Range minRange = computeMinRangeWithAllWords(documentWords, uniqueWords.size());
            outputWriter.printLine(String.format("Document %d: %d %d", document, minRange.start, minRange.end));
        }
        outputWriter.flush();
    }

    private static Range computeMinRangeWithAllWords(List<String> documentWords, int numberOfWords) {
        Map<String, Integer> wordToIndex = new TreeMap<>();
        TreeMap<Integer, String> indexToWord = new TreeMap<>();
        int start = -1;
        int end = -1;

        for (int i = 0; i < documentWords.size(); i++) {
            String word = documentWords.get(i);

            if (wordToIndex.containsKey(word)) {
                int index = wordToIndex.get(word);
                wordToIndex.remove(word);
                indexToWord.remove(index);
            }
            wordToIndex.put(word, i);
            indexToWord.put(i, word);

            if (wordToIndex.size() == numberOfWords) {
                int startCandidate = indexToWord.firstKey();
                int endCandidate = indexToWord.lastKey();
                int size = endCandidate - startCandidate + 1;
                if (start == -1 || (end - start + 1) > size) {
                    start = startCandidate;
                    end = endCandidate;
                }
            }
        }
        return new Range(start + 1, end + 1);
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (!Character.isLetter(character)) {
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
