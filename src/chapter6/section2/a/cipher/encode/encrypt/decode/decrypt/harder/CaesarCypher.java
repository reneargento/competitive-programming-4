package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 22/04/26.
 */
public class CaesarCypher {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Set<String> dictionary = new HashSet<>();
        String word = FastReader.getLine();
        while (!word.equals("#")) {
            dictionary.add(word);
            word = FastReader.getLine();
        }
        String text = FastReader.getLine();

        String decryptedText = decryptText(dictionary, text);

        String[] words = decryptedText.split(" ");
        int lineLength = 0;
        for (int i = 0; i < words.length; i++) {
            String wordValue = words[i];
            if (lineLength + wordValue.length() > 60) {
                outputWriter.printLine();
                lineLength = 0;
            }
            if (lineLength != 0) {
                outputWriter.print(" ");
            }
            outputWriter.print(wordValue);
            lineLength += wordValue.length() + 1;
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static String decryptText(Set<String> dictionary, String text) {
        int maxWordsMatched = 0;
        int bestK = 0;

        for (int k = 0; k <= 26; k++) {
            int wordsMatched = matchWords(dictionary, text, k);
            if (wordsMatched > maxWordsMatched) {
                maxWordsMatched = wordsMatched;
                bestK = k;
            }
        }

        StringBuilder decryptedText = new StringBuilder();
        for (char character : text.toCharArray()) {
            decryptedText.append(shiftCharacter(character, bestK));
        }
        return decryptedText.toString();
    }

    private static int matchWords(Set<String> dictionary, String text, int k) {
        int matches = 0;

        for (int length = 1; length < text.length(); length++) {
            for (int i = 0; i + length < text.length(); i++) {
                String word = text.substring(i, i + length);
                String shiftedWord = shiftLetters(word, k);
                if (dictionary.contains(shiftedWord)) {
                    matches++;
                }
            }
        }
        return matches;
    }

    private static String shiftLetters(String word, int k) {
        StringBuilder shiftedWord = new StringBuilder();
        for (char character : word.toCharArray()) {
            char shiftedCharacter = shiftCharacter(character, k);
            shiftedWord.append(shiftedCharacter);
        }
        return shiftedWord.toString();
    }

    private static char shiftCharacter(char character, int k) {
        int index;
        if (character == ' ') {
            index = 0;
        } else {
            index = character - 'A' + 1;
        }

        int shiftedIndex = (index + k) % 27;
        char shiftedCharacter;
        if (shiftedIndex == 0) {
            shiftedCharacter = ' ';
        } else {
            shiftedCharacter = (char) ('A' + (shiftedIndex - 1));
        }
        return shiftedCharacter;
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
