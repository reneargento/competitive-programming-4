package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/04/26.
 */
public class CrackingTheCode {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] encryptedMessages = new String[FastReader.nextInt()];
            for (int i = 0; i < encryptedMessages.length; i++) {
                encryptedMessages[i] = FastReader.getLine();
            }
            String decryptedMessage = FastReader.getLine();
            String messageToBeDecoded = FastReader.getLine();

            String nextDecryptedMessage = decryptMessage(encryptedMessages, decryptedMessage, messageToBeDecoded);
            if (nextDecryptedMessage == null) {
                outputWriter.printLine("IMPOSSIBLE");
            } else {
                outputWriter.printLine(nextDecryptedMessage);
            }
        }
        outputWriter.flush();
    }

    private static String decryptMessage(String[] encryptedMessages, String decryptedMessage, String messageToBeDecoded) {
        StringBuilder nextDecryptedMessage = new StringBuilder();
        List<String> matches = getPossibleMatches(encryptedMessages, decryptedMessage);
        if (matches.isEmpty()) {
            return null;
        }

        Map<Character, Character> decoderMap = createDecoderMap(decryptedMessage, matches);
        for (char character : messageToBeDecoded.toCharArray()) {
            if (decoderMap.containsKey(character)) {
                nextDecryptedMessage.append(decoderMap.get(character));
            } else {
                nextDecryptedMessage.append('?');
            }
        }
        return nextDecryptedMessage.toString();
    }

    private static List<String> getPossibleMatches(String[] encryptedMessages, String decryptedMessage) {
        List<String> matches = new ArrayList<>();

        for (String encryptedMessage : encryptedMessages) {
            if (isMatch(encryptedMessage, decryptedMessage)) {
                matches.add(encryptedMessage);
            }
        }
        return matches;
    }

    private static boolean isMatch(String encryptedMessage, String decryptedMessage) {
        if (encryptedMessage.length() != decryptedMessage.length()) {
            return false;
        }
        int[] frequenciesDecrypted = new int[26];
        int[] frequenciesEncrypted = new int[26];
        Map<Character, Character> decryptMap = new HashMap<>();

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char decryptedChar = encryptedMessage.charAt(i);
            char encryptedChar = decryptedMessage.charAt(i);

            if (decryptMap.containsKey(decryptedChar)) {
                if (decryptMap.get(decryptedChar) != encryptedChar) {
                    return false;
                }
            } else {
                decryptMap.put(decryptedChar, encryptedChar);
            }
            frequenciesDecrypted[decryptedChar - 'a']++;
            frequenciesEncrypted[encryptedChar - 'a']++;
        }

        for (Map.Entry<Character, Character> entry : decryptMap.entrySet()) {
            if (frequenciesDecrypted[entry.getKey() - 'a'] != frequenciesEncrypted[entry.getValue() - 'a']) {
                return false;
            }
        }
        return true;
    }

    private static Map<Character, Character> createDecoderMap(String decryptedMessage, List<String> matches) {
        Map<Character, Character> decoderMap = new HashMap<>();

        if (matches.size() == 1) {
            for (int i = 0; i < decryptedMessage.length(); i++) {
                char encryptedChar = matches.get(0).charAt(i);
                decoderMap.put(encryptedChar, decryptedMessage.charAt(i));
            }
        } else {
            for (int i = 0; i < decryptedMessage.length(); i++) {
                char character = decryptedMessage.charAt(i);
                char encryptedChar = matches.get(0).charAt(i);
                boolean canConfirm = true;

                for (int j = 1; j < matches.size(); j++) {
                    char encryptedCharCandidate = matches.get(j).charAt(i);
                    if (encryptedCharCandidate != encryptedChar) {
                        canConfirm = false;
                        break;
                    }
                }

                if (canConfirm) {
                    decoderMap.put(encryptedChar, character);
                }
            }
        }

        if (decoderMap.size() == 25) {
            handleSuperEdgeCase(decoderMap);
        }
        return decoderMap;
    }

    private static void handleSuperEdgeCase(Map<Character, Character> decoderMap) {
        Set<Character> encryptedSet = new HashSet<>();
        Set<Character> decryptedSet = new HashSet<>();

        for (Map.Entry<Character, Character> entry : decoderMap.entrySet()) {
            encryptedSet.add(entry.getKey());
            decryptedSet.add(entry.getValue());
        }

        char missingEncryptedChar = '?';
        char missingDecryptedChar = '?';
        for (char character = 'a'; character <= 'z'; character++) {
            if (!encryptedSet.contains(character)) {
                missingEncryptedChar = character;
            }
            if (!decryptedSet.contains(character)) {
                missingDecryptedChar = character;
            }
        }
        decoderMap.put(missingEncryptedChar, missingDecryptedChar);
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
