package chapter4.section6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/05/24.
 */
public class Checkers {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final long MOD = 1000007;
    private static final int[] neighborRows = { -1, -1 };
    private static final int[] neighborColumns = { -1, 1 };
    private static final char EMPTY_CELL = '.';
    private static final char WHITE_CELL = 'W';
    private static final char BLACK_CELL = 'B';

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.readInt();

        for (int t = 1; t <= tests; t++) {
            int boardSize = inputReader.readInt();
            char[][] board = new char[boardSize][boardSize];
            Cell startCell = null;

            for (int row = 0; row < board.length; row++) {
                String columns = inputReader.next();
                board[row] = columns.toCharArray();

                for (int column = 0; column < board[row].length; column++) {
                    if (board[row][column] == WHITE_CELL) {
                        startCell = new Cell(row, column);
                        break;
                    }
                }
            }

            long numberOfPaths = countPaths(board, startCell);
            outputWriter.printLine(String.format("Case %d: %d", t, numberOfPaths));
        }
        outputWriter.flush();
    }

    private static long countPaths(char[][] board, Cell startCell) {
        if (board.length == 1) {
            return 1;
        }
        long numberOfPaths = 0;
        long[][] allPathsCount = countNumberOfPaths(board, startCell.row, startCell.column);

        int topRow = 0;
        for (int column = 0; column < board[0].length; column++) {
            numberOfPaths += allPathsCount[topRow][column];
            numberOfPaths %= MOD;
        }
        return numberOfPaths;
    }

    private static long[][] countNumberOfPaths(char[][] board, int startRow, int startColumn) {
        long[][] numberOfPaths = new long[board.length][board[0].length];
        numberOfPaths[startRow][startColumn] = 1;

        for (int row = startRow; row >= 0; row--) {
            for (int column = 0; column < board[0].length; column++) {
                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];

                    if (isValid(board, neighborRow, neighborColumn)) {
                        if (board[neighborRow][neighborColumn] == EMPTY_CELL) {
                            numberOfPaths[neighborRow][neighborColumn] += numberOfPaths[row][column];
                            numberOfPaths[neighborRow][neighborColumn] %= MOD;
                        } else if (board[neighborRow][neighborColumn] == BLACK_CELL) {
                            int neighborNeighborRow = neighborRow + neighborRows[i];
                            int neighborNeighborColumn = neighborColumn + neighborColumns[i];
                            if (isValid(board, neighborNeighborRow, neighborNeighborColumn)
                                    && board[neighborNeighborRow][neighborNeighborColumn] != BLACK_CELL) {
                                numberOfPaths[neighborNeighborRow][neighborNeighborColumn] += numberOfPaths[row][column];
                                numberOfPaths[neighborNeighborRow][neighborNeighborColumn] %= MOD;
                            }
                        }
                    }
                }
            }
        }
        return numberOfPaths;
    }

    private static boolean isValid(char[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
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
