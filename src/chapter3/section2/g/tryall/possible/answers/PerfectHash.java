package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 05/12/21.
 */
public class PerfectHash {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();

        while (line != null) {
            List<String> words = getWords(line);
            long hashComponent = computeHashComponent(words);
            outputWriter.printLine(line);
            outputWriter.printLine(hashComponent);
            outputWriter.printLine();

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeHashComponent(List<String> words) {
        int[] thirtyTwoPowers = computeThirtyTwoPowers();
        long minimumStartValue = Long.MAX_VALUE;

        long[] wordNumbers = new long[words.size()];
        for (int i = 0; i < wordNumbers.length; i++) {
            wordNumbers[i] = getWordNumber(words.get(i), thirtyTwoPowers);
            minimumStartValue = Math.min(minimumStartValue, wordNumbers[i]);
        }
        int tableLength = words.size();
        long component = minimumStartValue;

        while (true) {
            boolean hasConflict = false;

            for (int i = 0; i < wordNumbers.length; i++) {
                long wordNumber = wordNumbers[i];
                long componentByWordNumber = component / wordNumber;
                long hash = componentByWordNumber % tableLength;
                long componentCandidate1 = (componentByWordNumber + 1) * wordNumber;

                for (int j = i + 1; j < wordNumbers.length; j++) {
                    long wordNumber2 = wordNumbers[j];
                    long componentByWordNumber2 = component / wordNumber2;
                    long hash2 = componentByWordNumber2 % tableLength;

                    if (hash == hash2) {
                        long componentCandidate2 = (componentByWordNumber2 + 1) * wordNumber2;
                        component = Math.min(componentCandidate1, componentCandidate2);
                        hasConflict = true;
                        break;
                    }
                }

                if (hasConflict) {
                    break;
                }
            }

            if (!hasConflict) {
                break;
            }
        }
        return component;
    }

    private static int[] computeThirtyTwoPowers() {
        int[] thirtyTwoPowers = new int[5];
        thirtyTwoPowers[0] = 1;
        for (int i = 1; i < thirtyTwoPowers.length; i++) {
            thirtyTwoPowers[i] = thirtyTwoPowers[i - 1] * 32;
        }
        return thirtyTwoPowers;
    }

    private static int getWordNumber(String word, int[] thirtyTwoPowers) {
        int wordNumber = 0;

        for (int i = 0; i < word.length(); i++) {
            int letterValue = (word.charAt(i) - 'a') + 1;
            wordNumber += letterValue * thirtyTwoPowers[word.length() - i - 1];
        }
        return wordNumber;
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ') {
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

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
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