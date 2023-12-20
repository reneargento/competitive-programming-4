package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/12/23.
 */
public class EnchantedForest {

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
        int length;

        public State(Cell cell, int length) {
            this.cell = cell;
            this.length = length;
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
            int blockedPositions = FastReader.nextInt();
            boolean[][] blocked = new boolean[rows][columns];
            for (int i = 0; i < blockedPositions; i++) {
                int blockedRow = FastReader.nextInt() - 1;
                int blockedColumn = FastReader.nextInt() - 1;
                blocked[blockedRow][blockedColumn] = true;
            }

            int jigglypuffs = FastReader.nextInt();
            for (int i = 0; i < jigglypuffs; i++) {
                int row = FastReader.nextInt() - 1;
                int column = FastReader.nextInt() - 1;
                int loudness = FastReader.nextInt();
                expandBlockedArea(blocked, row, column, loudness);
            }

            int shortestLength = computeShortestLength(blocked);
            if (shortestLength != -1) {
                outputWriter.printLine(shortestLength);
            } else {
                outputWriter.printLine("Impossible.");
            }

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void expandBlockedArea(boolean[][] blocked, int row, int column, int loudness) {
        int startRow = Math.max(row - loudness, 0);
        int endRow = Math.min(row + loudness, blocked.length - 1);
        int startColumn = Math.max(column - loudness, 0);
        int endColumn = Math.min(column + loudness, blocked[0].length - 1);
        Cell jigglypuffCell = new Cell(row, column);

        for (int r = startRow; r <= endRow; r++) {
            for (int c = startColumn; c <= endColumn; c++) {
                Cell cell = new Cell(r, c);
                if (distanceBetweenCoordinatesNoSqrt(cell, jigglypuffCell) <= loudness * loudness) {
                    blocked[r][c] = true;
                }
            }
        }
    }

    private static int computeShortestLength(boolean[][] blocked) {
        Queue<State> queue = new LinkedList<>();
        boolean[][] visited = new boolean[blocked.length][blocked[0].length];
        Cell startCell = new Cell(0, 0);
        queue.offer(new State(startCell, 0));
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.row == blocked.length - 1 && cell.column == blocked[0].length - 1) {
                return state.length;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(blocked, neighborRow, neighborColumn)
                        && !blocked[neighborRow][neighborColumn]
                        && !visited[neighborRow][neighborColumn]) {
                    visited[neighborRow][neighborColumn] = true;
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    queue.offer(new State(nextCell, state.length + 1));
                }
            }
        }
        return -1;
    }

    public static double distanceBetweenCoordinatesNoSqrt(Cell coordinate1, Cell coordinate2) {
        return (coordinate1.row - coordinate2.row) * (coordinate1.row - coordinate2.row)
                + (coordinate1.column - coordinate2.column) * (coordinate1.column - coordinate2.column);
    }

    private static boolean isValid(boolean[][] blocked, int row, int column) {
        return row >= 0 && row < blocked.length && column >= 0 && column < blocked[0].length;
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
