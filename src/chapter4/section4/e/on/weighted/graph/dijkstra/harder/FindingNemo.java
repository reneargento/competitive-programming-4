package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/01/24.
 */
public class FindingNemo {

    private static class Cell {
        boolean[] hasWall;
        boolean[] hasDoor;

        public Cell() {
            hasWall = new boolean[2];
            hasDoor = new boolean[2];
        }
    }

    private static class State implements Comparable<State> {
        int row;
        int column;
        int doorsCrossed;

        public State(int row, int column, int doorsCrossed) {
            this.row = row;
            this.column = column;
            this.doorsCrossed = doorsCrossed;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(doorsCrossed, other.doorsCrossed);
        }
    }

    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;
    private static final int NORTH = 0;
    private static final int WEST = 1;
    private static final int EAST = 2;
    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int walls = FastReader.nextInt();
        int doors = FastReader.nextInt();

        while (walls != -1) {
            Cell[][] labyrinth = new Cell[200][200];
            for (int row = 0; row < labyrinth.length; row++) {
                for (int column = 0; column < labyrinth[0].length; column++) {
                    labyrinth[row][column] = new Cell();
                }
            }

            for (int w = 0; w < walls; w++) {
                int x = FastReader.nextInt();
                int y = FastReader.nextInt();
                boolean isHorizontal = FastReader.nextInt() == 0;
                int length = FastReader.nextInt();

                int startRow = labyrinth.length - 1 - y;
                int startColumn = x;
                int endRow;
                int endColumn;
                if (isHorizontal) {
                    endRow = startRow;
                    endColumn = startColumn + length - 1;
                } else {
                    endRow = startRow - length + 1;
                    endColumn = startColumn;
                }

                for (int row = startRow; row >= endRow; row--) {
                    for (int column = startColumn; column <= endColumn; column++) {
                        if (isHorizontal) {
                            labyrinth[row][column].hasWall[HORIZONTAL] = true;
                        } else {
                            labyrinth[row][column].hasWall[VERTICAL] = true;
                        }
                    }
                }
            }

            for (int d = 0; d < doors; d++) {
                int column = FastReader.nextInt();
                int row = labyrinth.length - 1 - FastReader.nextInt();
                boolean isHorizontal = FastReader.nextInt() == 0;

                if (isHorizontal) {
                    labyrinth[row][column].hasDoor[HORIZONTAL] = true;
                    labyrinth[row][column].hasWall[HORIZONTAL] = false;
                } else {
                    labyrinth[row][column].hasDoor[VERTICAL] = true;
                    labyrinth[row][column].hasWall[VERTICAL] = false;
                }
            }
            int nemoColumn = (int) Math.floor(FastReader.nextDouble());
            int nemoRow = labyrinth.length - 1 - (int) Math.floor(FastReader.nextDouble());

            int minimumDoors = computeMinimumDoors(labyrinth, nemoRow, nemoColumn);
            outputWriter.printLine(minimumDoors);

            walls = FastReader.nextInt();
            doors = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumDoors(Cell[][] labyrinth, int nemoRow, int nemoColumn) {
        if (nemoRow >= labyrinth.length || nemoColumn >= labyrinth[0].length) {
            return 0;
        }
        PriorityQueue<State> priorityQueue = new PriorityQueue<>();
        int startRow = labyrinth.length - 1;
        priorityQueue.offer(new State(startRow, 0, 0));

        int[][] doorsCrossed = new int[labyrinth.length][labyrinth[0].length];
        for (int row = 0; row < doorsCrossed.length; row++) {
            Arrays.fill(doorsCrossed[row], Integer.MAX_VALUE);
        }
        doorsCrossed[startRow][0] = 0;

        while (!priorityQueue.isEmpty()) {
            State state = priorityQueue.poll();
            if (state.row == nemoRow && state.column == nemoColumn) {
                return state.doorsCrossed;
            }

            for (int direction = 0; direction < neighborRows.length; direction++) {
                int neighborRow = state.row + neighborRows[direction];
                int neighborColumn = state.column + neighborColumns[direction];

                if (isValid(labyrinth, neighborRow, neighborColumn)
                        && !isBlocked(labyrinth, state.row, state.column, direction)) {
                    int nextDoorsCrossed = state.doorsCrossed;
                    if (hasDoor(labyrinth, state.row, state.column, direction)) {
                        nextDoorsCrossed++;
                    }

                    State nextState = new State(neighborRow, neighborColumn, nextDoorsCrossed);
                    if (nextState.doorsCrossed < doorsCrossed[neighborRow][neighborColumn]) {
                        doorsCrossed[neighborRow][neighborColumn] = nextState.doorsCrossed;
                        priorityQueue.offer(nextState);
                    }
                }
            }
        }
        return -1;
    }

    private static boolean isBlocked(Cell[][] labyrinth, int row, int column, int direction) {
        switch (direction) {
            case NORTH: return row == 0 || labyrinth[row - 1][column].hasWall[HORIZONTAL];
            case WEST: return column == 0 || labyrinth[row][column].hasWall[VERTICAL];
            case EAST: return column == labyrinth[0].length - 1 || labyrinth[row][column + 1].hasWall[VERTICAL];
            default: return row == labyrinth.length - 1 || labyrinth[row][column].hasWall[HORIZONTAL];
        }
    }

    private static boolean hasDoor(Cell[][] labyrinth, int row, int column, int direction) {
        switch (direction) {
            case NORTH: return labyrinth[row - 1][column].hasDoor[HORIZONTAL];
            case WEST: return labyrinth[row][column].hasDoor[VERTICAL];
            case EAST: return labyrinth[row][column + 1].hasDoor[VERTICAL];
            default: return labyrinth[row][column].hasDoor[HORIZONTAL];
        }
    }

    private static boolean isValid(Cell[][] labyrinth, int row, int column) {
        return row >= 0 && row < labyrinth.length && column >= 0 && column < labyrinth[0].length;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
