package chapter1.section6.c.games.other.easier;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/10/20.
 */
public class HangmanJudge {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int round = FastReader.nextInt();

        while (round != -1) {
            String answer = FastReader.next();

            Set<Character> letters = new HashSet<>();
            for (int i = 0; i < answer.length(); i++) {
                letters.add(answer.charAt(i));
            }

            String attempt = FastReader.next();
            int wrongGuesses = 0;

            for (int i = 0; i < attempt.length(); i++) {
                char letter = attempt.charAt(i);

                if (!letters.contains(letter)) {
                    wrongGuesses++;

                    if (wrongGuesses == 7) {
                        break;
                    }
                }
                letters.remove(letter);
            }

            outputWriter.printLine("Round " + round);
            if (letters.isEmpty()) {
                outputWriter.printLine("You win.");
            } else if (wrongGuesses == 7) {
                outputWriter.printLine("You lose.");
            } else {
                outputWriter.printLine("You chickened out.");
            }
            round = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static class FastReader {
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
