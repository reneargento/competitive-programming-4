package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 07/08/22.
 */
public class DiscretePursuit {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            List<String> words = getWords(line);
            int thiefX = Integer.parseInt(words.get(0));
            int horizontalSpeedThief = Integer.parseInt(words.get(1));
            int verticalSpeedThief = Integer.parseInt(words.get(2));

            int minimumTimeToCatchThief = computeMinimumTimeToCatchThief(thiefX, horizontalSpeedThief,
                    verticalSpeedThief);
            outputWriter.printLine(minimumTimeToCatchThief);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumTimeToCatchThief(int thiefX, int horizontalSpeedThief, int verticalSpeedThief) {
        int timeToCatch = 0;
        int thiefY = 0;
        int speedCopMin = 0;
        int speedCopMax = 0;
        int minimum = 0;
        int maximum = 0;

        while (thiefX < minimum || thiefX > maximum
                || thiefY < minimum || thiefY > maximum) {
            timeToCatch++;
            thiefX += horizontalSpeedThief;
            thiefY += verticalSpeedThief;

            speedCopMin--;
            speedCopMax++;

            minimum += speedCopMin;
            maximum += speedCopMax;
        }
        return timeToCatch;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
