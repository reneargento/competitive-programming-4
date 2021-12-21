package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 20/12/21.
 */
public class The3n1problem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            List<String> data = getWords(line);
            int start = Integer.parseInt(data.get(0));
            int end = Integer.parseInt(data.get(1));

            int min = Math.min(start, end);
            int max = Math.max(start, end);

            int maxCycleLength = computeMaxCycleLength(min, max);
            outputWriter.printLine(String.format("%d %d %d", start, end, maxCycleLength));

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMaxCycleLength(int start, int end) {
        int maxCycleLength = 0;

        for (int number = start; number <= end; number++) {
            int cycleLength = computeCycleLength(number);
            maxCycleLength = Math.max(maxCycleLength, cycleLength);
        }
        return maxCycleLength;
    }

    private static int computeCycleLength(int number) {
        int cycleLength = 1;

        while (number != 1) {
            if (number % 2 == 0) {
                number /= 2;
            } else {
                number = 3 * number + 1;
            }
            cycleLength++;
        }
        return cycleLength;
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
