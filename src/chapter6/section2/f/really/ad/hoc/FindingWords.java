package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 01/07/2026.
 */
public class FindingWords {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        List<String> lines = new ArrayList<>();
        while (!line.equals("#")) {
            lines.add(line);
            line = FastReader.getLine();
        }
        formatText(lines, outputWriter);
        outputWriter.flush();
    }

    private static void formatText(List<String> lines, OutputWriter outputWriter) {
        int startLineIndex = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).substring(startLineIndex);
            if (line.endsWith("-")) {
                int lineEndIndex = line.length();
                for (int j = line.length() - 2; j >= 0; j--) {
                    if (line.charAt(j) == ' ') {
                        lineEndIndex = j;
                        break;
                    }
                }
                String firstPartWord = line.substring(lineEndIndex + 1, line.length() - 1);

                String nextLine = lines.get(i + 1);
                String nextLineFormatted = removeNonLetters(nextLine);
                int nextLineStartIndex = nextLineFormatted.indexOf(' ');
                String secondPartWord = nextLineFormatted.substring(0, nextLineStartIndex);
                String completeWord = firstPartWord + secondPartWord;

                String firstLineFormatted = removeNonLetters(line.substring(0, lineEndIndex + 1));
                outputWriter.printLine(firstLineFormatted);
                outputWriter.printLine(completeWord);
                startLineIndex = nextLine.indexOf(" ");
            } else {
                String formattedLine = removeNonLetters(line);
                outputWriter.printLine(formattedLine);
                startLineIndex = 0;
            }
        }
    }

    private static String removeNonLetters(String line) {
        return line.replaceAll("[^a-zA-Z ]", "");
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