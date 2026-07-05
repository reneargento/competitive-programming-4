package chapter6.section2.f.really.ad.hoc;

import java.io.*;

/**
 * Created by Rene Argento on 04/07/2026.
 */
public class Nimionese {

    private static final String[] HARD_CONSONANTS = { "b", "c", "d", "g", "k", "n", "p", "t" };
    private static final String[] ENDINGS = { "ah", "oh", "uh" };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] words = FastReader.getLine().split(" ");
        String translatedWords = translate(words);
        outputWriter.printLine(translatedWords);
        outputWriter.flush();
    }

    private static String translate(String[] words) {
        StringBuilder translatedWords = new StringBuilder();

        for (String word : words) {
            StringBuilder translatedWord = new StringBuilder();
            String startCharacter = getNearestString(word.charAt(0), HARD_CONSONANTS, false);
            translatedWord.append(startCharacter);
            char hardConsonant = startCharacter.charAt(0);

            boolean isAfterFirstSyllable = false;
            for (int i = 1; i < word.length(); i++) {
                char symbol = word.charAt(i);
                if (symbol == '-') {
                    isAfterFirstSyllable = true;
                    continue;
                }

                if (isAfterFirstSyllable && isHardConsonant(symbol)) {
                    translatedWord.append(Character.toLowerCase(hardConsonant));
                } else {
                    translatedWord.append(symbol);
                }
            }

            char endCharacter = translatedWord.charAt(translatedWord.length() - 1);
            if (isHardConsonant(endCharacter)) {
                String endValue = getNearestString(endCharacter, ENDINGS, true);
                translatedWord.append(endValue);
            }

            if (translatedWords.length() > 0) {
                translatedWords.append(" ");
            }
            translatedWords.append(translatedWord);
        }
        return translatedWords.toString();
    }

    private static String getNearestString(char letter, String[] values, boolean keepLowercase) {
        boolean isUppercase = Character.isUpperCase(letter);
        letter = Character.toLowerCase(letter);

        int smallestDistance = Integer.MAX_VALUE;
        String bestString = values[0];

        for (String value : values) {
            int distance = Math.abs(letter - value.charAt(0));
            if (distance < smallestDistance) {
                smallestDistance = distance;
                bestString = value;
            }
        }

        if (isUppercase && !keepLowercase) {
            bestString = bestString.toUpperCase();
        }
        return bestString;
    }

    private static boolean isHardConsonant(char letter) {
        letter = Character.toLowerCase(letter);
        for (String hardConsonant : HARD_CONSONANTS) {
            if (letter == hardConsonant.charAt(0)) {
                return true;
            }
        }
        return false;
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

        public void flush() {
            writer.flush();
        }
    }
}