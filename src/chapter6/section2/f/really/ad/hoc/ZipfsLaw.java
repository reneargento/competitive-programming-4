package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/07/2026.
 */
public class ZipfsLaw {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int caseId = 1;
        while (line != null) {
            int targetFrequency = Integer.parseInt(line);
            Map<String, Integer> wordFrequencies = new HashMap<>();
            line = FastReader.getLine();

            while (!line.equals("EndOfText")) {
                line = line.toLowerCase();
                line = line.replaceAll("[^a-z]", " ");
                String[] words = line.split("\\s+");
                for (String word : words) {
                    int frequency = 0;
                    if (wordFrequencies.containsKey(word)) {
                        frequency = wordFrequencies.get(word);
                    }
                    wordFrequencies.put(word, frequency + 1);
                }
                line = FastReader.getLine();
            }

            if (caseId > 1) {
                outputWriter.printLine();
            }
            printTargetFrequencyWords(wordFrequencies, targetFrequency, outputWriter);
            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printTargetFrequencyWords(Map<String, Integer> wordFrequencies, int targetFrequency,
                                                  OutputWriter outputWriter) {
        List<String> targetWords = new ArrayList<>();
        for (String word : wordFrequencies.keySet()) {
            if (wordFrequencies.get(word) == targetFrequency) {
                targetWords.add(word);
            }
        }
        if (targetWords.isEmpty()) {
            outputWriter.printLine("There is no such word.");
            return;
        }

        Collections.sort(targetWords);
        for (String word : targetWords) {
            outputWriter.printLine(word);
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

        public void flush() {
            writer.flush();
        }
    }
}