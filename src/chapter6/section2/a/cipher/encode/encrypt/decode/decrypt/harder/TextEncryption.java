package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/04/26.
 */
public class TextEncryption {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int skip = FastReader.nextInt();
        while (skip != 0) {
            String message = FastReader.getLine().toUpperCase();
            String encryptedMessage = encryptMessage(message, skip);

            outputWriter.printLine(encryptedMessage);
            skip = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String encryptMessage(String message, int skip) {
        message = message.replaceAll(" ", "");
        if (message.length() <= skip) {
            return message;
        }

        char[] encryptedMessage = new char[message.length()];
        boolean[] filled = new boolean[message.length()];
        int nextIndex = 0;

        for (int i = 0; i < message.length(); i++) {
            while (filled[nextIndex]) {
                nextIndex = (nextIndex + 1) % encryptedMessage.length;
            }
            encryptedMessage[nextIndex] = message.charAt(i);
            filled[nextIndex] = true;

            if (nextIndex + skip >= message.length()) {
                nextIndex = 0;
            } else {
                nextIndex = nextIndex + skip;
            }
        }
        return new String(encryptedMessage);
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
