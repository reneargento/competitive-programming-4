package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/02/22.
 */
public class CreatingPalindrome {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] sequence = new int[FastReader.nextInt()];
            int maximumDifficulty = FastReader.nextInt();
            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = FastReader.nextInt();
            }

            outputWriter.print(String.format("Case %d: ", t));
            if (isPalindrome(sequence)) {
                outputWriter.printLine("Too easy");
            } else {
                int difficulty = evaluateSequence(sequence, maximumDifficulty, 0, 0, 0);
                if (difficulty == Integer.MAX_VALUE) {
                    outputWriter.printLine("Too difficult");
                } else {
                    outputWriter.printLine(difficulty);
                }
            }
        }
        outputWriter.flush();
    }

    private static boolean isPalindrome(int[] sequence) {
        for (int i = 0; i < sequence.length / 2; i++) {
            if (sequence[i] != sequence[sequence.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

    private static int evaluateSequence(int[] sequence, int maximumDifficulty, int index, int insertionsAtStart,
                                        int insertionsAtEnd) {
        int totalInsertions = insertionsAtStart + insertionsAtEnd;
        int endIndex = sequence.length - 1 - index + insertionsAtEnd - insertionsAtStart;
        if (index > endIndex) {
            return totalInsertions;
        }
        if (totalInsertions > maximumDifficulty) {
            return Integer.MAX_VALUE;
        }

        int difficultyInsertingAtTheBeginning = Integer.MAX_VALUE;
        if (sequence[index] != sequence[endIndex]) {
            insertionsAtStart++;
            difficultyInsertingAtTheBeginning = evaluateSequence(sequence, maximumDifficulty, index,
                    insertionsAtStart, insertionsAtEnd);

            insertionsAtStart--;
            insertionsAtEnd++;
        }

        int difficulty = evaluateSequence(sequence, maximumDifficulty, index + 1, insertionsAtStart,
                insertionsAtEnd);
        return Math.min(difficultyInsertingAtTheBeginning, difficulty);
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
