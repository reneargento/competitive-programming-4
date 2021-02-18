package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 06/02/21.
 */
public class ThisAintYourGrandpasCheckerboard {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dimension = scanner.nextInt();
        char[][] board = new char[dimension][dimension];

        for (int row = 0; row < dimension; row++) {
            board[row] = scanner.next().toCharArray();
        }

        System.out.println(isCorrect(board));
    }

    private static int isCorrect(char[][] board) {
        int rowsCorrect = checkBoard(board, true);
        if (rowsCorrect == 0) {
            return 0;
        }
        return checkBoard(board, false);
    }

    private static int checkBoard(char[][] board, boolean isRow) {
        int length = board.length;

        for (int iterator1 = 0; iterator1 < length; iterator1++) {
            int blackSquares = 0;
            int whiteSquares = 0;

            for (int iterator2 = 0; iterator2 < length; iterator2++) {
                int sameColor = 1;
                for (int i = iterator2 + 1; i <= iterator2 + 2 && i < length; i++) {
                    if ((isRow && board[i][iterator1] == board[iterator2][iterator1]) ||
                            (!isRow && board[iterator1][i] == board[iterator1][iterator2])) {
                        sameColor++;
                    }
                }

                if (sameColor == 3) {
                    return 0;
                }

                char color;
                if (isRow) {
                    color = board[iterator2][iterator1];
                } else {
                    color = board[iterator1][iterator2];
                }

                if (color == 'B') {
                    blackSquares++;
                } else {
                    whiteSquares++;
                }
            }

            if (blackSquares != whiteSquares) {
                return 0;
            }
        }
        return 1;
    }
}
