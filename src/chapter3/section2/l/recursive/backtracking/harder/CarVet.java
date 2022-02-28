package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/02/22.
 */
public class CarVet {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Cell emptyLocation = null;
        int[][] lot = new int[FastReader.nextInt()][FastReader.nextInt()];
        for (int row = 0; row < lot.length; row++) {
            for (int column = 0; column < lot[0].length; column++) {
                lot[row][column] = FastReader.nextInt();
                if (lot[row][column] == -1) {
                    emptyLocation = new Cell(row, column);
                }
            }
        }
        Cell locationToUncover = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);

        Map<Integer, List<Direction>> forbiddenDirections = new HashMap<>();
        List<Integer> carsToMove = moveCars(lot, forbiddenDirections, locationToUncover, emptyLocation,
                new LinkedList<>());
        if (carsToMove != null) {
            outputWriter.print(carsToMove.get(0));
            for (int i = 1; i < carsToMove.size(); i++) {
                outputWriter.print(" " + carsToMove.get(i));
            }
            outputWriter.printLine();
        } else {
            outputWriter.printLine("impossible");
        }
        outputWriter.flush();
    }

    private static List<Integer> moveCars(int[][] lot, Map<Integer, List<Direction>> forbiddenDirections,
                                          Cell locationToUncover, Cell emptyLocation, List<Integer> movedCars) {
        if (emptyLocation.row == locationToUncover.row
                && emptyLocation.column == locationToUncover.column) {
            return movedCars;
        }

        List<Integer> bestMoves = null;

        // Move left
        if (emptyLocation.column < lot[0].length - 2
                && lot[emptyLocation.row][emptyLocation.column + 1] == lot[emptyLocation.row][emptyLocation.column + 2]) {
            int carToMove = lot[emptyLocation.row][emptyLocation.column + 1];
            if (!(forbiddenDirections.containsKey(carToMove)
                    && forbiddenDirections.get(carToMove).contains(Direction.LEFT))) {
                Cell nextEmptyLocation = new Cell(emptyLocation.row, emptyLocation.column + 2);
                Direction forbiddenDirection = Direction.RIGHT;
                bestMoves = moveCar(lot, forbiddenDirections, emptyLocation, nextEmptyLocation,
                        locationToUncover, carToMove, forbiddenDirection);
                rollbackMove(lot, forbiddenDirections, emptyLocation, nextEmptyLocation, carToMove,
                        forbiddenDirection);
            }
        }

        // Move right
        if (emptyLocation.column >= 2
                && lot[emptyLocation.row][emptyLocation.column - 1] == lot[emptyLocation.row][emptyLocation.column - 2]) {
            int carToMove = lot[emptyLocation.row][emptyLocation.column - 1];
            if (!(forbiddenDirections.containsKey(carToMove)
                    && forbiddenDirections.get(carToMove).contains(Direction.RIGHT))) {
                Cell nextEmptyLocation = new Cell(emptyLocation.row, emptyLocation.column - 2);
                Direction forbiddenDirection = Direction.LEFT;
                List<Integer> moves = moveCar(lot, forbiddenDirections, emptyLocation, nextEmptyLocation,
                        locationToUncover, carToMove, forbiddenDirection);
                bestMoves = updateBestMoves(bestMoves, moves);
                rollbackMove(lot, forbiddenDirections, emptyLocation, nextEmptyLocation, carToMove, forbiddenDirection);
            }
        }

        // Move up
        if (emptyLocation.row < lot.length - 2
                && lot[emptyLocation.row + 1][emptyLocation.column] == lot[emptyLocation.row + 2][emptyLocation.column]) {
            int carToMove = lot[emptyLocation.row + 1][emptyLocation.column];
            if (!(forbiddenDirections.containsKey(carToMove)
                    && forbiddenDirections.get(carToMove).contains(Direction.UP))) {
                Cell nextEmptyLocation = new Cell(emptyLocation.row + 2, emptyLocation.column);
                Direction forbiddenDirection = Direction.DOWN;
                List<Integer> moves = moveCar(lot, forbiddenDirections, emptyLocation, nextEmptyLocation,
                        locationToUncover, carToMove, forbiddenDirection);
                bestMoves = updateBestMoves(bestMoves, moves);
                rollbackMove(lot, forbiddenDirections, emptyLocation, nextEmptyLocation, carToMove,
                        forbiddenDirection);
            }
        }

        // Move down
        if (emptyLocation.row >= 2
                && lot[emptyLocation.row - 1][emptyLocation.column] == lot[emptyLocation.row - 2][emptyLocation.column]) {
            int carToMove = lot[emptyLocation.row - 1][emptyLocation.column];
            if (!(forbiddenDirections.containsKey(carToMove)
                    && forbiddenDirections.get(carToMove).contains(Direction.DOWN))) {
                Cell nextEmptyLocation = new Cell(emptyLocation.row - 2, emptyLocation.column);
                Direction forbiddenDirection = Direction.UP;
                List<Integer> moves = moveCar(lot, forbiddenDirections, emptyLocation, nextEmptyLocation,
                        locationToUncover, carToMove, forbiddenDirection);
                bestMoves = updateBestMoves(bestMoves, moves);
                rollbackMove(lot, forbiddenDirections, emptyLocation, nextEmptyLocation, carToMove, forbiddenDirection);
            }
        }

        if (bestMoves == null) {
            return null;
        }
        List<Integer> movedCarsCopy = new ArrayList<>(movedCars);
        movedCarsCopy.addAll(bestMoves);
        return movedCarsCopy;
    }

    private static List<Integer> moveCar(int[][] lot, Map<Integer, List<Direction>> forbiddenDirections,
                                         Cell emptyLocation, Cell nextEmptyLocation, Cell locationToUncover,
                                         int carToMove, Direction forbiddenDirection) {
        List<Integer> nextMovedCars = new ArrayList<>();
        nextMovedCars.add(carToMove);

        lot[emptyLocation.row][emptyLocation.column] = carToMove;
        lot[nextEmptyLocation.row][nextEmptyLocation.column] = -1;
        if (!forbiddenDirections.containsKey(carToMove)) {
            forbiddenDirections.put(carToMove, new ArrayList<>());
        }
        forbiddenDirections.get(carToMove).add(forbiddenDirection);
        return moveCars(lot, forbiddenDirections, locationToUncover, nextEmptyLocation, nextMovedCars);
    }

    private static void rollbackMove(int[][] lot, Map<Integer, List<Direction>> forbiddenDirections,
                                     Cell emptyLocation, Cell nextEmptyLocation, int carToMove,
                                     Direction forbiddenDirection) {
        lot[emptyLocation.row][emptyLocation.column] = -1;
        lot[nextEmptyLocation.row][nextEmptyLocation.column] = carToMove;
        forbiddenDirections.get(carToMove).remove(forbiddenDirection);
    }

    private static List<Integer> updateBestMoves(List<Integer> bestMoves, List<Integer> candidateMoves) {
        if (candidateMoves != null) {
            if (bestMoves == null || bestMoves.size() > candidateMoves.size()) {
                return candidateMoves;
            } else if (bestMoves.size() == candidateMoves.size()) {
                for (int i = 0; i < candidateMoves.size(); i++) {
                    if (candidateMoves.get(i) < bestMoves.get(i)) {
                        return candidateMoves;
                    }
                }
            }
        }
        return bestMoves;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
