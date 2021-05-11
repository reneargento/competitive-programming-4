package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class WhatsCryptanalysis {

    private static class LetterFrequency implements Comparable<LetterFrequency> {
        char letter;
        int frequency;

        public LetterFrequency(char letter, int frequency) {
            this.letter = letter;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(LetterFrequency other) {
            if (frequency != other.frequency) {
                return Integer.compare(other.frequency, frequency);
            }
            return Character.compare(letter, other.letter);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int[] frequencies = new int[26];

        int lines = FastReader.nextInt();
        for (int i = 0; i < lines; i++) {
            String line = FastReader.getLine().toUpperCase();
            computeFrequencies(line, frequencies);
        }
        outputResult(frequencies);
    }

    private static void computeFrequencies(String line, int[] frequencies) {
        for (char character : line.toCharArray()) {
            if (Character.isLetter(character)) {
                int index = character - 'A';
                frequencies[index]++;
            }
        }
    }

    private static void outputResult(int[] frequencies) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<LetterFrequency> letterFrequencies = new ArrayList<>();

        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                char letter = (char) ('A' + i);
                letterFrequencies.add(new LetterFrequency(letter, frequencies[i]));
            }
        }

        Collections.sort(letterFrequencies);
        for (LetterFrequency letterFrequency : letterFrequencies) {
            outputWriter.printLine(letterFrequency.letter + " " + letterFrequency.frequency);
        }

        outputWriter.flush();
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
