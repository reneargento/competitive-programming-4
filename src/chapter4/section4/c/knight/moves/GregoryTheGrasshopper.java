package chapter4.section4.c.knight.moves;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * Created by Rene Argento on 25/12/23.
 */
public class GregoryTheGrasshopper {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static class State {
        Cell cell;
        int hops;

        public State(Cell cell, int hops) {
            this.cell = cell;
            this.hops = hops;
        }
    }

    private static final int[] neighborRows = { -2, -2, 1, -1, 1, -1, 2, 2 };
    private static final int[] neighborColumns = { -1, 1, -2, -2, 2, 2, -1, 1 };
    private static final int IMPOSSIBLE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);

            int gregoryRow = Integer.parseInt(data[2]) - 1;
            int gregoryColumn = Integer.parseInt(data[3]) - 1;
            Cell gregoryCell = new Cell(gregoryRow, gregoryColumn);
            int targetRow = Integer.parseInt(data[4]) - 1;
            int targetColumn = Integer.parseInt(data[5]) - 1;
            Cell targetCell = new Cell(targetRow, targetColumn);

            int minimumHops = computeMinimumHops(rows, columns, gregoryCell, targetCell);
            if (minimumHops != IMPOSSIBLE) {
                outputWriter.printLine(minimumHops);
            } else {
                outputWriter.printLine("impossible");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumHops(int rows, int columns, Cell gregoryCell, Cell targetCell) {
        boolean[][] visited = new boolean[rows][columns];
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(gregoryCell, 0));
        visited[gregoryCell.row][gregoryCell.column] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.equals(targetCell)) {
                return state.hops;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(rows, columns, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]) {
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    queue.offer(new State(nextCell, state.hops + 1));
                    visited[neighborRow][neighborColumn] = true;
                }
            }
        }
        return IMPOSSIBLE;
    }

    private static boolean isValid(int rows, int columns, int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
