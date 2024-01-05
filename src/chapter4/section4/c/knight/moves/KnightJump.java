package chapter4.section4.c.knight.moves;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/23.
 */
public class KnightJump {

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
        int steps;

        public State(Cell cell, int steps) {
            this.cell = cell;
            this.steps = steps;
        }
    }

    private static final int[] neighborRows = { -2, -2, 1, -1, 1, -1, 2, 2 };
    private static final int[] neighborColumns = { -1, 1, -2, -2, 2, 2, -1, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = FastReader.nextInt();
        char[][] chessboard = new char[dimension][dimension];
        Cell startCell = null;

        for (int row = 0; row < chessboard.length; row++) {
            String columns = FastReader.getLine();
            for (int column = 0; column < columns.length(); column++) {
                char value = columns.charAt(column);
                if (value == 'K') {
                    startCell = new Cell(row, column);
                }
                chessboard[row][column] = value;
            }
        }
        int minimumNumberOfSteps = computeMinimumNumberOfSteps(chessboard, startCell);
        outputWriter.printLine(minimumNumberOfSteps);
        outputWriter.flush();
    }

    private static int computeMinimumNumberOfSteps(char[][] chessboard, Cell startCell) {
        boolean[][] visited = new boolean[chessboard.length][chessboard[0].length];
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(startCell, 0));
        visited[startCell.row][startCell.column] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.row == 0 && cell.column == 0) {
                return state.steps;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(chessboard, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]
                        && chessboard[neighborRow][neighborColumn] != '#') {
                    visited[neighborRow][neighborColumn] = true;
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    queue.offer(new State(nextCell, state.steps + 1));
                }
            }
        }
        return -1;
    }

    private static boolean isValid(char[][] chessboard, int row, int column) {
        return row >= 0 && row < chessboard.length && column >= 0 && column < chessboard[0].length;
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
