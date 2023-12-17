package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/12/23.
 */
public class CrazyKing {

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

    private static final int[] enemyNeighborRows = { 0, -1, -2, -2, -1, 1, 2, 2, 1 };
    private static final int[] enemyNeighborColumns = { 0, -2, -1, 1, 2, -2, -1, 1, 2 };
    private static final int[] kingNeighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] kingNeighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();

            Cell kingdomA = null;
            Cell kingdomB = null;

            char[][] forest = new char[rows][columns];
            for (int row = 0; row < rows; row++) {
                String data = FastReader.next();

                for (int column = 0; column < data.length(); column++) {
                    char symbol = data.charAt(column);
                    switch (symbol) {
                        case 'A': {
                            kingdomA = new Cell(row, column);
                            forest[row][column] = symbol;
                        } break;
                        case 'B': {
                            kingdomB = new Cell(row, column);
                            forest[row][column] = symbol;
                        } break;
                        case 'Z': addDangerousCells(forest, row, column); break;
                        default: {
                            if (forest[row][column] != 'Z') {
                                forest[row][column] = symbol;
                            }
                        }
                    }
                }
            }

            int shortestPathLength = getShortestPathLength(forest, kingdomA, kingdomB);
            if (shortestPathLength == -1) {
                outputWriter.printLine("King Peter, you can't go now!");
            } else {
                outputWriter.printLine(String.format("Minimal possible length of a trip is %d", shortestPathLength));
            }
        }
        outputWriter.flush();
    }

    private static int getShortestPathLength(char[][] forest, Cell kingdomA, Cell kingdomB) {
        Queue<State> queue = new LinkedList<>();
        boolean[][] visited = new boolean[forest.length][forest[0].length];

        queue.offer(new State(kingdomA, 0));
        visited[kingdomA.row][kingdomA.column] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.row == kingdomB.row && cell.column == kingdomB.column) {
                return state.length;
            }

            for (int i = 0; i < kingNeighborRows.length; i++) {
                int neighborRow = cell.row + kingNeighborRows[i];
                int neighborColumn = cell.column + kingNeighborColumns[i];

                if (isValid(forest, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]
                        && forest[neighborRow][neighborColumn] != 'Z') {
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    queue.offer(new State(nextCell, state.length + 1));
                    visited[neighborRow][neighborColumn] = true;
                }
            }
        }
        return -1;
    }

    private static void addDangerousCells(char[][] forest, int row, int column) {
        for (int i = 0; i < enemyNeighborRows.length; i++) {
            int neighborRow = row + enemyNeighborRows[i];
            int neighborColumn = column + enemyNeighborColumns[i];

            if (isValid(forest, neighborRow, neighborColumn)
                    && forest[neighborRow][neighborColumn] != 'A'
                    && forest[neighborRow][neighborColumn] != 'B') {
                forest[neighborRow][neighborColumn] = 'Z';
            }
        }
    }

    private static boolean isValid(char[][] forest, int row, int column) {
        return row >= 0 && row < forest.length && column >= 0 && column < forest[0].length;
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
