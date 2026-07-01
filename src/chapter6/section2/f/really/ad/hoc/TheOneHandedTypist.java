package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/06/2026.
 */
public class TheOneHandedTypist {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<Character, Integer> keyToFingerMap = buildKeyToFingerMap();
        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int fingersNotUsedNumber = Integer.parseInt(data[0]);
            String[] words = new String[Integer.parseInt(data[1])];

            Set<Integer> fingersNotUsed = new HashSet<>();
            for (int i = 0; i < fingersNotUsedNumber; i++) {
                fingersNotUsed.add(FastReader.nextInt());
            }

            for (int i = 0; i < words.length; i++) {
                words[i] = FastReader.getLine();
            }

            List<String> longestTypableWords = computeLongestTypableWords(keyToFingerMap, fingersNotUsed, words);
            outputWriter.printLine(longestTypableWords.size());
            for (String word : longestTypableWords) {
                outputWriter.printLine(word);
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> computeLongestTypableWords(Map<Character, Integer> keyToFingerMap,
                                                           Set<Integer> fingersNotUsed, String[] words) {
        Set<String> longestTypableWordsSet = new HashSet<>();
        int maxLength = 0;

        for (String word : words) {
            if (canType(keyToFingerMap, fingersNotUsed, word)) {
                if (longestTypableWordsSet.isEmpty()
                        || maxLength < word.length()) {
                    longestTypableWordsSet = new HashSet<>();
                    longestTypableWordsSet.add(word);
                    maxLength = word.length();
                } else if (maxLength == word.length()) {
                    longestTypableWordsSet.add(word);
                }
            }
        }

        List<String> longestTypableWords = new ArrayList<>(longestTypableWordsSet);
        Collections.sort(longestTypableWords);
        return longestTypableWords;
    }

    private static boolean canType(Map<Character, Integer> keyToFingerMap, Set<Integer> fingersNotUsed, String word) {
        for (char character : word.toCharArray()) {
            int fingerNeeded = keyToFingerMap.get(character);
            if (fingersNotUsed.contains(fingerNeeded)) {
                return false;
            }
        }
        return true;
    }

    private static Map<Character, Integer> buildKeyToFingerMap() {
        Map<Character, Integer> keyToFingerMap = new HashMap<>();
        keyToFingerMap.put('q', 1);
        keyToFingerMap.put('a', 1);
        keyToFingerMap.put('z', 1);
        keyToFingerMap.put('w', 2);
        keyToFingerMap.put('s', 2);
        keyToFingerMap.put('x', 2);
        keyToFingerMap.put('e', 3);
        keyToFingerMap.put('d', 3);
        keyToFingerMap.put('c', 3);
        keyToFingerMap.put('r', 4);
        keyToFingerMap.put('f', 4);
        keyToFingerMap.put('v', 4);
        keyToFingerMap.put('t', 4);
        keyToFingerMap.put('g', 4);
        keyToFingerMap.put('b', 4);
        keyToFingerMap.put('y', 7);
        keyToFingerMap.put('h', 7);
        keyToFingerMap.put('n', 7);
        keyToFingerMap.put('u', 7);
        keyToFingerMap.put('j', 7);
        keyToFingerMap.put('m', 7);
        keyToFingerMap.put('i', 8);
        keyToFingerMap.put('k', 8);
        keyToFingerMap.put(',', 8);
        keyToFingerMap.put('o', 9);
        keyToFingerMap.put('l', 9);
        keyToFingerMap.put('.', 9);
        keyToFingerMap.put('p', 10);
        keyToFingerMap.put(';', 10);
        keyToFingerMap.put('/', 10);
        return keyToFingerMap;
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