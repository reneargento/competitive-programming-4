package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rene Argento on 15/12/23.
 */
public class Fire3 {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class State {
        Cell cell;
        int minutes;

        public State(Cell cell, int minutes) {
            this.cell = cell;
            this.minutes = minutes;
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };
    private static final char JOE_SYMBOL = 'J';
    private static final char FIRE_SYMBOL = 'F';
    private static final char EMPTY_SYMBOL = '.';

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int rows = inputReader.readInt();
        int columns = inputReader.readInt();
        char[][] map = new char[rows][columns];
        Cell startCell = null;

        Queue<State> fireCells = new LinkedList<>();

        for (int row = 0; row < map.length; row++) {
            String line = inputReader.next();
            for (int column = 0; column < line.length(); column++) {
                char symbol = line.charAt(column);
                map[row][column] = symbol;

                if (symbol == JOE_SYMBOL) {
                    startCell = new Cell(row, column);
                } else if (symbol == FIRE_SYMBOL) {
                    Cell cell = new Cell(row, column);
                    fireCells.add(new State(cell, -1));
                }
            }
        }
        int minimumSecondsToExit = computeMinimumSeconds(map, fireCells, startCell);
        if (minimumSecondsToExit != -1) {
            outputWriter.printLine(minimumSecondsToExit);
        } else {
            outputWriter.printLine("IMPOSSIBLE");
        }
        outputWriter.flush();
    }

    private static int computeMinimumSeconds(char[][] map, Queue<State> fireCells, Cell startCell) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        Queue<State> queue = new LinkedList<>();
        int time = -1;

        visited[startCell.row][startCell.column] = true;
        queue.offer(new State(startCell, 0));

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (state.minutes > time) {
                time = state.minutes;
                spreadFire(map, fireCells, time);
            }

            int nextSecond = state.minutes + 1;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)) {
                    if (!visited[neighborRow][neighborColumn]
                            && map[neighborRow][neighborColumn] == EMPTY_SYMBOL) {
                        visited[neighborRow][neighborColumn] = true;
                        Cell nextCell = new Cell(neighborRow, neighborColumn);
                        queue.offer(new State(nextCell, nextSecond));
                    }
                } else {
                    // Found exit
                    return nextSecond;
                }
            }
        }
        return -1;
    }

    private static void spreadFire(char[][] map, Queue<State> fireCells, int time) {
        while (!fireCells.isEmpty() && fireCells.peek().minutes < time) {
            State state = fireCells.poll();
            Cell cell = state.cell;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)
                        && (map[neighborRow][neighborColumn] == EMPTY_SYMBOL
                        || map[neighborRow][neighborColumn] == JOE_SYMBOL)) {
                    map[neighborRow][neighborColumn] = FIRE_SYMBOL;
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    fireCells.offer(new State(nextCell, state.minutes + 1));
                }
            }
        }
    }

    private static boolean isValid(char[][] map, int row, int column) {
        return row >= 0 && row < map.length && column >= 0 && column < map[0].length;
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

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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
