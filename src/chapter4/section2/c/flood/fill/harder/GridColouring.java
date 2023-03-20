package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 17/03/23.
 */
public class GridColouring {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        String line = FastReader.getLine();
        while (line != null) {
            List<String> rows = new ArrayList<>();

            while (line.isEmpty() || line.charAt(0) != '_') {
                rows.add(line);
                line = FastReader.getLine();
            }

            char[][] grid = new char[rows.size()][];
            for (int row = 0; row < grid.length; row++) {
                grid[row] = rows.get(row).toCharArray();
            }

            paintGrid(grid, neighborRows, neighborColumns);

            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[row].length; column++) {
                    outputWriter.print(grid[row][column]);
                }
                outputWriter.printLine();
            }
            outputWriter.printLine(line);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void paintGrid(char[][] grid, int[] neighborRows, int[] neighborColumns) {
        boolean[][] visited = createVisitedArray(grid);
        char contourCharacter = '_';

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                if (grid[row][column] != ' ') {
                    if (!visited[row][column] && grid[row][column] != contourCharacter) {
                        if (contourCharacter == '_') {
                            contourCharacter = grid[row][column];
                        }

                        if (grid[row][column] != contourCharacter) {
                            paintGrid(grid, visited, neighborRows, neighborColumns, grid[row][column], row, column);
                        }
                    }
                }
            }
        }
    }

    private static boolean[][] createVisitedArray(char[][] grid) {
        boolean[][] visited = new boolean[grid.length][];
        for (int row = 0; row < grid.length; row++) {
            visited[row] = new boolean[grid[row].length];
        }
        return visited;
    }

    private static void paintGrid(char[][] grid, boolean[][] visited, int[] neighborRows, int[] neighborColumns,
                                  char markingCharacter, int row, int column) {
        visited[row][column] = true;
        grid[row][column] = markingCharacter;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)
                    && grid[neighborRow][neighborColumn] == ' ') {
                paintGrid(grid, visited, neighborRows, neighborColumns, markingCharacter, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[row].length;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
