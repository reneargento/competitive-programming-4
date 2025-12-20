package chapter5.section4.d.others.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 18/12/25.
 */
public class LittleNephew {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            List<String> words = getWords(line);
            long hats = Integer.parseInt(words.get(0));
            long shirts = Integer.parseInt(words.get(1));
            long pants = Integer.parseInt(words.get(2));
            long socks = Integer.parseInt(words.get(3));
            long shoes = Integer.parseInt(words.get(4));

            if (hats == 0 && shirts == 0 && pants == 0 && socks == 0 && shoes == 0) {
                break;
            }
            long waysToDress = hats * shirts * pants * socks * socks * shoes * shoes;
            outputWriter.printLine(waysToDress);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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
