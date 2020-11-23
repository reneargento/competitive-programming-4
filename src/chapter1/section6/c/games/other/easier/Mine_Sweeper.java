package chapter1.section6.c.games.other.easier;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 17/10/20.
 */
public class Mine_Sweeper {

    private static class Cell {
        int row, column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int gridSize = scanner.nextInt();
            char[][] grid = new char[gridSize][gridSize];

            for (int r = 0; r < gridSize; r++) {
                String line = scanner.next();
                grid[r] = line.toCharArray();
            }

            Set<Cell> mineCells = new HashSet<>();
            int[][] filledGrid = fillGrid(grid, mineCells);

            boolean exploded = false;
            boolean[][] cellsToPrint = new boolean[gridSize][gridSize];

            for (int r = 0; r < gridSize; r++) {
                String line = scanner.next();
                for (int c = 0; c < line.length(); c++) {
                    if (line.charAt(c) == 'x') {
                        cellsToPrint[r][c] = true;

                        if (grid[r][c] == '*') {
                            exploded = true;
                        }
                    }
                }
            }

            if (t > 0) {
                System.out.println();
            }

            for (int r = 0; r < gridSize; r++) {
                for (int c = 0; c < gridSize; c++) {
                    if (cellsToPrint[r][c] && grid[r][c] != '*') {
                        System.out.print(filledGrid[r][c]);
                    } else if (exploded && grid[r][c] == '*') {
                        System.out.print('*');
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();
            }
        }
    }

    private static int[][] fillGrid(char[][] grid, Set<Cell> mineCells) {
        int length = grid.length;
        int[][] filledGrid = new int[length][length];
        int[] neighborRows = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] neighborColumns = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int r = 0; r < length; r++) {
            for (int c = 0; c < length; c++) {
                if (grid[r][c] == '*') {
                    mineCells.add(new Cell(r, c));

                    for (int i = 0; i < neighborRows.length; i++) {
                        int neighborRow = r + neighborRows[i];
                        int neighborColumn = c + neighborColumns[i];

                        if (isValid(grid, neighborRow, neighborColumn)
                                && grid[neighborRow][neighborColumn] != '*') {
                            filledGrid[neighborRow][neighborColumn]++;
                        }
                    }
                }
            }
        }
        return filledGrid;
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

}
