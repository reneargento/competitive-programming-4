package chapter5.section2.c.base.number.conversion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 25/01/25.
 */
public class WhatBaseIsThis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            List<String> words = getWords(line);
            computeEqualBases(words.get(0), words.get(1), outputWriter);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void computeEqualBases(String number1, String number2, OutputWriter outputWriter) {
        for (int base1 = 1; base1 <= 36; base1++) {
            for (int base2 = 1; base2 <= 36; base2++) {
                try {
                    long value1 = Long.parseLong(number1, base1);
                    long value2 = Long.parseLong(number2, base2);

                    if (value1 == value2) {
                        outputWriter.printLine(String.format("%s (base %d) = %s (base %d)", number1, base1, number2, base2));
                        return;
                    }
                } catch (NumberFormatException exception) {}
            }
        }
        outputWriter.printLine(String.format("%s is not equal to %s in any base 2..36", number1, number2));
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
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

        public void flush() {
            writer.flush();
        }
    }
}