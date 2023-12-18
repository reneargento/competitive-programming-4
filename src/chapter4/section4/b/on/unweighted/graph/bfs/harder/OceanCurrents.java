package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 15/12/23.
 */
public class OceanCurrents {

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
        int energySpent;

        public State(Cell cell, int energySpent) {
            this.cell = cell;
            this.energySpent = energySpent;
        }
    }

    private static final int[] neighborRows = { -1, -1, 0, 1, 1, 1, 0, -1 };
    private static final int[] neighborColumns = { 0, 1, 1, 1, 0, -1, -1, -1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        int[][] lake = new int[rows][columns];
        for (int row = 0; row < lake.length; row++) {
            String line = FastReader.next();
            for (int column = 0; column < line.length(); column++) {
                char symbol = line.charAt(column);
                int value = Character.getNumericValue(symbol);
                lake[row][column] = value;
            }
        }

        int trips = FastReader.nextInt();
        for (int i = 0; i < trips; i++) {
            Cell startCell = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);
            Cell destinationCell = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);
            int minimumEnergySpent = computeMinimumEnergy(lake, startCell, destinationCell);
            outputWriter.printLine(minimumEnergySpent);
        }
        outputWriter.flush();
    }

    private static int computeMinimumEnergy(int[][] lake, Cell startCell, Cell destinationCell) {
        int[][] distances = new int[lake.length][lake[0].length];
        for (int[] values : distances) {
            Arrays.fill(values, Integer.MAX_VALUE);
        }

        Deque<State> deque = new ArrayDeque<>();
        deque.offer(new State(startCell, 0));
        distances[startCell.row][startCell.column] = 0;

        while (!deque.isEmpty()) {
            State state = deque.pollFirst();
            Cell cell = state.cell;

            if (cell.row == destinationCell.row && cell.column == destinationCell.column) {
                return state.energySpent;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(lake, neighborRow, neighborColumn)) {
                    int energy = (i == lake[cell.row][cell.column]) ? 0 : 1;
                    int totalEnergy = state.energySpent + energy;

                    if (totalEnergy < distances[neighborRow][neighborColumn]) {
                        Cell nextCell = new Cell(neighborRow, neighborColumn);
                        State nextState = new State(nextCell, totalEnergy);
                        if (energy == 0) {
                            deque.offerFirst(nextState);
                        } else {
                            deque.offerLast(nextState);
                        }
                        distances[neighborRow][neighborColumn] = totalEnergy;
                    }
                }
            }
        }
        return -1;
    }

    private static boolean isValid(int[][] lake, int row, int column) {
        return row >= 0 && row < lake.length && column >= 0 && column < lake[0].length;
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
