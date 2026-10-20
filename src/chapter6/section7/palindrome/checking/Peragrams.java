package chapter6.section7.palindrome.checking;

import java.io.*;

/**
 * Created by Rene Argento on 18/10/2026.
 */
public class Peragrams {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        int charactersToRemove = countCharactersToRemove(string);
        outputWriter.printLine(charactersToRemove);
        outputWriter.flush();
    }

    private static int countCharactersToRemove(String string) {
        int[] frequencies = new int[26];
        for (char character : string.toCharArray()) {
            frequencies[character - 'a']++;
        }

        int oddFrequencies = 0;
        for (int frequency : frequencies) {
            if (frequency % 2 != 0) {
                oddFrequencies++;
            }
        }
        return Math.max(oddFrequencies - 1, 0);
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