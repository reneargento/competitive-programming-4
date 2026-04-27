package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/04/26.
 */
public class Decode {

    private static class KeyFrequency implements Comparable<KeyFrequency> {
        char value;
        int frequency;

        public KeyFrequency(char value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(KeyFrequency other) {
            if (this.frequency != other.frequency) {
                return Integer.compare(other.frequency, this.frequency);
            }
            return Character.compare(this.value, other.value);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String knownMessage = readKnownMessage();
        String encodedMessage = readEncodedMessage();

        String decodedMessage = decodeText(knownMessage, encodedMessage);
        outputWriter.printLine(decodedMessage);
        outputWriter.flush();
    }

    private static String readKnownMessage() throws IOException {
        StringBuilder knownMessage = new StringBuilder();

        String line = FastReader.getLine();
        while (!line.isEmpty()) {
            knownMessage.append(line);
            line = FastReader.getLine();
        }
        return knownMessage.toString();
    }

    private static String readEncodedMessage() throws IOException {
        StringBuilder encodedMessage = new StringBuilder();
        String line = FastReader.getLine();
        while (line != null) {
            if (encodedMessage.length() > 0) {
                encodedMessage.append("\n");
            }
            encodedMessage.append(line);
            line = FastReader.getLine();
        }
        return encodedMessage.toString();
    }

    private static String decodeText(String knownMessage, String encodedMessage) {
        Map<Character, Character> decodeMap = createDecodeMap(knownMessage, encodedMessage);
        StringBuilder decodedText = new StringBuilder();

        for (char character : encodedMessage.toCharArray()) {
            char decodedCharacter;
            if (Character.isLetter(character)) {
                boolean isLowerCase = Character.isLowerCase(character);
                decodedCharacter = decodeMap.get(Character.toUpperCase(character));
                if (isLowerCase) {
                    decodedCharacter = Character.toLowerCase(decodedCharacter);
                }
            } else {
                decodedCharacter = character;
            }
            decodedText.append(decodedCharacter);
        }
        return decodedText.toString();
    }

    private static Map<Character, Character> createDecodeMap(String knownMessage, String encodedMessage) {
        Map<Character, Character> decodeMap = new HashMap<>();
        List<KeyFrequency> clearFrequencies = computeFrequency(knownMessage);
        List<KeyFrequency> encodedFrequencies = computeFrequency(encodedMessage);

        for (int i = 0; i < clearFrequencies.size(); i++) {
            decodeMap.put(encodedFrequencies.get(i).value, clearFrequencies.get(i).value);
        }
        return decodeMap;
    }

    private static List<KeyFrequency> computeFrequency(String message) {
        String uppercaseMessage = message.toUpperCase();
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char key : uppercaseMessage.toCharArray()) {
            if (!Character.isLetter(key)) {
                continue;
            }
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
