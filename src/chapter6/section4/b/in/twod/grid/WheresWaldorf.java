package chapter6.section4.b.in.twod.grid;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/08/2026.
 */
public class WheresWaldorf {

    private static class Location {
        int row;
        int column;

        public Location(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            char[][] grid = new char[FastReader.nextInt()][FastReader.nextInt()];
            for (int row = 0; row < grid.length; row++) {
                grid[row] = FastReader.getLine().toUpperCase().toCharArray();
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            int words = FastReader.nextInt();
            for (int w = 0; w < words; w++) {
                String word = FastReader.getLine().toUpperCase();
                Location location = searchWord(grid, word);
                outputWriter.printLine(location.row + " " + location.column);
            }
        }
        outputWriter.flush();
    }

    private static Location searchWord(char[][] grid, String word) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                if (searchAllDirections(grid, word, row, column)) {
                    return new Location(row + 1, column + 1);
                }
            }
        }
        return null;
    }

    private static boolean searchAllDirections(char[][] grid, String word, int row, int column) {
        boolean found = false;

        // Right
        if (column + word.length() <= grid[row].length) {
            found |= matchesHorizontal(grid, word, row, column,column + word.length(), 1);
        }
        // Left
        if (column >= word.length()) {
            found |= matchesHorizontal(grid, word, row, column,column - word.length(), -1);
        }
        // Down
        if (row + word.length() <= grid.length) {
            found |= matchesVertical(grid, word, row, row + word.length(), column, 1);
        }
        // Up
        if (row >= word.length()) {
            found |= matchesVertical(grid, word, row, row - word.length(), column, -1);
        }
        // Diagonal left top-down
        if (row + word.length() <= grid.length
                && column + word.length() <= grid[row].length) {
            found |= matchesDiagonal(grid, word, row, column, row + word.length(),
                    column + word.length(), 1, 1);
        }
        // Diagonal left bottom-up
        if (row >= word.length()
                && column >= word.length()) {
            found |= matchesDiagonal(grid, word, row, column, row - word.length(),
                    column - word.length(), -1, -1);
        }
        // Diagonal right top-down
        if (row + word.length() <= grid.length
                && column >= word.length()) {
            found |= matchesDiagonal(grid, word, row, column, row + word.length(),
                    column - word.length(), 1, -1);
        }
        // Diagonal right bottom-up
        if (row >= word.length()
                && column + word.length() <= grid[row].length) {
            found |= matchesDiagonal(grid, word, row, column, row - word.length(),
                    column + word.length(), -1, 1);
        }
        return found;
    }

    private static boolean matchesHorizontal(char[][] grid, String word, int row, int startColumn, int endColumn,
                                             int increment) {
        int wordIndex = 0;
        for (int column = startColumn; column != endColumn; column += increment) {
            if (grid[row][column] != word.charAt(wordIndex)) {
                return false;
            }
            wordIndex++;
        }
        return true;
    }

    private static boolean matchesVertical(char[][] grid, String word, int startRow, int endRow, int column,
                                           int increment) {
        int wordIndex = 0;
        for (int row = startRow; row != endRow; row += increment) {
            if (grid[row][column] != word.charAt(wordIndex)) {
                return false;
            }
            wordIndex++;
        }
        return true;
    }

    private static boolean matchesDiagonal(char[][] grid, String word, int startRow, int startColumn, int endRow,
                                           int endColumn, int rowIncrement, int columnIncrement) {
        int wordIndex = 0;
        for (int row = startRow, column = startColumn; row != endRow && column != endColumn; row += rowIncrement,
                column += columnIncrement) {
            if (grid[row][column] != word.charAt(wordIndex)) {
                return false;
            }
            wordIndex++;
        }
        return true;
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