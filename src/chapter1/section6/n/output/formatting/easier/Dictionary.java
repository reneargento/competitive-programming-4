package chapter1.section6.n.output.formatting.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/12/20.
 */
public class Dictionary {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            String word = FastReader.getLine();
            String previousWord = null;
            StringBuilder spaces = new StringBuilder();

            while (word != null && !word.equals("")) {
                if (previousWord == null) {
                    outputWriter.printLine(word);
                } else {
                    int equalLeadingChars = equalLeadingChars(word, previousWord);
                    if (equalLeadingChars < spaces.length()) {
                        while (spaces.length() > equalLeadingChars) {
                            spaces.deleteCharAt(spaces.length() - 1);
                        }
                    } else if (equalLeadingChars > spaces.length()) {
                        spaces.append(" ");
                    }

                    outputWriter.printLine(spaces + word);
                }
                previousWord = word;
                word = FastReader.getLine();
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static int equalLeadingChars(String word1, String word2) {
        int equalChars = 0;
        int minLength = Math.min(word1.length(), word2.length());

        for (int i = 0; i < minLength; i++) {
            if (word1.charAt(i) == word2.charAt(i)) {
                equalChars++;
            } else {
                break;
            }
        }
        return equalChars;
    }

    public static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        // Used to check EOF
        // If getLine() == null, it is a EOF
        // Otherwise, it returns the next line
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
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
