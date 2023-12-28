package chapter4.section4.c.knight.moves;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rene Argento on 24/12/23.
 */
public class KnightMoves {

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
        int moves;

        public State(Cell cell, int moves) {
            this.cell = cell;
            this.moves = moves;
        }
    }

    private static final int[] neighborRows = { -2, -2, 1, -1, 1, -1, 2, 2 };
    private static final int[] neighborColumns = { -1, 1, -2, -2, 2, 2, -1, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int column1 = data[0].charAt(0) - 'a';
            int column2 = data[1].charAt(0) - 'a';
            int row1 = Character.getNumericValue(data[0].charAt(1)) - 1;
            int row2 = Character.getNumericValue(data[1].charAt(1)) - 1;

            Cell startCell = new Cell(row1, column1);
            Cell endCell = new Cell(row2, column2);

            int minimumMoves = computeMinimumMoves(startCell, endCell);
            outputWriter.printLine(String.format("To get from %s to %s takes %d knight moves.",
                    data[0], data[1], minimumMoves));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumMoves(Cell startCell, Cell endCell) {
        boolean[][] visited = new boolean[8][8];
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(startCell, 0));
        visited[startCell.row][startCell.column] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.row == endCell.row && cell.column == endCell.column) {
                return state.moves;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(visited, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]) {
                    visited[neighborRow][neighborColumn] = true;
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    queue.offer(new State(nextCell, state.moves + 1));
                }
            }
        }
        return -1;
    }

    private static boolean isValid(boolean[][] visited, int row, int column) {
        return row >= 0 && row < visited.length && column >= 0 && column < visited[0].length;
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
