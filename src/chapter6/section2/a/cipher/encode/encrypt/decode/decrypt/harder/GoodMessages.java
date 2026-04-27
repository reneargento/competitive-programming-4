package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/04/26.
 */
public class GoodMessages {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int offset = FastReader.nextInt();
        String message = FastReader.getLine();
        int steps = FastReader.nextInt();

        String result = applyCipher(offset, message, steps);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String applyCipher(int offset, String message, int steps) {
        int borisHappy = 0;
        for (int step = 0; step < steps; step++) {
            StringBuilder nextMessage = new StringBuilder();

            for (char character : message.toCharArray()) {
                int index = ((character - 'a') + offset) % 26;
                char nextCharacter = (char) ('a' + index);
                nextMessage.append(nextCharacter);
            }
            borisHappy += checkVowels(nextMessage.toString());
            message = nextMessage.toString();
        }
        return (borisHappy > 0) ? "Boris" : "Colleague";
    }

    private static int checkVowels(String message) {
        int vowels = 0;
        for (char character : message.toCharArray()) {
            if (character == 'a'
                    || character == 'e'
                    || character == 'i'
                    || character == 'o'
                    || character == 'u'
                    || character == 'y') {
                vowels++;
            }
        }
        int halfConsonants = (int) Math.ceil((message.length() - vowels) / 2.0);
        if (vowels < halfConsonants) {
            return 1;
        }
        return -1;
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
