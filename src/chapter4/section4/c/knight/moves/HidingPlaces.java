package chapter4.section4.c.knight.moves;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/12/23.
 */
public class HidingPlaces {

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
        int jumps;

        public State(Cell cell, int jumps) {
            this.cell = cell;
            this.jumps = jumps;
        }
    }

    private static class HidingPlace implements Comparable<HidingPlace> {
        int rank;
        char file;

        public HidingPlace(int rank, char file) {
            this.rank = rank;
            this.file = file;
        }

        @Override
        public int compareTo(HidingPlace other) {
            if (rank != other.rank) {
                return Integer.compare(other.rank, rank);
            }
            return Character.compare(file, other.file);
        }

        @Override
        public String toString() {
            return String.valueOf(file) + rank;
        }
    }

    private static class Result {
        List<HidingPlace> hidingPlaces;
        int maxJumps;

        public Result(List<HidingPlace> hidingPlaces, int maxJumps) {
            this.hidingPlaces = hidingPlaces;
            this.maxJumps = maxJumps;
        }
    }

    private static final int[] neighborRows = { -2, -2, 1, -1, 1, -1, 2, 2 };
    private static final int[] neighborColumns = { -1, 1, -2, -2, 2, 2, -1, 1 };
    private static final int INFINITE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String location = FastReader.getLine();
            int column = location.charAt(0) - 'a';
            int row = 8 - Character.getNumericValue(location.charAt(1));
            Cell initialPosition = new Cell(row, column);

            Result result = findHidingPlaces(initialPosition);
            List<HidingPlace> hidingPlaces = result.hidingPlaces;
            outputWriter.print(result.maxJumps);
            for (HidingPlace hidingPlace : hidingPlaces) {
                outputWriter.print(" " + hidingPlace);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static Result findHidingPlaces(Cell initialPosition) {
        boolean[][] visited = new boolean[8][8];
        int[][] distances = new int[8][8];
        for (int[] values : distances) {
            Arrays.fill(values, INFINITE);
        }
        int maxDistance = 0;
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(initialPosition, 0));
        distances[initialPosition.row][initialPosition.column] = 0;
        visited[initialPosition.row][initialPosition.column] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];
                int newDistance = state.jumps + 1;

                if (isValid(distances, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]) {
                    visited[neighborRow][neighborColumn] = true;
                    distances[neighborRow][neighborColumn] = newDistance;
                    maxDistance = Math.max(maxDistance, newDistance);
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    queue.offer(new State(nextCell, newDistance));
                }
            }
        }
        List<HidingPlace> hidingPlaces = buildHidingPlaces(distances, maxDistance);
        return new Result(hidingPlaces, maxDistance);
    }

    private static List<HidingPlace> buildHidingPlaces(int[][] distances, int maxDistance) {
        List<HidingPlace> hidingPlaces = new ArrayList<>();

        for (int row = 0; row < distances.length; row++) {
            for (int column = 0; column < distances[0].length; column++) {
                if (distances[row][column] == maxDistance) {
                    int rank = 8 - row;
                    char file = (char) ('a' + column);
                    hidingPlaces.add(new HidingPlace(rank, file));
                }
            }
        }
        Collections.sort(hidingPlaces);
        return hidingPlaces;
    }

    private static boolean isValid(int[][] distances, int row, int column) {
        return row >= 0 && row < distances.length && column >= 0 && column < distances[0].length;
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
