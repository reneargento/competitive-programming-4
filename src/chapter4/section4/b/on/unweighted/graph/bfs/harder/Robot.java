package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/12/23.
 */
public class Robot {

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
        int orientation;

        public State(Cell cell, int seconds, int orientation) {
            this.cell = cell;
            this.seconds = seconds;
            this.orientation = orientation;
        }
    }

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;
    private static final int[] turnCommands = { -1, 1, 2, 1 };
    private static final int INFINITE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        while (rows != 0 || columns != 0) {
            int[][] store = new int[rows][columns];
            for (int row = 0; row < store.length; row++) {
                for (int column = 0; column < store[0].length; column++) {
                    store[row][column] = FastReader.nextInt();
                }
            }

            Cell startCell = new Cell(FastReader.nextInt(), FastReader.nextInt());
            Cell destinationCell = new Cell(FastReader.nextInt(), FastReader.nextInt());
            int startOrientation = getOrientation(FastReader.next());

            int time = computeTime(store, startCell, destinationCell, startOrientation);
            outputWriter.printLine(time);

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeTime(int[][] store, Cell startCell, Cell destinationCell, int startOrientation) {
        if (!isValid(store, startCell.row, startCell.column)) {
            return -1;
        }
        int shortestTimeDestination = INFINITE;
        // [row][column][orientation]
        int[][][] shortestTimes = new int[store.length][store[0].length][4];
        for (int[][] times : shortestTimes) {
            for (int[] timeValues : times) {
                Arrays.fill(timeValues, INFINITE);
            }
        }
        shortestTimes[startCell.row][startCell.column][startOrientation] = 0;

        Deque<State> deque = new ArrayDeque<>();
        deque.offerFirst(new State(startCell, 0, startOrientation));

        while (!deque.isEmpty()) {
            State state = deque.pollFirst();
            Cell cell = state.cell;

            if (cell.row == destinationCell.row && cell.column == destinationCell.column) {
                shortestTimeDestination = Math.min(shortestTimeDestination, state.seconds);
            }

            // GO command
            for (int meters = 1; meters <= 3; meters++) {
                int nextRow = cell.row;
                int nextColumn = cell.column;
                boolean isValidPath = true;

                for (int i = 1; i <= meters; i++) {
                    switch (state.orientation) {
                        case NORTH: nextRow--; break;
                        case EAST: nextColumn++; break;
                        case SOUTH: nextRow++; break;
                        default: nextColumn--;
                    }

                    if (!isValid(store, nextRow, nextColumn)) {
                        isValidPath = false;
                        break;
                    }
                }

                if (!isValidPath) {
                    break;
                }

                if (state.seconds + 1 < shortestTimes[nextRow][nextColumn][state.orientation]) {
                    Cell nextCell = new Cell(nextRow, nextColumn);
                    deque.offerFirst(new State(nextCell, state.seconds + 1, state.orientation));
                    shortestTimes[nextRow][nextColumn][state.orientation] = state.seconds + 1;
                }
            }

            // TURN command
            for (int i = 1; i <= 3; i++) {
                int newOrientation = (state.orientation + i) % 4;
                int newSeconds = state.seconds + turnCommands[i];

                if (newSeconds < shortestTimes[cell.row][cell.column][newOrientation]) {
                    shortestTimes[cell.row][cell.column][newOrientation] = newSeconds;
                    deque.offerLast(new State(cell, newSeconds, newOrientation));
                }
            }
        }

        if (shortestTimeDestination == INFINITE) {
            return -1;
        }
        return shortestTimeDestination;
    }

    private static int getOrientation(String orientation) {
        switch (orientation) {
            case "north": return NORTH;
            case "east": return EAST;
            case "south": return SOUTH;
            default: return WEST;
        }
    }

    private static boolean isValid(int[][] store, int row, int column) {
        return row >= 1 && row < store.length && column >= 1 && column < store[0].length
                && store[row - 1][column - 1] != 1 && store[row - 1][column] != 1
                && store[row][column - 1] != 1 && store[row][column] != 1;
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