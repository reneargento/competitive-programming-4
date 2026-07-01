package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 30/06/2026.
 */
public class RaggedRight {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<String> paragraph = new ArrayList<>();
        String line = FastReader.getLine();
        int maxLength = 0;
        while (line != null) {
            paragraph.add(line);
            maxLength = Math.max(maxLength, line.length());
            line = FastReader.getLine();
        }

        int raggednessScore = computeRaggednessScore(paragraph, maxLength);
        outputWriter.printLine(raggednessScore);
        outputWriter.flush();
    }

    private static int computeRaggednessScore(List<String> paragraph, int maxLength) {
        int raggednessScore = 0;
        for (int i = 0; i < paragraph.size() - 1; i++) {
            int difference = maxLength - paragraph.get(i).length();
            raggednessScore += (difference * difference);
        }
        return raggednessScore;
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