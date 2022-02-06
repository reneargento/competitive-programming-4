package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/02/22.
 */
public class NumericalMaze {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class Result {
        Cell start;
        Cell end;

        public Result(Cell start, Cell end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            int[][] maze = new int[FastReader.nextInt()][FastReader.nextInt()];
            for (int row = 0; row < maze.length; row++) {
                for (int column = 0; column < maze[0].length; column++) {
                    maze[row][column] = FastReader.nextInt();
                }
            }

            Result endpoints = solveMaze(maze);
            outputWriter.printLine(String.format("%d %d", endpoints.start.row + 1, endpoints.start.column + 1));
            outputWriter.printLine(String.format("%d %d", endpoints.end.row + 1, endpoints.end.column + 1));
        }
        outputWriter.flush();
    }

    private static Result solveMaze(int[][] maze) {
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int column = 0; column < maze[0].length; column++) {
            if (maze[0][column] != 1) {
                continue;
            }

            Cell start = new Cell(0, column);
            boolean[][] visited = new boolean[maze.length][maze[0].length];
            visited[0][column] = true;

            Cell end = findPath(maze, neighborRows, neighborColumns, start, 1, 2, visited);
            if (end != null) {
                return new Result(start, end);
            }
        }
        return null;
    }

    private static Cell findPath(int[][] maze, int[] neighborRows, int[] neighborColumns,
                                 Cell currentCell, int currentValue, int maxValue, boolean[][] visited) {
        if (currentCell.row == maze.length - 1 && currentValue == 1) {
            return currentCell;
        }

        Cell bestEnd = null;
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = currentCell.row + neighborRows[i];
            int neighborColumn = currentCell.column + neighborColumns[i];

            if (isValid(maze, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn]
                    && maze[neighborRow][neighborColumn] == currentValue) {
                visited[neighborRow][neighborColumn] = true;
                Cell nextCell = new Cell(neighborRow, neighborColumn);
                int nextValue = currentValue + 1;
                int nextMaxValue = maxValue;

                if (currentValue == maxValue) {
                    nextValue = 1;
                    nextMaxValue = maxValue + 1;
                }

                Cell end = findPath(maze, neighborRows, neighborColumns, nextCell, nextValue, nextMaxValue, visited);
                if (end != null) {
                    if (bestEnd == null || end.column < bestEnd.column) {
                        bestEnd = end;
                    }
                }
            }
        }
        return bestEnd;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
