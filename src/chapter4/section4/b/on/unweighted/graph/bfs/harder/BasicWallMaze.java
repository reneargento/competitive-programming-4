package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/12/23.
 */
public class BasicWallMaze {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };
    private static final int WALL_HORIZONTAL = 1;
    private static final int WALL_VERTICAL = 2;
    private static final int WALL_BOTH = 3;
    private static final char NORTH = 'N';
    private static final char SOUTH = 'S';
    private static final char EAST = 'E';
    private static final char WEST = 'W';

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int startColumn = FastReader.nextInt();
        int startRow = FastReader.nextInt();

        while (startColumn != 0 || startRow != 0) {
            int[][] maze = new int[6][6];
            Cell startCell = new Cell(startRow - 1, startColumn - 1);
            int endColumn = FastReader.nextInt() - 1;
            int endRow = FastReader.nextInt() - 1;
            Cell endCell = new Cell(endRow, endColumn);

            for (int i = 0; i < 3; i++) {
                int startWallColumn = FastReader.nextInt();
                int startWallRow = FastReader.nextInt();
                int endWallColumn = FastReader.nextInt();
                int endWallRow = FastReader.nextInt();

                Cell startWall = new Cell(startWallRow, startWallColumn);
                Cell endWall = new Cell(endWallRow, endWallColumn);
                placeWall(maze, startWall, endWall);
            }
            String path = computePath(maze, startCell, endCell);
            outputWriter.printLine(path);

            startColumn = FastReader.nextInt();
            startRow = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void placeWall(int[][] maze, Cell startWall, Cell endWall) {
        if (startWall.row == endWall.row) {
            if (startWall.row == 6) {
                return;
            }
            for (int column = startWall.column; column < endWall.column; column++) {
                maze[startWall.row][column] += WALL_HORIZONTAL;
            }
        } else {
            if (startWall.column == 6) {
                return;
            }
            for (int row = startWall.row; row < endWall.row; row++) {
                maze[row][startWall.column] += WALL_VERTICAL;
            }
        }
    }

    private static String computePath(int[][] maze, Cell startCell, Cell endCell) {
        Cell[][] parentCells = new Cell[maze.length][maze[0].length];
        Queue<Cell> queue = new LinkedList<>();
        boolean[][] visited = new boolean[maze.length][maze[0].length];

        queue.offer(startCell);
        visited[startCell.row][startCell.column] = true;

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            if (cell.equals(endCell)) {
                return buildPath(startCell, endCell, parentCells);
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(maze, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]) {
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    if (canMove(maze, cell, nextCell)) {
                        visited[neighborRow][neighborColumn] = true;
                        parentCells[neighborRow][neighborColumn] = new Cell(cell.row, cell.column);
                        queue.offer(nextCell);
                    }
                }
            }
        }
        return null;
    }

    private static String buildPath(Cell startCell, Cell endCell, Cell[][] parentCells) {
        StringBuilder path = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();
        Cell currentCell = endCell;

        while (!currentCell.equals(startCell)) {
            Cell parentCell = parentCells[currentCell.row][currentCell.column];
            if (parentCell.row < currentCell.row) {
                stack.push(SOUTH);
            } else if (parentCell.row > currentCell.row) {
                stack.push(NORTH);
            } else if (parentCell.column < currentCell.column) {
                stack.push(EAST);
            } else {
                stack.push(WEST);
            }
            currentCell = parentCell;
        }

        while (!stack.isEmpty()) {
            path.append(stack.pop());
        }
        return path.toString();
    }

    private static boolean canMove(int[][] maze, Cell currentCell, Cell nextCell) {
        if (currentCell.row > nextCell.row) {
            // Moving north
            return maze[currentCell.row][currentCell.column] != WALL_HORIZONTAL
                    && maze[currentCell.row][currentCell.column] != WALL_BOTH;
        } else if (currentCell.row < nextCell.row) {
            // Moving south
            return maze[nextCell.row][nextCell.column] != WALL_HORIZONTAL
                    && maze[nextCell.row][nextCell.column] != WALL_BOTH;
        } else if (currentCell.column < nextCell.column) {
            // Moving east
            return maze[nextCell.row][nextCell.column] != WALL_VERTICAL
                    && maze[nextCell.row][nextCell.column] != WALL_BOTH;
        } else {
            // Moving west
            return maze[currentCell.row][currentCell.column] != WALL_VERTICAL
                    && maze[currentCell.row][currentCell.column] != WALL_BOTH;
        }
    }

    private static boolean isValid(int[][] maze, int row, int column) {
        return row >= 0 && row < maze.length && column >= 0 && column < maze[0].length;
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
