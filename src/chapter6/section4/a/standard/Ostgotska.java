package chapter6.section4.a.standard;

import java.io.*;

/**
 * Created by Rene Argento on 18/08/2026.
 */
public class Ostgotska {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] words = FastReader.getLine().split(" ");
        String result = checkLanguage(words);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String checkLanguage(String[] words) {
        int wordsNeededForOstgotska = (int) Math.ceil((words.length * 40) / 100.00);
        int wordsInOstgotska = 0;

        for (String word : words) {
            if (hasAeSubstring(word)) {
                wordsInOstgotska++;
            }
        }

        if (wordsInOstgotska >= wordsNeededForOstgotska) {
            return "dae ae ju traeligt va";
        } else {
            return "haer talar vi rikssvenska";
        }
    }

    private static boolean hasAeSubstring(String word) {
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i - 1) == 'a' && word.charAt(i) == 'e') {
                return true;
            }
        }
        return false;
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