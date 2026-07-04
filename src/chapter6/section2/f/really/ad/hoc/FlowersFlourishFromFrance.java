package chapter6.section2.f.really.ad.hoc;

import java.io.*;

/**
 * Created by Rene Argento on 03/07/2026.
 */
public class FlowersFlourishFromFrance {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String sentence = FastReader.getLine();
        while (!sentence.equals("*")) {
            char result = isTautogram(sentence);
            outputWriter.printLine(result);
            sentence = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static char isTautogram(String sentence) {
        String[] words = sentence.toLowerCase().split(" ");
        char firstLetter = words[0].charAt(0);
        for (int i = 1; i < words.length; i++) {
            if (words[i].charAt(0) != firstLetter) {
                return 'N';
            }
        }
        return 'Y';
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