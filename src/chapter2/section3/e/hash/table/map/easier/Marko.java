package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/05/21.
 */
public class Marko {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int words = FastReader.nextInt();
        Map<String, Integer> keysFrequency = new HashMap<>();
        Map<Character, Character> digitToLetterMap = createDigitToLetterMap();

        for (int i = 0; i < words; i++) {
            String word = FastReader.next();
            String keys = mapWordToKeys(word, digitToLetterMap);
            int frequency = keysFrequency.getOrDefault(keys, 0);
            keysFrequency.put(keys, frequency + 1);
        }

        String keysPressed = FastReader.next();
        int wordsPossibleToConstruct = keysFrequency.getOrDefault(keysPressed, 0);
        outputWriter.printLine(wordsPossibleToConstruct);
        outputWriter.flush();
    }

    private static String mapWordToKeys(String word, Map<Character, Character> digitToLetterMap) {
        StringBuilder keys = new StringBuilder();
        for (char letter : word.toCharArray()) {
            keys.append(digitToLetterMap.get(letter));
        }
        return keys.toString();
    }

    private static Map<Character, Character> createDigitToLetterMap() {
        Map<Character, Character> digitToLetterMap = new HashMap<>();
        digitToLetterMap.put('a', '2');
        digitToLetterMap.put('b', '2');
        digitToLetterMap.put('c', '2');
        digitToLetterMap.put('d', '3');
        digitToLetterMap.put('e', '3');
        digitToLetterMap.put('f', '3');
        digitToLetterMap.put('g', '4');
        digitToLetterMap.put('h', '4');
        digitToLetterMap.put('i', '4');
        digitToLetterMap.put('j', '5');
        digitToLetterMap.put('k', '5');
        digitToLetterMap.put('l', '5');
        digitToLetterMap.put('m', '6');
        digitToLetterMap.put('n', '6');
        digitToLetterMap.put('o', '6');
        digitToLetterMap.put('p', '7');
        digitToLetterMap.put('q', '7');
        digitToLetterMap.put('r', '7');
        digitToLetterMap.put('s', '7');
        digitToLetterMap.put('t', '8');
        digitToLetterMap.put('u', '8');
        digitToLetterMap.put('v', '8');
        digitToLetterMap.put('w', '9');
        digitToLetterMap.put('x', '9');
        digitToLetterMap.put('y', '9');
        digitToLetterMap.put('z', '9');
        return digitToLetterMap;
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
