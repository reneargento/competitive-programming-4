package chapter6.section4.b.in.twod.grid;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/08/2026.
 */
public class Boggle {

    private static class Result {
        int score;
        String longestWord;
        int wordsFound;

        public Result(int score, String longestWord, int wordsFound) {
            this.score = score;
            this.longestWord = longestWord;
            this.wordsFound = wordsFound;
        }
    }

    private static final int[] NEIGHBOR_ROWS = { -1, 1, 0, 0, -1, -1, 1, 1 };
    private static final int[] NEIGHBOR_COLUMNS = { 0, 0, -1, 1, -1, 1, -1, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] dictionary = new String[FastReader.nextInt()];
        for (int i = 0; i < dictionary.length; i++) {
            dictionary[i] = FastReader.getLine();
        }
        FastReader.getLine();

        int boards = FastReader.nextInt();
        for (int b = 0; b < boards; b++) {
            char[][] board = new char[4][4];
            for (int row = 0; row < board.length; row++) {
                board[row] = FastReader.getLine().toCharArray();
            }
            FastReader.getLine();

            Result result = playBoggle(board, dictionary);
            outputWriter.printLine(String.format("%d %s %d", result.score, result.longestWord, result.wordsFound));
        }
        outputWriter.flush();
    }

    private static Result playBoggle(char[][] board, String[] dictionary) {
        int score = 0;
        String longestWord = "";
        int wordsFound = 0;

        for (String word : dictionary) {
            for (int row = 0; row < board.length; row++) {
                boolean found = false;

                for (int column = 0; column < board[row].length; column++) {
                    if (board[row][column] != word.charAt(0)) {
                        continue;
                    }

                    int visited = 0;
                    found = playBoggle(board, word, visited, 0, row, column);
                    if (found) {
                        score += getScore(word.length());
                        wordsFound++;
                        if (word.length() > longestWord.length()
                                || (word.length() == longestWord.length() && word.compareTo(longestWord) < 0)) {
                            longestWord = word;
                        }
                        break;
                    }
                }

                if (found) {
                    break;
                }
            }
        }
        return new Result(score, longestWord, wordsFound);
    }

    private static boolean playBoggle(char[][] board, String word, int visited, int wordIndex, int row, int column) {
        if (wordIndex == word.length()) {
            return true;
        }
        int cellIndex = row * board.length + column;
        if (row < 0
                || column < 0
                || row >= board.length
                || column >= board[row].length
                || (visited & (1 << cellIndex)) > 0
                || board[row][column] != word.charAt(wordIndex)) {
            return false;
        }

        visited |= (1 << cellIndex);
        for (int i = 0; i < NEIGHBOR_ROWS.length; i++) {
            int neighborRow = row + NEIGHBOR_ROWS[i];
            int neighborColumn = column + NEIGHBOR_COLUMNS[i];

            boolean found = playBoggle(board, word, visited, wordIndex + 1, neighborRow, neighborColumn);
            if (found) {
                return true;
            }
        }
        return false;
    }

    private static int getScore(int wordLength) {
        if (wordLength < 3) {
            return 0;
        }
        if (wordLength == 3 || wordLength == 4) {
            return 1;
        }
        if (wordLength == 5) {
            return 2;
        }
        if (wordLength == 6) {
            return 3;
        }
        if (wordLength == 7) {
            return 5;
        }
        return 11;
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