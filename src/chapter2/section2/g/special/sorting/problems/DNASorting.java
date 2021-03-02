package chapter2.section2.g.special.sorting.problems;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/02/21.
 */
public class DNASorting {

    private static class DNAString implements Comparable<DNAString> {
        int id;
        String dnaString;
        int inversions;

        public DNAString(int id, String dnaString, int inversions) {
            this.id = id;
            this.dnaString = dnaString;
            this.inversions = inversions;
        }

        @Override
        public int compareTo(DNAString other) {
            if (inversions != other.inversions) {
                return Integer.compare(inversions, other.inversions);
            }
            return Integer.compare(id, other.id);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            int stringsLength = FastReader.nextInt();
            int numberOfStrings = FastReader.nextInt();

            DNAString[] dnaStrings = new DNAString[numberOfStrings];
            for (int i = 0; i < dnaStrings.length; i++) {
                String dnaString = FastReader.next();
                int inversions = countInversions(dnaString);
                dnaStrings[i] = new DNAString(i, dnaString, inversions);
            }

            Arrays.sort(dnaStrings);

            for (DNAString dnaString : dnaStrings) {
                outputWriter.printLine(dnaString.dnaString);
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static int countInversions(String string) {
        int[] frequencies = new int[26];
        int totalInversions = 0;

        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            totalInversions += computeLocalInversions(character, frequencies);
            frequencies[character - 'A']++;
        }
        return totalInversions;
    }

    private static int computeLocalInversions(char character, int[] frequencies) {
        int inversions = 0;
        int cIndex = 'C' - 'A';
        int gIndex = 'G' - 'A';
        int tIndex = 'T' - 'A';

        switch (character) {
            case 'A': inversions = frequencies[cIndex] + frequencies[gIndex] + frequencies[tIndex]; break;
            case 'C': inversions = frequencies[gIndex] + frequencies[tIndex]; break;
            case 'G': inversions = frequencies[tIndex]; break;
        }
        return inversions;
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
