package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/01/26.
 */
public class Incognito {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int attributes = FastReader.nextInt();
            Map<String, Integer> categoryFrequencies = new HashMap<>();

            for (int a = 0; a < attributes; a++) {
                FastReader.next();
                String attribute = FastReader.next();
                if (!categoryFrequencies.containsKey(attribute)) {
                    categoryFrequencies.put(attribute, 1);
                }
                int frequency = categoryFrequencies.get(attribute) + 1;
                categoryFrequencies.put(attribute, frequency);
            }

            long disguises = countDisguises(categoryFrequencies);
            outputWriter.printLine(disguises);
        }
        outputWriter.flush();
    }

    private static long countDisguises(Map<String, Integer> categoryFrequencies) {
        long disguises = 1;
        for (String attribute : categoryFrequencies.keySet()) {
            disguises *= categoryFrequencies.get(attribute);
        }
        return disguises - 1;
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
