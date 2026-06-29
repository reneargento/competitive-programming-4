package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/06/2026.
 */
public class AutomaticCorrectionOfMisspellings {

    private static class DictionaryWord {
        String value;
        int index;

        public DictionaryWord(String value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dictionaryWords = FastReader.nextInt();
        Map<String, Integer> dictionary = new HashMap<>();

        for (int d = 0; d < dictionaryWords; d++) {
            String word = FastReader.next();
            dictionary.put(word, d);
        }
        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            String query = FastReader.next();
            String result = applyAutoCorrection(dictionary, query);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String applyAutoCorrection(Map<String, Integer> dictionary, String query) {
        if (dictionary.containsKey(query)) {
            return query + " is correct";
        }

        DictionaryWord bestAutoCorrection = null;

        // Missing letter check
        if (query.length() > 1) {
            String missingFirstLetter = query.substring(1);
            if (dictionary.containsKey(missingFirstLetter)) {
                bestAutoCorrection = updateBestAutoCorrection(bestAutoCorrection,
                        missingFirstLetter, dictionary.get(missingFirstLetter));
            }

            String missingLastLetter = query.substring(0, query.length() - 1);
            if (dictionary.containsKey(missingLastLetter)) {
                bestAutoCorrection = updateBestAutoCorrection(bestAutoCorrection,
                        missingLastLetter, dictionary.get(missingLastLetter));
            }
        }
        for (int i = 1; i < query.length() - 1; i++) {
            String queryMissingLetter = query.substring(0, i) + query.substring(i + 1);
            if (dictionary.containsKey(queryMissingLetter)) {
                bestAutoCorrection = updateBestAutoCorrection(bestAutoCorrection,
                        queryMissingLetter, dictionary.get(queryMissingLetter));
            }
        }

        // Extra letter check
        for (int i = 0; i <= query.length(); i++) {
            for (char letter = 'a'; letter <= 'z'; letter++) {
                String queryExtraLetter = query.substring(0, i) + letter;
                if (i < query.length()) {
                    queryExtraLetter += query.substring(i);
                }
                if (dictionary.containsKey(queryExtraLetter)) {
                    bestAutoCorrection = updateBestAutoCorrection(bestAutoCorrection,
                            queryExtraLetter, dictionary.get(queryExtraLetter));
                }
            }
        }

        // Replacing letter check
        for (int i = 0; i < query.length(); i++) {
            for (char letter = 'a'; letter <= 'z'; letter++) {
                String queryReplaceLetter = query.substring(0, i) + letter;
                if (i < query.length() - 1) {
                    queryReplaceLetter += query.substring(i + 1);
                }
                if (dictionary.containsKey(queryReplaceLetter)) {
                    bestAutoCorrection = updateBestAutoCorrection(bestAutoCorrection,
                            queryReplaceLetter, dictionary.get(queryReplaceLetter));
                }
            }
        }

        // Order check
        for (int i = 0; i < query.length() - 1; i++) {
            char letter1 = query.charAt(i);
            char letter2 = query.charAt(i + 1);
            String queryOrderLetter = query.substring(0, i) + letter2 + letter1;
            if (i < query.length() - 2) {
                queryOrderLetter += query.substring(i + 2);
            }
            if (dictionary.containsKey(queryOrderLetter)) {
                bestAutoCorrection = updateBestAutoCorrection(bestAutoCorrection,
                        queryOrderLetter, dictionary.get(queryOrderLetter));
            }
        }

        if (bestAutoCorrection == null) {
            return query + " is unknown";
        }
        return query + " is a misspelling of " + bestAutoCorrection.value;
    }

    private static DictionaryWord updateBestAutoCorrection(DictionaryWord currentBest, String query, int index) {
        if (currentBest == null || currentBest.index > index) {
            return new DictionaryWord(query, index);
        }
        return currentBest;
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