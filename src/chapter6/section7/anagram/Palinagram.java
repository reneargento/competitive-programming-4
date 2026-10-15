package chapter6.section7.anagram;

import java.io.*;

/**
 * Created by Rene Argento on 13/10/2026.
 */
public class Palinagram {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        while (!string.equals("#")) {
            String palinagramAppendix = computePalinagramAppendix(string);
            outputWriter.printLine(palinagramAppendix);
            string = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computePalinagramAppendix(String string) {
        int[] characterFrequency = computeCharacterFrequency(string);
        int oddFrequencies = countOddFrequencies(characterFrequency);
        if (oddFrequencies <= 1) {
            return "";
        }

        StringBuilder palinagramAppendix = new StringBuilder();
        int oddFrequenciesComputed = 0;
        for (int i = 0; i < characterFrequency.length; i++) {
            if (characterFrequency[i] % 2 == 1) {
                char characterValue = (char) (i + 'a');
                palinagramAppendix.append(characterValue);

                oddFrequenciesComputed++;
                if (oddFrequenciesComputed == oddFrequencies - 1) {
                    break;
                }
            }
        }
        return palinagramAppendix.toString();
    }

    private static int[] computeCharacterFrequency(String string) {
        int[] characterFrequency = new int[26];

        for (char character : string.toCharArray()) {
            int characterIndex = character - 'a';
            characterFrequency[characterIndex]++;
        }
        return characterFrequency;
    }

    private static int countOddFrequencies(int[] characterFrequency) {
        int oddFrequencies = 0;
        for (int frequency : characterFrequency) {
            if (frequency % 2 == 1) {
                oddFrequencies++;
            }
        }
        return oddFrequencies;
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