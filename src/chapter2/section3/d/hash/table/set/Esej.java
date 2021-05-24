package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/05/21.
 */
public class Esej {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int minimumNumberOfWords = FastReader.nextInt();
        int maximumNumberOfWords = FastReader.nextInt();
        StringBuilder word = new StringBuilder("a");

        for (int i = 0; i < maximumNumberOfWords; i++) {
            outputWriter.print(word.toString());
            getNextWord(word);

            if (i != maximumNumberOfWords - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static void getNextWord(StringBuilder word) {
        int lastIndex = word.length() - 1;
        char lastCharacter = word.charAt(lastIndex);
        word.deleteCharAt(lastIndex);

        if (lastCharacter != 'z') {
            char nextChar = (char) (lastCharacter + 1);
            word.append(nextChar);
        } else {
            word.append('a');
            int indexToIncrement = lastIndex - 1;

            while (indexToIncrement >= 0) {
                if (word.charAt(indexToIncrement) != 'z') {
                    break;
                } else {
                    word.replace(indexToIncrement, indexToIncrement + 1, "a");
                }
                indexToIncrement--;
            }

            if (indexToIncrement >= 0) {
                char currentCharacter = word.charAt(indexToIncrement);
                char nextChar = (char) (currentCharacter + 1);
                String stringToReplace = String.valueOf(nextChar);
                word.replace(indexToIncrement, indexToIncrement + 1, stringToReplace);
            } else {
                word.append('a');
            }
        }
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
