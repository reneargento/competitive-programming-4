package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/02/21.
 */
public class ErrorCorrection {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dimension = scanner.nextInt();

        while (dimension != 0) {
            int[][] grid = new int[dimension][dimension];
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[0].length; column++) {
                    grid[row][column] = scanner.nextInt();
                }
            }
            evaluateMatrix(grid);
            dimension = scanner.nextInt();
        }
    }

    private static void evaluateMatrix(int[][] grid) {
        boolean ok = true;
        int invalidRow = -1;
        int invalidColumn = -1;

        for (int row = 0; row < grid.length; row++) {
            int rowSum = 0;
            for (int column = 0; column < grid[0].length; column++) {
                rowSum += grid[row][column];
            }
            if (rowSum % 2 != 0) {
                if (invalidRow != -1) {
                    ok = false;
                    break;
                }
                invalidRow = row + 1;
            }
        }

        for (int column = 0; column < grid[0].length; column++) {
            int columnSum = 0;
            for (int row = 0; row < grid.length; row++) {
                columnSum += grid[row][column];
            }
            if (columnSum % 2 != 0) {
                if (invalidColumn != -1) {
                    ok = false;
                    break;
                }
                invalidColumn = column + 1;
            }
        }

        if (ok) {
            if (invalidRow == -1) {
                System.out.println("OK");
            } else {
                System.out.printf("Change bit (%d,%d)\n", invalidRow, invalidColumn);
            }
        } else {
            System.out.println("Corrupt");
        }
    }
}
