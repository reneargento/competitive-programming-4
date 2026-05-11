package chapter6.section2.c.regular.expression;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rene Argento on 09/05/26.
 */
public class KindergartenCountingGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int wordsCount = countWords(line);
            outputWriter.printLine(wordsCount);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countWords(String line) {
        int wordsCount = 0;
        line = line.replaceAll("[^a-zA-Z]", " ");
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        String[] words = line.split("\\s+");
        for (String word : words) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.find()) {
                wordsCount++;
            }
        }
        return wordsCount;
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
