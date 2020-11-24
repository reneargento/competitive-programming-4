package chapter1.section6.d.games.other.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 29/10/20.
 */
public class RockScissorsPaper {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        int[] neighborRows = {-1, 0, 0, 1};
        int[] neighborColumns = {0, -1, 1, 0};

        for (int t = 0; t < tests; t++) {
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();
            int days = scanner.nextInt();
            scanner.nextLine();

            char[][] grid = new char[rows][columns];

            for (int r = 0; r < rows; r++) {
                String line = scanner.nextLine();
                grid[r] = line.toCharArray();
            }

            for (int i = 0; i < days; i++) {
                grid = battle(grid, neighborRows, neighborColumns);
            }
            if (t > 0) {
                System.out.println();
            }
            printGrid(grid);
        }
    }

    private static char[][] battle(char[][] grid, int[] neighborRows, int[] neighborColumns) {
        if (grid.length == 0) {
            return grid;
        }
        char[][] newGrid = new char[grid.length][grid[0].length];

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                newGrid[row][column] = grid[row][column];

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];

                    if (isValid(grid, neighborRow, neighborColumn)
                            && grid[row][column] != grid[neighborRow][neighborColumn]) {
                        char winner = battle(grid[row][column], grid[neighborRow][neighborColumn]);
                        if (winner == grid[neighborRow][neighborColumn]) {
                            newGrid[row][column] = winner;
                            break;
                        }
                    }
                }
            }
        }
        return newGrid;
    }

    private static char battle(char lifeForm1, char lifeForm2) {
        if (lifeForm1 == 'R') {
            if (lifeForm2 == 'S') {
                return 'R';
            } else if (lifeForm2 == 'P') {
                return 'P';
            }
        }

        if (lifeForm1 == 'P') {
            if (lifeForm2 == 'S') {
                return 'S';
            } else if (lifeForm2 == 'R') {
                return 'P';
            }
        }

        if (lifeForm1 == 'S') {
            if (lifeForm2 == 'P') {
                return 'S';
            } else if (lifeForm2 == 'R') {
                return 'R';
            }
        }
        return lifeForm1;
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    private static void printGrid(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                System.out.print(grid[row][column]);
            }
            System.out.println();
        }
    }

}
