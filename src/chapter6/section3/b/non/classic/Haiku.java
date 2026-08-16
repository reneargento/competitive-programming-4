package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/08/2026.
 */
public class Haiku {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] syllables = new String[FastReader.nextInt()];
        for (int i = 0; i < syllables.length; i++) {
            syllables[i] = FastReader.next();
        }
        String[] phrases = new String[3];
        for (int i = 0; i < phrases.length;i++) {
            phrases[i] = FastReader.getLine();
        }

        String result = isHaiku(syllables, phrases);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String isHaiku(String[] syllables, String[] phrases) {
        // dp[phrase number][phrase index][number of syllables] = is haiku
        Boolean[][][] dp = new Boolean[3][101][8];
        Boolean result = isHaiku(syllables, phrases, dp, 0, 0, 0);
        return result ? "haiku" : "come back next year";
    }

    private static Boolean isHaiku(String[] syllables, String[] phrases, Boolean[][][] dp, int phraseNumber,
                                   int phraseIndex, int numberOfSyllables) {
        if (phraseNumber == 2
                && phraseIndex == phrases[2].length()
                && numberOfSyllables == 5) {
            return true;
        }
        if (numberOfSyllables > 7
                || (phraseNumber == 2 && numberOfSyllables > 5)) {
            return false;
        }
        if (dp[phraseNumber][phraseIndex][numberOfSyllables] != null) {
            return dp[phraseNumber][phraseIndex][numberOfSyllables];
        }

        boolean isHaiku = false;
        for (String syllable : syllables) {
            if (matches(syllable, phrases[phraseNumber], phraseIndex)) {
                int nextPhraseNumber = phraseNumber;
                int nextPhraseIndex = phraseIndex + syllable.length();
                int nextNumberOfSyllables = numberOfSyllables + 1;

                if (nextPhraseIndex == phrases[nextPhraseNumber].length()
                        && ((phraseNumber == 0 && nextNumberOfSyllables == 5)
                             || (phraseNumber == 1 && nextNumberOfSyllables == 7))) {
                    nextPhraseNumber++;
                    nextNumberOfSyllables = 0;
                    nextPhraseIndex = 0;
                }

                if (nextPhraseIndex < phrases[nextPhraseNumber].length()
                        && phrases[nextPhraseNumber].charAt(nextPhraseIndex) == ' ') {
                    nextPhraseIndex++;
                }
                isHaiku |= isHaiku(syllables, phrases, dp, nextPhraseNumber, nextPhraseIndex, nextNumberOfSyllables);
            }
        }

        dp[phraseNumber][phraseIndex][numberOfSyllables] = isHaiku;
        return dp[phraseNumber][phraseIndex][numberOfSyllables];
    }

    private static boolean matches(String syllable, String phrase, int phraseIndex) {
        if (phraseIndex + syllable.length() > phrase.length()) {
            return false;
        }
        for (int i = 0; i < syllable.length(); i++) {
            if (syllable.charAt(i) != phrase.charAt(phraseIndex + i)) {
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