package chapter6.section7.palindrome.generating;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/10/2026.
 */
public class DromicpalinSubstrings {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String string = FastReader.getLine();
            int dromicpalinSubstrings = countDromicpalinSubstrings(string);
            outputWriter.printLine(String.format("Case %d: %s", t, dromicpalinSubstrings));
        }
        outputWriter.flush();
    }

    private static int countDromicpalinSubstrings(String string) {
        int dromicpalinSubstrings = string.length();

        for (int length = 2; length <= string.length(); length++) {
            int[] frequencies = computeInitialFrequencies(string, length);
            int oddFrequencies = countOddFrequencies(frequencies);
            if (oddFrequencies <= 1) {
                dromicpalinSubstrings++;
            }

            for (int i = length; i < string.length(); i++) {
                int oldCharacterIndex = getCharacterIndex(string.charAt(i - length));
                int newCharacterIndex = getCharacterIndex(string.charAt(i));

                frequencies[oldCharacterIndex]--;
                if (frequencies[oldCharacterIndex] % 2 == 0) {
                    oddFrequencies--;
                } else {
                    oddFrequencies++;
                }

                frequencies[newCharacterIndex]++;
                if (frequencies[newCharacterIndex] % 2 == 0) {
                    oddFrequencies--;
                } else {
                    oddFrequencies++;
                }

                if (oddFrequencies <= 1) {
                    dromicpalinSubstrings++;
                }
            }
        }
        return dromicpalinSubstrings;
    }

    private static int[] computeInitialFrequencies(String string, int length) {
        int[] frequencies = new int[26];
        for (int i = 0; i < length; i++) {
            frequencies[getCharacterIndex(string.charAt(i))]++;
        }
        return frequencies;
    }

    private static int getCharacterIndex(char character) {
        return (character - 'a');
    }

    private static int countOddFrequencies(int[] frequencies) {
        int oddFrequencies = 0;
        for (int frequency : frequencies) {
            if (frequency % 2 == 1) {
                oddFrequencies++;
            }
        }
        return oddFrequencies;
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

        public void flush() {
            writer.flush();
        }
    }
}