package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/05/23.
 */
public class MappingTheRoute {

    private static final String NOT_IN_PATH = "???";
    private static final String EMPTY_CELL = "   ";
    private static final String SOUTHERN_WALL = "---";
    private static final int[] neighborRows = { 0, -1, 0, 1 };
    private static final int[] neighborColumns = { -1, 0, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int mazeID = 1;
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int startRow = FastReader.nextInt();
        int startColumn = FastReader.nextInt();
        int goalRow = FastReader.nextInt();
        int goalColumn = FastReader.nextInt();

        while (rows != 0 || columns != 0 || startRow != 0 || startColumn != 0 || goalRow != 0 || goalColumn != 0) {
            int[][] grid = new int[rows][columns];
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[0].length; column++) {
                    grid[row][column] = FastReader.nextInt();
                }
            }

            String[][] maze = exploreMaze(grid, startRow - 1, startColumn - 1, goalRow - 1, goalColumn - 1);
            printMaze(maze, grid, mazeID, outputWriter);

            mazeID++;
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
            startRow = FastReader.nextInt();
            startColumn = FastReader.nextInt();
            goalRow = FastReader.nextInt();
            goalColumn = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String[][] exploreMaze(int[][] grid, int startRow, int startColumn, int goalRow, int goalColumn) {
        String[][] maze = new String[grid.length][grid[0].length];
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        depthFirstSearch(grid, visited, maze, goalRow, goalColumn, 1, startRow, startColumn);
        return maze;
    }

    private static boolean depthFirstSearch(int[][] grid, boolean[][] visited, String[][] maze, int goalRow,
                                            int goalColumn, int cellID, int row, int column) {
        visited[row][column] = true;
        maze[row][column] = String.valueOf(String.format("%3s", cellID));

        if (row == goalRow && column == goalColumn) {
            return true;
        }

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn]
                    && !cantMove(grid, row, column, i)) {
                boolean foundPath = depthFirstSearch(grid, visited, maze, goalRow, goalColumn, cellID + 1,
                        neighborRow, neighborColumn);
                if (foundPath) {
                    return true;
                }
            }
        }
        maze[row][column] = NOT_IN_PATH;
        return false;
    }

    private static void printMaze(String[][] maze, int[][] grid, int mazeID, OutputWriter outputWriter) {
        String headerFooter = computeHeaderFooter(maze[0].length);

        outputWriter.printLine(String.format("Maze %d\n", mazeID));
        outputWriter.printLine(headerFooter);
        for (int row = 0; row < maze.length; row++) {
            outputWriter.print("|");
            for (int column = 0; column < maze[row].length; column++) {
                String cell = maze[row][column];
                if (cell != null) {
                    outputWriter.print(cell);
                } else {
                    outputWriter.print(EMPTY_CELL);
                }

                if (grid[row][column] % 2 == 1) {
                    outputWriter.print("|");
                } else if (column != grid[row].length - 1) {
                    outputWriter.print(" ");
                }
            }
            if (grid[row][grid[row].length - 1] % 2 == 0) {
                outputWriter.print("|");
            }
            outputWriter.printLine();

            if (row != maze.length - 1) {
                outputWriter.print("+");
                for (int column = 0; column < maze[row].length; column++) {
                    if (grid[row][column] >= 2) {
                        outputWriter.print(SOUTHERN_WALL);
                    } else {
                        outputWriter.print(EMPTY_CELL);
                    }
                    outputWriter.print("+");
                }
                outputWriter.printLine();
            }
        }
        outputWriter.printLine(headerFooter);
        outputWriter.printLine();
        outputWriter.printLine();
    }

    private static String computeHeaderFooter(int columns) {
        StringBuilder headerFooter = new StringBuilder("+");
        for (int i = 0; i < columns; i++) {
            headerFooter.append("---+");
        }
        return headerFooter.toString();
    }

    private static boolean isValid(int[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    private static boolean cantMove(int[][] grid, int row, int column, int moveIndex) {
        return (moveIndex == 0 && cantMoveWest(grid, row, column)) ||
                (moveIndex == 1 && cantMoveNorth(grid, row, column)) ||
                (moveIndex == 2 && cantMoveEast(grid, row, column)) ||
                (moveIndex == 3 && cantMoveSouth(grid, row, column));
    }

    private static boolean cantMoveEast(int[][] grid, int row, int column) {
        return grid[row][column] % 2 == 1;
    }

    private static boolean cantMoveWest(int[][] grid, int row, int column) {
        if (column == 0) {
            return true;
        }
        return cantMoveEast(grid, row, column - 1);
    }

    private static boolean cantMoveSouth(int[][] grid, int row, int column) {
        return grid[row][column] >= 2;
    }

    private static boolean cantMoveNorth(int[][] grid, int row, int column) {
        if (row == 0) {
            return true;
        }
        return cantMoveSouth(grid, row - 1, column);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
