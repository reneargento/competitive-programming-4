package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/03/23.
 */
public class YGame {

    private static class SidesReached {
        boolean reachedRightSide;
        boolean reachedBottom;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int order = FastReader.nextInt();
        int blackPositions = FastReader.nextInt();
        int[] neighborRows = { -1, -1, 0, 0, 1, 1 };
        int[] neighborColumns = { -1, 0, -1, 1, 0, 1 };

        while (order != 0 || blackPositions != 0) {
            boolean[][] grid = new boolean[order + 1][order + 1];

            for (int i = 0; i < blackPositions; i++) {
                int column = FastReader.nextInt();
                FastReader.nextInt(); // ignored
                int row = order - FastReader.nextInt();
                grid[row][column] = true;
            }
            boolean bennyWon = canBennyWin(grid, neighborRows, neighborColumns);
            outputWriter.printLine(bennyWon ? "Benny" : "Willy");

            order = FastReader.nextInt();
            blackPositions = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean canBennyWin(boolean[][] grid, int[] neighborRows, int[] neighborColumns) {
        boolean[][] visited = new boolean[grid.length][grid.length];

        for (int row = 0; row < grid.length; row++) {
            if (!visited[row][0] && grid[row][0]) {
                boolean canBennyWin = depthFirstSearch(grid, neighborRows, neighborColumns, visited, new SidesReached(),
                        row, 0);
                if (canBennyWin) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean depthFirstSearch(boolean[][] grid, int[] neighborRows, int[] neighborColumns,
                                            boolean[][] visited, SidesReached sidesReached, int row, int column) {
        visited[row][column] = true;

        if (column == row) {
            sidesReached.reachedRightSide = true;
        }
        if (row == grid.length - 1) {
            sidesReached.reachedBottom = true;
        }
        if (sidesReached.reachedRightSide && sidesReached.reachedBottom) {
            return true;
        }

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn]
                    && grid[neighborRow][neighborColumn]) {
                boolean canBennyWin = depthFirstSearch(grid, neighborRows, neighborColumns, visited, sidesReached,
                        neighborRow, neighborColumn);
                if (canBennyWin) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValid(boolean[][] grid, int row, int column) {
        int numberOfColumns = row + 1;
        return row >= 0 && row < grid.length && column >= 0 && column <= numberOfColumns;
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
