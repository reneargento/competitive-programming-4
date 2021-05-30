package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by Rene Argento on 29/05/21.
 */
public class EntropyTextAnalyzer {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("****END_OF_INPUT****")) {
            Map<String, Integer> wordFrequencies = new HashMap<>();
            int totalWords = 0;

            while (!line.equals("****END_OF_TEXT****")) {
                line = line.trim();

                if (!line.equals("")) {
                    line = line.replaceAll("[,\\.:;!\\?\"\\(\\)]", "");
                    line = line.toLowerCase();

                    String[] words = line.split(" ");
                    totalWords += words.length;
                    for (String word : words) {
                        int frequency = wordFrequencies.getOrDefault(word, 0);
                        wordFrequencies.put(word, frequency + 1);
                    }
                }
                line = FastReader.getLine();
            }
            computeEntropy(wordFrequencies, totalWords, outputWriter);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void computeEntropy(Map<String, Integer> wordFrequencies, int totalWords,
                                       OutputWriter outputWriter) {
        double textEntropy = 0;
        double oneDividedByLambda = 1.0 / totalWords;
        double lambdaLog = Math.log(totalWords) / Math.log(10);

        for (int frequency : wordFrequencies.values()) {
            textEntropy += frequency * (lambdaLog - (Math.log(frequency) / Math.log(10)));
        }

        textEntropy *= oneDividedByLambda;
        long relativeEntropy = Math.round(textEntropy / lambdaLog * 100);
        textEntropy = roundValuePrecisionDigits(textEntropy, 1);

        outputWriter.printLine(totalWords + " " + textEntropy + " " + relativeEntropy);
    }

    private static double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.ISO_8859_1), 65536);
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
