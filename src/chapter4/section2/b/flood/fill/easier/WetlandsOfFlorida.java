package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/03/23.
 */
public class WetlandsOfFlorida {

    private static class LakeArea {
        int value;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();
        int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            List<String> rows = new ArrayList<>();
            while (line != null && !line.isEmpty() && Character.isLetter(line.charAt(0))) {
                rows.add(line);
                line = FastReader.getLine();
            }

            char[][] grid = new char[rows.size()][rows.get(0).length()];
            for (int row = 0; row < rows.size(); row++) {
                grid[row] = rows.get(row).toCharArray();
            }

            LakeArea[][] lakeAreaSizes = computeLakeAreaSizes(grid, neighborRows, neighborColumns);
            if (t > 0) {
                outputWriter.printLine();
            }

            while (line != null && !line.isEmpty() && Character.isDigit(line.charAt(0))) {
                String[] data = line.split(" ");
                int row = Integer.parseInt(data[0]) - 1;
                int column = Integer.parseInt(data[1]) - 1;
                outputWriter.printLine(lakeAreaSizes[row][column].value);

                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static LakeArea[][] computeLakeAreaSizes(char[][] grid, int[] neighborRows, int[] neighborColumns) {
        LakeArea[][] lakeAreaSizes = new LakeArea[grid.length][grid[0].length];

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] == 'W') {
                    LakeArea lakeArea = new LakeArea();
                    floodFill(grid, neighborRows, neighborColumns, lakeAreaSizes, lakeArea, row, column);
                }
            }
        }
        return lakeAreaSizes;
    }

    private static void floodFill(char[][] grid, int[] neighborRows, int[] neighborColumns, LakeArea[][] lakeAreaSizes,
                                  LakeArea lakeArea, int row, int column) {
        lakeAreaSizes[row][column] = lakeArea;
        grid[row][column] = '*';
        lakeArea.value++;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(grid, neighborRow, neighborColumn)
                    && grid[neighborRow][neighborColumn] == 'W') {
                floodFill(grid, neighborRows, neighborColumns, lakeAreaSizes, lakeArea, neighborRow, neighborColumn);
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
