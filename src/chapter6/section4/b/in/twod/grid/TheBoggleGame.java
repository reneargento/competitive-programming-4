package chapter6.section4.b.in.twod.grid;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/08/2026.
 */
public class TheBoggleGame {

    private static final int[] NEIGHBOR_ROWS = { -1, 1, 0, 0, -1, -1, 1, 1 };
    private static final int[] NEIGHBOR_COLUMNS = { 0, 0, -1, 1, -1, 1, -1, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int boardPair = 1;
        while (!line.equals("#")) {
            char[][] board1 = new char[4][4];
            char[][] board2 = new char[4][4];
            int row = 0;

            while (!line.isEmpty()) {
                line = line.replace(" ", "");

                for (int i = 0; i < line.length(); i++) {
                    if (i < 4) {
                        board1[row][i] = line.charAt(i);
                    } else {
                        board2[row][i - 4] = line.charAt(i);
                    }
                }
                row++;
                line = FastReader.getLine();
            }

            if (boardPair > 1) {
                outputWriter.printLine();
            }
            List<String> commonWords = computeCommonWords(board1, board2);
            if (commonWords.isEmpty()) {
                outputWriter.printLine("There are no common words for this pair of boggle boards.");
            } else {
                for (String commonWord : commonWords) {
                    outputWriter.printLine(commonWord);
                }
            }
            boardPair++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> computeCommonWords(char[][] board1, char[][] board2) {
        Set<String> commonWords = new HashSet<>();

        for (int row = 0; row < board1.length; row++) {
            for (int column = 0; column < board1[row].length; column++) {
                char[] currentWord = new char[4];
                searchWords(board1, board2, commonWords, 0, currentWord, 0, row, column);
            }
        }
        List<String> commonWordsList = new ArrayList<>(commonWords);
        Collections.sort(commonWordsList);
        return commonWordsList;
    }

    private static boolean searchWords(char[][] board1, char[][] board2, Set<String> commonWords, int visited,
                                       char[] currentWord, int currentWordIndex, int row, int column) {
        if (currentWordIndex == 4) {
            String word = new String(currentWord);
            if (isValidWord(word)
                    && hasWord(board2, word)) {
                commonWords.add(word);
            }
            return true;
        }
        int cellIndex = row * board1.length + column;
        if (!isValid(board1, row, column)
                || (visited & (1 << cellIndex)) != 0) {
            return false;
        }

        currentWord[currentWordIndex] = board1[row][column];
        int nextVisited = visited | (1 << cellIndex);
        for (int i = 0; i != NEIGHBOR_ROWS.length; i++) {
            int nextRow = row + NEIGHBOR_ROWS[i];
            int nextColumn = column + NEIGHBOR_COLUMNS[i];
            boolean result = searchWords(board1, board2, commonWords, nextVisited, currentWord, currentWordIndex + 1,
                    nextRow, nextColumn);
            if (result) {
                break;
            }
        }
        return false;
    }

    private static boolean hasWord(char[][] board, String word) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == word.charAt(0)
                        && hasWord(board, word, 0, 0, row, column)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasWord(char[][] board, String word, int visited, int wordIndex, int row,
                                   int column) {
        if (wordIndex == word.length()) {
            return true;
        }
        int cellIndex = row * board.length + column;
        if (!isValid(board, row, column)
                || (visited & (1 << cellIndex)) != 0
                || board[row][column] != word.charAt(wordIndex)) {
            return false;
        }

        for (int i = 0; i < NEIGHBOR_ROWS.length; i++) {
            int nextRow = row + NEIGHBOR_ROWS[i];
            int nextColumn = column + NEIGHBOR_COLUMNS[i];
            int nextVisited = (visited | (1 << cellIndex));

            boolean result = hasWord(board, word, nextVisited, wordIndex + 1, nextRow, nextColumn);
            if (result) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidWord(String word) {
        int vowels = 0;
        for (char character : word.toCharArray()) {
            if (character == 'A'
                    || character == 'E'
                    || character == 'I'
                    || character == 'O'
                    || character == 'U'
                    || character == 'Y') {
                vowels++;
            }
        }
        return vowels == 2;
    }

    private static boolean isValid(char[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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