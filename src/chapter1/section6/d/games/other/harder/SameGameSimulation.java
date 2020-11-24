package chapter1.section6.d.games.other.harder;

import java.util.*;

/**
 * Created by Rene Argento on 28/10/20.
 */
public class SameGameSimulation {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            Cell cell = (Cell) other;
            return row == cell.row &&
                    column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        int[] neighborRows = {-1, 0, 0, 1};
        int[] neighborColumns = {0, -1, 1, 0};

        int gridNumber = 1;

        while (rows != 0 || columns != 0) {
            int[][] grid = new int[rows][columns];
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < columns; c++) {
                    grid[r][c] = scanner.nextInt();
                }
            }

            int targetRow = scanner.nextInt() - 1;
            int targetColumn = scanner.nextInt() - 1;
            boolean won = false;

            while (targetRow != -1 || targetColumn != -1) {
                if (!won && canBeRemoved(grid, targetRow, targetColumn, neighborRows, neighborColumns)) {
                    floodFill(grid, targetRow, targetColumn, neighborRows, neighborColumns);
                    won = won(grid);

                    moveVerticalCells(grid);
                    moveColumnsIfNeeded(grid);
                }

                targetRow = scanner.nextInt() - 1;
                targetColumn = scanner.nextInt() - 1;
            }

            if (gridNumber > 1) {
                System.out.println();
            }
            System.out.printf("Grid %d.\n", gridNumber);
            if (won) {
                System.out.println("    Game Won");
            } else {
                for (int r = grid.length - 1; r >= 0; r--) {
                    for (int c = 0; c < grid[0].length; c++) {
                        if (c == 0) {
                            System.out.print("    ");
                        }

                        if (grid[r][c] != -1) {
                            System.out.print(grid[r][c]);
                        } else {
                            System.out.print(" ");
                        }

                        if (c < grid[0].length - 1) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
            }

            gridNumber++;
            rows = scanner.nextInt();
            columns = scanner.nextInt();
        }
    }

    private static boolean canBeRemoved(int[][] grid, int row, int column, int[] neighborRows, int[] neighborColumns) {
        if (!isValid(grid, row, column)) {
            return false;
        }

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)) {
                if (grid[neighborRow][neighborColumn] == grid[row][column]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void floodFill(int[][] grid, int row, int column, int[] neighborRows, int[] neighborColumns) {
        int originalValue = grid[row][column];
        Set<Cell> visited = new HashSet<>();

        Queue<Cell> queue = new LinkedList<>();
        Cell origin = new Cell(row, column);
        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            grid[cell.row][cell.column] = -1;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(grid, neighborRow, neighborColumn)) {
                    Cell neighbor = new Cell(neighborRow, neighborColumn);

                    if (grid[neighborRow][neighborColumn] == originalValue && !visited.contains(neighbor)) {
                        queue.offer(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
        }
    }

    private static boolean isValid(int[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    private static void moveVerticalCells(int[][] grid) {
        for (int r = 0; r < grid.length - 1; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == -1) {
                    for (int upperRow = r + 1; upperRow < grid.length; upperRow++) {
                        if (grid[upperRow][c] != -1) {
                            grid[r][c] = grid[upperRow][c];
                            grid[upperRow][c] = -1;
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void moveColumnsIfNeeded(int[][] grid) {
        for (int c = 0; c < grid[0].length - 1; c++) {
            boolean shouldMove = true;

            for (int r = 0; r < grid.length; r++) {
                if (grid[r][c] != -1) {
                    shouldMove = false;
                    break;
                }
            }

            if (shouldMove) {
                int columnToMove = -1;
                for (int rightColumn = c + 1; rightColumn < grid[0].length; rightColumn++) {

                    for (int r = 0; r < grid.length; r++) {
                        if (grid[r][rightColumn] != -1) {
                            columnToMove = rightColumn;
                            break;
                        }
                    }

                    if (columnToMove != -1) {
                        break;
                    }
                }

                if (columnToMove != -1) {
                    for (int r = 0; r < grid.length; r++) {
                        grid[r][c] = grid[r][columnToMove];
                        grid[r][columnToMove] = -1;
                    }
                }
            }
        }
    }

    private static boolean won(int[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != -1) {
                    return false;
                }
            }
        }
        return true;
    }

}
