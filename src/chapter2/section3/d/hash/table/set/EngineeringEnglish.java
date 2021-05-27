package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 26/05/21.
 */
public class EngineeringEnglish {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        Set<String> uniqueWords = new HashSet<>();

        while (line != null) {
            String[] lineWords = line.split(" ");

            for (int i = 0; i < lineWords.length; i++) {
                String word = lineWords[i];
                String lowerCaseWord = word.toLowerCase();

                if (uniqueWords.contains(lowerCaseWord)) {
                    outputWriter.print(".");
                } else {
                    outputWriter.print(word);
                }

                if (i != lineWords.length - 1) {
                    outputWriter.print(" ");
                }
                uniqueWords.add(lowerCaseWord);
            }

            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
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
