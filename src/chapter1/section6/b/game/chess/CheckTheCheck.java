package chapter1.section6.b.game.chess;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/10/20.
 */
public class CheckTheCheck {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class BoardInformation {
        char[][] board;
        Cell whiteKing;
        Cell blackKing;

        public BoardInformation(char[][] board, Cell whiteKing, Cell blackKing) {
            this.board = board;
            this.whiteKing = whiteKing;
            this.blackKing = blackKing;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BoardInformation boardInformation = readBoard(scanner);
        int game = 1;
        while (boardInformation != null) {
            scanner.nextLine();
            String king = "no";
            boolean isWhiteKingInCheck = isInCheck(boardInformation, true);

            if (isWhiteKingInCheck) {
                king = "white";
            } else {
                boolean isBlackKingInCheck = isInCheck(boardInformation, false);
                if (isBlackKingInCheck) {
                    king = "black";
                }
            }
            System.out.printf("Game #%d: %s king is in check.\n", game, king);
            game++;
            boardInformation = readBoard(scanner);
        }
    }

    private static BoardInformation readBoard(Scanner scanner) {
        boolean isValid = false;
        char[][] board = new char[8][8];
        Cell whiteKing = null;
        Cell blackKing = null;

        for (int row = 0; row < 8; row++) {
            String line = scanner.nextLine();
            for (int column = 0; column < 8; column++) {
                char cellValue = line.charAt(column);

                if (cellValue != '.') {
                    isValid = true;

                    if (cellValue == 'K') {
                        whiteKing = new Cell(row, column);
                    } else if (cellValue == 'k') {
                        blackKing = new Cell(row, column);
                    }
                }
                board[row][column] = cellValue;
            }
        }

        if (!isValid) {
            return null;
        }
        return new BoardInformation(board, whiteKing, blackKing);
    }

    private static boolean isInCheck(BoardInformation boardInformation, boolean isWhite) {
        int row;
        int column;

        if (isWhite) {
            row = boardInformation.whiteKing.row;
            column = boardInformation.whiteKing.column;
        } else {
            row = boardInformation.blackKing.row;
            column = boardInformation.blackKing.column;
        }
        return isInCheckWithPawn(boardInformation, isWhite, row, column)
                || isInCheckVerticalHorizontal(boardInformation, isWhite, 'r', row, column)
                || isInCheckDiagonal(boardInformation, isWhite, 'b', row, column)
                || isInCheckWithQueen(boardInformation, isWhite, row, column)
                || isInCheckWithKing(boardInformation, isWhite, row, column)
                || isInCheckWithKnight(boardInformation, isWhite, row, column);
    }

    private static boolean isInCheckWithPawn(BoardInformation boardInformation, boolean isWhite, int row, int column) {
        int[] neighborRows;
        int[] neighborColumns = {-1, 1};
        char pawn;

        if (isWhite) {
            neighborRows = new int[]{-1, -1};
            pawn = 'p';
        } else {
            neighborRows = new int[]{1, 1};
            pawn = 'P';
        }
        return isInCheck(neighborRows, neighborColumns, row, column, boardInformation, pawn);
    }

    private static boolean isInCheckVerticalHorizontal(BoardInformation boardInformation, boolean isWhite, char piece,
                                                       int row, int column) {
        char[][] board = boardInformation.board;

        if (isWhite) {
            piece = Character.toLowerCase(piece);
        } else {
            piece = Character.toUpperCase(piece);
        }

        // Left
        for (int c = column - 1; c >= 0; c--) {
            if (board[row][c] == piece) {
                return true;
            } else if (board[row][c] != '.') {
                break;
            }
        }
        // Right
        for (int c = column + 1; c < 8; c++) {
            if (board[row][c] == piece) {
                return true;
            } else if (board[row][c] != '.') {
                break;
            }
        }
        // Up
        for (int r = row - 1; r >= 0; r--) {
            if (board[r][column] == piece) {
                return true;
            } else if (board[r][column] != '.') {
                break;
            }
        }
        // Down
        for (int r = row + 1; r < 8; r++) {
            if (board[r][column] == piece) {
                return true;
            } else if (board[r][column] != '.') {
                break;
            }
        }
        return false;
    }

    private static boolean isInCheckDiagonal(BoardInformation boardInformation, boolean isWhite, char piece, int row,
                                             int column) {
        char[][] board = boardInformation.board;

        if (isWhite) {
            piece = Character.toLowerCase(piece);
        } else {
            piece = Character.toUpperCase(piece);
        }

        // Upper-left
        for (int r = row - 1, c = column - 1; r >= 0 && c >= 0; r--, c--) {
            if (board[r][c] == piece) {
                return true;
            } else if (board[r][c] != '.') {
                break;
            }
        }
        // Upper-right
        for (int r = row - 1, c = column + 1; r >= 0 && c < 8; r--, c++) {
            if (board[r][c] == piece) {
                return true;
            } else if (board[r][c] != '.') {
                break;
            }
        }
        // Lower-left
        for (int r = row + 1, c = column - 1; r < 8 && c >= 0; r++, c--) {
            if (board[r][c] == piece) {
                return true;
            } else if (board[r][c] != '.') {
                break;
            }
        }
        // Lower-right
        for (int r = row + 1, c = column + 1; r < 8 && c < 8; r++, c++) {
            if (board[r][c] == piece) {
                return true;
            } else if (board[r][c] != '.') {
                break;
            }
        }
        return false;
    }

    private static boolean isInCheckWithQueen(BoardInformation boardInformation, boolean isWhite, int row, int column) {
        char queen = isWhite ? 'q' : 'Q';
        return isInCheckVerticalHorizontal(boardInformation, isWhite, queen, row, column)
                || isInCheckDiagonal(boardInformation, isWhite, queen, row, column);
    }

    private static boolean isInCheckWithKing(BoardInformation boardInformation, boolean isWhite, int row, int column) {
        int[] neighborRows = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] neighborColumns = {-1, 0, 1, -1, 1, -1, 0, 1};
        char king = isWhite ? 'k' : 'K';
        return isInCheck(neighborRows, neighborColumns, row, column, boardInformation, king);
    }

    private static boolean isInCheckWithKnight(BoardInformation boardInformation, boolean isWhite, int row, int column) {
        int[] neighborRows = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] neighborColumns = {-1, 1, -2, 2, -2, 2, -1, 1};
        char knight = isWhite ? 'n' : 'N';
        return isInCheck(neighborRows, neighborColumns, row, column, boardInformation, knight);
    }

    private static boolean isInCheck(int[] neighborRows, int[] neighborColumns, int row, int column,
                                     BoardInformation boardInformation, char piece) {
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(boardInformation.board, neighborRow, neighborColumn)) {
                if (boardInformation.board[neighborRow][neighborColumn] == piece) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValid(char[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
    }
}
