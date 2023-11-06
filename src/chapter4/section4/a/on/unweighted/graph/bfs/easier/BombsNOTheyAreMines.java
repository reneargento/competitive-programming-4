package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/10/23.
 */
public class BombsNOTheyAreMines {

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
        int time;

        public State(Cell cell, int time) {
            this.cell = cell;
            this.time = time;
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        while (rows != 0 || columns != 0) {
            boolean[][] cellsToAvoid = new boolean[rows][columns];

            int rowsWithBombs = FastReader.nextInt();
            for (int i = 0; i < rowsWithBombs; i++) {
                String[] data = FastReader.getLine().split(" ");
                int row = Integer.parseInt(data[0]);

                for (int c = 2; c < data.length; c++) {
                    int column = Integer.parseInt(data[c]);
                    cellsToAvoid[row][column] = true;
                }
            }

            Cell start = new Cell(FastReader.nextInt(), FastReader.nextInt());
            Cell end = new Cell(FastReader.nextInt(), FastReader.nextInt());

            int time = computeTime(cellsToAvoid, rows, columns, start, end);
            outputWriter.printLine(time);

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeTime(boolean[][] cellsToAvoid, int rows, int columns, Cell start, Cell end) {
        State state = new State(start, 0);
        Queue<State> queue = new LinkedList<>();
        queue.offer(state);

        cellsToAvoid[start.row][start.column] = true;

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.cell.equals(end)) {
                return currentState.time;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int nextRow = currentState.cell.row + neighborRows[i];
                int nextColumn = currentState.cell.column + neighborColumns[i];
                if (isValid(rows, columns, nextRow, nextColumn)) {
                    Cell nextCell = new Cell(nextRow, nextColumn);
                    if (cellsToAvoid[nextCell.row][nextCell.column]) {
                        continue;
                    }
                    cellsToAvoid[nextCell.row][nextCell.column] = true;
                    queue.offer(new State(new Cell(nextRow, nextColumn), currentState.time + 1));
                }
            }
        }
        return -1;
    }

    private static boolean isValid(int rows, int columns, int rowNumber, int columnNumber) {
        return rowNumber >= 0 && rowNumber < rows && columnNumber >= 0 && columnNumber < columns;
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