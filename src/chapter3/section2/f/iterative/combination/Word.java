package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/12/21.
 */
public class Word {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();

        while (line != null) {
            String word = FastReader.getLine();
            String[] rewritingRules = new String[8];
            for (int i = 0; i < rewritingRules.length; i++) {
                rewritingRules[i] = FastReader.next();
            }
            int rewrites = FastReader.nextInt();

            String finalWord = applyRewrites(word.toCharArray(), rewritingRules, rewrites);
            outputWriter.printLine(finalWord);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String applyRewrites(char[] word, String[] rewritingRules, int rewrites) {
        List<String> wordList = new ArrayList<>();
        Map<String, Integer> wordToPositionMap = new HashMap<>();
        int rewritesApplied = 0;

        while (rewritesApplied < rewrites) {
            String nextWord = applyRewrite(word, rewritingRules);
            rewritesApplied++;

            if (wordToPositionMap.containsKey(nextWord)) {
                int wordIndex = wordToPositionMap.get(nextWord);
                int cycleLength = wordList.size() - wordIndex;

                int rewritesRemaining = rewrites - rewritesApplied;
                rewritesRemaining %= cycleLength;
                int indexOfFinalWord = (wordIndex + rewritesRemaining) % wordList.size();
                nextWord = wordList.get(indexOfFinalWord);
                rewrites = 0;
            } else {
                wordToPositionMap.put(nextWord, wordList.size());
                wordList.add(nextWord);
            }

            word = nextWord.toCharArray();
        }
        String finalWord = new String(word);
        return getLexicographicallySmallestWord(finalWord);
    }

    private static String applyRewrite(char[] word, String[] rewritingRules) {
        char[] nextWord = new char[word.length];

        for (int i = 0; i < word.length; i++) {
            char nextChar = 'a';
            int indexToCheck1 = i - 2;
            if (indexToCheck1 < 0) {
                indexToCheck1 = word.length + indexToCheck1;
            }
            int indexToCheck2 = i;
            int indexToCheck3 = (i + 1) % word.length;

            for (String rule : rewritingRules) {
                if (word[indexToCheck1] == rule.charAt(0)
                        && word[indexToCheck2] == rule.charAt(1)
                        && word[indexToCheck3] == rule.charAt(2)) {
                    nextChar = rule.charAt(3);
                    break;
                }
            }
            nextWord[i] = nextChar;
        }
        return new String(nextWord);
    }

    private static String getLexicographicallySmallestWord(String word) {
        List<String> words = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            String start = word.substring(i);
            String end = word.substring(0, i);
            words.add(start + end);
        }
        Collections.sort(words);
        return words.get(0);
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
