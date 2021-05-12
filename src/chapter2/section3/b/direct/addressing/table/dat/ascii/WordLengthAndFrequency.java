package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;

/**
 * Created by Rene Argento on 13/05/21.
 */
public class WordLengthAndFrequency {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        StringBuilder text = new StringBuilder();

        while (line != null) {
            if (line.equals("#")) {
                processBlock(text.toString(), outputWriter);
                text = new StringBuilder();
            } else {
                text.append(line);

                int lastIndex = line.length() - 1;
                if (lastIndex >= 0 && line.charAt(lastIndex) != '-') {
                    text.append(" ");
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void processBlock(String text, OutputWriter outputWriter) {
        text = text.replaceAll("'|’|-", "");
        String[] words = text.split(" |\\?|!|,|\\.");
        int[] frequency = new int[31];

        for (String word : words) {
            int length = word.length();
            frequency[length]++;
        }

        for (int i = 1; i < frequency.length; i++) {
            if (frequency[i] > 0) {
                outputWriter.printLine(i + " " + frequency[i]);
            }
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
