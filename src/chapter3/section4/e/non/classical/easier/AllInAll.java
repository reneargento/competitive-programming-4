package chapter3.section4.e.non.classical.easier;

import java.io.*;

/**
 * Created by Rene Argento on 19/07/22.
 */
public class AllInAll {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] words = line.split(" ");
            boolean isSubsequence = isSubsequence(words[0], words[1]);
            outputWriter.printLine(isSubsequence? "Yes" : "No");
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isSubsequence(String word1, String word2) {
        int word2Index = 0;
        for (int word1Index = 0; word1Index < word1.length(); word1Index++) {
            boolean matched = false;
            for (; word2Index < word2.length(); word2Index++) {
                if (word2.charAt(word2Index) == word1.charAt(word1Index)) {
                    word2Index++;
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }
        return true;
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
