package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/10/23.
 */
public class Grid {

    private static class State {
        int row;
        int column;
        int moves;

        public State(int row, int column, int moves) {
            this.row = row;
            this.column = column;
            this.moves = moves;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[][] grid = new int[FastReader.nextInt()][FastReader.nextInt()];
        for (int row = 0; row < grid.length; row++) {
            String rowValue = FastReader.getLine();
            for (int column = 0; column < rowValue.length(); column++) {
                int digit = Character.getNumericValue(rowValue.charAt(column));
                grid[row][column] = digit;
            }
        }
        int minimumMoves = computeMinimumMoves(grid);
        outputWriter.printLine(minimumMoves);
        outputWriter.flush();
    }

    private static int computeMinimumMoves(int[][] grid) {
        State state = new State(0, 0, 0);
        Queue<State> queue = new LinkedList<>();
        queue.offer(state);

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.row == grid.length - 1 && currentState.column == grid[0].length - 1) {
                return currentState.moves;
            }

            int digit = grid[currentState.row][currentState.column];
            int[] neighborRows = { -digit, 0, 0, digit };
            int[] neighborColumns = { 0, -digit, digit, 0 };

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = currentState.row + neighborRows[i];
                int neighborColumn = currentState.column + neighborColumns[i];
                if (isValid(grid, neighborRow, neighborColumn) && !visited[neighborRow][neighborColumn]) {
                    visited[neighborRow][neighborColumn] = true;
                    queue.offer(new State(neighborRow, neighborColumn, currentState.moves + 1));
                }
            }
        }
        return -1;
    }

    private static boolean isValid(int[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[row].length;
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