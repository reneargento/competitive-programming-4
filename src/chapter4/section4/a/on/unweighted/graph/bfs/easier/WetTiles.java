package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/12/23.
 */
public class WetTiles {

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
        int minutesPassed;

        public State(Cell cell, int minutesPassed) {
            this.cell = cell;
            this.minutesPassed = minutesPassed;
        }
    }

    private static final int UNVISITED = 0;
    private static final int WET = 1;
    private static final int WALL = 2;

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int columns = FastReader.nextInt();

        while (columns != -1) {
            int[][] house = new int[FastReader.nextInt()][columns];
            int minutes = FastReader.nextInt();
            int leaksNumber = FastReader.nextInt();
            int innerWalls = FastReader.nextInt();
            List<Cell> leaks = new ArrayList<>();

            for (int i = 0; i < leaksNumber; i++) {
                int column = FastReader.nextInt() - 1;
                int row = FastReader.nextInt() - 1;
                house[row][column] = WET;
                leaks.add(new Cell(row, column));
            }

            for (int i = 0; i < innerWalls; i++) {
                int column1 = FastReader.nextInt() - 1;
                int row1 = FastReader.nextInt() - 1;
                int column2 = FastReader.nextInt() - 1;
                int row2 = FastReader.nextInt() - 1;
                markWall(house, row1, column1, row2, column2);
            }

            int totalTilesWet = computeTotalTilesWet(house, leaks, minutes);
            outputWriter.printLine(totalTilesWet);
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void markWall(int[][] house, int row1, int column1, int row2, int column2) {
        int rowIncrement = Integer.compare(row2, row1);
        int columnIncrement = Integer.compare(column2, column1);

        for (int row = row1, column = column1; row != row2 || column != column2; row += rowIncrement,
                column += columnIncrement) {
            house[row][column] = WALL;
        }
        house[row2][column2] = WALL;
    }

    private static int computeTotalTilesWet(int[][] house, List<Cell> leaks, int minutes) {
        int totalTilesWet = leaks.size();
        Queue<State> queue = new LinkedList<>();

        for (Cell leak : leaks) {
            queue.offer(new State(leak, 1));
        }

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (state.minutesPassed == minutes) {
                break;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(house, neighborRow, neighborColumn)
                        && house[neighborRow][neighborColumn] == UNVISITED) {
                    house[neighborRow][neighborColumn] = WET;
                    totalTilesWet++;
                    Cell neighborCell = new Cell(neighborRow, neighborColumn);
                    queue.offer(new State(neighborCell, state.minutesPassed + 1));
                }
            }
        }
        return totalTilesWet;
    }

    private static boolean isValid(int[][] house, int row, int column) {
        return row >= 0 && row < house.length && column >= 0 && column < house[0].length;
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
