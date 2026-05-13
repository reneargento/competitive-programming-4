package chapter6.section2.c.regular.expression;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rene Argento on 10/05/26.
 */
public class HaikuReview {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String haiku = FastReader.getLine();
        Pattern pattern = Pattern.compile("[aeiouy]+");
        int[] requiredSyllables = { 5, 7, 5 };
        while (!haiku.equals("e/o/i")) {
            String result = checkHaiku(pattern, requiredSyllables, haiku);
            outputWriter.printLine(result);
            haiku = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String checkHaiku(Pattern pattern, int[] requiredSyllables, String haiku) {
        String[] lines = haiku.split("/");
        for (int i = 0; i < lines.length; i++) {
            if (countSyllables(pattern, lines[i]) != requiredSyllables[i]) {
                return String.valueOf(i + 1);
            }
        }
        return "Y";
    }

    private static int countSyllables(Pattern pattern, String line) {
        int syllablesCount = 0;
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            syllablesCount++;
        }
        return syllablesCount;
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
