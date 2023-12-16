package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/12/23.
 */
public class PrimeSpiral {

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

    private static final int RIGHT = 0;
    private static final int UP = 1;
    private static final int LEFT = 2;
    private static final int DOWN = 3;
    private static final int MAX_VALUE = 10500;
    private static final int MAX_DIMENSION = 130;

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<Integer, Cell> valueToCellMap = new HashMap<>();
        int[][] spiral = computeSpiral(valueToCellMap);
        Set<Integer> primeNumbers = eratosthenesSieveGetPrimes(MAX_VALUE);
        int[][] visited = new int[MAX_DIMENSION][MAX_DIMENSION];

        int visitID = 1;
        int caseID = 1;
        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int source = Integer.parseInt(data[0]);
            int target = Integer.parseInt(data[1]);

            int distance = computeShortestPathDistance(spiral, valueToCellMap, primeNumbers, visited, visitID, source,
                    target);
            outputWriter.print(String.format("Case %d: ", caseID));
            if (distance != -1) {
                outputWriter.printLine(distance);
            } else {
                outputWriter.printLine("impossible");
            }

            caseID++;
            visitID++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeShortestPathDistance(int[][] spiral, Map<Integer, Cell> valueToCellMap,
                                                   Set<Integer> primeNumbers, int[][] visited, int visitID,
                                                   int source, int target) {
        Cell sourceCell = valueToCellMap.get(source);
        Cell targetCell = valueToCellMap.get(target);

        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(sourceCell, 0));
        visited[sourceCell.row][sourceCell.column] = visitID;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.row == targetCell.row && cell.column == targetCell.column) {
                return state.steps;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(spiral, neighborRow, neighborColumn)
                        && visited[neighborRow][neighborColumn] != visitID) {
                    int value = spiral[neighborRow][neighborColumn];
                    if (!primeNumbers.contains(value)) {
                        visited[neighborRow][neighborColumn] = visitID;
                        Cell nextCell = new Cell(neighborRow, neighborColumn);
                        queue.offer(new State(nextCell, state.steps + 1));
                    }
                }
            }
        }
        return -1;
    }

    private static int[][] computeSpiral(Map<Integer, Cell> valueToCellMap) {
        int[][] spiral = new int[MAX_DIMENSION][MAX_DIMENSION];

        int row = 75;
        int column = 75;
        int steps = 1;
        int direction = RIGHT;
        int value = 1;

        while (value < MAX_VALUE) {
            for (int i = 0; i < 2; i++) {
                for (int s = 0; s < steps; s++) {
                    spiral[row][column] = value;
                    valueToCellMap.put(value, new Cell(row, column));
                    value++;

                    switch (direction) {
                        case RIGHT: column++; break;
                        case UP: row--; break;
                        case LEFT: column--; break;
                        default: row++;
                    }
                }
                direction = getNextDirection(direction);
            }
            steps++;
        }
        return spiral;
    }

    private static Set<Integer> eratosthenesSieveGetPrimes(int number) {
        Set<Integer> primeNumbers = new HashSet<>();
        boolean[] isPrime = new boolean[number + 1];

        // 1- Mark all numbers as prime
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        // 2- Remove numbers multiple of the current element
        // 3- Repeat until we finish verifying the maxNumberToCheck
        for (long i = 2; i <= number; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbers.add((int) i);
            }
        }
        return primeNumbers;
    }

    private static int getNextDirection(int direction) {
        switch (direction) {
            case RIGHT: return UP;
            case UP: return LEFT;
            case LEFT: return DOWN;
            default: return RIGHT;
        }
    }

    private static boolean isValid(int[][] spiral, int row, int column) {
        return row >= 0 && row < spiral.length && column >= 0 && column < spiral[0].length;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
