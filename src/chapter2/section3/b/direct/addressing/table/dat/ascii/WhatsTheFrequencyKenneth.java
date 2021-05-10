package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 07/05/21.
 */
public class WhatsTheFrequencyKenneth {

    private static class FrequencyResult {
        String letters;
        int frequency;

        public FrequencyResult(String letters, int frequency) {
            this.letters = letters;
            this.frequency = frequency;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            FrequencyResult frequencyResult = analyzeLine(line);
            outputWriter.printLine(frequencyResult.letters + " " + frequencyResult.frequency);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static FrequencyResult analyzeLine(String line) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int highestFrequency = 0;

        for (char character : line.toCharArray()) {
            if (!Character.isLetter(character)) {
                continue;
            }
            int frequency = frequencyMap.getOrDefault(character, 0);
            int newFrequency = frequency + 1;
            frequencyMap.put(character, newFrequency);
            highestFrequency = Math.max(highestFrequency, newFrequency);
        }

        StringBuilder letters = new StringBuilder();
        for (Character letter : frequencyMap.keySet()) {
            if (frequencyMap.get(letter) == highestFrequency) {
                letters.append(letter);
            }
        }

        char[] sortedLetters = letters.toString().toCharArray();
        Arrays.sort(sortedLetters);
        return new FrequencyResult(new String(sortedLetters), highestFrequency);
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
