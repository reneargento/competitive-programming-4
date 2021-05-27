package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 26/05/21.
 */
public class NoDuplicates {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] words = FastReader.getLine().split(" ");
        Set<String> uniqueWords = new HashSet<>();
        boolean noDuplicates = true;

        for (String word : words) {
            if (uniqueWords.contains(word)) {
                noDuplicates = false;
                break;
            }
            uniqueWords.add(word);
        }

        outputWriter.printLine(noDuplicates ? "yes" : "no");
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
