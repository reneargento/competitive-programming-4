package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/05/21.
 */
public class LetterFrequency {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine().toLowerCase();
            String highestFrequencyLetters = getHighestFrequencyLetters(line);
            outputWriter.printLine(highestFrequencyLetters);
        }
        outputWriter.flush();
    }

    private static String getHighestFrequencyLetters(String line) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int highestFrequency = 0;

        for (char character : line.toCharArray()) {
            if (Character.isLetter(character)) {
                int frequency = frequencyMap.getOrDefault(character, 0);
                int newFrequency = frequency + 1;
                frequencyMap.put(character, newFrequency);
                highestFrequency = Math.max(highestFrequency, newFrequency);
            }
        }

        StringBuilder highestFrequencyLetters = new StringBuilder();
        for (char letter : frequencyMap.keySet()) {
            if (frequencyMap.get(letter) == highestFrequency) {
                highestFrequencyLetters.append(letter);
            }
        }

        char[] letters = highestFrequencyLetters.toString().toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
