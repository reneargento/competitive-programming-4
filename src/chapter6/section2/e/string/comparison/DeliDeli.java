package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/06/2026.
 */
public class DeliDeli {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int irregularWords = FastReader.nextInt();
        int queries = FastReader.nextInt();
        Map<String, String> irregularWordsMap = new HashMap<>();

        for (int i = 0; i < irregularWords; i++) {
            String irregularWord = FastReader.next();
            String plural = FastReader.next();
            irregularWordsMap.put(irregularWord, plural);
        }

        for (int i = 0; i < queries; i++) {
            String query = FastReader.next();
            String plural = computePlural(irregularWordsMap, query);
            outputWriter.printLine(plural);
        }
        outputWriter.flush();
    }

    private static String computePlural(Map<String, String> irregularWordsMap, String query) {
        if (irregularWordsMap.containsKey(query)) {
            return irregularWordsMap.get(query);
        }
        int lastIndex = query.length() - 1;
        if (query.length() >= 2) {
            if (query.charAt(lastIndex) == 'y' && isConsonant(query.charAt(lastIndex - 1))) {
                return query.substring(0, lastIndex) + "ies";
            }

            String wordEnding = query.substring(lastIndex - 1);
            if (wordEnding.equals("ch") || wordEnding.equals("sh")) {
                return query + "es";
            }
        }

        char lastChar = query.charAt(lastIndex);
        if (lastChar == 'o' || lastChar == 's' || lastChar == 'x') {
            return query + "es";
        }
        return query + "s";
    }

    private static boolean isConsonant(char character) {
        return character != 'a' && character != 'e' && character != 'i' && character != 'o' && character != 'u';
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