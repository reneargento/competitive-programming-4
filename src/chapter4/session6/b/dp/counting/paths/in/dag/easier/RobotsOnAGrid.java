package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rene Argento on 18/05/24.
 */
public class RobotsOnAGrid {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final long GAME_LIES = -1;
    private static final long INCONCEIVABLE = -2;
    private static final char EMPTY = '.';
    private static final int MOD = 2147483647;
    private static final int[] neighborRowsSimple = { 0, 1 };
    private static final int[] neighborColumnsSimple = { 1, 0 };
    private static final int[] neighborRowsComplete = { 0, 1, 0, -1 };
    private static final int[] neighborColumnsComplete = { 1, 0, -1, 0 };

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = inputReader.readInt();

        char[][] grid = new char[dimension][dimension];
        for (int row = 0; row < grid.length; row++) {
            String columns = inputReader.readString();
            grid[row] = columns.toCharArray();
        }

        long result = walkRobot(grid);

        if (result == GAME_LIES) {
            outputWriter.printLine("THE GAME IS A LIE");
        } else if (result == INCONCEIVABLE) {
            outputWriter.printLine("INCONCEIVABLE");
        } else {
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static long walkRobot(char[][] grid) {
        long[][] paths = countPaths(grid);
        int lastRow = grid.length - 1;
        int lastColumn = grid[0].length - 1;

        if (paths[lastRow][lastColumn] != 0) {
            return paths[lastRow][lastColumn] % MOD;
        }

        if (breadthFirstSearch(grid)) {
            return GAME_LIES;
        }
        return INCONCEIVABLE;
    }

    private static long[][] countPaths(char[][] grid) {
        long[][] paths = new long[grid.length][grid[0].length];
        paths[0][0] = 1;

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                for (int i = 0; i < neighborRowsSimple.length; i++) {
                    int neighborRow = row + neighborRowsSimple[i];
                    int neighborColumn = column + neighborColumnsSimple[i];

                    if (isValid(grid, neighborRow, neighborColumn)
                            && grid[neighborRow][neighborColumn] == EMPTY) {
                        paths[neighborRow][neighborColumn] += paths[row][column];
                        paths[neighborRow][neighborColumn] %= MOD;
                    }
                }
            }
        }
        return paths;
    }

    private static boolean breadthFirstSearch(char[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(0, 0));

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            int row = cell.row;
            int column = cell.column;

            if (row == grid.length - 1 && column == grid[0].length - 1) {
                return true;
            }

            for (int i = 0; i < neighborRowsComplete.length; i++) {
                int neighborRow = row + neighborRowsComplete[i];
                int neighborColumn = column + neighborColumnsComplete[i];

                if (isValid(grid, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]
                        && grid[neighborRow][neighborColumn] == EMPTY) {
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    visited[neighborRow][neighborColumn] = true;
                    queue.offer(nextCell);
                }
            }
        }
        return false;
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public String readLine() {
            int c = read();
            if (c == -1) {
                return null;
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == -1;
        }

        public String next() {
            return readString();
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
