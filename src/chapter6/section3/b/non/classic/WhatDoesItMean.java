package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/08/2026.
 */
public class WhatDoesItMean {

    private static class DictionaryWord {
        String word;
        int meanings;

        public DictionaryWord(String word, int meanings) {
            this.word = word;
            this.meanings = meanings;
        }
    }

    private static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        DictionaryWord[] dictionary = new DictionaryWord[FastReader.nextInt()];
        String familyName = FastReader.next();

        for (int i = 0; i < dictionary.length; i++) {
            dictionary[i] = new DictionaryWord(FastReader.next(), FastReader.nextInt());
        }

        long possibleMeanings = computePossibleMeanings(dictionary, familyName.toCharArray());
        outputWriter.printLine(possibleMeanings);
        outputWriter.flush();
    }

    private static long computePossibleMeanings(DictionaryWord[] dictionary, char[] familyName) {
        // dp[family name index] = maximum possible meanings
        long[] dp = new long[familyName.length];
        Arrays.fill(dp, -1);
        return computePossibleMeanings(dictionary, familyName, dp, 0);
    }

    private static long computePossibleMeanings(DictionaryWord[] dictionary, char[] familyName, long[] dp,
                                                int familyNameIndex) {
        if (familyNameIndex == familyName.length) {
            return 1;
        }
        if (dp[familyNameIndex] != -1) {
            return dp[familyNameIndex];
        }

        long totalMeanings = 0;
        for (int i = 0; i < dictionary.length; i++) {
            DictionaryWord dictionaryWord = dictionary[i];
            if (wordMatches(dictionaryWord.word, familyName, familyNameIndex)) {
                totalMeanings += dictionaryWord.meanings * computePossibleMeanings(dictionary, familyName, dp,
                        familyNameIndex + dictionaryWord.word.length());
                totalMeanings %= MOD;
            }
        }

        dp[familyNameIndex] = totalMeanings;
        return dp[familyNameIndex];
    }

    private static boolean wordMatches(String dictionaryWord, char[] familyName, int startIndex) {
        if (dictionaryWord.length() + startIndex > familyName.length) {
            return false;
        }
        for (int i = 0; i < dictionaryWord.length(); i++) {
            int familyNameIndex = startIndex + i;
            if (dictionaryWord.charAt(i) != familyName[familyNameIndex]) {
                return false;
            }
        }
        return true;
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