package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/04/26.
 */
public class PermutationEncryption {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int keyLength = FastReader.nextInt();
        while (keyLength > 0) {
            int[] key = new int[keyLength];
            for (int i = 0; i < keyLength; i++) {
                key[i] = FastReader.nextInt() - 1;
            }
            String message = FastReader.getLine();
            String encryptedMessage = encryptMessage(key, message);
            outputWriter.printLine(encryptedMessage);
            keyLength = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String encryptMessage(int[] key, String message) {
        StringBuilder messagePadded = new StringBuilder(message);
        while (messagePadded.length() % key.length != 0) {
            messagePadded.append(" ");
        }

        StringBuilder encryptedMessage = new StringBuilder("'");
        for (int i = 0; i + key.length <= messagePadded.length(); i += key.length) {
            for (int j = 0; j < key.length; j++) {
                char newCharacter = messagePadded.charAt(i + key[j]);
                encryptedMessage.append(newCharacter);
            }
        }
        return encryptedMessage.append("'").toString();
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
