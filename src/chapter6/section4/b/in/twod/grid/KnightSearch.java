package chapter6.section4.b.in.twod.grid;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/08/2026.
 */
public class KnightSearch {

    private static final int[] NEIGHBOR_ROWS = { -1, -1, -2, -2, 1, 1, 2, 2 };
    private static final int[] NEIGHBOR_COLUMNS = { -2, 2, -1, 1, -2, 2, -1, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int boardSize = FastReader.nextInt();
        String string = FastReader.next();

        String result = canFindString(boardSize, string);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String canFindString(int boardSize, String string) {
        char[][] board = buildBoard(boardSize, string);
        String targetString = "ICPCASIASG";

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (canFindString(board, targetString, 0, row, column)) {
                    return "YES";
                }
            }
        }
        return "NO";
    }

    private static boolean canFindString(char[][] board, String targetString, int stringIndex, int row, int column) {
        if (stringIndex == targetString.length()) {
            return true;
        }
        if (row < 0
                || column < 0
                || row >= board.length
                || column >= board[row].length
                || board[row][column] != targetString.charAt(stringIndex)) {
            return false;
        }

        for (int i = 0; i < NEIGHBOR_ROWS.length; i++) {
            int nextRow = row + NEIGHBOR_ROWS[i];
            int nextColumn = column + NEIGHBOR_COLUMNS[i];
            boolean result = canFindString(board, targetString, stringIndex + 1, nextRow, nextColumn);
            if (result) {
                return true;
            }
        }
        return false;
    }

    private static char[][] buildBoard(int boardSize, String string) {
        char[][] board = new char[boardSize][boardSize];
        int rowIndex = 0;

        for (int i = 0; i < string.length(); i += boardSize) {
            String row = string.substring(i, i + boardSize);
            board[rowIndex] = row.toCharArray();
            rowIndex++;
        }
        return board;
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

        public void flush() {
            writer.flush();
        }
    }
}