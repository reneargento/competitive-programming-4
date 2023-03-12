package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/23.
 */
public class Lakes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int t = 0; t < tests; t++) {
            String[] data = FastReader.getLine().split(" ");
            int row = Integer.parseInt(data[0]) - 1;
            int column = Integer.parseInt(data[1]) - 1;

            List<String> rows = new ArrayList<>();
            String line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                rows.add(line);
                line = FastReader.getLine();
            }

            char[][] grid = new char[rows.size()][rows.get(0).length()];
            for (int i = 0; i < rows.size(); i++) {
                grid[i] = rows.get(i).toCharArray();
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            int area = computeArea(grid, neighborRows, neighborColumns, row, column);
            outputWriter.printLine(area);
        }
        outputWriter.flush();
    }

    private static int computeArea(char[][] grid, int[] neighborRows, int[] neighborColumns, int row, int column) {
        int area = 1;
        grid[row][column] = '1';

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(grid, neighborRow, neighborColumn) && grid[neighborRow][neighborColumn] == '0') {
                area += computeArea(grid, neighborRows, neighborColumns, neighborRow, neighborColumn);
            }
        }
        return area;
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
