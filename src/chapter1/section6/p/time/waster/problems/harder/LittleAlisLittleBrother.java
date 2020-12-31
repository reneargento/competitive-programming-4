package chapter1.section6.p.time.waster.problems.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/12/20.
 */
public class LittleAlisLittleBrother {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            int pieces = FastReader.nextInt();
            char[][] board = new char[rows][columns];

            for (int row = 0; row < rows; row++) {
                board[row] = FastReader.getLine().toCharArray();
            }

            for (int p = 0; p < pieces; p++) {
                int pieceRows = FastReader.nextInt();
                int pieceColumns = FastReader.nextInt();
                char[][] piece = new char[pieceRows][pieceColumns];

                for (int pieceRow = 0; pieceRow < pieceRows; pieceRow++) {
                    piece[pieceRow] = FastReader.getLine().toCharArray();
                }
                System.out.println(doesPieceFit(board, piece) ? "Yes" : "No");
            }
            System.out.println();
        }
    }

    private static boolean doesPieceFit(char[][] board, char[][] piece) {
        char[][] minimumBoundingPiece = getMinimumBoundingBox(piece);

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (doesPieceFit(board, minimumBoundingPiece, row, column)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean doesPieceFit(char[][] board, char[][] piece, int boardRow, int boardColumn) {
        for (int row = 0; row < piece.length; row++) {
            for (int column = 0; column < piece[0].length; column++) {
                if (piece[row][column] == '*') {
                    int nextBoardRow = boardRow + row;
                    int nextBoardColumn = boardColumn + column;

                    if (!isValid(board, nextBoardRow, nextBoardColumn)
                            || board[nextBoardRow][nextBoardColumn] != '*') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isValid(char[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
    }

    private static char[][] getMinimumBoundingBox(char[][] piece) {
        int startRow = 0;
        int endRow = 0;
        int startColumn = 0;
        int endColumn = 0;
        boolean found = false;

        for (int row = 0; row < piece.length; row++) {
            for (int column = 0; column < piece[0].length; column++) {
                if (piece[row][column] == '*') {
                    startRow = row;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        found = false;

        for (int row = piece.length - 1; row >= 0; row--) {
            for (int column = 0; column < piece[0].length; column++) {
                if (piece[row][column] == '*') {
                    endRow = row;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        found = false;

        for (int column = 0; column < piece[0].length; column++) {
            for (int row = 0; row < piece.length; row++) {
                if (piece[row][column] == '*') {
                    startColumn = column;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        found = false;

        for (int column = piece[0].length - 1; column >= 0; column--) {
            for (int row = 0; row < piece.length; row++) {
                if (piece[row][column] == '*') {
                    endColumn = column;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        int rowsNumber = endRow - startRow + 1;
        int columnsNumber = endColumn - startColumn + 1;
        char[][] minimumBoundingPiece = new char[rowsNumber][columnsNumber];
        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                minimumBoundingPiece[row - startRow][column - startColumn] = piece[row][column];
            }
        }
        return minimumBoundingPiece;
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
