package chapter4.section4.section2;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/11/23.
 */
// Cases where the dimension is less or equals to 4 and higher or equals to 5 are separated on method
// computeDistanceBetweenInterestingCells().
// Corner cells and side cells do not have to be handled explicitly because the only difference from them to other cells
// is the distance to nearby cells. For example, to move from a non-corner and non-side cell to a diagonal neighbor it
// takes 2 moves, while to move from a corner cell to a diagonal neighbor it takes 4 moves. Cases like this are handled
// by running a BFS from the starting cell to the target cell when the distance between them is small, since different
// cells (corner, side, other cells) need a different number of moves in that case.
// If the starting cell and the target cell are too close, we have the same case as described above, and it has to be
// handled by running a BFS to find the correct number of moves, since, as previously mentioned, this number can be
// different for corner cells, side cells and other cells. When the starting cell and the target cell are not close,
// we don't have this difference and the pre-computed distance can be used for any of the cells.
// Based on https://github.com/shahed-shd/Online-Judge-Solutions/blob/master/UVa-Online-Judge/11643%20-%20Knight%20Tour.cpp
public class Exercise1 {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final int[] neighborRows = { -1, -2, -2, -1, 1, 2, 2, 1 };
    private static final int[] neighborColumns = { -2, -1, 1, 2, -2, -1, 1, 2 };
    private static final int INFINITE = Integer.MAX_VALUE;
    private static final int MAX_DIMENSION = 1000;
    private static int bfsIndex = 0;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[][] preDistances = computePreDistances();

        for (int t = 1; t <= tests; t++) {
            int dimension = FastReader.nextInt();
            int cells = FastReader.nextInt();

            Cell[] interestingCells = new Cell[cells];
            for (int i = 0; i < interestingCells.length; i++) {
                int row = FastReader.nextInt() - 1;
                int column = FastReader.nextInt() - 1;
                interestingCells[i] = new Cell(row, column);
            }

            int minimumMoves = computeMinimumMoves(dimension, preDistances, interestingCells);
            outputWriter.printLine(String.format("Case %d: %d", t, minimumMoves));
        }
        outputWriter.flush();
    }

    private static int computeMinimumMoves(int dimension, int[][] preDistances, Cell[] interestingCells) {
        int[][] interestingCellDistances = computeDistanceBetweenInterestingCells(dimension, interestingCells,
                preDistances);
        return computeMinimumMovesDP(interestingCellDistances);
    }

    private static int[][] computePreDistances() {
        int[][] preDistances = new int[MAX_DIMENSION][MAX_DIMENSION];
        for (int[] values : preDistances) {
            Arrays.fill(values, INFINITE);
        }
        boolean[][] visited = new boolean[MAX_DIMENSION][MAX_DIMENSION];
        preDistances[0][0] = 0;

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(0, 0));

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(MAX_DIMENSION, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]) {
                    visited[neighborRow][neighborColumn] = true;
                    preDistances[neighborRow][neighborColumn] = preDistances[cell.row][cell.column] + 1;
                    queue.offer(new Cell(neighborRow, neighborColumn));
                }
            }
        }
        return preDistances;
    }

    private static int[][] computeDistanceBetweenInterestingCells(int dimension, Cell[] interestingCells,
                                                                  int[][] preDistances) {
        int cellsNumber = interestingCells.length;
        int[][] interestingCellDistances = new int[cellsNumber][cellsNumber];
        int[][] bfsTempDistances = new int[dimension][dimension];
        int[][] visited = new int[dimension][dimension];

        for (int cell1 = 0; cell1 < interestingCells.length; cell1++) {
            for (int cell2 = 0; cell2 < cell1; cell2++) {
                int rowDistance = Math.abs(interestingCells[cell1].row - interestingCells[cell2].row);
                int columnDistance = Math.abs(interestingCells[cell1].column - interestingCells[cell2].column);
                int totalDistance = rowDistance + columnDistance;

                int distance;
                // Separate cases
                if (totalDistance > 16) {
                    distance = preDistances[rowDistance][columnDistance];
                } else {
                    int bfsDistance = computeDistanceWithBFS(interestingCells[cell1], interestingCells[cell2],
                            bfsTempDistances, visited);
                    distance = bfsDistance != INFINITE ? bfsDistance : preDistances[rowDistance][columnDistance];
                }
                interestingCellDistances[cell1][cell2] = distance;
                interestingCellDistances[cell2][cell1] = distance;
            }
        }
        return interestingCellDistances;
    }

    private static int computeDistanceWithBFS(Cell start, Cell target, int[][] bfsTempDistances, int[][] visited) {
        int dimension = bfsTempDistances.length;
        bfsIndex++;
        Queue<Cell> queue = new LinkedList<>();

        queue.offer(start);
        bfsTempDistances[start.row][start.column] = 0;
        visited[start.row][start.column] = bfsIndex;

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(dimension, neighborRow, neighborColumn)
                        && visited[neighborRow][neighborColumn] != bfsIndex) {
                    visited[neighborRow][neighborColumn] = bfsIndex;
                    int newDistance = bfsTempDistances[cell.row][cell.column] + 1;
                    if (newDistance > 30) {
                        break;
                    }
                    bfsTempDistances[neighborRow][neighborColumn] = newDistance;

                    if (target.row == neighborRow && target.column == neighborColumn) {
                        return newDistance;
                    }
                    queue.offer(new Cell(neighborRow, neighborColumn));
                }
            }
        }
        return INFINITE;
    }

    private static int computeMinimumMovesDP(int[][] interestingCellDistances) {
        int cellsNumber = interestingCellDistances.length;
        int maxMaskSize = (1 << cellsNumber);
        // dp[mask of visited interesting cells][index of last cell visited]
        int[][] dp = new int[maxMaskSize][cellsNumber];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        // Mark first cell as visited
        int mask = 1;
        return computeMinimumMovesDP(interestingCellDistances, dp, mask, 0);
    }

    private static int computeMinimumMovesDP(int[][] interestingCellDistances, int[][] dp, int mask,
                                             int currentCellIndex) {
        if (mask == (1 << interestingCellDistances.length) - 1) {
            return interestingCellDistances[currentCellIndex][0];
        }

        if (dp[mask][currentCellIndex] != -1) {
            return dp[mask][currentCellIndex];
        }

        int minimumMoves = INFINITE;
        for (int cellID = 0; cellID < interestingCellDistances.length; cellID++) {
            if (isBitSet(mask, cellID)) {
                continue;
            }
            int newMask = setBit(mask, cellID);
            int newMinimumMoves = interestingCellDistances[currentCellIndex][cellID] +
                    computeMinimumMovesDP(interestingCellDistances, dp, newMask, cellID);
            minimumMoves = Math.min(minimumMoves, newMinimumMoves);
        }

        dp[mask][currentCellIndex] = minimumMoves;
        return minimumMoves;
    }

    private static boolean isValid(int dimension, int row, int column) {
        return row >= 0 && row < dimension && column >= 0 && column < dimension;
    }

    private static int setBit(int mask, int index) {
        return mask | (1 << index);
    }

    private static boolean isBitSet(int mask, int index) {
        return (mask & (1 << index)) > 0;
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
