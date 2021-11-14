package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 14/11/21.
 */
public class Lektira {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String word = FastReader.getLine();

        String bestWord = getBestWord(word);
        outputWriter.printLine(bestWord);

        outputWriter.flush();
    }

    private static String getBestWord(String word) {
        List<String> wordsFormed = new ArrayList<>();

        for (int i = 1; i < word.length(); i++) {
            for (int j = i + 1; j < word.length(); j++) {
                StringBuilder part1 = new StringBuilder(word.substring(0, i));
                StringBuilder part2 = new StringBuilder(word.substring(i, j));
                StringBuilder part3 = new StringBuilder(word.substring(j));

                String finalWord = part1.reverse().append(part2.reverse()).append(part3.reverse()).toString();
                wordsFormed.add(finalWord);
            }
        }
        Collections.sort(wordsFormed);
        return wordsFormed.get(0);
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
