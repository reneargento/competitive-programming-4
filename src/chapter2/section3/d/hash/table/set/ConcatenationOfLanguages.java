package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/05/21.
 */
public class ConcatenationOfLanguages {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int stringsOnFirstLanguage = FastReader.nextInt();
            int stringsOnSecondLanguage = FastReader.nextInt();

            String[] firstLanguage = readStrings(stringsOnFirstLanguage);
            String[] secondLanguage = readStrings(stringsOnSecondLanguage);
            long concatenatedWordsCount = countConcatenations(firstLanguage, secondLanguage);
            outputWriter.printLine(String.format("Case %d: %d", t, concatenatedWordsCount));
        }
        outputWriter.flush();
    }

    private static String[] readStrings(int size) throws IOException {
        String[] language = new String[size];

        for (int i = 0; i < language.length; i++) {
            language[i] = FastReader.getLine();
        }
        return language;
    }

    private static long countConcatenations(String[] firstLanguage, String[] secondLanguage) {
        Set<String> concatenatedWords = new HashSet<>();

        for (String string1 : firstLanguage) {
            for (String string2 : secondLanguage) {
                String concatenation = string1 + string2;
                concatenatedWords.add(concatenation);
            }
        }
        return concatenatedWords.size();
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
