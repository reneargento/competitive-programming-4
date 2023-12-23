package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/12/23.
 */
public class ElegantShowroom {

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
        int carsMoved;

        public State(Cell cell, int carsMoved) {
            this.cell = cell;
            this.carsMoved = carsMoved;
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };
    private static final char WALL = '#';
    private static final char CAR = 'c';
    private static final char DOOR = 'D';

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        char[][] showroom = new char[FastReader.nextInt()][FastReader.nextInt()];

        for (int row = 0; row < showroom.length; row++) {
            showroom[row] = FastReader.getLine().toCharArray();
        }
        Cell carToMove = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);

        int minimumCarsToMove = computeMinimumCarsToMove(showroom, carToMove);
        outputWriter.printLine(minimumCarsToMove);
        outputWriter.flush();
    }

    private static int computeMinimumCarsToMove(char[][] showroom, Cell carToMove) {
        Deque<State> deque = new ArrayDeque<>();
        int[][] carsMoved = new int[showroom.length][showroom[0].length];
        for (int[] values : carsMoved) {
            Arrays.fill(values, Integer.MAX_VALUE);
        }

        deque.offer(new State(carToMove, 1));
        carsMoved[carToMove.row][carToMove.column] = 1;

        while (!deque.isEmpty()) {
            State state = deque.pollFirst();
            Cell cell = state.cell;

            if (isExit(showroom, cell)) {
                return state.carsMoved;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(showroom, neighborRow, neighborColumn)
                        && showroom[neighborRow][neighborColumn] != WALL) {
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    int movedCar = showroom[neighborRow][neighborColumn] == CAR ? 1 : 0;
                    State nextState = new State(nextCell, state.carsMoved + movedCar);

                    if (nextState.carsMoved < carsMoved[neighborRow][neighborColumn]) {
                        carsMoved[neighborRow][neighborColumn] = nextState.carsMoved;

                        if (movedCar == 0) {
                            deque.offerFirst(nextState);
                        } else {
                            deque.offerLast(nextState);
                        }
                    }
                }
            }
        }
        return -1;
    }

    private static boolean isValid(char[][] showroom, int row, int column) {
        return row >= 0 && row < showroom.length && column >= 0 && column < showroom[0].length;
    }

    private static boolean isExit(char[][] showroom, Cell cell) {
        return showroom[cell.row][cell.column] == DOOR
                && (cell.row == 0 || cell.row == showroom.length - 1
                       || cell.column == 0 || cell.column == showroom[0].length - 1);
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
