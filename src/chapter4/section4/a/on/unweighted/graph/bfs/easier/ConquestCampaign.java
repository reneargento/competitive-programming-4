package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/12/23.
 */
public class ConquestCampaign {

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
        int daysPassed;

        public State(Cell cell, int daysPassed) {
            this.cell = cell;
            this.daysPassed = daysPassed;
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        Cell[] weakCells = new Cell[FastReader.nextInt()];

        for (int i = 0; i < weakCells.length; i++) {
            weakCells[i] = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);
        }

        int daysNeededForConquest = computeDaysNeededForConquest(weakCells, rows, columns);
        outputWriter.printLine(daysNeededForConquest);
        outputWriter.flush();
    }

    private static int computeDaysNeededForConquest(Cell[] weakCells, int rows, int columns) {
        int daysNeededForConquest = 1;
        boolean[][] visited = new boolean[rows][columns];
        Queue<State> queue = new LinkedList<>();

        for (Cell cell : weakCells) {
            visited[cell.row][cell.column] = true;
            queue.offer(new State(cell, 1));
        }

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(visited, neighborRow, neighborColumn) && !visited[neighborRow][neighborColumn]) {
                    visited[neighborRow][neighborColumn] = true;
                    Cell neighborCell = new Cell(neighborRow, neighborColumn);
                    int nextDays = state.daysPassed + 1;

                    queue.offer(new State(neighborCell, nextDays));
                    daysNeededForConquest = Math.max(daysNeededForConquest, nextDays);
                }
            }
        }
        return daysNeededForConquest;
    }

    private static boolean isValid(boolean[][] visited, int row, int column) {
        return row >= 0 && row < visited.length && column >= 0 && column < visited[0].length;
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
