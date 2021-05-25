package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/05/21.
 */
public class Shiritori {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int words = FastReader.nextInt();

        Set<String> usedWords = new HashSet<>();
        String previousWord = null;
        int player = 0;
        boolean fairGame = true;

        for (int i = 0; i < words; i++) {
            String word = FastReader.next();

            if (i > 0) {
                if (word.charAt(0) != previousWord.charAt(previousWord.length() - 1)
                        || usedWords.contains(word)) {
                    fairGame = false;
                    break;
                }
            }

            usedWords.add(word);
            player = (player + 1) % 2;
            previousWord = word;
        }

        if (fairGame) {
            outputWriter.printLine("Fair Game");
        } else {
            outputWriter.printLine(String.format("Player %d lost", player + 1));
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
