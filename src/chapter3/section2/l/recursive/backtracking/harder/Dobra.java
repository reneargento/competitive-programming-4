package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 12/02/22.
 */
public class Dobra {

    private static final char[] replacements = { 'A', 'Z', 'L' };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char[] word = FastReader.getLine().toCharArray();
        long pleasantWords = countPleasantWords(word);
        outputWriter.printLine(pleasantWords);
        outputWriter.flush();
    }

    private static long countPleasantWords(char[] word) {
        if (!isValidWord(word)) {
            return 0;
        }
        List<Integer> indexesToUpdate = getIndexesToUpdate(word);
        Set<Integer> indexesToUpdateSet = new HashSet<>(indexesToUpdate);
        return countPleasantWords(word, indexesToUpdate, indexesToUpdateSet, 0);
    }

    private static long countPleasantWords(char[] word, List<Integer> indexesToUpdate,
                                           Set<Integer> indexesToUpdateSet, int index) {
        if (index == indexesToUpdate.size()) {
            return isPleasantWord(word, indexesToUpdate, indexesToUpdateSet);
        }

        long pleasantWordsCount = 0;
        int indexToUpdate = indexesToUpdate.get(index);

        for (char replacement : replacements) {
            word[indexToUpdate] = replacement;
            if (indexToUpdate >= 2) {
                if ((isVowel(replacement) && has3ConsecutiveVowels(word, indexToUpdate - 2))
                        || (!isVowel(replacement) && has3ConsecutiveConsonants(word, indexToUpdate - 2))) {
                    continue;
                }
            }
            pleasantWordsCount += countPleasantWords(word, indexesToUpdate, indexesToUpdateSet, index + 1);
        }
        return pleasantWordsCount;
    }

    private static List<Integer> getIndexesToUpdate(char[] word) {
        List<Integer> indexesToUpdate = new ArrayList<>();
        for (int i = 0; i < word.length; i++) {
            if (word[i] == '_') {
                indexesToUpdate.add(i);
            }
        }
        return indexesToUpdate;
    }

    private static boolean isValidWord(char[] word) {
        for (int i = 0; i < word.length - 2; i++) {
            if (word[i] != '_' && word[i + 1] != '_' && word[i + 2] != '_') {
                if (has3ConsecutiveVowels(word, i) || has3ConsecutiveConsonants(word, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean has3ConsecutiveVowels(char[] word, int startIndex) {
        return isVowel(word[startIndex]) && isVowel(word[startIndex + 1]) && isVowel(word[startIndex + 2]);
    }

    private static boolean has3ConsecutiveConsonants(char[] word, int startIndex) {
        return !isVowel(word[startIndex]) && !isVowel(word[startIndex + 1]) && !isVowel(word[startIndex + 2]);
    }

    private static long isPleasantWord(char[] word, List<Integer> indexesToUpdate, Set<Integer> indexesToUpdateSet) {
        int lCount = 0;
        int lsPlaced = 0;

        for (int i = 0; i < word.length - 2; i++) {
            if (word[i] == 'L') {
                lCount++;

                if (indexesToUpdateSet.contains(i)) {
                    lsPlaced++;
                }
            }

            if (has3ConsecutiveVowels(word, i) || has3ConsecutiveConsonants(word, i)) {
                return 0;
            }
        }

        if (word.length >= 2 && word[word.length - 2] == 'L') {
            lCount++;
            if (indexesToUpdateSet.contains(word.length - 2)) {
                lsPlaced++;
            }
        }
        if (word.length >= 1 && word[word.length - 1] == 'L') {
            lCount++;
            if (indexesToUpdateSet.contains(word.length - 1)) {
                lsPlaced++;
            }
        }
        if (lCount == 0 || lsPlaced >= 2) {
            return 0;
        }

        long pleasantScore = 1;
        boolean processedL = false;

        for (int index : indexesToUpdate) {
            if (isVowel(word[index])) {
                pleasantScore *= 5;
            } else if (word[index] != 'L') {
                int distinctConsonants;
                if (processedL) {
                    distinctConsonants = 21;
                } else {
                    distinctConsonants = 20;
                }
                pleasantScore *= distinctConsonants;
            } else {
                processedL = true;
            }
        }
        return pleasantScore;
    }

    private static boolean isVowel(char character) {
        return character == 'A' ||
                character == 'E' ||
                character == 'I' ||
                character == 'O' ||
                character == 'U';
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
