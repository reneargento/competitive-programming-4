package chapter1.section6.b.game.chess;

import java.util.Scanner;

/**
 * Created by Rene Argento on 14/10/20.
 */
public class ChessboardInFEN {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String chessboardFEN = scanner.next();
            String[][] chessboard = generateChessboard(chessboardFEN);
            boolean[][] attackArea = new boolean[8][8];
            fillAttackArea(attackArea, chessboard);
            int unoccupiedNotAttackedCells = countUnoccupiedNotAttackedCells(attackArea, chessboard);
            System.out.println(unoccupiedNotAttackedCells);
        }
    }

    private static String[][] generateChessboard(String chessboardFEN) {
        String[][] chessboard = new String[8][8];
        int row = 0;
        int column = 0;

        for (int i = 0; i < chessboardFEN.length(); i++) {
            char cell = chessboardFEN.charAt(i);

            if (Character.isDigit(cell)) {
                column += Character.getNumericValue(cell);
            } else if (cell == '/') {
                row++;
                column = 0;
            } else {
                chessboard[row][column] = String.valueOf(cell);
                column++;
            }
        }
        return chessboard;
    }

    private static void fillAttackArea(boolean[][] attackArea, String[][] chessboard) {
        for (int row = 0; row < chessboard.length; row++) {
            for (int column = 0; column < chessboard[0].length; column++) {
                if (chessboard[row][column] != null) {
                    switch (chessboard[row][column]) {
                        case "P": updateAttackAreaWithPawn(attackArea, row, column, true); break;
                        case "p": updateAttackAreaWithPawn(attackArea, row, column, false); break;
                        case "N": updateAttackAreaWithKnight(attackArea, row, column); break;
                        case "n": updateAttackAreaWithKnight(attackArea, row, column); break;
                        case "B": updateAttackAreaWithBishop(attackArea, row, column, chessboard); break;
                        case "b": updateAttackAreaWithBishop(attackArea, row, column, chessboard); break;
                        case "R": updateAttackAreaWithRook(attackArea, row, column, chessboard); break;
                        case "r": updateAttackAreaWithRook(attackArea, row, column, chessboard); break;
                        case "Q": updateAttackAreaWithQueen(attackArea, row, column, chessboard); break;
                        case "q": updateAttackAreaWithQueen(attackArea, row, column, chessboard); break;
                        case "K": updateAttackAreaWithKing(attackArea, row, column); break;
                        case "k": updateAttackAreaWithKing(attackArea, row, column); break;
                    }
                }
            }
        }
    }

    private static int countUnoccupiedNotAttackedCells(boolean[][] attackArea, String[][] chessboard) {
        int count = 0;

        for (int i = 0; i < attackArea.length; i++) {
            for (int j = 0; j < attackArea[0].length; j++) {
                if (!attackArea[i][j] && chessboard[i][j] == null) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isValid(boolean[][] attackArea, int row, int column) {
        return row >= 0 && row < attackArea.length && column >= 0 && column < attackArea[0].length;
    }

    private static void updateAttackAreaWithKing(boolean[][] attackArea, int row, int column) {
        int[] neighborRows = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] neighborColumns = {0, -1, 1, -1, 1, 0, -1, 1};
        updateAttackArea(attackArea, row, column, neighborRows, neighborColumns);
    }

    private static void updateAttackAreaWithRook(boolean[][] attackArea, int row, int column, String[][] chessboard) {
        // Up
        for (int i = row - 1; i >= 0; i--) {
            if (chessboard[i][column] != null) {
                break;
            }
            attackArea[i][column] = true;
        }

        // Down
        for (int i = row + 1; i < attackArea.length; i++) {
            if (chessboard[i][column] != null) {
                break;
            }
            attackArea[i][column] = true;
        }

        // Left
        for (int i = column - 1; i >= 0; i--) {
            if (chessboard[row][i] != null) {
                break;
            }
            attackArea[row][i] = true;
        }

        // Right
        for (int i = column + 1; i < attackArea[0].length; i++) {
            if (chessboard[row][i] != null) {
                break;
            }
            attackArea[row][i] = true;
        }
    }

    private static void updateAttackAreaWithQueen(boolean[][] attackArea, int row, int column, String[][] chessboard) {
        updateAttackAreaWithRook(attackArea, row, column, chessboard);
        updateAttackAreaWithBishop(attackArea, row, column, chessboard);
    }

    private static void updateAttackAreaWithBishop(boolean[][] attackArea, int row, int column, String[][] chessboard) {
        // Left diagonal up
        for (int i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j--) {
            if (chessboard[i][j] != null) {
                break;
            }
            attackArea[i][j] = true;
        }

        // Left diagonal down
        for (int i = row + 1, j = column + 1; i < attackArea.length && j < attackArea[0].length; i++, j++) {
            if (chessboard[i][j] != null) {
                break;
            }
            attackArea[i][j] = true;
        }

        // Right diagonal up
        for (int i = row - 1, j = column + 1; i >= 0 && j < attackArea[0].length; i--, j++) {
            if (chessboard[i][j] != null) {
                break;
            }
            attackArea[i][j] = true;
        }

        // Right diagonal down
        for (int i = row + 1, j = column - 1; i < attackArea.length && j >= 0; i++, j--) {
            if (chessboard[i][j] != null) {
                break;
            }
            attackArea[i][j] = true;
        }
    }

    private static void updateAttackAreaWithKnight(boolean[][] attackArea, int row, int column) {
        int[] neighborRows = {-2, -2, -1, 1, 2, 2, -1, 1};
        int[] neighborColumns = {-1, 1, -2, -2, -1, 1, 2, 2};
        updateAttackArea(attackArea, row, column, neighborRows, neighborColumns);
    }

    private static void updateAttackAreaWithPawn(boolean[][] attackArea, int row, int column, boolean isWhite) {
        int[] neighborRows;
        int[] neighborColumns;

        if (isWhite) {
            neighborRows = new int[]{-1, -1};
            neighborColumns = new int[]{-1, 1};
        } else {
            neighborRows = new int[]{1, 1};
            neighborColumns = new int[]{-1, 1};
        }
        updateAttackArea(attackArea, row, column, neighborRows, neighborColumns);
    }

    private static void updateAttackArea(boolean[][] attackArea, int row, int column, int[] neighborRows, int[] neighborColumns) {
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(attackArea, neighborRow, neighborColumn)) {
                attackArea[neighborRow][neighborColumn] = true;
            }
        }
    }
}
