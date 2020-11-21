package chapter1.section6.b.game.chess;

import java.util.*;

/**
 * Created by Rene Argento on 15/10/20.
 */
public class HelpMeWithTheGame {

    private static class Cell implements Comparable<Cell> {
        char piece;
        char column;
        int row;
        boolean isWhite;

        public Cell(char piece, char column, int row) {
            this.piece = piece;
            this.column = column;
            this.row = row;

            if (Character.isLowerCase(piece)) {
                this.piece = Character.toUpperCase(piece);
                isWhite = false;
            } else {
                isWhite = true;
            }
        }

        @Override
        public int compareTo(Cell otherCell) {
            int pieceValue = getPieceValue(piece);
            int otherPieceValue = getPieceValue(otherCell.piece);

            if (pieceValue != otherPieceValue) {
                return pieceValue - otherPieceValue;
            }

            if (row != otherCell.row) {
                if (isWhite) {
                    return row - otherCell.row;
                } else {
                    return otherCell.row - row;
                }
            }
            return column - otherCell.column;
        }

        private int getPieceValue(char piece) {
            switch (piece) {
                case 'K': return 0;
                case 'Q': return 1;
                case 'R': return 2;
                case 'B': return 3;
                case 'N': return 4;
                default: return 5;
            }
        }

        @Override
        public String toString() {
            String pieceDescription = "";
            if (piece != 'P') {
                pieceDescription += piece;
            }
            pieceDescription += column + String.valueOf(row);
            return pieceDescription;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int lineCount = 0;
        List<Cell> whiteCells = new ArrayList<>();
        List<Cell> blackCells = new ArrayList<>();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (lineCount % 2 == 0) {
                lineCount++;
                continue;
            }

            for (int i = 0; i < line.length(); i++) {
                char value = line.charAt(i);
                if (isPiece(value)) {
                    int columnInt = i / 4;
                    char column = (char) ('a' + columnInt);
                    int row = 8 - (lineCount / 2);
                    Cell cell = new Cell(value, column, row);

                    if (Character.isLowerCase(value)) {
                        blackCells.add(cell);
                    } else {
                        whiteCells.add(cell);
                    }
                }
            }
            lineCount++;
        }

        Collections.sort(whiteCells);
        Collections.sort(blackCells);

        System.out.print("White: ");
        printCells(whiteCells);
        System.out.print("Black: ");
        printCells(blackCells);
    }

    private static boolean isPiece(char value) {
        char upperCaseValue = Character.toUpperCase(value);
        return upperCaseValue == 'K'
                || upperCaseValue == 'Q'
                || upperCaseValue == 'R'
                || upperCaseValue == 'B'
                || upperCaseValue == 'N'
                || upperCaseValue == 'P';
    }

    private static void printCells(List<Cell> cells) {
        StringJoiner cellsList = new StringJoiner(",");
        for (Cell cell : cells) {
            cellsList.add(String.valueOf(cell));
        }
        System.out.println(cellsList);
    }
}
