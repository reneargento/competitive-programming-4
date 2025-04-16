package chapter5.section2.g.grid;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/04/25.
 */
public class BeeBreeding {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class MoveResult {
        int newRow;
        int newColumn;
        int newValue;

        public MoveResult(int newRow, int newColumn, int newValue) {
            this.newRow = newRow;
            this.newColumn = newColumn;
            this.newValue = newValue;
        }
    }

    private static final int SOUTH_INDEX = 4;
    private static final int SOUTHWEST_INDEX = 5;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<Integer, Cell> idToCellMap = computeCells();

        int cellId1 = FastReader.nextInt();
        int cellId2 = FastReader.nextInt();

        while (cellId1 != 0 || cellId2 != 0) {
            int shortestDistance = computeShortestDistance(idToCellMap, cellId1, cellId2);
            outputWriter.printLine(String.format("The distance between cells %d and %d is %d.", cellId1,
                    cellId2, shortestDistance));

            cellId1 = FastReader.nextInt();
            cellId2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeShortestDistance(Map<Integer, Cell> idToCellMap, int cellId1, int cellId2) {
        Cell startCell = idToCellMap.get(cellId1);
        Cell endCell = idToCellMap.get(cellId2);

        int rowDistance = Math.abs(startCell.row - endCell.row);
        int columnDistance = Math.abs(startCell.column - endCell.column);
        int rowColumnDistance = Math.abs(startCell.row - endCell.row + startCell.column - endCell.column);
        return (rowDistance + columnDistance + rowColumnDistance) / 2;
    }

    private static Map<Integer, Cell> computeCells() {
        Map<Integer, Cell> idToCellMap = new HashMap<>();
        int dimension = 130;
        int row = dimension / 2;
        int column = dimension / 2;
        int value = 1;
        int iterations = 1;

        // northwest -> north -> northeast -> southeast -> south -> southwest
        int[] neighborRows = { 0, -1, -1, 0, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, 1, 0, -1 };

        MoveResult moveResult = move(dimension, idToCellMap, row, column, value, iterations, 0, 0);
        moveResult = move(dimension, idToCellMap, row, column, moveResult.newValue, iterations, 1, 0);

        while (moveResult.newValue < 11000) {
            for (int i = 0; i < neighborRows.length; i++) {
                if (i == SOUTH_INDEX) {
                    iterations++;
                }
                int iterationsToUse = i != SOUTHWEST_INDEX ? iterations : iterations - 1;

                moveResult = move(dimension, idToCellMap, moveResult.newRow, moveResult.newColumn, moveResult.newValue,
                        iterationsToUse, neighborRows[i], neighborColumns[i]);
            }
        }
        return idToCellMap;
    }

    private static MoveResult move(int dimension, Map<Integer, Cell> idToCellMap, int row, int column, int value,
                                   int iterations, int rowDelta, int columnDelta) {
        for (int i = 0; i < iterations; i++) {
            row += rowDelta;
            column += columnDelta;
            if (!isValid(dimension, row, column)) {
                break;
            }
            idToCellMap.put(value, new Cell(row, column));
            value++;
        }
        return new MoveResult(row, column, value);
    }

    private static boolean isValid(int dimension, int row, int column) {
        return row >= 0 && row < dimension && column >= 0 && column < dimension;
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
