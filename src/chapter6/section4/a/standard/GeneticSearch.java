package chapter6.section4.a.standard;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 17/08/2026.
 */
public class GeneticSearch {

    private static class Result {
        int type1Occurrences;
        int type2Occurrences;
        int type3Occurrences;

        public Result(int type1Occurrences, int type2Occurrences, int type3Occurrences) {
            this.type1Occurrences = type1Occurrences;
            this.type2Occurrences = type2Occurrences;
            this.type3Occurrences = type3Occurrences;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String searchString = FastReader.next();
        while (!searchString.equals("0")) {
            String text = FastReader.next();
            Result result = searchString(searchString, text);

            outputWriter.printLine(result.type1Occurrences + " " + result.type2Occurrences
                    + " " + result.type3Occurrences);
            searchString = FastReader.next();
        }
        outputWriter.flush();
    }

    private static Result searchString(String searchString, String text) {
        int type1Occurrences = countType1Strings(searchString, text);
        int type2Occurrences = countType2Strings(searchString, text);
        int type3Occurrences = countType3Strings(searchString, text);
        return new Result(type1Occurrences, type2Occurrences, type3Occurrences);
    }

    private static int countType1Strings(String searchString, String text) {
        KnuthMorrisPratt kmp = new KnuthMorrisPratt(searchString);
        return kmp.count(text);
    }

    private static int countType2Strings(String searchString, String text) {
        int type2Occurrences = 0;
        Set<String> searchStrings = new HashSet<>();

        for (int i = 0; i < searchString.length(); i++) {
            String updatedString = searchString.substring(0, i) + searchString.substring(i + 1);
            searchStrings.add(updatedString);
        }

        for (String search : searchStrings) {
            KnuthMorrisPratt kmp = new KnuthMorrisPratt(search);
            type2Occurrences += kmp.count(text);
        }
        return type2Occurrences;
    }

    private static int countType3Strings(String searchString, String text) {
        int type3Occurrences = 0;
        Set<String> searchStrings = new HashSet<>();
        String[] characters = { "A", "C", "G", "T" };

        for (int i = 0; i <= searchString.length(); i++) {
            for (int characterIndex = 0; characterIndex < characters.length; characterIndex++) {
                String updatedString = searchString.substring(0, i)
                        + characters[characterIndex] + searchString.substring(i);
                searchStrings.add(updatedString);
            }
        }

        for (String search : searchStrings) {
            KnuthMorrisPratt kmp = new KnuthMorrisPratt(search);
            type3Occurrences += kmp.count(text);
        }
        return type3Occurrences;
    }

    private static class KnuthMorrisPratt {
        private final String pattern;
        private final int[] next;

        public KnuthMorrisPratt(String pattern) {
            this.pattern = pattern;
            int patternLength = pattern.length();
            next = new int[patternLength];

            int j = -1;
            for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                // Compute next[patternIndex]
                if (patternIndex == 0) {
                    next[patternIndex] = -1;
                } else if (pattern.charAt(patternIndex) != pattern.charAt(j)) {
                    next[patternIndex] = j;
                } else {
                    next[patternIndex] = next[j];
                }

                while (j >= 0 && pattern.charAt(patternIndex) != pattern.charAt(j)) {
                    j = next[j];
                }
                j++;
            }
        }

        private int count(String text) {
            int count = 0;

            int occurrenceIndex = searchFromIndex(text, 0);
            while (occurrenceIndex != text.length()) {
                count++;
                occurrenceIndex = searchFromIndex(text, occurrenceIndex + 1);
            }
            return count;
        }

        private int searchFromIndex(String text, int textStartIndex) {
            int textIndex;
            int patternIndex;
            int textLength = text.length();
            int patternLength = pattern.length();

            for (textIndex = textStartIndex, patternIndex = 0; textIndex < textLength && patternIndex < patternLength;
                 textIndex++) {
                while (patternIndex >= 0 && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                    patternIndex = next[patternIndex];
                }
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                return textIndex - patternLength; // found
            } else {
                return textLength;                // not found
            }
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