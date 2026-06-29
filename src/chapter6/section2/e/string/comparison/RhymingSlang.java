package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/06/2026.
 */
@SuppressWarnings("unchecked")
public class RhymingSlang {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String commonWord = FastReader.getLine();
        int listsNumber = FastReader.nextInt();

        List<List<String>> relevantWordEndings = new ArrayList<>();
        for (int i = 0; i < listsNumber; i++) {
            List<String> endings = new ArrayList<>();
            boolean isRelevant = false;
            String[] words = FastReader.getLine().split(" ");
            for (String word : words) {
                endings.add(word);
                if (commonWord.endsWith(word)) {
                    isRelevant = true;
                }
            }

            if (isRelevant) {
                relevantWordEndings.add(endings);
            }
        }

        int phrases = FastReader.nextInt();
        for (int p = 0; p < phrases; p++) {
            String[] phrase = FastReader.getLine().split(" ");
            String rhymeCheck = checkRhyme(relevantWordEndings, phrase[phrase.length - 1]);
            outputWriter.printLine(rhymeCheck);
        }
        outputWriter.flush();
    }

    private static String checkRhyme(List<List<String>> relevantWordEndings, String phraseWord) {
        for (List<String> endingLists : relevantWordEndings) {
            for (String ending : endingLists) {
                if (phraseWord.endsWith(ending)) {
                    return "YES";
                }
            }
        }
        return "NO";
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