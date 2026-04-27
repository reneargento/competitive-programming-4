package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/04/26.
 */
public class CryptKickerII {

    private static final String PLAINTEXT = "the quick brown fox jumps over the lazy dog";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();
        Map<Character, Integer> plaintextFrequencyMap = computeCharacterFrequency(PLAINTEXT);

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            List<String> text = new ArrayList<>();

            String line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                text.add(line);
                line = FastReader.getLine();
            }
            decodeText(outputWriter, text, plaintextFrequencyMap);
        }
        outputWriter.flush();
    }

    private static void decodeText(OutputWriter outputWriter, List<String> text,
                                   Map<Character, Integer> plaintextFrequencyMap) {
        Map<Character, Character> decodeMap = getDecodeMap(text, plaintextFrequencyMap);
        if (decodeMap == null) {
            outputWriter.printLine("No solution.");
            return;
        }

        for (String line : text) {
            for (char character : line.toCharArray()) {
                outputWriter.print(decodeMap.get(character));
            }
            outputWriter.printLine();
        }
    }

    private static Map<Character, Character> getDecodeMap(List<String> text,
                                                          Map<Character, Integer> plaintextFrequencyMap) {
        for (String line : text) {
            Map<Character, Character> decodeMap = getDecodeMap(line, plaintextFrequencyMap);
            if (decodeMap != null) {
                return decodeMap;
            }
        }
        return null;
    }

    private static Map<Character, Character> getDecodeMap(String line, Map<Character, Integer> plaintextFrequencyMap) {
        if (line.length() != PLAINTEXT.length()) {
            return null;
        }
        Map<Character, Character> decodeMap = new HashMap<>();
        Map<Character, Integer> textFrequencyMap = computeCharacterFrequency(line);

        for (int i = 0; i < line.length(); i++) {
            char lineCharacter = line.charAt(i);
            char plaintextCharacter = PLAINTEXT.charAt(i);

            if ((lineCharacter == ' ' && plaintextCharacter != ' ')
                    || (lineCharacter != ' ' && plaintextCharacter == ' ') ) {
                return null;
            }
            if (!decodeMap.containsKey(lineCharacter)) {
                if (!textFrequencyMap.get(lineCharacter).equals(plaintextFrequencyMap.get(plaintextCharacter))) {
                    return null;
                }
                decodeMap.put(lineCharacter, plaintextCharacter);
            } else {
                if (decodeMap.get(lineCharacter) != plaintextCharacter) {
                    return null;
                }
            }
        }
        return decodeMap;
    }

    private static Map<Character, Integer> computeCharacterFrequency(String text) {
        Map<Character, Integer> characterFrequencyMap = new HashMap<>();
        for (char character : text.toCharArray()) {
            int frequency = 0;
            if (characterFrequencyMap.containsKey(character)) {
                frequency = characterFrequencyMap.get(character);
            }
            characterFrequencyMap.put(character, frequency + 1);
        }
        return characterFrequencyMap;
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
