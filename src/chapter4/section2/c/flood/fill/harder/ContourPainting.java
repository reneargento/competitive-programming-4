package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/03/23.
 */
public class ContourPainting {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final int MAX_ROW_LENGTH = 81;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            List<String> rows = new ArrayList<>();

            while (line != null && (line.isEmpty() || line.charAt(0) != '_')) {
                rows.add(line);
                line = FastReader.getLine();
            }

            char[][] grid = createGrid(rows);
            paintGrid(grid, neighborRows, neighborColumns);
            int[] lastColumnIndex = computeLastColumnIndex(grid);

            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column <= lastColumnIndex[row]; column++) {
                    outputWriter.print(grid[row][column]);
                }
                outputWriter.printLine();
            }
            outputWriter.printLine(line);
        }
        outputWriter.flush();
    }

    private static char[][] createGrid(List<String> rows) {
        char[][] grid = new char[rows.size()][MAX_ROW_LENGTH];
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                grid[row][column] = ' ';
            }
        }

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < rows.get(row).length(); column++) {
                grid[row][column] = rows.get(row).charAt(column);
            }
        }
        return grid;
    }

    private static void paintGrid(char[][] grid, int[] neighborRows, int[] neighborColumns) {
        Cell asteriskPosition = findAsteriskPosition(grid);
        boolean[][] visited = new boolean[grid.length][];
        for (int row = 0; row < grid.length; row++) {
            visited[row] = new boolean[grid[row].length];
        }

        floodFill(grid, visited, neighborRows, neighborColumns, asteriskPosition.row, asteriskPosition.column);

        if (grid[asteriskPosition.row][asteriskPosition.column] == '*') {
            grid[asteriskPosition.row][asteriskPosition.column] = ' ';
        }
    }

    private static Cell findAsteriskPosition(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                if (grid[row][column] == '*') {
                    return new Cell(row, column);
                }
            }
        }
        return null;
    }

    private static void floodFill(char[][] grid, boolean[][] visited, int[] neighborRows, int[] neighborColumns,
                                  int row, int column) {
        visited[row][column] = true;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)
                    && grid[neighborRow][neighborColumn] != '#'
                    && grid[neighborRow][neighborColumn] != '*'
                    && grid[neighborRow][neighborColumn] != ' ') {
                grid[row][column] = '#';
            }

            if (isValid(grid, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn]
                    && grid[neighborRow][neighborColumn] == ' ') {
                floodFill(grid, visited, neighborRows, neighborColumns, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[row].length;
    }

    private static int[] computeLastColumnIndex(char[][] grid) {
        int[] lastColumnIndex = new int[grid.length];
        Arrays.fill(lastColumnIndex, -1);

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] != ' ') {
                    lastColumnIndex[row] = column;
                }
            }
        }
        return lastColumnIndex;
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
