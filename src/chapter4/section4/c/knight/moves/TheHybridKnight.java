package chapter4.section4.c.knight.moves;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/23.
 */
public class TheHybridKnight {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static class State {
        Cell cell;
        int moves;
        int moveType;

        public State(Cell cell, int moves, int moveType) {
            this.cell = cell;
            this.moves = moves;
            this.moveType = moveType;
        }
    }

    private static final int[] neighborRowsKnight = { -2, -2, 1, -1, 1, -1, 2, 2 };
    private static final int[] neighborColumnsKnight = { -1, 1, -2, -2, 2, 2, -1, 1 };
    private static final int[] neighborRowsMutantKnight = { -3, -3, 1, -1, 1, -1, 3, 3 };
    private static final int[] neighborColumnsMutantKnight = { -1, 1, -3, -3, 3, 3, -1, 1 };
    private static final int[] neighborRowsMutantPawn = { -1, 0, 0, 1 };
    private static final int[] neighborColumnsMutantPawn = { 0, -1, 1, 0 };
    private static final int[] moveTypes = { 0, 1, 2 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int boardSize = FastReader.nextInt();
        int setID = 1;

        while (boardSize != 0) {
            outputWriter.printLine(String.format("Set %d:", setID));

            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int startLocation = FastReader.nextInt() - 1;
                int endLocation = FastReader.nextInt() - 1;

                int minimumNumberOfMoves = computeMinimumMoves(boardSize, startLocation, endLocation);
                if (minimumNumberOfMoves != -1) {
                    outputWriter.printLine(minimumNumberOfMoves);
                } else {
                    outputWriter.printLine("?");
                }
            }
            setID++;
            boardSize = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumMoves(int boardSize, int startLocation, int endLocation) {
        int startRow = startLocation / boardSize;
        int startColumn = startLocation % boardSize;
        int endRow = endLocation / boardSize;
        int endColumn = endLocation % boardSize;
        Cell startCell = new Cell(startRow, startColumn);
        Cell endCell = new Cell(endRow, endColumn);

        boolean[][][] visited = new boolean[boardSize][boardSize][3];
        Queue<State> queue = new LinkedList<>();
        for (int moveType : moveTypes) {
            queue.offer(new State(startCell, 0, moveType));
            visited[startCell.row][startCell.column][moveType] = true;
        }
        int minimumMoves = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (state.moves > minimumMoves) {
                return minimumMoves;
            }
            if (cell.equals(endCell)) {
                return state.moves;
            }

            int nextMoveType = (state.moveType + 1) % 3;
            switch (nextMoveType) {
                case 0: makeMove(queue, visited, state, neighborRowsKnight, neighborColumnsKnight, boardSize, 0);
                        break;
                case 1: makeMove(queue, visited, state, neighborRowsMutantKnight, neighborColumnsMutantKnight, boardSize, 1);
                        break;
                default: makeMove(queue, visited, state, neighborRowsMutantPawn, neighborColumnsMutantPawn, boardSize, 2);
                         if (canUseMutantPawnAttack(cell, endCell)) {
                             minimumMoves = state.moves + 1;
                         }
            }
        }
        return -1;
    }

    private static void makeMove(Queue<State> queue, boolean[][][] visited, State state, int[] neighborRows,
                                 int[] neighborColumns, int boardSize, int moveType) {
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = state.cell.row + neighborRows[i];
            int neighborColumn = state.cell.column + neighborColumns[i];

            if (isValid(boardSize, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn][moveType]) {
                Cell nextCell = new Cell(neighborRow, neighborColumn);
                queue.offer(new State(nextCell, state.moves + 1, moveType));
                visited[neighborRow][neighborColumn][moveType] = true;
            }
        }
    }

    private static boolean canUseMutantPawnAttack(Cell cell, Cell endCell) {
        int[] neighborRowsMutantPawnAttack = { -1, -1, 1, 1 };
        int[] neighborColumnsMutantPawnAttack = { -1, 1, -1, 1 };

        for (int i = 0; i < neighborRowsMutantPawnAttack.length; i++) {
            int neighborRow = cell.row + neighborRowsMutantPawnAttack[i];
            int neighborColumn = cell.column + neighborColumnsMutantPawnAttack[i];
            Cell nextCell = new Cell(neighborRow, neighborColumn);

            if (nextCell.equals(endCell)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValid(int boardSize, int row, int column) {
        return row >= 0 && row < boardSize && column >= 0 && column < boardSize;
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
