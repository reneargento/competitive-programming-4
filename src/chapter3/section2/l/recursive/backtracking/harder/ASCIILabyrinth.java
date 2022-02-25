package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/02/22.
 */
public class ASCIILabyrinth {

    private enum Cell {
        EMPTY, HORIZONTAL, VERTICAL, LEFT_TO_DOWN, TOP_TO_RIGHT, DOWN_TO_RIGHT, LEFT_TO_TOP
    }

    private static class CellLocation {
        int row;
        int column;
        Cell cell;

        public CellLocation(int row, int column, Cell cell) {
            this.row = row;
            this.column = column;
            this.cell = cell;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        Map<Cell, List<Cell>> rotations = getRotations();

        for (int t = 0; t < tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            Cell[][] board = readBoard(rows, columns);
            boolean[][] visited = new boolean[rows][columns];

            int solutions = countSolutionsToLabyrinth(board, rotations, 0, 0,
                    null, visited);
            outputWriter.printLine(String.format("Number of solutions: %d", solutions));
        }
        outputWriter.flush();
    }

    private static int countSolutionsToLabyrinth(Cell[][] board, Map<Cell, List<Cell>> rotations,
                                                 int row, int column, CellLocation previousCell,
                                                 boolean[][] visited) {
        if ((row == board.length - 1 && column == board[0].length)
                || (row == board.length && column == board[0].length - 1)) {
            return 1;
        }
        if (!isValid(board, row, column)) {
            return 0;
        }

        Cell cell = board[row][column];
        if (cell.equals(Cell.EMPTY)) {
            return 0;
        }
        List<Cell> rotatedCells = rotations.get(cell);
        Cell currentCell = board[row][column];
        int solutions = 0;

        for (Cell rotatedCell : rotatedCells) {
            board[row][column] = rotatedCell;
            CellLocation currentCellLocation = new CellLocation(row, column, rotatedCell);

            if (previousCell == null || canMove(previousCell.cell, previousCell.row, previousCell.column,
                    row, column, board, visited)) {
                int nextRow;
                int nextColumn;

                if (previousCell == null) {
                    if (rotatedCell.equals(Cell.HORIZONTAL) || rotatedCell.equals(Cell.TOP_TO_RIGHT)) {
                        nextRow = 0;
                        nextColumn = 1;
                    } else if (rotatedCell.equals(Cell.VERTICAL) || rotatedCell.equals(Cell.LEFT_TO_DOWN)) {
                        nextRow = 1;
                        nextColumn = 0;
                    } else {
                        continue;
                    }
                } else if (previousCell.row < row) {
                    if (rotatedCell.equals(Cell.VERTICAL)) {
                        nextRow = row + 1;
                        nextColumn = column;
                    } else if (rotatedCell.equals(Cell.TOP_TO_RIGHT)) {
                        nextRow = row;
                        nextColumn = column + 1;
                    } else if (rotatedCell.equals(Cell.LEFT_TO_TOP)) {
                        nextRow = row;
                        nextColumn = column - 1;
                    } else {
                        continue;
                    }
                } else if (previousCell.row > row) {
                    if (rotatedCell.equals(Cell.VERTICAL)) {
                        nextRow = row - 1;
                        nextColumn = column;
                    } else if (rotatedCell.equals(Cell.DOWN_TO_RIGHT)) {
                        nextRow = row;
                        nextColumn = column + 1;
                    } else if (rotatedCell.equals(Cell.LEFT_TO_DOWN)) {
                        nextRow = row;
                        nextColumn = column - 1;
                    } else {
                        continue;
                    }
                } else if (previousCell.column < column) {
                    if (rotatedCell.equals(Cell.HORIZONTAL)) {
                        nextRow = row;
                        nextColumn = column + 1;
                    } else if (rotatedCell.equals(Cell.LEFT_TO_DOWN)) {
                        nextRow = row + 1;
                        nextColumn = column;
                    } else if (rotatedCell.equals(Cell.LEFT_TO_TOP)) {
                        nextRow = row - 1;
                        nextColumn = column;
                    } else {
                        continue;
                    }
                } else if (previousCell.column > column) {
                    if (rotatedCell.equals(Cell.HORIZONTAL)) {
                        nextRow = row;
                        nextColumn = column - 1;
                    } else if (rotatedCell.equals(Cell.DOWN_TO_RIGHT)) {
                        nextRow = row + 1;
                        nextColumn = column;
                    } else if (rotatedCell.equals(Cell.TOP_TO_RIGHT)) {
                        nextRow = row - 1;
                        nextColumn = column;
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }

                if (isValid(board, nextRow, nextColumn) && visited[nextRow][nextColumn]) {
                    continue;
                }
                visited[row][column] = true;
                solutions += countSolutionsToLabyrinth(board, rotations, nextRow, nextColumn,
                        currentCellLocation, visited);
                visited[row][column] = false;
            }
        }
        board[row][column] = currentCell;
        return solutions;
    }

    private static boolean isValid(Cell[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
    }

    private static boolean canMove(Cell currentCell, int currentRow, int currentColumn, int nextRow,
                                   int nextColumn, Cell[][] board, boolean[][] visited) {
        if (visited[nextRow][nextColumn]) {
            return false;
        }
        if (currentRow == board.length - 1 && currentColumn == board[0].length - 1) {
            return true;
        }
        Cell nextCell = board[nextRow][nextColumn];

        switch (currentCell) {
            case HORIZONTAL:
                if (!(currentColumn < nextColumn && isMovingRight(nextCell))
                        && !(currentColumn > nextColumn && isMovingLeft(nextCell))) {
                    return false;
                }
                break;
            case VERTICAL:
                if (!(currentRow < nextRow && isMovingDown(nextCell))
                        && !(currentRow > nextRow && isMovingUp(nextCell))) {
                    return false;
                }
                break;
            case LEFT_TO_DOWN:
                if (!(currentRow < nextRow && isMovingDown(nextCell))
                        && !(currentColumn > nextColumn && isMovingLeft(nextCell))) {
                    return false;
                }
                break;
            case TOP_TO_RIGHT:
                if (!(currentRow > nextRow && isMovingUp(nextCell))
                        && !(currentColumn < nextColumn && isMovingRight(nextCell))) {
                    return false;
                }
                break;
            case DOWN_TO_RIGHT:
                if (!(currentRow < nextRow && isMovingDown(nextCell))
                        && !(currentColumn < nextColumn && isMovingRight(nextCell))) {
                    return false;
                }
                break;
            case LEFT_TO_TOP:
                if (!(currentRow > nextRow && isMovingUp(nextCell))
                        && !(currentColumn > nextColumn && isMovingLeft(nextCell))) {
                    return false;
                }
                break;
        }
        return true;
    }

    private static boolean isMovingDown(Cell nextCell) {
        return nextCell.equals(Cell.LEFT_TO_TOP)
                || nextCell.equals(Cell.TOP_TO_RIGHT)
                || nextCell.equals(Cell.VERTICAL);
    }

    private static boolean isMovingUp(Cell nextCell) {
        return nextCell.equals(Cell.DOWN_TO_RIGHT)
                || nextCell.equals(Cell.LEFT_TO_DOWN)
                || nextCell.equals(Cell.VERTICAL);
    }

    private static boolean isMovingRight(Cell nextCell) {
        return nextCell.equals(Cell.LEFT_TO_DOWN)
                || nextCell.equals(Cell.LEFT_TO_TOP)
                || nextCell.equals(Cell.HORIZONTAL);
    }

    private static boolean isMovingLeft(Cell nextCell) {
        return nextCell.equals(Cell.TOP_TO_RIGHT)
                || nextCell.equals(Cell.DOWN_TO_RIGHT)
                || nextCell.equals(Cell.HORIZONTAL);
    }

    private static Map<Cell, List<Cell>> getRotations() {
        Map<Cell, List<Cell>> rotations = new HashMap<>();

        List<Cell> straightRotationList = new ArrayList<>();
        straightRotationList.add(Cell.HORIZONTAL);
        straightRotationList.add(Cell.VERTICAL);

        List<Cell> turnRotationList = new ArrayList<>();
        turnRotationList.add(Cell.LEFT_TO_DOWN);
        turnRotationList.add(Cell.TOP_TO_RIGHT);
        turnRotationList.add(Cell.DOWN_TO_RIGHT);
        turnRotationList.add(Cell.LEFT_TO_TOP);

        rotations.put(Cell.HORIZONTAL, straightRotationList);
        rotations.put(Cell.VERTICAL, straightRotationList);
        rotations.put(Cell.LEFT_TO_DOWN, turnRotationList);
        rotations.put(Cell.TOP_TO_RIGHT, turnRotationList);
        rotations.put(Cell.DOWN_TO_RIGHT, turnRotationList);
        rotations.put(Cell.LEFT_TO_TOP, turnRotationList);
        return rotations;
    }

    private static Cell[][] readBoard(int rows, int columns) throws IOException {
        int boardRows = rows * 4 + 1;
        int boardColumns = columns * 4 + 1;

        char[][] ASCBoard = new char[boardRows][boardColumns];
        for (int i = 0; i < ASCBoard.length; i++) {
            ASCBoard[i] = FastReader.getLine().toCharArray();
        }

        Cell[][] board = new Cell[rows][columns];
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                int ASCBoardRow1 = row * 4 + 2;
                int ASCBoardRow2 = ASCBoardRow1 + 1;
                int ASCColumn1 = column * 4 + 3;
                int ASCColumn2 = ASCColumn1 - 1;

                if (ASCBoard[ASCBoardRow2][ASCColumn2] == '*') {
                    board[row][column] = Cell.LEFT_TO_DOWN;
                } else if (ASCBoard[ASCBoardRow1][ASCColumn1] == '*') {
                    board[row][column] = Cell.HORIZONTAL;
                } else {
                    board[row][column] = Cell.EMPTY;
                }
            }
        }
        return board;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
