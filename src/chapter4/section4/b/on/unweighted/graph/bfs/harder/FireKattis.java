package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/12/23.
 */
public class FireKattis {

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
        int seconds;

        public State(Cell cell, int seconds) {
            this.cell = cell;
            this.seconds = seconds;
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };
    private static final char START_SYMBOL = '@';
    private static final char FIRE_SYMBOL = '*';
    private static final char EMPTY_SYMBOL = '.';

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int width = FastReader.nextInt();
            int height = FastReader.nextInt();
            char[][] map = new char[height][width];
            Cell startCell = null;

            Queue<State> fireCells = new LinkedList<>();

            for (int row = 0; row < map.length; row++) {
                String columns = FastReader.next();
                for (int column = 0; column < columns.length(); column++) {
                    char symbol = columns.charAt(column);
                    map[row][column] = symbol;

                    if (symbol == START_SYMBOL) {
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

            if (state.seconds > time) {
                time = state.seconds;
                spreadFire(map, fireCells, time);
            }

            int nextSecond = state.seconds + 1;

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
        while (!fireCells.isEmpty() && fireCells.peek().seconds < time) {
            State state = fireCells.poll();
            Cell cell = state.cell;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)
                        && (map[neighborRow][neighborColumn] == EMPTY_SYMBOL
                             || map[neighborRow][neighborColumn] == START_SYMBOL)) {
                    map[neighborRow][neighborColumn] = FIRE_SYMBOL;
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    fireCells.offer(new State(nextCell, state.seconds + 1));
                }
            }
        }
    }

    private static boolean isValid(char[][] map, int row, int column) {
        return row >= 0 && row < map.length && column >= 0 && column < map[0].length;
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
