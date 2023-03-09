package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/03/23.
 */
public class OilDeposits {

    private static final char OIL_SOCKET = '@';

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

        while (rows != 0) {
            char[][] grid = new char[rows][columns];
            for (int row = 0; row < rows; row++) {
                grid[row] = FastReader.next().toCharArray();
            }
            int oilDeposits = countOilDeposits(grid, neighborRows, neighborColumns);
            outputWriter.printLine(oilDeposits);

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int countOilDeposits(char[][] grid, int[] neighborRows, int[] neighborColumns) {
        int oilDeposits = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (!visited[row][column] && grid[row][column] == OIL_SOCKET) {
                    floodFill(grid, visited, neighborRows, neighborColumns, row, column);
                    oilDeposits++;
                }
            }
        }
        return oilDeposits;
    }

    private static void floodFill(char[][] grid, boolean[][] visited, int[] neighborRows, int[] neighborColumns,
                                  int row, int column) {
        visited[row][column] = true;
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn]
                    && grid[neighborRow][neighborColumn] == OIL_SOCKET) {
                floodFill(grid, visited, neighborRows, neighborColumns, neighborRow, neighborColumn);
            }
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
