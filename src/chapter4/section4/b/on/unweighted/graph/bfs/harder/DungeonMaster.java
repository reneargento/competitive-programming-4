package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 17/12/23.
 */
public class DungeonMaster {

    private static class Cell {
        int level;
        int row;
        int column;

        public Cell(int level, int row, int column) {
            this.level = level;
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return level == cell.level && row == cell.row && column == cell.column;
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

    private static final int[] neighborLevels = { 0, 0, 0, 0, -1, 1 };
    private static final int[] neighborRows = { -1, 0, 0, 1, 0, 0 };
    private static final int[] neighborColumns = { 0, -1, 1, 0, 0, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int levels = FastReader.nextInt();
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        while (levels != 0 || rows != 0 || columns != 0) {
            char[][][] dungeon = new char[levels][rows][columns];
            Cell startCell = null;
            Cell exitCell = null;

            for (int level = 0; level < levels; level++) {
                for (int row = 0; row < rows; row++) {
                    String cells = FastReader.next();
                    for (int column = 0; column < cells.length(); column++) {
                        char cell = cells.charAt(column);
                        dungeon[level][row][column] = cell;
                        if (cell == 'S') {
                            startCell = new Cell(level, row, column);
                        } else if (cell == 'E') {
                            exitCell = new Cell(level, row, column);
                        }
                    }
                }
            }

            int minimumTimeToEscape = computeMinimumTimeToEscape(dungeon, startCell, exitCell);
            if (minimumTimeToEscape != -1) {
                outputWriter.printLine(String.format("Escaped in %d minute(s).", minimumTimeToEscape));
            } else {
                outputWriter.printLine("Trapped!");
            }

            levels = FastReader.nextInt();
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumTimeToEscape(char[][][] dungeon, Cell startCell, Cell exitCell) {
        Queue<State> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[dungeon.length][dungeon[0].length][dungeon[0][0].length];

        visited[startCell.level][startCell.row][startCell.column] = true;
        queue.offer(new State(startCell, 0));

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.equals(exitCell)) {
                return state.minutes;
            }

            for (int i = 0; i < neighborLevels.length; i++) {
                int neighborLevel = cell.level + neighborLevels[i];
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(dungeon, neighborLevel, neighborRow, neighborColumn)
                        && dungeon[neighborLevel][neighborRow][neighborColumn] != '#'
                        && !visited[neighborLevel][neighborRow][neighborColumn]) {
                    visited[neighborLevel][neighborRow][neighborColumn] = true;
                    Cell nextCell = new Cell(neighborLevel, neighborRow, neighborColumn);
                    queue.offer(new State(nextCell, state.minutes + 1));
                }
            }
        }
        return -1;
    }

    private static boolean isValid(char[][][] dungeon, int level, int row, int column) {
        return level >= 0 && level < dungeon.length
                && row >= 0 && row < dungeon[0].length
                && column >= 0 && column < dungeon[0][0].length;
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
