package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/05/21.
 */
public class HayPoints {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int words = FastReader.nextInt();
        int jobDescriptions = FastReader.nextInt();
        Map<String, Integer> wordsPoints = new HashMap<>();

        for (int i = 0; i < words; i++) {
            wordsPoints.put(FastReader.next(), FastReader.nextInt());
        }

        for (int i = 0; i < jobDescriptions; i++) {
            int salary = 0;

            String line = FastReader.getLine();
            while (!line.equals(".")) {
                String[] wordsInLine = line.split(" ");
                for (String word : wordsInLine) {
                    salary += wordsPoints.getOrDefault(word, 0);
                }
                line = FastReader.getLine();
            }
            outputWriter.printLine(salary);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
