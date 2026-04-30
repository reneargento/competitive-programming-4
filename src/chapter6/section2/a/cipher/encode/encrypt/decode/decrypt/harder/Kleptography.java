package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/04/26.
 */
public class Kleptography {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int keywordLength = FastReader.nextInt(); // not used
        int textLength = FastReader.nextInt(); // not used
        String lastPlaintext = FastReader.getLine();
        String cipherText = FastReader.getLine();

        String decryptedText = decryptText(lastPlaintext, cipherText);
        outputWriter.printLine(decryptedText);
        outputWriter.flush();
    }

    private static String decryptText(String lastPlaintext, String cipherText) {
        StringBuilder decryptedText = new StringBuilder(lastPlaintext);
        int decryptedTextIndex = decryptedText.length() - 1;

        for (int i = cipherText.length() - 1; i >= 0; i--) {
            int decryptedCharIndex =
                    mod(cipherText.charAt(i) - decryptedText.charAt(decryptedTextIndex), 26);
            char decryptedChar = (char) ('a' + decryptedCharIndex);
            decryptedText.insert(0, decryptedChar);
        }
        return decryptedText.substring(lastPlaintext.length());
    }

    private static int mod(int number, int mod) {
        return ((number % mod) + mod) % mod;
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
