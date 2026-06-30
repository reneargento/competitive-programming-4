package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/06/2026.
 */
public class SmartPhone {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String target = FastReader.getLine();
            String current = FastReader.getLine();
            String[] suggestions = new String[3];
            for (int i = 0; i < suggestions.length; i++) {
                suggestions[i] = FastReader.getLine();
            }

            int minimumKeys = computeMinimumKeys(target, current, suggestions);
            outputWriter.printLine(minimumKeys);
        }
        outputWriter.flush();
    }

    private static int computeMinimumKeys(String target, String current, String[] suggestions) {
        int lastEqualIndexCurrentTarget = getLastEqualIndex(current, target);
        int keysToDelete = (current.length() - 1) - lastEqualIndexCurrentTarget;
        int newLength = current.length() - keysToDelete;
        int bestMinimum = keysToDelete + (target.length() - newLength);

        for (String suggestion : suggestions) {
            int lastEqualIndexSuggestionTarget = getLastEqualIndex(suggestion, target);
            keysToDelete = (suggestion.length() - 1) - lastEqualIndexSuggestionTarget;
            newLength = suggestion.length() - keysToDelete;
            int minimumWithSuggestion = 1 + keysToDelete + (target.length() - newLength);
            bestMinimum = Math.min(bestMinimum, minimumWithSuggestion);
        }
        return bestMinimum;
    }

    private static int getLastEqualIndex(String word1, String word2) {
        int minLength = Math.min(word1.length(), word2.length());

        for (int i = 0; i < minLength; i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                return i - 1;
            }
        }
        return minLength - 1;
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