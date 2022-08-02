package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 31/07/22.
 */
public class Simplicity {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String string = FastReader.getLine();

        int minimumLettersToErase = computeMinimumLettersToErase(string);
        outputWriter.printLine(minimumLettersToErase);
        outputWriter.flush();
    }

    private static int computeMinimumLettersToErase(String string) {
        int minimumLettersToErase = 0;
        int[] frequency = new int[26];

        for (char character : string.toCharArray()) {
            int index = character - 'a';
            frequency[index]++;
        }
        Arrays.sort(frequency);

        for (int i = 0; i < frequency.length - 2; i++) {
            minimumLettersToErase += frequency[i];
        }
        return minimumLettersToErase;
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
