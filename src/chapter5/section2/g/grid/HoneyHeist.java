package chapter5.section2.g.grid;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/04/25.
 */
public class HoneyHeist {

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
        int distance;

        public State(Cell cell, int distance) {
            this.cell = cell;
            this.distance = distance;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int length = FastReader.nextInt();
        int maxCellsToChew = FastReader.nextInt();
        int startCellId = FastReader.nextInt();
        int honeyCellId = FastReader.nextInt();
        int waxHardenedCellsNumber = FastReader.nextInt();
        Set<Integer> waxHardenedCells = new HashSet<>();

        for (int i = 0; i < waxHardenedCellsNumber; i++) {
            waxHardenedCells.add(FastReader.nextInt());
        }

        int minimumCellsToReachHoney = computeMinimumCellsToReachHoney(length, maxCellsToChew, startCellId, honeyCellId,
                waxHardenedCells);
        if (minimumCellsToReachHoney == -1) {
            outputWriter.printLine("No");
        } else {
            outputWriter.printLine(minimumCellsToReachHoney);
        }
        outputWriter.flush();
    }

    private static int computeMinimumCellsToReachHoney(int length, int maxCellsToChew, int startCellId,
                                                       int honeyCellId, Set<Integer> waxHardenedCells) {
        Cell[] startEndCells = new Cell[2];
        int[][] grid = buildGrid(length, startEndCells, startCellId, honeyCellId);
        boolean[][] visited = new boolean[grid.length][grid.length];

        int[] neighborRows =    { 0, -1, 0, 1, -1, 1 };
        int[] neighborColumns = { -1, 0, 1, 0, -1, 1 };

        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(startEndCells[0], 0));
        visited[startEndCells[0].row][startEndCells[0].column] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.row == startEndCells[1].row
                    && cell.column == startEndCells[1].column) {
                if (state.distance <= maxCellsToChew) {
                    return state.distance;
                } else {
                    return -1;
                }
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(grid, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]) {
                    int value = grid[neighborRow][neighborColumn];
                    if (value != -1 && !waxHardenedCells.contains(value)) {
                        Cell nextCell = new Cell(neighborRow, neighborColumn);
                        queue.offer(new State(nextCell, state.distance + 1));
                        visited[neighborRow][neighborColumn] = true;
                    }
                }
            }
        }
        return -1;
    }

    private static int[][] buildGrid(int length, Cell[] startEndCells, int startCellId, int honeyCellId) {
        int gridDimension = length * 2 - 1;
        int[][] grid = new int[gridDimension][gridDimension];
        for (int[] row : grid) {
            Arrays.fill(row, -1);
        }

        int middleRow = grid.length / 2;
        int numbersInRow = length;
        int value = 1;

        for (int row = 0; row < grid.length; row++) {
            int startColumn;
            if (row < middleRow) {
                startColumn = 0;
            } else {
                startColumn = gridDimension - numbersInRow;
            }

            int numbersAdded = 0;
            for (int column = startColumn; column < grid[row].length; column++) {
                numbersAdded++;
                grid[row][column] = value;
                if (value == startCellId) {
                    startEndCells[0] = new Cell(row, column);
                } else if (value == honeyCellId) {
                    startEndCells[1] = new Cell(row, column);
                }

                value++;
                if (numbersAdded == numbersInRow) {
                    break;
                }
            }

            if (row < middleRow) {
                numbersInRow++;
            } else {
                numbersInRow--;
            }
        }
        return grid;
    }

    private static boolean isValid(int[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
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
