package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 20/12/20.
 */
public class Amazing {

    private static class ExperimentResults {
        int notVisited;
        int visitedOnce;
        int visitedTwice;
        int visitedThreeTimes;
        int visitedFourTimes;
    }

    private enum Direction {
        EAST, WEST, NORTH, SOUTH;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        while (rows != 0 || columns != 0) {
            char[][] maze = new char[rows][columns];
            for (int r = 0; r < rows; r++) {
                maze[r] = scanner.next().toCharArray();
            }
            ExperimentResults experimentResults = exploreMaze(maze);
            System.out.printf("%3d%3d%3d%3d%3d\n", experimentResults.notVisited, experimentResults.visitedOnce,
                    experimentResults.visitedTwice, experimentResults.visitedThreeTimes, experimentResults.visitedFourTimes);

            rows = scanner.nextInt();
            columns = scanner.nextInt();
        }
    }

    private static ExperimentResults exploreMaze(char[][] maze) {
        int[][] visits = new int[maze.length][maze[0].length];

        int row = maze.length - 1;
        int column = 0;
        Direction direction = Direction.EAST;

        while (true) {
            switch (direction) {
                case EAST:
                    if (!isValid(maze, row + 1, column) && canGoEast(maze, row, column)) {
                        column++;
                    } else if (canGoSouth(maze, row, column)) {
                        row++;
                        direction = Direction.SOUTH;
                    } else if (canGoNorth(maze, row, column)) {
                        row--;
                        direction = Direction.NORTH;
                    } else {
                        column--;
                        direction = Direction.WEST;
                    }
                    break;
                case WEST:
                    if (!isValid(maze, row - 1, column) && canGoWest(maze, row, column)) {
                        column--;
                    } else if (canGoNorth(maze, row, column)) {
                        row--;
                        direction = Direction.NORTH;
                    } else if (canGoSouth(maze, row, column)) {
                        row++;
                        direction = Direction.SOUTH;
                    } else {
                        column++;
                        direction = Direction.EAST;
                    }
                    break;
                case NORTH:
                    if (!isValid(maze, row, column + 1) && canGoNorth(maze, row, column)) {
                        row--;
                    } else if (canGoEast(maze, row, column)) {
                        column++;
                        direction = Direction.EAST;
                    } else if (canGoWest(maze, row, column)) {
                        column--;
                        direction = Direction.WEST;
                    } else {
                        row++;
                        direction = Direction.SOUTH;
                    }
                    break;
                default:
                    if (!isValid(maze, row, column - 1) && canGoSouth(maze, row, column)) {
                        row++;
                    } else if (canGoWest(maze, row, column)) {
                        column--;
                        direction = Direction.WEST;
                    } else if (canGoEast(maze, row, column)) {
                        column++;
                        direction = Direction.EAST;
                    } else {
                        row--;
                        direction = Direction.NORTH;
                    }
            }
            visits[row][column]++;
            if (row == maze.length - 1 && column == 0) {
                break;
            }
        }
        return getResults(visits, maze);
    }

    private static boolean canGoEast(char[][] maze, int row, int column) {
        return isValid(maze, row, column + 1) &&
                (!isValid(maze, row + 1, column + 1) || !isValid(maze, row + 1, column));
    }

    private static boolean canGoWest(char[][] maze, int row, int column) {
        return isValid(maze, row, column - 1) &&
                (!isValid(maze, row - 1, column - 1) || !isValid(maze, row - 1, column));
    }

    private static boolean canGoNorth(char[][] maze, int row, int column) {
        return isValid(maze, row - 1, column) &&
                (!isValid(maze, row - 1, column + 1) || !isValid(maze, row, column + 1));
    }

    private static boolean canGoSouth(char[][] maze, int row, int column) {
        return isValid(maze, row + 1, column) &&
                (!isValid(maze, row + 1, column - 1) || !isValid(maze, row, column - 1));
    }

    private static ExperimentResults getResults(int[][] visits, char[][] maze) {
        ExperimentResults experimentResults = new ExperimentResults();

        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                if (maze[r][c] == '0') {
                    switch (visits[r][c]) {
                        case 0: experimentResults.notVisited++; break;
                        case 1: experimentResults.visitedOnce++; break;
                        case 2: experimentResults.visitedTwice++; break;
                        case 3: experimentResults.visitedThreeTimes++; break;
                        case 4: experimentResults.visitedFourTimes++;
                    }
                }
            }
        }
        return experimentResults;
    }

    private static boolean isValid(char[][] maze, int row, int column) {
        return row >= 0 && row < maze.length && column >= 0 && column < maze[0].length && maze[row][column] == '0';
    }
}
