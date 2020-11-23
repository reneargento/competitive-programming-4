package chapter1.section6.c.games.other.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/10/20.
 */
public class Minesweeper {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        int fieldNumber = 1;
        while (rows != 0 || columns != 0) {
            char[][] field = new char[rows][columns];

            for (int r = 0; r < rows; r++) {
                String line = scanner.next();
                for (int c = 0; c < columns; c++) {
                    field[r][c] = line.charAt(c);
                }
            }

            int[][] fieldCount = new int[rows][columns];

            int[] neighborRows = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] neighborColumns = {-1, 0, 1, -1, 1, -1, 0, 1};

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    if (field[r][c] == '*') {
                        fieldCount[r][c] = -1;

                        for (int i = 0; i < neighborRows.length; i++) {
                            int neighborRow = r + neighborRows[i];
                            int neighborColumn = c + neighborColumns[i];

                            if (isValid(fieldCount, neighborRow, neighborColumn)) {
                                if (field[neighborRow][neighborColumn] != '*') {
                                    fieldCount[neighborRow][neighborColumn]++;
                                }
                            }
                        }
                    }
                }
            }

            if (fieldNumber > 1) {
                System.out.println();
            }

            System.out.printf("Field #%d:\n", fieldNumber);
            printFieldCount(fieldCount);

            fieldNumber++;
            rows = scanner.nextInt();
            columns = scanner.nextInt();
        }

    }

    private static boolean isValid(int[][] field, int row, int column) {
        return row >= 0 && row < field.length && column >= 0 && column < field[0].length;
    }

    private static void printFieldCount(int[][] fieldCount) {
        for (int r = 0; r < fieldCount.length; r++) {
            for (int c = 0; c < fieldCount[0].length; c++) {
                char value = fieldCount[r][c] == - 1 ? '*' : String.valueOf(fieldCount[r][c]).charAt(0);
                System.out.print(value);
            }
            System.out.println();
        }
    }
}
