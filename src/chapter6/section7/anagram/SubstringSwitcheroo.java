package chapter6.section7.anagram;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/10/2026.
 */
public class SubstringSwitcheroo {

    private static class FrequencyKey {
        private final int[] frequencies;
        private final int hashcode;

        public FrequencyKey(int[] frequencies) {
            this.frequencies = frequencies.clone();
            this.hashcode = Arrays.hashCode(frequencies);
        }

        @Override
        public int hashCode() {
            return hashcode;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FrequencyKey other)) {
                return false;
            }
            return Arrays.equals(frequencies, other.frequencies);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String originalString = FastReader.getLine();
            String editedString = FastReader.getLine();

            String longestAnagram = computeLongestAnagram(originalString, editedString);
            outputWriter.printLine(longestAnagram);
        }
        outputWriter.flush();
    }

    private static String computeLongestAnagram(String originalString, String editedString) {
        for (int length = originalString.length(); length >= 1; length--) {
            Set<FrequencyKey> frequencyKeys = computeFrequencySet(editedString, length);

            int[] originalStringFrequencies = computeFrequencies(originalString, 0, length);
            if (frequencyKeys.contains(new FrequencyKey(originalStringFrequencies))) {
                return originalString.substring(0, length);
            }

            for (int start = 1; start + length <= originalString.length(); start++) {
                originalStringFrequencies[originalString.charAt(start - 1) - 'a']--;
                originalStringFrequencies[originalString.charAt(start + length - 1) - 'a']++;

                if (frequencyKeys.contains(new FrequencyKey(originalStringFrequencies))) {
                    return originalString.substring(start, start + length);
                }
            }
        }
        return "NONE";
    }

    private static Set<FrequencyKey> computeFrequencySet(String editedString, int length) {
        Set<FrequencyKey> frequencyKeys = new HashSet<>();
        int[] editedStringFrequencies = computeFrequencies(editedString, 0, length);
        frequencyKeys.add(new FrequencyKey(editedStringFrequencies));

        for (int i = length; i < editedString.length(); i++) {
            editedStringFrequencies[editedString.charAt(i - length) - 'a']--;
            editedStringFrequencies[editedString.charAt(i) - 'a']++;
            frequencyKeys.add(new FrequencyKey(editedStringFrequencies));
        }
        return frequencyKeys;
    }

    private static int[] computeFrequencies(String string, int startIndex, int endIndex) {
        int[] frequencies = new int[26];
        for (int i = startIndex; i < endIndex; i++) {
            frequencies[string.charAt(i) - 'a']++;
        }
        return frequencies;
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