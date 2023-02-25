package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/02/23.
 */
public class KnightInAWarGrid {

    private static class Result {
        int evenSquares;
        int oddSquares;

        public Result(int evenSquares, int oddSquares) {
            this.evenSquares = evenSquares;
            this.oddSquares = oddSquares;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            int dimension1Moves = FastReader.nextInt();
            int dimension2Moves = FastReader.nextInt();
            int[][] waysToReach = new int[rows][columns];

            int cellsWithWater = FastReader.nextInt();
            for (int i = 0; i < cellsWithWater; i++) {
                waysToReach[FastReader.nextInt()][FastReader.nextInt()] = -1;
            }

            Result result = computeTour(waysToReach, dimension1Moves, dimension2Moves);
            outputWriter.printLine(String.format("Case %d: %d %d", t, result.evenSquares, result.oddSquares));
        }
        outputWriter.flush();
    }

    private static Result computeTour(int[][] waysToReach, int dimension1Moves, int dimension2Moves) {
        int evenSquares = 0;
        int oddSquares = 0;
        boolean[][] visited = new boolean[waysToReach.length][waysToReach[0].length];
        int[] dimension1Move = { -dimension1Moves, -dimension1Moves, dimension1Moves, dimension1Moves };
        int[] dimension2Move = { -dimension2Moves, dimension2Moves, -dimension2Moves, dimension2Moves };
        int[][] moves = new int[][] { dimension1Move, dimension2Move };
        boolean hasDifferentMoves = (dimension1Moves != dimension2Moves);

        depthFirstSearch(waysToReach, moves, visited, 0, 0, hasDifferentMoves);

        for (int row = 0; row < waysToReach.length; row++) {
            for (int column = 0; column < waysToReach[row].length; column++) {
                if ((row == 0 && column == 0) || waysToReach[row][column] > 0) {
                    boolean isEven = waysToReach[row][column] % 2 == 0;
                    if (isEven) {
                        evenSquares++;
                    } else {
                        oddSquares++;
                    }
                }
            }
        }
        return new Result(evenSquares, oddSquares);
    }

    private static void depthFirstSearch(int[][] waysToReach, int[][] moves, boolean[][] visited, int row, int column,
                                         boolean hasDifferentMoves) {
        visited[row][column] = true;

        for (int m = 0; m < moves.length; m++) {
            // Handle edge case when dimension1Moves == dimension2Moves
            if (m == 1 && !hasDifferentMoves) {
                break;
            }
            for (int i = 0; i < moves[0].length; i++) {
                // Handle edge case when dimension1Moves == 0 || dimension2Moves == 0
                if ((i > 0 && moves[m][i] == moves[m][i - 1]
                        && moves[1 - m][i] == moves[1 - m][i - 1]) ||
                    (i > 1 && moves[m][i] == moves[m][i - 2]
                        && moves[1 - m][i] == moves[1 - m][i - 2])) {
                    continue;
                }

                int nextRow = row + moves[m][i];
                int nextColumn = column + moves[1 - m][i];
                if (isValid(waysToReach, nextRow, nextColumn) && waysToReach[nextRow][nextColumn] >= 0) {
                    waysToReach[nextRow][nextColumn]++;

                    if (!visited[nextRow][nextColumn]) {
                        depthFirstSearch(waysToReach, moves, visited, nextRow, nextColumn, hasDifferentMoves);
                    }
                }
            }
        }
    }

    private static boolean isValid(int[][] waysToReach, int row, int column) {
        return row >= 0 && row < waysToReach.length && column >= 0 && column < waysToReach[0].length;
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
