package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by Rene Argento on 30/03/21.
 */
public class BrokenKeyboardAkaBeijuText {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String text = FastReader.getLine();

        while (text != null) {
            LinkedList<String> words = new LinkedList<>();
            boolean isAtHome = true;

            StringBuilder currentText = new StringBuilder();

            for (char character : text.toCharArray()) {
                if (character == '[' || character == ']') {
                    if (isAtHome) {
                        words.addFirst(currentText.toString());
                    } else {
                        words.addLast(currentText.toString());
                    }

                    currentText = new StringBuilder();
                    isAtHome = (character == '[');
                } else {
                    currentText.append(character);
                }
            }

            if (isAtHome) {
                words.addFirst(currentText.toString());
            } else {
                words.addLast(currentText.toString());
            }
            printBeijuText(words, outputWriter);

            text = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printBeijuText(LinkedList<String> words, OutputWriter outputWriter) {
        for (String word : words) {
            outputWriter.print(word);
        }
        outputWriter.printLine();
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
