package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/10/21.
 */
public class TheSkylineProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int[] heights = new int[10000];

        while (line != null) {
            List<String> data = getWords(line);
            int left = Integer.parseInt(data.get(0));
            int right = Integer.parseInt(data.get(2));
            int height = Integer.parseInt(data.get(1));

            for (int coordinate = left; coordinate < right; coordinate++) {
                if (heights[coordinate] < height) {
                    heights[coordinate] = height;
                }
            }
            line = FastReader.getLine();
        }

        StringBuilder skylineVector = new StringBuilder();
        int currentHeight = 0;
        boolean isFirstBuilding = true;

        for (int coordinate = 0; coordinate < heights.length; coordinate++) {
            int height = heights[coordinate];

            if (height != currentHeight) {
                if (!isFirstBuilding) {
                    skylineVector.append(" ");
                } else {
                    isFirstBuilding = false;
                }

                skylineVector.append(coordinate).append(" ").append(height);
                currentHeight = height;
            }
        }
        outputWriter.printLine(skylineVector);
        outputWriter.flush();
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ') {
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
