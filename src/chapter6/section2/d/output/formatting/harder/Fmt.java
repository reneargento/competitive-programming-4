package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 24/06/2026.
 */
public class Fmt {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        List<String> words = new ArrayList<>();
        int lineLength = -1;
        while (line != null) {
            if (line.isEmpty()) {
                print(words, outputWriter);
                outputWriter.printLine();
                words = new ArrayList<>();
                lineLength = -1;
            } else {
                if (line.charAt(0) == ' ') {
                    print(words, outputWriter);
                    words = new ArrayList<>();
                    lineLength = -1;
                }

                String[] tokens =  line.split(" ", -1);
                for (String word : tokens) {
                    if (lineLength != 72 || !word.isEmpty()) {
                        if (lineLength + 1 + word.length() > 72) {
                            print(words, outputWriter);
                            words = new ArrayList<>();
                            words.add(word);
                            lineLength = word.length();
                        } else {
                            words.add(word);
                            lineLength += 1 + word.length();
                        }
                    }
                }
            }
            line = FastReader.getLine();
        }
        print(words, outputWriter);
        outputWriter.flush();
    }

    private static void print(List<String> words, OutputWriter outputWriter) {
        if (words.isEmpty()) {
            return;
        }
        String line = String.join(" ", words);
        line = line.replaceFirst("\\s+$", "");
        outputWriter.printLine(line);
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