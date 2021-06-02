package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 31/05/21.
 */
public class WordIndex {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Integer> wordIndex = createWordIndex();
        String word = FastReader.getLine();
        while (word != null) {
            if (isValidWord(word.toCharArray(), word.length())) {
                outputWriter.printLine(wordIndex.get(word));
            } else {
                outputWriter.printLine("0");
            }
            word = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isValidWord(char[] word, int length) {
        for (int i = 1; i < length; i++) {
            if (word[i] <= word[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private static Map<String, Integer> createWordIndex() {
        Map<String, Integer> wordIndex = new HashMap<>();
        int index = 1;
        char[] letters = new char[6];
        letters[0] = 'a';
        int lettersIndex = 0;

        while (lettersIndex < 5) {
            if (isValidWord(letters, lettersIndex + 1)) {
                StringBuilder word = new StringBuilder();
                for (int i = 0; i <= lettersIndex; i++) {
                    word.append(letters[i]);
                }
                wordIndex.put(word.toString(), index);
                index++;
            }

            char lastChar = letters[lettersIndex];
            if (lastChar != 'z') {
                char nextChar = (char) (lastChar + 1);
                letters[lettersIndex] = nextChar;
            } else {
                letters[lettersIndex] = 'a';

                boolean updatedPreviousChar = false;
                for (int currentIndex = lettersIndex - 1; currentIndex >= 0; currentIndex--) {
                    if (letters[currentIndex] != 'z') {
                        char nextChar = (char) (letters[currentIndex] + 1);
                        letters[currentIndex] = nextChar;
                        updatedPreviousChar = true;
                        break;
                    } else {
                        letters[currentIndex] = 'a';
                    }
                }

                if (!updatedPreviousChar) {
                    lettersIndex++;
                    letters[lettersIndex] = 'a';
                }
            }
        }
        return wordIndex;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
