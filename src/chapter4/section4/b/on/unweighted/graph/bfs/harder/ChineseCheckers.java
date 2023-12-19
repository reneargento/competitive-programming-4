package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/12/23.
 */
public class ChineseCheckers {

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
        boolean isJumping;

        public State(Cell cell, int steps, boolean isJumping) {
            this.cell = cell;
            this.steps = steps;
            this.isJumping = isJumping;
        }
    }

    private static class PossibleMovement implements Comparable<PossibleMovement> {
        Cell destination;
        int steps;

        public PossibleMovement(Cell destination, int steps) {
            this.destination = destination;
            this.steps = steps;
        }

        @Override
        public int compareTo(PossibleMovement other) {
            if (destination.row != other.destination.row) {
                return Integer.compare(other.destination.row, destination.row);
            }
            return Integer.compare(destination.column, other.destination.column);
        }
    }

    private static final int[] neighborRowsMove = { 1, 0, 0 };
    private static final int[] neighborColumnsMove = { 0, -1, 1 };
    private static final int[] neighborRowsJump = { 2, 0, 0, 2, 2 };
    private static final int[] neighborColumnsJump = { 0, -2, 2, -2, 2 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int testID = 1;
        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);
            int pieces = 4 * columns;
            boolean[][] chessboard = new boolean[rows][columns];

            for (int p = 0; p < pieces; p++) {
                int pieceRow = FastReader.nextInt() - 1;
                int pieceColumn = FastReader.nextInt() - 1;
                chessboard[pieceRow][pieceColumn] = true;
            }

            Cell selectedPiece = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);
            List<PossibleMovement> possibleMovements = computePossibleMovements(chessboard, selectedPiece);

            if (testID > 1) {
                outputWriter.printLine();
            }
            for (PossibleMovement possibleMovement : possibleMovements) {
                Cell destination = possibleMovement.destination;
                outputWriter.printLine(String.format("%d %d %d", destination.row + 1, destination.column + 1,
                        possibleMovement.steps));
            }

            testID++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<PossibleMovement> computePossibleMovements(boolean[][] chessboard, Cell selectedPiece) {
        List<PossibleMovement> possibleMovements = new ArrayList<>();
        boolean[][] visited = new boolean[chessboard.length][chessboard[0].length];
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(selectedPiece, 0, false));

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (!state.isJumping) {
                moveCell(chessboard, queue, visited, cell, neighborRowsMove, neighborColumnsMove,
                        possibleMovements, state.steps, false);
            }
            moveCell(chessboard, queue, visited, cell, neighborRowsJump, neighborColumnsJump,
                    possibleMovements, state.steps, true);
        }
        Collections.sort(possibleMovements);
        return possibleMovements;
    }

    private static void moveCell(boolean[][] chessboard, Queue<State> queue, boolean[][] visited, Cell cell,
                                 int[] neighborRows, int[] neighborColumns,  List<PossibleMovement> possibleMovements,
                                 int currentSteps, boolean isJump) {
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = cell.row + neighborRows[i];
            int neighborColumn = cell.column + neighborColumns[i];

            if (isJump) {
                int intermediateRow = cell.row + (neighborRows[i] / 2);
                int intermediateColumn = cell.column + (neighborColumns[i] / 2);
                if (isValid(chessboard, intermediateRow, intermediateColumn)
                        && !chessboard[intermediateRow][intermediateColumn]) {
                    // Not jumping any piece
                    continue;
                }
            }

            if (isValid(chessboard, neighborRow, neighborColumn)
                    && !chessboard[neighborRow][neighborColumn]
                    && !visited[neighborRow][neighborColumn]) {
                visited[neighborRow][neighborColumn] = true;
                Cell nextCell = new Cell(neighborRow, neighborColumn);
                int nextSteps = currentSteps + 1;
                possibleMovements.add(new PossibleMovement(nextCell, nextSteps));

                if (isJump) {
                    queue.offer(new State(nextCell, nextSteps, true));
                }
            }
        }
    }

    private static boolean isValid(boolean[][] chessboard, int row, int column) {
        return row >= 0 && row < chessboard.length && column >= 0 && column < chessboard[0].length;
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