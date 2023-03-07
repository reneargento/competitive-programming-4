package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/03/23.
 */
public class FoldingACube {

    private static final int MOVE_TOP = 0;
    private static final int MOVE_LEFT = 1;
    private static final int MOVE_RIGHT = 2;
    private static final int MOVE_DOWN = 3;

    private static class State {
        int numberOnTop;
        int numberOnRight;

        public State(int numberOnTop, int numberOnRight) {
            this.numberOnTop = numberOnTop;
            this.numberOnRight = numberOnRight;
        }
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        char[][] squares = new char[6][6];
        Cell startCell = null;

        for (int row = 0; row < squares.length; row++) {
            squares[row] = FastReader.next().toCharArray();
            for (int column = 0; column < squares[row].length; column++) {
                if (squares[row][column] == '#') {
                    startCell = new Cell(row, column);
                }
            }
        }

        boolean canFold = canFold(squares, startCell);
        outputWriter.printLine(canFold ? "can fold" : "cannot fold");
        outputWriter.flush();
    }

    private static boolean canFold(char[][] squares, Cell startCell) {
        State[][][] stateMoves = createStateMoves();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };
        boolean[][][][] visited = new boolean[squares.length][squares[0].length][6][6];
        return depthFirstSearch(squares, visited, neighborRows, neighborColumns, stateMoves, new HashSet<>(),
                0, 1, startCell.row, startCell.column);
    }

    private static boolean depthFirstSearch(char[][] squares, boolean[][][][] visited, int[] neighborRows,
                                            int[] neighborColumns, State[][][] stateMoves, Set<Integer> numbersOnTop,
                                            int numberOnTop, int numberOnRight, int row, int column) {
        visited[row][column][numberOnTop][numberOnRight] = true;
        numbersOnTop.add(numberOnTop);
        if (numbersOnTop.size() == 6) {
            return true;
        }

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            State newState;

            if (isValid(squares, neighborRow, neighborColumn)) {
                if (i == 0) {
                    newState = stateMoves[numberOnTop][numberOnRight][MOVE_TOP];
                } else if (i == 1) {
                    newState = stateMoves[numberOnTop][numberOnRight][MOVE_LEFT];
                } else if (i == 2) {
                    newState = stateMoves[numberOnTop][numberOnRight][MOVE_RIGHT];
                } else {
                    newState = stateMoves[numberOnTop][numberOnRight][MOVE_DOWN];
                }

                if (!visited[neighborRow][neighborColumn][newState.numberOnTop][newState.numberOnRight]
                        && squares[neighborRow][neighborColumn] == '#') {
                    boolean result = depthFirstSearch(squares, visited, neighborRows, neighborColumns, stateMoves,
                            numbersOnTop, newState.numberOnTop, newState.numberOnRight, neighborRow, neighborColumn);
                    if (result) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isValid(char[][] squares, int row, int column) {
        return row >= 0 && row < squares.length && column >= 0 && column < squares[row].length;
    }

    private static State[][][] createStateMoves() {
        State[][][] stateMoves = new State[6][6][4];
        stateMoves[0][1][MOVE_TOP] = new State(2, 1);
        stateMoves[0][1][MOVE_LEFT] = new State(1, 5);
        stateMoves[0][1][MOVE_RIGHT] = new State(4, 0);
        stateMoves[0][1][MOVE_DOWN] = new State(3, 1);

        stateMoves[0][2][MOVE_TOP] = new State(4, 2);
        stateMoves[0][2][MOVE_LEFT] = new State(2, 5);
        stateMoves[0][2][MOVE_RIGHT] = new State(3, 0);
        stateMoves[0][2][MOVE_DOWN] = new State(1, 2);

        stateMoves[0][3][MOVE_TOP] = new State(1, 3);
        stateMoves[0][3][MOVE_LEFT] = new State(3, 5);
        stateMoves[0][3][MOVE_RIGHT] = new State(2, 0);
        stateMoves[0][3][MOVE_DOWN] = new State(4, 3);

        stateMoves[0][4][MOVE_TOP] = new State(3, 4);
        stateMoves[0][4][MOVE_LEFT] = new State(4, 5);
        stateMoves[0][4][MOVE_RIGHT] = new State(1, 0);
        stateMoves[0][4][MOVE_DOWN] = new State(2, 4);

        stateMoves[1][0][MOVE_TOP] = new State(3, 0);
        stateMoves[1][0][MOVE_LEFT] = new State(0, 4);
        stateMoves[1][0][MOVE_RIGHT] = new State(5, 1);
        stateMoves[1][0][MOVE_DOWN] = new State(2, 0);

        stateMoves[1][2][MOVE_TOP] = new State(0, 2);
        stateMoves[1][2][MOVE_LEFT] = new State(2, 4);
        stateMoves[1][2][MOVE_RIGHT] = new State(3, 1);
        stateMoves[1][2][MOVE_DOWN] = new State(5, 2);

        stateMoves[1][3][MOVE_TOP] = new State(5, 3);
        stateMoves[1][3][MOVE_LEFT] = new State(3, 4);
        stateMoves[1][3][MOVE_RIGHT] = new State(2, 1);
        stateMoves[1][3][MOVE_DOWN] = new State(0, 3);

        stateMoves[1][5][MOVE_TOP] = new State(2, 5);
        stateMoves[1][5][MOVE_LEFT] = new State(5, 4);
        stateMoves[1][5][MOVE_RIGHT] = new State(0, 1);
        stateMoves[1][5][MOVE_DOWN] = new State(3, 5);

        stateMoves[2][0][MOVE_TOP] = new State(1, 0);
        stateMoves[2][0][MOVE_LEFT] = new State(0, 3);
        stateMoves[2][0][MOVE_RIGHT] = new State(5, 2);
        stateMoves[2][0][MOVE_DOWN] = new State(4, 0);

        stateMoves[2][1][MOVE_TOP] = new State(5, 1);
        stateMoves[2][1][MOVE_LEFT] = new State(1, 3);
        stateMoves[2][1][MOVE_RIGHT] = new State(4, 2);
        stateMoves[2][1][MOVE_DOWN] = new State(0, 1);

        stateMoves[2][4][MOVE_TOP] = new State(0, 4);
        stateMoves[2][4][MOVE_LEFT] = new State(4, 3);
        stateMoves[2][4][MOVE_RIGHT] = new State(1, 2);
        stateMoves[2][4][MOVE_DOWN] = new State(5, 4);

        stateMoves[2][5][MOVE_TOP] = new State(4, 5);
        stateMoves[2][5][MOVE_LEFT] = new State(5, 3);
        stateMoves[2][5][MOVE_RIGHT] = new State(0, 2);
        stateMoves[2][5][MOVE_DOWN] = new State(1, 5);

        stateMoves[3][0][MOVE_TOP] = new State(4, 0);
        stateMoves[3][0][MOVE_LEFT] = new State(0, 2);
        stateMoves[3][0][MOVE_RIGHT] = new State(5, 3);
        stateMoves[3][0][MOVE_DOWN] = new State(1, 0);

        stateMoves[3][1][MOVE_TOP] = new State(0, 1);
        stateMoves[3][1][MOVE_LEFT] = new State(1, 2);
        stateMoves[3][1][MOVE_RIGHT] = new State(4, 3);
        stateMoves[3][1][MOVE_DOWN] = new State(5, 1);

        stateMoves[3][4][MOVE_TOP] = new State(5, 4);
        stateMoves[3][4][MOVE_LEFT] = new State(4, 2);
        stateMoves[3][4][MOVE_RIGHT] = new State(1, 3);
        stateMoves[3][4][MOVE_DOWN] = new State(0, 4);

        stateMoves[3][5][MOVE_TOP] = new State(1, 5);
        stateMoves[3][5][MOVE_LEFT] = new State(5, 2);
        stateMoves[3][5][MOVE_RIGHT] = new State(0, 3);
        stateMoves[3][5][MOVE_DOWN] = new State(4, 5);

        stateMoves[4][0][MOVE_TOP] = new State(2, 0);
        stateMoves[4][0][MOVE_LEFT] = new State(0, 1);
        stateMoves[4][0][MOVE_RIGHT] = new State(5, 4);
        stateMoves[4][0][MOVE_DOWN] = new State(3, 0);

        stateMoves[4][2][MOVE_TOP] = new State(5, 2);
        stateMoves[4][2][MOVE_LEFT] = new State(2, 1);
        stateMoves[4][2][MOVE_RIGHT] = new State(3, 4);
        stateMoves[4][2][MOVE_DOWN] = new State(0, 2);

        stateMoves[4][3][MOVE_TOP] = new State(0, 3);
        stateMoves[4][3][MOVE_LEFT] = new State(3, 1);
        stateMoves[4][3][MOVE_RIGHT] = new State(2, 4);
        stateMoves[4][3][MOVE_DOWN] = new State(5, 3);

        stateMoves[4][5][MOVE_TOP] = new State(3, 5);
        stateMoves[4][5][MOVE_LEFT] = new State(5, 1);
        stateMoves[4][5][MOVE_RIGHT] = new State(0, 4);
        stateMoves[4][5][MOVE_DOWN] = new State(2, 5);

        stateMoves[5][1][MOVE_TOP] = new State(3, 1);
        stateMoves[5][1][MOVE_LEFT] = new State(1, 0);
        stateMoves[5][1][MOVE_RIGHT] = new State(4, 5);
        stateMoves[5][1][MOVE_DOWN] = new State(2, 1);

        stateMoves[5][2][MOVE_TOP] = new State(1, 2);
        stateMoves[5][2][MOVE_LEFT] = new State(2, 0);
        stateMoves[5][2][MOVE_RIGHT] = new State(3, 5);
        stateMoves[5][2][MOVE_DOWN] = new State(4, 2);

        stateMoves[5][3][MOVE_TOP] = new State(4, 3);
        stateMoves[5][3][MOVE_LEFT] = new State(3, 0);
        stateMoves[5][3][MOVE_RIGHT] = new State(2, 5);
        stateMoves[5][3][MOVE_DOWN] = new State(1, 3);

        stateMoves[5][4][MOVE_TOP] = new State(2, 4);
        stateMoves[5][4][MOVE_LEFT] = new State(4, 0);
        stateMoves[5][4][MOVE_RIGHT] = new State(1, 5);
        stateMoves[5][4][MOVE_DOWN] = new State(3, 4);

        return stateMoves;
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
