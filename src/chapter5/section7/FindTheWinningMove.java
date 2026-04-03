package chapter5.section7;

import java.io.*;

/**
 * Created by Rene Argento on 01/04/26.
 */
public class FindTheWinningMove {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final String NO_FORCED_WIN = "#####";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("$")) {
            char[][] board = new char[4][4];
            for (int row = 0; row < board.length; row++) {
                board[row] = FastReader.getLine().toCharArray();
            }

            String winPosition = computeWinPosition(board);
            outputWriter.printLine(winPosition);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computeWinPosition(char[][] board) {
        Cell winningPosition = moveX(board);
        if (winningPosition == null) {
            return NO_FORCED_WIN;
        }
        return "(" + winningPosition.row + "," + winningPosition.column + ")";
    }

    private static Cell moveX(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == '.') {
                    board[row][column] = 'x';
                    if (wonTheGame(board, 'x') || !moveO(board)) {
                        board[row][column] = '.';
                        return new Cell(row, column);
                    }
                    board[row][column] = '.';
                }
            }
        }
        return null;
    }

    private static boolean moveO(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == '.') {
                    board[row][column] = 'o';
                    if (wonTheGame(board, 'o') || moveX(board) == null) {
                        board[row][column] = '.';
                        return true;
                    }
                    board[row][column] = '.';
                }
            }
        }
        return false;
    }

    private static boolean wonTheGame(char[][] board, char charToCheck) {
        // Rows
        for (int row = 0; row < board.length; row++) {
            boolean allFilled = true;
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] != charToCheck) {
                    allFilled = false;
                    break;
                }
            }
            if (allFilled) {
                return true;
            }
        }

        // Columns
        for (int column = 0; column < board[0].length; column++) {
            boolean allFilled = true;
            for (int row = 0; row < board.length; row++) {
                if (board[row][column] != charToCheck) {
                    allFilled = false;
                    break;
                }
            }
            if (allFilled) {
                return true;
            }
        }

        // Diagonals
        boolean allFilledDiagonal1 = true;
        for (int row = 0; row < board.length; row++) {
            if (board[row][row] != charToCheck) {
                allFilledDiagonal1 = false;
                break;
            }
        }

        boolean allFilledDiagonal2 = true;
        int column = board[0].length - 1;
        for (int row = 0; row < board.length; row++, column--) {
            if (board[row][column] != charToCheck) {
                allFilledDiagonal2 = false;
                break;
            }
        }
        return allFilledDiagonal1 || allFilledDiagonal2;
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
