package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/03/23.
 */
public class GettingGoldKattis {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };
        Cell startPosition = null;

        int columns = FastReader.nextInt();
        int rows = FastReader.nextInt();
        char[][] grid = new char[rows][columns];
        boolean[][] visited = new boolean[rows][columns];
        for (int row = 0; row < grid.length; row++) {
            grid[row] = FastReader.getLine().toCharArray();

            for (int column = 0; column < grid[row].length; column++) {
                if (grid[row][column] == 'P') {
                    startPosition = new Cell(row, column);
                }
            }
        }

        int maxGold = computeMaxGold(grid, visited, neighborRows, neighborColumns, startPosition.row,
                startPosition.column);
        outputWriter.printLine(maxGold);
        outputWriter.flush();
    }

    private static int computeMaxGold(char[][] grid, boolean[][] visited, int[] neighborRows, int[] neighborColumns,
                                      int row, int column) {
        visited[row][column] = true;
        int gold = grid[row][column] == 'G' ? 1 : 0;
        boolean sensedDraft = false;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (grid[neighborRow][neighborColumn] == 'T') {
                sensedDraft = true;
                break;
            }
        }

        if (!sensedDraft) {
            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];
                if (grid[neighborRow][neighborColumn] != '#' && !visited[neighborRow][neighborColumn]) {
                    gold += computeMaxGold(grid, visited, neighborRows, neighborColumns, neighborRow, neighborColumn);
                }
            }
        }
        return gold;
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
