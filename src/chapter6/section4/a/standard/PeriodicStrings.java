package chapter6.section4.a.standard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/08/2026.
 */
public class PeriodicStrings {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            String string = FastReader.getLine();
            int smallestPeriod = computeSmallestPeriod(string);

            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(smallestPeriod);
        }
        outputWriter.flush();
    }

    private static int computeSmallestPeriod(String string) {
        String concatenatedString = string + string;
        KnuthMorrisPratt kmp = new KnuthMorrisPratt(string);
        return kmp.findSecondOccurrence(concatenatedString);
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

        private int findSecondOccurrence(String text) {
            List<Integer> offsets = new ArrayList<>();
            int occurrenceIndex = searchFromIndex(text, 0);

            while (occurrenceIndex != text.length()) {
                offsets.add(occurrenceIndex);
                if (offsets.size() == 2) {
                    return occurrenceIndex;
                }
                occurrenceIndex = searchFromIndex(text, occurrenceIndex + 1);
            }
            return text.length();
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

        public void flush() {
            writer.flush();
        }
    }
}