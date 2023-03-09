package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/03/23.
 */
public class CountingCellsInABlob {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();
        int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int t = 0; t < tests; t++) {
            String row = FastReader.getLine();
            List<String> rows = new ArrayList<>();

            while (row != null && !row.isEmpty()) {
                rows.add(row);
                row = FastReader.getLine();
            }

            char[][] grid = new char[rows.size()][rows.get(0).length()];
            for (int i = 0; i < grid.length; i++) {
                String currentRow = rows.get(i);
                grid[i] = currentRow.toCharArray();
            }

            int largestBlobSize = computeLargestBlobSize(grid, neighborRows, neighborColumns);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(largestBlobSize);
        }
        outputWriter.flush();
    }

    private static int computeLargestBlobSize(char[][] grid, int[] neighborRows, int[] neighborColumns) {
        int largestBlobSize = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] == '1') {
                    int blobSize = computeBlobSize(grid, neighborRows, neighborColumns, row, column);
                    largestBlobSize = Math.max(largestBlobSize, blobSize);
                }
            }
        }
        return largestBlobSize;
    }

    private static int computeBlobSize(char[][] grid, int[] neighborRows, int[] neighborColumns, int row, int column) {
        int blobSize = 1;
        grid[row][column] = '0';

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(grid, neighborRow, neighborColumn) && grid[neighborRow][neighborColumn] == '1') {
                blobSize += computeBlobSize(grid, neighborRows, neighborColumns, neighborRow, neighborColumn);
            }
        }
        return blobSize;
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
