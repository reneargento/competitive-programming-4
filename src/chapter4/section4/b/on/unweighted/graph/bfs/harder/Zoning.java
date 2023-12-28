package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/12/23.
 */
public class Zoning {

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
        int distance;

        public State(Cell cell, int distance) {
            this.cell = cell;
            this.distance = distance;
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = FastReader.nextInt();
        int[][] map = new int[dimension][dimension];
        Queue<State> queue = new LinkedList<>();

        int[][] distances = new int[map.length][map[0].length];
        for (int[] values : distances) {
            Arrays.fill(values, Integer.MAX_VALUE);
        }

        for (int row = 0; row < map.length; row++) {
            String columns = FastReader.getLine();
            for (int column = 0; column < columns.length(); column++) {
                map[row][column] = Character.getNumericValue(columns.charAt(column));
                if (map[row][column] == 3) {
                    Cell cell = new Cell(row, column);
                    queue.offer(new State(cell, 0));
                    distances[row][column] = 0;
                }
            }
        }

        int largestDistance = computeLargestDistance(map, queue, distances);
        outputWriter.printLine(largestDistance);
        outputWriter.flush();
    }

    private static int computeLargestDistance(int[][] map, Queue<State> queue, int[][] distances) {
        int largestDistance = 0;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)) {
                    int candidateDistance = distances[cell.row][cell.column] + 1;
                    if (candidateDistance < distances[neighborRow][neighborColumn]) {
                        distances[neighborRow][neighborColumn] = candidateDistance;
                        Cell nextCell = new Cell(neighborRow, neighborColumn);
                        queue.offer(new State(nextCell, state.distance + 1));

                        if (map[neighborRow][neighborColumn] == 1) {
                            largestDistance = Math.max(largestDistance, candidateDistance);
                        }
                    }
                }
            }
        }
        return largestDistance;
    }

    private static boolean isValid(int[][] map, int row, int column) {
        return row >= 0 && row < map.length && column >= 0 && column < map[0].length;
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
