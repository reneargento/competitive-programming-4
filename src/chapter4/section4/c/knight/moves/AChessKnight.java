package chapter4.section4.c.knight.moves;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/12/23.
 */
public class AChessKnight {

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
        int moves;
        int lastMoveType;

        public State(Cell cell, int moves, int lastMoveType) {
            this.cell = cell;
            this.moves = moves;
            this.lastMoveType = lastMoveType;
        }
    }

    private static final int[] neighborRows = { -2, -2, 1, -1, 1, -1, 2, 2, -2, -2, 2, 2 };
    private static final int[] neighborColumns = { -1, 1, -2, -2, 2, 2, -1, 1, -2, 2, -2, 2 };
    private static final int TYPE_NONE = 0;
    private static final int TYPE_K = 1;
    private static final int TYPE_B = 2;
    private static final int TYPE_T = 3;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int chessboardSize = FastReader.nextInt();

        while (chessboardSize != 0){
            int chessboardDimension = chessboardSize * 2;
            boolean[][] obstacles = new boolean[chessboardDimension][chessboardDimension];
            Cell startCell = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);
            Cell endCell = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);

            int obstacleRow = FastReader.nextInt();
            int obstacleColumn = FastReader.nextInt();
            while (obstacleRow != 0) {
                obstacles[obstacleRow - 1][obstacleColumn - 1] = true;
                obstacleRow = FastReader.nextInt();
                obstacleColumn = FastReader.nextInt();
            }

            int minimumMoves = computeMinimumMoves(startCell, endCell, obstacles);
            if (minimumMoves != -1) {
                outputWriter.printLine(String.format("Result : %d", minimumMoves));
            } else {
                outputWriter.printLine("Solution doesn't exist");
            }
            chessboardSize = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumMoves(Cell startCell, Cell endCell, boolean[][] obstacles) {
        boolean[][][] visited = new boolean[obstacles.length][obstacles[0].length][4];
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(startCell, 0, TYPE_NONE));
        visited[startCell.row][startCell.column][TYPE_NONE] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.row == endCell.row && cell.column == endCell.column) {
                return state.moves;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];
                int moveType = i <= 7 ? TYPE_K : TYPE_B;
                processCell(queue, visited, obstacles, state, neighborRow, neighborColumn, moveType);
            }

            int invertedRow = visited.length - 1 - cell.row;
            int invertedColumn = visited[0].length - 1 - cell.column;
            int[] teleportRows = { invertedRow, cell.row };
            int[] teleportColumns = { cell.column, invertedColumn };
            for (int i = 0; i < teleportRows.length; i++) {
                int neighborRow = teleportRows[i];
                int neighborColumn = teleportColumns[i];
                processCell(queue, visited, obstacles, state, neighborRow, neighborColumn, TYPE_T);
            }
        }
        return -1;
    }

    private static void processCell(Queue<State> queue, boolean[][][] visited, boolean[][] obstacles,
                                    State state, int neighborRow, int neighborColumn, int moveType) {
        if (isValid(visited, neighborRow, neighborColumn)
                && !obstacles[neighborRow][neighborColumn]
                && !visited[neighborRow][neighborColumn][moveType]
                && moveType != state.lastMoveType) {
            visited[neighborRow][neighborColumn][moveType] = true;
            Cell nextCell = new Cell(neighborRow, neighborColumn);
            queue.offer(new State(nextCell, state.moves + 1, moveType));
        }
    }

    private static boolean isValid(boolean[][][] visited, int row, int column) {
        return row >= 0 && row < visited.length && column >= 0 && column < visited[0].length;
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
