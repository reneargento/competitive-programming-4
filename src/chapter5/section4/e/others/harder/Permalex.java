package chapter5.section4.e.others.harder;

import java.io.*;

/**
 * Created by Rene Argento on 06/01/26.
 */
public class Permalex {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();

        while (!string.equals("#")) {
            long position = computePosition(string);
            outputWriter.printLine(String.format("%10d", position));
            string = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computePosition(String string) {
        char[] characters = string.toCharArray();
        int[] frequency = countFrequency(characters);
        long position = 0;

        for (int i = 0; i < characters.length; i++) {
            int index = characters[i] - 'a';

            for (int j = 0; j < index; j++) {
                if (frequency[j] == 0) {
                    continue;
                }
                frequency[j]--;

                long[] skipsPerIndex = new long[31];
                for (int k = 2; k < characters.length - i; k++) {
                    skipsPerIndex[k] = k;
                }
                for (int k = 0; k < frequency.length; k++) {
                    for (int occurrenceIndex = 2; occurrenceIndex <= frequency[k]; occurrenceIndex++) {
                        long positionsSkipped = occurrenceIndex;
                        for (int l = 2; l < characters.length - i; l++) {
                            long gcd = gcd(positionsSkipped, skipsPerIndex[l]);
                            positionsSkipped /= gcd;
                            skipsPerIndex[l] /= gcd;
                        }
                    }
                }

                long totalPositionsSkipped = 1;
                for (int k = 2; k < characters.length - i; k++) {
                    totalPositionsSkipped *= skipsPerIndex[k];
                }
                position += totalPositionsSkipped;
                frequency[j]++;
            }
            frequency[index]--;
        }
        return position + 1;
    }

    private static int[] countFrequency(char[] characters) {
        int[] frequency = new int[26];
        for (char character : characters) {
            int index = character - 'a';
            frequency[index]++;
        }
        return frequency;
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
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
