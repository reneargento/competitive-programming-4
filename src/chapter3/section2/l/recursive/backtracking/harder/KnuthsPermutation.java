package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;

/**
 * Created by Rene Argento on 20/02/22.
 */
public class KnuthsPermutation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int caseId = 1;

        while (line != null) {
            if (caseId > 1) {
                outputWriter.printLine();
            }
            char[] characters = line.toCharArray();
            printPermutations(characters, outputWriter, new StringBuilder(), 0);

            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printPermutations(char[] characters, OutputWriter outputWriter,
                                          StringBuilder currentCharacters, int index) {
        if (index == characters.length) {
            outputWriter.printLine(currentCharacters.toString());
            return;
        }

        for (int i = 0; i <= currentCharacters.length(); i++) {
            StringBuilder nextCharacters = new StringBuilder(currentCharacters);
            nextCharacters.insert(i, characters[index]);
            printPermutations(characters, outputWriter, nextCharacters, index + 1);
        }
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
