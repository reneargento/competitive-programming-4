package chapter6.section4.b.in.twod.grid;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/08/2026.
 */
public class PlayingBoggle {

    private static final int[] NEIGHBOR_ROWS = { -1, 1, 0, 0, -1, -1, 1, 1 };
    private static final int[] NEIGHBOR_COLUMNS = { 0, 0, -1, 1, -1, 1, -1, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            FastReader.getLine();
            char[][] grid = new char[4][4];
            for (int row = 0; row < grid.length; row++) {
                grid[row] = FastReader.getLine().toCharArray();
            }

            int totalScore = 0;
            int dictionaryLength = FastReader.nextInt();
            for (int i = 0; i < dictionaryLength; i++) {
                String word = FastReader.getLine();
                if (searchWord(grid, word)) {
                    totalScore += computeScore(word.length());
                }
            }
            outputWriter.printLine(String.format("Score for Boggle game #%d: %d", t, totalScore));
        }
        outputWriter.flush();
    }

    private static boolean searchWord(char[][] grid, String word) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                boolean[][] visited = new boolean[grid.length][grid[row].length];
                boolean result = searchWord(grid, word, visited, 0, row, column);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean searchWord(char[][] grid, String word, boolean[][] visited, int wordIndex, int row,
                                      int column) {
        if (wordIndex == word.length()) {
            return true;
        }
        if (!isValid(grid, row, column)
                || visited[row][column]
                || grid[row][column] != word.charAt(wordIndex)) {
            return false;
        }

        visited[row][column] = true;
        for (int i = 0; i < NEIGHBOR_ROWS.length; i++) {
            int neighborRow = row + NEIGHBOR_ROWS[i];
            int neighborColumn = column + NEIGHBOR_COLUMNS[i];

            boolean result = searchWord(grid, word, visited, wordIndex + 1, neighborRow, neighborColumn);
            if (result) {
                return true;
            }
        }
        visited[row][column] = false;
        return false;
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    private static int computeScore(int wordLength) {
        if (wordLength < 3) {
            return 0;
        }
        if (wordLength == 3 ||  wordLength == 4) {
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