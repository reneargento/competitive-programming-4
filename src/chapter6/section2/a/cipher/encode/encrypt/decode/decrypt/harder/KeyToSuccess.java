package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/04/26.
 */
public class KeyToSuccess {

    private static class KeyFrequency implements Comparable<KeyFrequency> {
        char value;
        int frequency;

        public KeyFrequency(char value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(KeyFrequency other) {
            return Integer.compare(other.frequency, this.frequency);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String clearMessage = FastReader.next();
            String encodedMessage = FastReader.next();

            String decodedText = decodeText(clearMessage, encodedMessage);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(decodedText);
        }
        outputWriter.flush();
    }

    private static String decodeText(String clearMessage, String encodedMessage) {
        Map<Character, Character> decodeMap = createDecodeMap(clearMessage, encodedMessage);
        StringBuilder decodedText = new StringBuilder();

        for (char character : encodedMessage.toCharArray()) {
            char decodedCharacter = decodeMap.get(character);
            decodedText.append(decodedCharacter);
        }
        return decodedText.toString();
    }

    private static Map<Character, Character> createDecodeMap(String clearMessage, String encodedMessage) {
        Map<Character, Character> decodeMap = new HashMap<>();
        List<KeyFrequency> clearFrequencies = computeFrequency(clearMessage);
        List<KeyFrequency> encodedFrequencies = computeFrequency(encodedMessage);

        for (int i = 0; i < clearFrequencies.size(); i++) {
            decodeMap.put(encodedFrequencies.get(i).value, clearFrequencies.get(i).value);
        }
        return decodeMap;
    }

    private static List<KeyFrequency> computeFrequency(String message) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char key : message.toCharArray()) {
            int frequency = 0;
            if (frequencyMap.containsKey(key)) {
                frequency = frequencyMap.get(key);
            }
            frequencyMap.put(key, frequency + 1);
        }

        List<KeyFrequency> keyFrequencies = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            keyFrequencies.add(new KeyFrequency(entry.getKey(), entry.getValue()));
        }
        Collections.sort(keyFrequencies);
        return keyFrequencies;
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

        public void flush() {
            writer.flush();
        }
    }
}
