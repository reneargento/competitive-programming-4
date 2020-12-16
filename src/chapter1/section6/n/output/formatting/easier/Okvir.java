package chapter1.section6.n.output.formatting.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/12/20.
 */
public class Okvir {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        int topRows = scanner.nextInt();
        int leftColumns = scanner.nextInt();
        int rightColumns = scanner.nextInt();
        int bottomRows = scanner.nextInt();

        int totalRows = rows + topRows + bottomRows;
        int totalColumns = columns + leftColumns + rightColumns;

        char[][] crossword = new char[totalRows][totalColumns];

        for (int row = topRows; row < totalRows - bottomRows; row++) {
            String word = scanner.next();
            int wordIndex = 0;
            for (int column = leftColumns; column < totalColumns - rightColumns; column++) {
                crossword[row][column] = word.charAt(wordIndex++);
            }
        }

        for (int row = 0; row < topRows; row++) {
            fillRow(crossword, row);
        }
        for (int column = 0; column < leftColumns; column++) {
            fillColumn(crossword, column);
        }

        if (topRows == 0 && bottomRows != 0) {
            fillBottomRow(crossword, totalRows, bottomRows, true);
            fillRightColumns(crossword, totalColumns, rightColumns, false);
        } else if (leftColumns == 0 && rightColumns != 0) {
            fillRightColumns(crossword, totalColumns, rightColumns, true);
            fillBottomRow(crossword, totalRows, bottomRows, false);
        } else {
            fillRightColumns(crossword, totalColumns, rightColumns, true);
            fillBottomRow(crossword, totalRows, bottomRows, true);
        }
        printCrossword(crossword);
    }

    private static void fillRightColumns(char[][] crossword, int totalColumns, int rightColumns, boolean topDown) {
        for (int column = totalColumns - rightColumns; column < totalColumns; column++) {
            if (topDown) {
                fillColumn(crossword, column);
            } else {
                fillColumnBottomUp(crossword, column);
            }
        }
    }

    private static void fillBottomRow(char[][] crossword, int totalRows, int bottomRows, boolean leftRight) {
        for (int row = totalRows - bottomRows; row < totalRows; row++) {
            if (leftRight) {
                fillRow(crossword, row);
            } else {
                fillRowRightLeft(crossword, row);
            }
        }
    }

    private static void fillRow(char[][] crossword, int row) {
        for (int column = 0; column < crossword[0].length; column++) {
            char symbol = getSymbol(crossword, row, column);
            crossword[row][column] = symbol;
        }
    }

    private static void fillRowRightLeft(char[][] crossword, int row) {
        for (int column = crossword[0].length - 1; column >= 0; column--) {
            char symbol = getSymbol(crossword, row, column);
            crossword[row][column] = symbol;
        }
    }

    private static void fillColumn(char[][] crossword, int column) {
        for (int row = 0; row < crossword.length; row++) {
            char symbol = getSymbol(crossword, row, column);
            crossword[row][column] = symbol;
        }
    }

    private static void fillColumnBottomUp(char[][] crossword, int column) {
        for (int row = crossword.length - 1; row >= 0; row--) {
            char symbol = getSymbol(crossword, row, column);
            crossword[row][column] = symbol;
        }
    }

    private static char getSymbol(char[][] crossword, int row, int column) {
        char symbol;
        if (row != 0 && isValidSymbol(crossword, row - 1, column)) {
            symbol = getInverseSymbol(crossword, row - 1, column);
        } else if (column > 0 && isValidSymbol(crossword, row, column - 1)) {
            symbol = getInverseSymbol(crossword, row, column - 1);
        } else if (column < crossword[0].length - 1 && isValidSymbol(crossword, row, column + 1)) {
            symbol = getInverseSymbol(crossword, row, column + 1);
        } else if (row < crossword.length - 1 && isValidSymbol(crossword, row + 1, column)) {
            symbol = getInverseSymbol(crossword, row + 1, column);
        } else if (row == 0 && column == 0) {
            symbol = '#';
        } else {
            symbol = '.';
        }
        return symbol;
    }

    private static boolean isValidSymbol(char[][] crossword, int row, int column) {
        return crossword[row][column] == '#' || crossword[row][column] == '.';
    }

    private static char getInverseSymbol(char[][] crossword, int row, int column) {
        if (crossword[row][column] == '#') {
            return '.';
        } else {
            return '#';
        }
    }

    private static void printCrossword(char[][] crossword) {
        for (int row = 0; row < crossword.length; row++) {
            for (int column = 0; column < crossword[0].length; column++) {
                System.out.print(crossword[row][column]);
            }
            System.out.println();
        }
    }
}
