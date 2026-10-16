package chapter6.section7.anagram;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/10/2026.
 */
public class AnagramChecker {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<String> dictionary = new ArrayList<>();
        String word = FastReader.getLine();
        Set<String> originalWords = new HashSet<>();
        while (!word.equals("#")) {
            dictionary.add(word);
            originalWords.add(word);
            word = FastReader.getLine();
        }

        String phrase = FastReader.getLine();
        while (!phrase.equals("#")) {
            List<String> anagrams = getAnagrams(dictionary, phrase, originalWords);
            for (String anagram : anagrams) {
                outputWriter.printLine(phrase + " = " + anagram);
            }
            phrase = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> getAnagrams(List<String> dictionaryList, String phrase, Set<String> originalWords) {
        List<String> anagrams = new ArrayList<>();
        String[] dictionary = dictionaryList.toArray(new String[0]);
        int[] frequencies = computeFrequencies(phrase, originalWords);
        int[] currentFrequency = new int[frequencies.length];

        getAnagrams(dictionary, anagrams, frequencies, new HashSet<>(), currentFrequency, 0);
        return anagrams;
    }

    private static void getAnagrams(String[] dictionary, List<String> anagrams, int[] targetFrequencies,
                                    Set<Integer> wordsIndexInCurrentSet, int[] currentFrequencies, int index) {
        int compareResult = compareFrequencies(targetFrequencies, currentFrequencies);
        if (compareResult == 0) {
            if (!wordsIndexInCurrentSet.isEmpty()) {
                List<String> wordsInAnagram = new ArrayList<>();
                StringBuilder anagram = new StringBuilder();

                for (int wordIndex : wordsIndexInCurrentSet) {
                    wordsInAnagram.add(dictionary[wordIndex]);
                }
                Collections.sort(wordsInAnagram);

                anagram.append(wordsInAnagram.get(0));
                for (int i = 1; i < wordsInAnagram.size(); i++) {
                    anagram.append(" ").append(wordsInAnagram.get(i));
                }
                anagrams.add(anagram.toString());
            }
            return;
        }

        if (index == dictionary.length) {
            return;
        }

        if (compareResult == 2) {
            addFrequency(currentFrequencies, dictionary[index]);
            wordsIndexInCurrentSet.add(index);

            getAnagrams(dictionary, anagrams, targetFrequencies, wordsIndexInCurrentSet,
                    currentFrequencies, index + 1);
            subtractFrequency(currentFrequencies, dictionary[index]);
            wordsIndexInCurrentSet.remove(index);

            getAnagrams(dictionary, anagrams, targetFrequencies, wordsIndexInCurrentSet,
                    currentFrequencies, index + 1);
        }
    }

    private static void addFrequency(int[] frequencies, String word) {
        for (char character : word.toCharArray()) {
            frequencies[character]++;
        }
    }

    private static void subtractFrequency(int[] frequencies, String word) {
        for (char character : word.toCharArray()) {
            frequencies[character]--;
        }
    }

    // 0 -> equal frequencies
    // 1 -> frequencies2 has one or more frequencies higher than frequencies1
    // 2 -> frequencies1 has one or more frequencies higher than frequencies2
    private static int compareFrequencies(int[] frequencies1, int[] frequencies2) {
        if (Arrays.equals(frequencies1, frequencies2)) {
            return 0;
        }
        for (int i = 0; i < frequencies1.length; i++) {
            if (frequencies2[i] > frequencies1[i]) {
                return 1;
            }
        }
        return 2;
    }

    private static int[] computeFrequencies(String string, Set<String> originalWords) {
        int[] frequencies = new int[256];

        String[] words = string.split(" ");
        for (String word : words) {
            if (originalWords.contains(word)) {
                continue;
            }
            addFrequency(frequencies, word);
        }
        return frequencies;
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