package chapter1.section6.b.game.chess;

import java.util.*;

/**
 * Created by Rene Argento on 14/10/20.
 */
public class ChessKattis {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object otherCell) {
            if (this == otherCell) {
                return true;
            }
            if (otherCell == null || getClass() != otherCell.getClass()) {
                return false;
            }
            Cell cell = (Cell) otherCell;
            return row == cell.row && column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int startColumn = mapCharToInt(scanner.next().charAt(0));
            int startRow = scanner.nextInt() - 1;
            int endColumn = mapCharToInt(scanner.next().charAt(0));
            int endRow = scanner.nextInt() - 1;

            Cell startCell = new Cell(startRow, startColumn);
            Cell endCell = new Cell(endRow, endColumn);

            List<Cell> moves = new ArrayList<>();
            moves.add(startCell);

            if (startCell.equals(endCell)) {
                printMoves(moves);
                continue;
            }

            if (isPossible(startRow, startColumn, endRow, endColumn)) {
                if (isBlack(startRow, startColumn)) {
                    if (!isBlackDiagonal(startRow, startColumn)) {
                        Cell moveFromSource = moveToDiagonal(startRow, startColumn, true);
                        moves.add(moveFromSource);

                        if (moveFromSource.equals(endCell)) {
                            printMoves(moves);
                            continue;
                        }
                    }
                    Cell moveToDestination = moveToDiagonal(endRow, endColumn, true);
                    addCellIfDifferentFromLast(moves, moveToDestination);
                    addCellIfDifferentFromLast(moves, endCell);
                    printMoves(moves);
                } else {
                    if (!isWhiteDiagonal(startRow, startColumn)) {
                        Cell moveFromSource = moveToDiagonal(startRow, startColumn, false);
                        moves.add(moveFromSource);

                        if (moveFromSource.equals(endCell)) {
                            printMoves(moves);
                            continue;
                        }
                    }
                    Cell moveToDestination = moveToDiagonal(endRow, endColumn, false);
                    addCellIfDifferentFromLast(moves, moveToDestination);
                    addCellIfDifferentFromLast(moves, endCell);
                    printMoves(moves);
                }
            } else {
                System.out.println("Impossible");
            }
        }
    }

    private static void printMoves(List<Cell> moves) {
        System.out.print(moves.size() - 1 + " ");
        StringJoiner cellList = new StringJoiner(" ");

        for (Cell move : moves) {
            char column = mapIntToChar(move.column);
            cellList.add(String.valueOf(column + " " + (move.row + 1)));
        }
        System.out.println(cellList);
    }

    private static int mapCharToInt(char charValue) {
        return Character.toLowerCase(charValue) - 'a';
    }

    private static char mapIntToChar(int intValue) {
        return (char) Character.toUpperCase(intValue + 'a');
    }

    private static boolean isBlack(int row, int column) {
        return (row % 2 == 0 && column % 2 == 0) || (row % 2 != 0 && column % 2 != 0);
    }

    private static boolean isWhiteDiagonal(int row, int column) {
        return row + column == 7;
    }

    private static boolean isBlackDiagonal(int row, int column) {
        return row == column;
    }

    private static Cell moveToDiagonal(int row, int column, boolean isBlack) {
        if (isBlack) {
            int position = (row + column) / 2;
            return new Cell(position, position);
        } else {
            int diagonalColumn = 7 - row;
            int distance = (diagonalColumn - column) / 2;
            int nextRow = row + distance;
            int nextColumn = column + distance;
            return new Cell(nextRow, nextColumn);
        }
    }

    private static boolean isPossible(int startRow, int startColumn, int endRow, int endColumn) {
        return isBlack(startRow, startColumn) == isBlack(endRow, endColumn);
    }

    private static void addCellIfDifferentFromLast(List<Cell> moves, Cell move) {
        if (!moves.get(moves.size() - 1).equals(move)) {
            moves.add(move);
        }
    }

}
