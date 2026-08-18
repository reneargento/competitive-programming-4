package chapter6.section4.a.standard;

import java.io.*;

/**
 * Created by Rene Argento on 18/08/2026.
 */
public class Hangman {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char[] word = FastReader.getLine().toCharArray();
        char[] guesses = FastReader.getLine().toCharArray();

        String result = playGame(word, guesses);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String playGame(char[] word, char[] guesses) {
        int lettersMissing = word.length;
        int misses = 0;

        for (char guess : guesses) {
            int frequency = countFrequency(word, guess);
            if (frequency == 0) {
                misses++;
                if (misses == 10) {
                    return "LOSE";
                }
            } else {
                lettersMissing -= frequency;
                if (lettersMissing == 0) {
                    return "WIN";
                }
            }
        }
        return "IMPOSSIBLE STATE";
    }

    private static int countFrequency(char[] word, char character) {
        int frequency = 0;
        for (char wordCharacter : word) {
            if (wordCharacter == character) {
                frequency++;
            }
        }
        return frequency;
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