package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 02/02/21.
 */
public class GridSuccessors {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int[][] grid = new int[3][3];

            for (int row = 0; row < grid.length; row++) {
                String values = scanner.next();
                for (int column = 0; column < grid[0].length; column++) {
                    grid[row][column] = Character.getNumericValue(values.charAt(column));
                }
            }

            int[] neighborRows = {-1, 0, 0, 1};
            int[] neighborColumns = {0, -1, 1, 0};
            int index = -1;
            while (true) {
                int[][] nextGrid = applyFunction(grid, neighborRows, neighborColumns);
                if (isEqual(grid, nextGrid)) {
                    System.out.println(index);
                    break;
                }
                grid = nextGrid;
                index++;
            }
        }
    }

    private static int[][] applyFunction(int[][] grid, int[] neighborRows, int[] neighborColumns) {
        int[][] nextGrid = new int[grid.length][grid[0].length];

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                int sum = 0;

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];
                    if (isValid(grid, neighborRow, neighborColumn)) {
                        sum += grid[neighborRow][neighborColumn];
                    }
                }
                nextGrid[row][column] = sum % 2;
            }
        }
        return nextGrid;
    }

    private static boolean isEqual(int[][] grid1, int[][] grid2) {
        for (int row = 0; row < grid1.length; row++) {
            for (int column = 0; column < grid1[0].length; column++) {
                if (grid1[row][column] != grid2[row][column]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }
}
