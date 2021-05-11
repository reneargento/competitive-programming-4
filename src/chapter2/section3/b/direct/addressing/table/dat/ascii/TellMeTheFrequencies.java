package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 10/05/21.
 */
public class TellMeTheFrequencies {

    private static class LetterFrequency implements Comparable<LetterFrequency> {
        int ascIIValue;
        int frequency;

        public LetterFrequency(int ascIIValue, int frequency) {
            this.ascIIValue = ascIIValue;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(LetterFrequency other) {
            if (frequency != other.frequency) {
                return Integer.compare(frequency, other.frequency);
            }
            return Integer.compare(other.ascIIValue, ascIIValue);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int caseNumber = 1;

        while (line != null) {
            if (caseNumber > 1) {
                outputWriter.printLine();
            }
            analyzeFrequency(line, outputWriter);
            line = FastReader.getLine();
            caseNumber++;
        }
        outputWriter.flush();
    }

    private static void analyzeFrequency(String line, OutputWriter outputWriter) {
        int[] frequencies = new int[100];

        for (char character : line.toCharArray()) {
            int index = (int) character - 32;
            frequencies[index]++;
        }

        List<LetterFrequency> letterFrequencyList = new ArrayList<>();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                int ascIIValue = i + 32;
                letterFrequencyList.add(new LetterFrequency(ascIIValue, frequencies[i]));
            }
        }

        Collections.sort(letterFrequencyList);
        for (LetterFrequency letterFrequency : letterFrequencyList) {
            outputWriter.printLine(letterFrequency.ascIIValue + " " + letterFrequency.frequency);
        }
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
