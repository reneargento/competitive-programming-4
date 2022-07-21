package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/07/22.
 */
public class ShortStoryCompetition {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            String[] words = new String[Integer.parseInt(data[0])];
            int maxLinesPerPage = Integer.parseInt(data[1]);
            int maxCharactersPerLine = Integer.parseInt(data[2]);
            for (int i = 0; i < words.length; i++) {
                words[i] = FastReader.next();
            }

            int minimumPages = computeMinimumPages(words, maxLinesPerPage, maxCharactersPerLine);
            outputWriter.printLine(minimumPages);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumPages(String[] words, int maxLinesPerPage, int maxCharactersPerLine) {
        int minimumPages = 1;
        int currentCharacterCount = 0;
        int currentLines = 1;

        for (String word : words) {
            int nextCharacterCount = currentCharacterCount + word.length();
            if (currentCharacterCount != 0) {
                nextCharacterCount++;
            }

            if (nextCharacterCount > maxCharactersPerLine) {
                if (currentLines == maxLinesPerPage) {
                    minimumPages++;
                    currentLines = 1;
                } else {
                    currentLines++;
                }
                currentCharacterCount = word.length();
            } else {
                currentCharacterCount = nextCharacterCount;
            }
        }
        return minimumPages;
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
