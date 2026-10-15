package chapter6.section7.anagram;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 13/10/2026.
 */
public class Multigram {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String word = FastReader.getLine();
        String result = isMultigram(word);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String isMultigram(String word) {
        List<Integer> factors = getFactors(word.length());
        Collections.sort(factors);

        for (int factor : factors) {
            int[] frequencies = countFrequencies(word, 0, factor);
            boolean isMultigram = true;

            for (int i = factor; i < word.length(); i += factor) {
                int[] nextFrequencies = countFrequencies(word, i, i + factor);
                if (!areEqualFrequencies(frequencies, nextFrequencies)) {
                    isMultigram = false;
                    break;
                }
            }

            if (isMultigram) {
                return word.substring(0, factor);
            }
        }
        return "-1";
    }

    private static int[] countFrequencies(String word, int startIndex, int endIndex) {
        int[] frequencies = new int[26];
        for (int i = startIndex; i < endIndex; i++) {
            int characterIndex = word.charAt(i) - 'a';
            frequencies[characterIndex]++;
        }
        return frequencies;
    }

    private static boolean areEqualFrequencies(int[] frequencies1, int[] frequencies2) {
        for (int i = 0; i < frequencies1.length; i++) {
            if (frequencies1[i] != frequencies2[i]) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> getFactors(int number) {
        List<Integer> factors = new ArrayList<>();
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factors.add(i);

                if (i != number / i && i != 1) {
                    factors.add(number / i);
                }
            }
        }
        return factors;
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