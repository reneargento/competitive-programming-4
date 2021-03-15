package chapter2.section2.i.big.integer;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 15/03/21.
 */
public class IfWeWereAChildAgain {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            List<String> words = getWords(line);
            BigInteger number1 = new BigInteger(words.get(0));
            BigInteger number2 = new BigInteger(words.get(2));
            BigInteger result;

            if (words.get(1).equals("/")) {
                result = number1.divide(number2);
            } else {
                result = number1.mod(number2);
            }
            outputWriter.printLine(result);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
