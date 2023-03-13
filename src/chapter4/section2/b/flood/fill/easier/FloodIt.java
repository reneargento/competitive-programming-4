package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/23.
 */
public class FloodIt {

    private static class Result {
        int movesNeeded;
        int[] timesColorChosen;

        public Result(int movesNeeded, int[] timesColorChosen) {
            this.movesNeeded = movesNeeded;
            this.timesColorChosen = timesColorChosen;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int t = 0; t < tests; t++) {
            int dimension = FastReader.nextInt();
            char[][] grid = new char[dimension][dimension];
            for (int row = 0; row < grid.length; row++) {
                grid[row] = FastReader.next().toCharArray();
            }

            Result result = playGame(grid, neighborRows, neighborColumns);
            outputWriter.printLine(result.movesNeeded);
            outputWriter.print(result.timesColorChosen[0]);
            for (int i = 1; i < result.timesColorChosen.length; i++) {
                outputWriter.print(" " + result.timesColorChosen[i]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static Result playGame(char[][] grid, int[] neighborRows, int[] neighborColumns) {
        int movesNeeded = 0;
        int[] timesColorChosen = new int[6];
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        visited[0][0] = true;
        countTiles(grid, visited, neighborRows, neighborColumns, grid[0][0], 0, 0);

        while (!isGameFinished(grid)) {
            char originalSource = grid[0][0];
            char nextNumber = chooseBestNumber(grid, visited, neighborRows, neighborColumns);
            if (nextNumber != originalSource) {
                movesNeeded++;
                int index = Character.getNumericValue(nextNumber) - 1;
                timesColorChosen[index]++;
            }
        }
        return new Result(movesNeeded, timesColorChosen);
    }

    private static boolean isGameFinished(char[][] grid) {
        char number = grid[0][0];
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] != number) {
                    return false;
                }
            }
        }
        return true;
    }

    private static char chooseBestNumber(char[][] grid, boolean[][] visited, int[] neighborRows, int[] neighborColumns) {
        char bestNumber = '0';
        int highestTilesCount = 0;
        boolean[][] newVisited = null;
        char[][] newGrid = null;

        for (char number = '1'; number <= '6'; number++) {
            boolean[][] visitedWithNumber = new boolean[visited.length][visited[0].length];
            char[][] gridWithNumber = copyAndUpdateGrid(grid, visited, number);

            int tiles = countTiles(gridWithNumber, visitedWithNumber, neighborRows, neighborColumns, number, 0, 0);
            if (tiles > highestTilesCount) {
                highestTilesCount = tiles;
                bestNumber = number;
                newVisited = visitedWithNumber;
                newGrid = gridWithNumber;
            }
        }

        copyArray(newVisited, visited);
        copyArray(newGrid, grid);
        return bestNumber;
    }

    private static char[][] copyAndUpdateGrid(char[][] grid, boolean[][] visited, char number) {
        char[][] gridWithNumber = new char[grid.length][grid[0].length];

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (visited[row][column]) {
                    gridWithNumber[row][column] = number;
                } else {
                    gridWithNumber[row][column] = grid[row][column];
                }
            }
        }
        return gridWithNumber;
    }

    private static int countTiles(char[][] grid, boolean[][] visited, int[] neighborRows, int[] neighborColumns,
                                  char number, int row, int column) {
        int tiles = 1;
        visited[row][column] = true;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(grid, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn]
                    && grid[neighborRow][neighborColumn] == number) {
                tiles += countTiles(grid, visited, neighborRows, neighborColumns, number, neighborRow, neighborColumn);
            }
        }
        return tiles;
    }

    private static void copyArray(boolean[][] array1, boolean[][] array2) {
        for (int i = 0; i < array1.length; i++) {
            System.arraycopy(array1[i], 0, array2[i], 0, array1[i].length);
        }
    }

    private static void copyArray(char[][] array1, char[][] array2) {
        for (int i = 0; i < array1.length; i++) {
            System.arraycopy(array1[i], 0, array2[i], 0, array1[i].length);
        }
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
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
