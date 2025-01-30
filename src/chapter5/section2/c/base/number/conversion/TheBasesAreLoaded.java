package chapter5.section2.c.base.number.conversion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 25/01/25.
 */
public class TheBasesAreLoaded {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            List<String> values = getWords(line);
            int currentBase = Integer.parseInt(values.get(0));
            int targetBase = Integer.parseInt(values.get(1));
            String number = values.get(2);

            String numberInTargetBase = computeNumberInBase(number, currentBase, targetBase);
            if (numberInTargetBase == null) {
                outputWriter.printLine(String.format("%s is an illegal base %d number", number, currentBase));
            } else {
                outputWriter.printLine(String.format("%s base %d = %s base %d", number, currentBase, numberInTargetBase, targetBase));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computeNumberInBase(String number, int currentBase, int targetBase) {
        try {
            long numberInBase10 = Long.parseLong(number, currentBase);
            return Long.toString(numberInBase10, targetBase).toUpperCase();
        } catch (NumberFormatException exception) {
            return null;
        }
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