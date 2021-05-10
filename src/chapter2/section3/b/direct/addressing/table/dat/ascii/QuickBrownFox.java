package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/05/21.
 */
public class QuickBrownFox {

    private static class PangramAnalysis {
        boolean isPangram;
        String missingLetters;

        public PangramAnalysis(boolean isPangram, String missingLetters) {
            this.isPangram = isPangram;
            this.missingLetters = missingLetters;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String phrase = FastReader.getLine().toLowerCase();
            PangramAnalysis result = checkPhrase(phrase);
            if (result.isPangram) {
                outputWriter.printLine("pangram");
            } else {
                outputWriter.printLine("missing " + result.missingLetters);
            }
        }
        outputWriter.flush();
    }

    private static PangramAnalysis checkPhrase(String phrase) {
        boolean[] lettersExistence = new boolean[26];
        int lettersFound = 0;

        for (char character : phrase.toCharArray()) {
            if (Character.isLetter(character)) {
                int index = character - 'a';
                if (!lettersExistence[index]) {
                    lettersFound++;
                }
                lettersExistence[index] = true;
            }
        }

        if (lettersFound == 26) {
            return new PangramAnalysis(true, null);
        }

        StringBuilder missingLetters = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (!lettersExistence[i]) {
                char character = (char) (i + 'a');
                missingLetters.append(character);
            }
        }
        return new PangramAnalysis(false, missingLetters.toString());
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
