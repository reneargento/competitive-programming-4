package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by Rene Argento on 09/06/21.
 */
public class ListOfConquests {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int lines = FastReader.nextInt();
        Map<String, Integer> conquestsPerCountry = new TreeMap<>();

        for (int i = 0; i < lines; i++) {
            String[] countryAndName = FastReader.getLine().split(" ");
            String country = countryAndName[0];
            int conquests = conquestsPerCountry.getOrDefault(country, 0);
            conquestsPerCountry.put(country, conquests + 1);
        }

        for (Map.Entry<String, Integer> conquest : conquestsPerCountry.entrySet()) {
            outputWriter.printLine(conquest.getKey() + " " + conquest.getValue());
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
