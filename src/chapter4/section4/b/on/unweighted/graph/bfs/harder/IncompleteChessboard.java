package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rene Argento on 15/12/23.
 */
public class IncompleteChessboard {

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
    }

    private static class State {
        Cell cell;
        int moves;

        public State(Cell cell, int moves) {
            this.cell = cell;
            this.moves = moves;
        }
    }

    private static final int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseID = 1;
        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int aCellRow = Integer.parseInt(data[0]) - 1;
            int aCellColumn = Integer.parseInt(data[1]) - 1;
            int bCellRow = Integer.parseInt(data[2]) - 1;
            int bCellColumn = Integer.parseInt(data[3]) - 1;
            int cCellRow = Integer.parseInt(data[4]) - 1;
            int cCellColumn = Integer.parseInt(data[5]) - 1;

            Cell aCell = new Cell(aCellRow, aCellColumn);
            Cell bCell = new Cell(bCellRow, bCellColumn);
            Cell cCell = new Cell(cCellRow, cCellColumn);

            int minimumMoves = computeMinimumMoves(aCell, bCell, cCell);
            outputWriter.printLine(String.format("Case %d: %d", caseID, minimumMoves));

            caseID++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumMoves(Cell aCell, Cell bCell, Cell cCell) {
        boolean[][] visited = new boolean[8][8];
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(aCell, 0));
        visited[aCell.row][aCell.column] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.equals(bCell)) {
                return state.moves;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]) {
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    if (!nextCell.equals(cCell)) {
                        queue.offer(new State(nextCell, state.moves + 1));
                        visited[neighborRow][neighborColumn] = true;
                    }
                }
            }
        }
        return -1;
    }

    private static boolean isValid(int row, int column) {
        return row >= 0 && row < 8 && column >= 0 && column < 8;
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
