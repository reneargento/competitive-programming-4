package chapter6.section4.b.in.twod.grid;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/08/2026.
 */
public class HiddenWords {

    private static final int[] NEIGHBOR_ROWS = { -1, 1, 0, 0 };
    private static final int[] NEIGHBOR_COLUMNS = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char[][] grid = new char[FastReader.nextInt()][FastReader.nextInt()];
        for (int row = 0; row < grid.length; row++) {
            grid[row] = FastReader.getLine().toCharArray();
        }
        int[] gridFrequencies = computeGridFrequencies(grid);
        Map<String, Boolean> wordsProcessed = new HashMap<>();

        int wordsFound = 0;
        int words = FastReader.nextInt();
        for (int w = 0; w < words; w++) {
            String word = FastReader.next();
            if (wordsProcessed.containsKey(word)) {
                if (wordsProcessed.get(word)) {
                    wordsFound++;
                }
            } else {
                boolean result = searchWord(grid, word, gridFrequencies);
                wordsProcessed.put(word, result);
                if (result) {
                    wordsFound++;
                }
            }
        }
        outputWriter.printLine(wordsFound);
        outputWriter.flush();
    }

    private static boolean searchWord(char[][] grid, String word, int[] gridFrequencies) {
        int[][] visited = new int[grid.length][grid[0].length];
        int visitedId = 1;

        int firstLetterId = word.charAt(0) - 'A';
        int lastLetterId = word.charAt(word.length() - 1) - 'A';
        if (gridFrequencies[firstLetterId] > gridFrequencies[lastLetterId]) {
            word = new StringBuilder(word).reverse().toString();
        }
        if (!enoughCharactersExist(word, gridFrequencies)) {
            return false;
        }

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                if (grid[row][column] != word.charAt(0)) {
                    continue;
                }

                if (searchWord(grid, word, visited, visitedId,0, row, column)) {
                    return true;
                }
                visitedId++;
            }
        }
        return false;
    }

    private static boolean searchWord(char[][] grid, String word, int[][] visited, int visitedId, int wordIndex,
                                      int row, int column) {
        if (wordIndex == word.length()) {
            return true;
        }
        if (row < 0
                || column < 0
                || row >= grid.length
                || column >= grid[row].length
                || visited[row][column] == visitedId
                || grid[row][column] != word.charAt(wordIndex)) {
            return false;
        }

        visited[row][column] = visitedId;
        for (int i = 0; i < NEIGHBOR_ROWS.length; i++) {
            int nextRow = row + NEIGHBOR_ROWS[i];
            int nextColumn = column + NEIGHBOR_COLUMNS[i];

            boolean result = searchWord(grid, word, visited, visitedId, wordIndex + 1,
                    nextRow, nextColumn);
            if (result) {
                return true;
            }
        }
        visited[row][column] = 0;
        return false;
    }

    private static int[] computeGridFrequencies(char[][] grid) {
        int[] gridFrequencies = new int[26];
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                int charId = grid[row][column] - 'A';
                gridFrequencies[charId]++;
            }
        }
        return gridFrequencies;
    }

    private static boolean enoughCharactersExist(String word, int[] gridFrequencies) {
        int[] wordFrequencies = new int[26];
        for (int i = 0; i < word.length(); i++) {
            int charId = word.charAt(i) - 'A';
            wordFrequencies[charId]++;
        }

        for (int i = 0; i < word.length(); i++) {
            int charId = word.charAt(i) - 'A';
            if (wordFrequencies[charId] > gridFrequencies[charId]) {
                return false;
            }
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