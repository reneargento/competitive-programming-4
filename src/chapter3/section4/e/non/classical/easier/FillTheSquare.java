package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/07/22.
 */
public class FillTheSquare {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int dimension = FastReader.nextInt();
            char[][] grid = new char[dimension][dimension];
            for (int i = 0; i < grid.length; i++) {
                grid[i] = FastReader.next().toCharArray();
            }
            fillGrid(grid);

            outputWriter.printLine(String.format("Case %d:", t));
            for (char[] row : grid) {
                for (int column = 0; column < grid[0].length; column++) {
                    outputWriter.print(row[column]);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static void fillGrid(char[][] grid) {
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] == '.') {
                    grid[row][column] = getCharacter(grid, row, column, neighborRows, neighborColumns);
                }
            }
        }
    }

    private static char getCharacter(char[][] grid, int row, int column, int[] neighborRows, int[] neighborColumns) {
        boolean[] neighborCharacters = new boolean[4];

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)) {
                switch (grid[neighborRow][neighborColumn]) {
                    case 'A': neighborCharacters[0] = true; break;
                    case 'B': neighborCharacters[1] = true; break;
                    case 'C': neighborCharacters[2] = true; break;
                    case 'D': neighborCharacters[3] = true;
                }
            }
        }

        for (int i = 0; i < neighborCharacters.length; i++) {
            if (!neighborCharacters[i]) {
                return (char) ('A' + i);
            }
        }
        return 'E';
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
