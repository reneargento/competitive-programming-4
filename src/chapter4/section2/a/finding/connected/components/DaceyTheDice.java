package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/03/23.
 */
public class DaceyTheDice {

    private static final int MOVE_TOP = 0;
    private static final int MOVE_LEFT = 1;
    private static final int MOVE_RIGHT = 2;
    private static final int MOVE_DOWN = 3;

    private static class State {
        int pointsUp;
        int pointsRight;

        public State(int pointsUp, int pointsRight) {
            this.pointsUp = pointsUp;
            this.pointsRight = pointsRight;
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
        int tests = FastReader.nextInt();
        State[][][] stateMoves = createStateMoves();

        for (int t = 0; t < tests; t++) {
            int forestLength = FastReader.nextInt();
            char[][] forest = new char[forestLength][forestLength];
            Cell initialLocation = null;

            for (int row = 0; row < forest.length; row++) {
                forest[row] = FastReader.next().toCharArray();

                for (int column = 0; column < forest[row].length; column++) {
                    if (forest[row][column] == 'S') {
                        initialLocation = new Cell(row, column);
                    }
                }
            }

            boolean canGetInsideHome = canGetInsideHome(forest, stateMoves, initialLocation);
            outputWriter.printLine(canGetInsideHome ? "Yes" : "No");
        }
        outputWriter.flush();
    }

    private static boolean canGetInsideHome(char[][] forest, State[][][] stateMoves, Cell initialLocation) {
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };
        boolean[][][][] visited = new boolean[forest.length][forest[0].length][7][7];
        return depthFirstSearch(forest, visited, neighborRows, neighborColumns, stateMoves, 1, 2,
                initialLocation.row, initialLocation.column);
    }

    private static boolean depthFirstSearch(char[][] forest, boolean[][][][] visited, int[] neighborRows,
                                            int[] neighborColumns, State[][][] stateMoves, int pointsUp, int pointsRight,
                                            int row, int column) {
        if (forest[row][column] == 'H' && pointsUp == 2) {
            return true;
        }

        visited[row][column][pointsUp][pointsRight] = true;
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            State neighborState;
            if (i == 0) {
                neighborState = stateMoves[pointsUp][pointsRight][MOVE_TOP];
            } else if (i == 1) {
                neighborState = stateMoves[pointsUp][pointsRight][MOVE_LEFT];
            } else if (i == 2) {
                neighborState = stateMoves[pointsUp][pointsRight][MOVE_RIGHT];
            } else {
                neighborState = stateMoves[pointsUp][pointsRight][MOVE_DOWN];
            }

            if (isValid(forest, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn][neighborState.pointsUp][neighborState.pointsRight]
                    && forest[neighborRow][neighborColumn] != '*') {

                boolean canGetInsideHome = depthFirstSearch(forest, visited, neighborRows, neighborColumns, stateMoves,
                        neighborState.pointsUp, neighborState.pointsRight, neighborRow, neighborColumn);
                if (canGetInsideHome) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValid(char[][] forest, int row, int column) {
        return row >= 0 && row < forest.length && column >= 0 && column < forest[row].length;
    }

    private static State[][][] createStateMoves() {
        // [pointsUp][pointsRight][moveDirection] = newState
        State[][][] stateMoves = new State[7][7][4];
        stateMoves[1][2][MOVE_TOP] = new State(3, 2);
        stateMoves[1][2][MOVE_LEFT] = new State(2, 6);
        stateMoves[1][2][MOVE_RIGHT] = new State(5, 1);
        stateMoves[1][2][MOVE_DOWN] = new State(4, 2);

        stateMoves[1][3][MOVE_TOP] = new State(5, 3);
        stateMoves[1][3][MOVE_LEFT] = new State(3, 6);
        stateMoves[1][3][MOVE_RIGHT] = new State(4, 1);
        stateMoves[1][3][MOVE_DOWN] = new State(2, 3);

        stateMoves[1][4][MOVE_TOP] = new State(2, 4);
        stateMoves[1][4][MOVE_LEFT] = new State(4, 6);
        stateMoves[1][4][MOVE_RIGHT] = new State(3, 1);
        stateMoves[1][4][MOVE_DOWN] = new State(5, 4);

        stateMoves[1][5][MOVE_TOP] = new State(4, 5);
        stateMoves[1][5][MOVE_LEFT] = new State(5, 6);
        stateMoves[1][5][MOVE_RIGHT] = new State(2, 1);
        stateMoves[1][5][MOVE_DOWN] = new State(3, 5);

        stateMoves[2][1][MOVE_TOP] = new State(4, 1);
        stateMoves[2][1][MOVE_LEFT] = new State(1, 5);
        stateMoves[2][1][MOVE_RIGHT] = new State(6, 2);
        stateMoves[2][1][MOVE_DOWN] = new State(3, 1);

        stateMoves[2][3][MOVE_TOP] = new State(1, 3);
        stateMoves[2][3][MOVE_LEFT] = new State(3, 5);
        stateMoves[2][3][MOVE_RIGHT] = new State(4, 2);
        stateMoves[2][3][MOVE_DOWN] = new State(6, 3);

        stateMoves[2][4][MOVE_TOP] = new State(6, 4);
        stateMoves[2][4][MOVE_LEFT] = new State(4, 5);
        stateMoves[2][4][MOVE_RIGHT] = new State(3, 2);
        stateMoves[2][4][MOVE_DOWN] = new State(1, 4);

        stateMoves[2][6][MOVE_TOP] = new State(3, 6);
        stateMoves[2][6][MOVE_LEFT] = new State(6, 5);
        stateMoves[2][6][MOVE_RIGHT] = new State(1, 2);
        stateMoves[2][6][MOVE_DOWN] = new State(4, 6);

        stateMoves[3][1][MOVE_TOP] = new State(2, 1);
        stateMoves[3][1][MOVE_LEFT] = new State(1, 4);
        stateMoves[3][1][MOVE_RIGHT] = new State(6, 3);
        stateMoves[3][1][MOVE_DOWN] = new State(5, 1);

        stateMoves[3][2][MOVE_TOP] = new State(6, 2);
        stateMoves[3][2][MOVE_LEFT] = new State(2, 4);
        stateMoves[3][2][MOVE_RIGHT] = new State(5, 3);
        stateMoves[3][2][MOVE_DOWN] = new State(1, 2);

        stateMoves[3][5][MOVE_TOP] = new State(1, 5);
        stateMoves[3][5][MOVE_LEFT] = new State(5, 4);
        stateMoves[3][5][MOVE_RIGHT] = new State(2, 3);
        stateMoves[3][5][MOVE_DOWN] = new State(6, 5);

        stateMoves[3][6][MOVE_TOP] = new State(5, 6);
        stateMoves[3][6][MOVE_LEFT] = new State(6, 4);
        stateMoves[3][6][MOVE_RIGHT] = new State(1, 3);
        stateMoves[3][6][MOVE_DOWN] = new State(2, 6);

        stateMoves[4][1][MOVE_TOP] = new State(5, 1);
        stateMoves[4][1][MOVE_LEFT] = new State(1, 3);
        stateMoves[4][1][MOVE_RIGHT] = new State(6, 4);
        stateMoves[4][1][MOVE_DOWN] = new State(2, 1);

        stateMoves[4][2][MOVE_TOP] = new State(1, 2);
        stateMoves[4][2][MOVE_LEFT] = new State(2, 3);
        stateMoves[4][2][MOVE_RIGHT] = new State(5, 4);
        stateMoves[4][2][MOVE_DOWN] = new State(6, 2);

        stateMoves[4][5][MOVE_TOP] = new State(6, 5);
        stateMoves[4][5][MOVE_LEFT] = new State(5, 3);
        stateMoves[4][5][MOVE_RIGHT] = new State(2, 4);
        stateMoves[4][5][MOVE_DOWN] = new State(1, 5);

        stateMoves[4][6][MOVE_TOP] = new State(2, 6);
        stateMoves[4][6][MOVE_LEFT] = new State(6, 3);
        stateMoves[4][6][MOVE_RIGHT] = new State(1, 4);
        stateMoves[4][6][MOVE_DOWN] = new State(5, 6);

        stateMoves[5][1][MOVE_TOP] = new State(3, 1);
        stateMoves[5][1][MOVE_LEFT] = new State(1, 2);
        stateMoves[5][1][MOVE_RIGHT] = new State(6, 5);
        stateMoves[5][1][MOVE_DOWN] = new State(4, 1);

        stateMoves[5][3][MOVE_TOP] = new State(6, 3);
        stateMoves[5][3][MOVE_LEFT] = new State(3, 2);
        stateMoves[5][3][MOVE_RIGHT] = new State(4, 5);
        stateMoves[5][3][MOVE_DOWN] = new State(1, 3);

        stateMoves[5][4][MOVE_TOP] = new State(1, 4);
        stateMoves[5][4][MOVE_LEFT] = new State(4, 2);
        stateMoves[5][4][MOVE_RIGHT] = new State(3, 5);
        stateMoves[5][4][MOVE_DOWN] = new State(6, 4);

        stateMoves[5][6][MOVE_TOP] = new State(4, 6);
        stateMoves[5][6][MOVE_LEFT] = new State(6, 2);
        stateMoves[5][6][MOVE_RIGHT] = new State(1, 5);
        stateMoves[5][6][MOVE_DOWN] = new State(3, 6);

        stateMoves[6][2][MOVE_TOP] = new State(4, 2);
        stateMoves[6][2][MOVE_LEFT] = new State(2, 1);
        stateMoves[6][2][MOVE_RIGHT] = new State(5, 6);
        stateMoves[6][2][MOVE_DOWN] = new State(3, 2);

        stateMoves[6][3][MOVE_TOP] = new State(2, 3);
        stateMoves[6][3][MOVE_LEFT] = new State(3, 1);
        stateMoves[6][3][MOVE_RIGHT] = new State(4, 6);
        stateMoves[6][3][MOVE_DOWN] = new State(5, 3);

        stateMoves[6][4][MOVE_TOP] = new State(5, 4);
        stateMoves[6][4][MOVE_LEFT] = new State(4, 1);
        stateMoves[6][4][MOVE_RIGHT] = new State(3, 6);
        stateMoves[6][4][MOVE_DOWN] = new State(2, 4);

        stateMoves[6][5][MOVE_TOP] = new State(3, 5);
        stateMoves[6][5][MOVE_LEFT] = new State(5, 1);
        stateMoves[6][5][MOVE_RIGHT] = new State(2, 6);
        stateMoves[6][5][MOVE_DOWN] = new State(4, 5);

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
