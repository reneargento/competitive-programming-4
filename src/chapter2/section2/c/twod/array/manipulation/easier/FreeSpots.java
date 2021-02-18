package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 05/02/21.
 */
public class FreeSpots {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        int subBoards = scanner.nextInt();

        while (width != 0 || height != 0 || subBoards != 0) {
            boolean[][] board = new boolean[height][width];

            for (int i = 0; i < subBoards; i++) {
                int column1 = scanner.nextInt() - 1;
                int row1 = scanner.nextInt() - 1;
                int column2 = scanner.nextInt() - 1;
                int row2 = scanner.nextInt() - 1;

                int minRow;
                int maxRow;
                int minColumn;
                int maxColumn;

                if (row1 < row2) {
                    minRow = row1;
                    maxRow = row2;
                } else {
                    minRow = row2;
                    maxRow = row1;
                }

                if (column1 < column2) {
                    minColumn = column1;
                    maxColumn = column2;
                } else {
                    minColumn = column2;
                    maxColumn = column1;
                }

                fillBoard(board, minRow, maxRow, minColumn, maxColumn);
            }
            int positions = countPositionsOutsideSubPortions(board);
            if (positions == 0) {
                System.out.println("There is no empty spots.");
            } else if (positions == 1) {
                System.out.println("There is one empty spot.");
            } else {
                System.out.printf("There are %d empty spots.\n", positions);
            }

            width = scanner.nextInt();
            height = scanner.nextInt();
            subBoards = scanner.nextInt();
        }
    }

    private static void fillBoard(boolean[][] board, int minRow, int maxRow, int minColumn, int maxColumn) {
        for (int row = minRow; row <= maxRow; row++) {
            for (int column = minColumn; column <= maxColumn; column++) {
                board[row][column] = true;
            }
        }
    }

    private static int countPositionsOutsideSubPortions(boolean[][] board) {
        int positions = 0;
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (!board[row][column]) {
                    positions++;
                }
            }
        }
        return positions;
    }
}
