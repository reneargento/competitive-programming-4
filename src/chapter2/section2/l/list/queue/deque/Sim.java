package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/04/21.
 */
public class Sim {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            LinkedList<Character> displayedText = new LinkedList<>();
            LinkedList<Character> currentText = new LinkedList<>();

            boolean isOnHome = false;

            for (char character : line.toCharArray()) {
                if (character == '[' || character == ']') {
                    if (isOnHome) {
                        appendListFirst(displayedText, currentText);
                    } else {
                        displayedText.addAll(currentText);
                    }
                    currentText = new LinkedList<>();

                    isOnHome = character == '[';
                } else if (character == '<') {
                    if (currentText.isEmpty() && !isOnHome && !displayedText.isEmpty()) {
                        displayedText.removeLast();
                    } else if (!currentText.isEmpty()) {
                        currentText.removeLast();
                    }
                } else {
                    currentText.addLast(character);
                }
            }

            if (isOnHome) {
                appendListFirst(displayedText, currentText);
            } else {
                displayedText.addAll(currentText);
            }

            printText(displayedText, outputWriter);
        }
        outputWriter.flush();
    }

    private static void appendListFirst(LinkedList<Character> displayedText, LinkedList<Character> currentText) {
        for (int i = currentText.size() - 1; i >= 0; i--) {
            displayedText.addFirst(currentText.get(i));
        }
    }

    private static void printText(LinkedList<Character> displayedText, OutputWriter outputWriter) {
        for (Character character : displayedText) {
            outputWriter.print(character);
        }
        outputWriter.printLine();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
