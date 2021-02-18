package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/02/21.
 */
public class NineKnights {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] board = new char[5][5];

        for (int row = 0; row < board.length; row++) {
            board[row] = scanner.next().toCharArray();
        }

        boolean isValid = isValidConfiguration(board);
        System.out.println(isValid ? "valid" : "invalid");
    }

    private static boolean isValidConfiguration(char[][] board) {
        int[] rowMoves = { -2, -2, -1, -1, 1, 1, 2, 2 };
        int[] columnMoves = { -1, 1, -2, 2, -2, 2, -1, 1 };
        int knights = 0;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {

                if (board[row][column] == 'k') {
                    knights++;

                    for (int i = 0; i < rowMoves.length; i++) {
                        int moveRow = row + rowMoves[i];
                        int moveColumn = column + columnMoves[i];

                        if (isValid(board, moveRow, moveColumn) && board[moveRow][moveColumn] == 'k') {
                            return false;
                        }
                    }
                }
            }
        }
        return knights == 9;
    }

    private static boolean isValid(char[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
    }
}
