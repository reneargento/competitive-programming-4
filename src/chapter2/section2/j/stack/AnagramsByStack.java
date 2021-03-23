package chapter2.section2.j.stack;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/03/21.
 */
public class AnagramsByStack {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String sourceWord = FastReader.getLine();

        while (sourceWord != null) {
            String targetWord = FastReader.getLine();
            List<String> sequences = generateSequences(sourceWord, targetWord);
            printSequences(sequences, outputWriter);

            sourceWord = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> generateSequences(String sourceWord, String targetWord) {
        List<String> sequences = new ArrayList<>();
        if (sourceWord.length() == targetWord.length()) {
            generateSequences(sourceWord, targetWord, new ArrayDeque<>(), 0, 0, sequences, new StringBuilder());
        }
        Collections.sort(sequences);
        return sequences;
    }

    private static void generateSequences(String sourceWord, String targetWord, Deque<Character> stack,
                                          int sourceIndex, int targetIndex, List<String> sequences,
                                          StringBuilder currentSequence) {
        if (targetIndex == targetWord.length()) {
            sequences.add(currentSequence.toString());
            return;
        }

        if (!stack.isEmpty() && stack.peek() == targetWord.charAt(targetIndex)) {
            StringBuilder copySequence = new StringBuilder(currentSequence.toString());
            copySequence.append("o");
            char characterToPop = stack.pop();
            generateSequences(sourceWord, targetWord, stack, sourceIndex, targetIndex + 1,
                    sequences, copySequence);
            stack.push(characterToPop);
        }

        if (sourceIndex < sourceWord.length()) {
            currentSequence.append('i');
            stack.push(sourceWord.charAt(sourceIndex));
            generateSequences(sourceWord, targetWord, stack, sourceIndex + 1, targetIndex, sequences,
                    currentSequence);
            stack.pop();
        }
    }

    private static void printSequences(List<String> sequences, OutputWriter outputWriter) {
        outputWriter.printLine("[");
        for (String sequence : sequences) {
            for (int i = 0; i < sequence.length(); i++) {
                outputWriter.print(sequence.charAt(i));

                if (i != sequence.length() - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
        outputWriter.printLine("]");
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
