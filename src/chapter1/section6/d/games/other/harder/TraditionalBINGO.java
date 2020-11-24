package chapter1.section6.d.games.other.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 21/10/20.
 */
public class TraditionalBINGO {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int[][] card = new int[5][5];
            boolean[][] marked = new boolean[5][5];

            for (int i = 0; i < card.length; i++) {
                for (int j = 0; j < card[0].length; j++) {
                    if (i == 2 && j == 2) {
                        continue;
                    }
                    card[i][j] = scanner.nextInt();
                }
            }

            marked[2][2] = true;

            int rounds = 0;
            boolean won = false;

            for (int i = 1; i <= 75; i++) {
                int number = scanner.nextInt();

                if (!won) {
                    Cell cell = getCellWithNumber(card, number);
                    if (cell != null) {
                        won = markAndCheckBingo(marked, cell.row, cell.column);
                    }
                    rounds++;
                }
            }
            System.out.printf("BINGO after %d numbers announced\n", rounds);
        }
    }

    private static Cell getCellWithNumber(int[][] card, int number) {
        for (int i = 0; i < card.length; i++) {
            for (int j = 0; j < card[0].length; j++) {
                if (card[i][j] == number) {
                    return new Cell(i, j);
                }
            }
        }
        return null;
    }

    private static boolean isLeftDiagonal(int row, int column) {
        return row == column;
    }

    private static boolean isRightDiagonal(int row, int column) {
        return row + column == 4;
    }

    private static boolean markAndCheckBingo(boolean[][] marked, int row, int column) {
        marked[row][column] = true;

        if (isLeftDiagonal(row, column)) {
            if (marked[0][0]
                    && marked[1][1]
                    && marked[2][2]
                    && marked[3][3]
                    && marked[4][4]) {
                return true;
            }
        }

        if (isRightDiagonal(row, column)) {
            if (marked[0][4]
                    && marked[1][3]
                    && marked[2][2]
                    && marked[3][1]
                    && marked[4][0]) {
                return true;
            }
        }

        boolean completeColumn = true;
        for (int i = 0; i < marked.length; i++) {
            if (!marked[i][column]) {
                completeColumn = false;
                break;
            }
        }

        boolean completeRow = true;
        for (int i = 0; i < marked[0].length; i++) {
            if (!marked[row][i]) {
                completeRow = false;
                break;
            }
        }
        return completeColumn || completeRow;
    }

}
