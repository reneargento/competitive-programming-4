package chapter4.section3.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/09/23.
 */
public class MillionaireMadness {

    private static class State implements Comparable<State> {
        int row;
        int column;
        int cost;

        State(int row, int column, int cost) {
            this.row = row;
            this.column = column;
            this.cost = cost;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(cost, other.cost);
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int[][] vault = new int[rows][columns];

        for (int row = 0; row < vault.length; row++) {
            for (int column = 0; column < vault[0].length; column++) {
                vault[row][column] = FastReader.nextInt();
            }
        }

        int shortestLadder = computeShortestLadder(vault);
        outputWriter.printLine(shortestLadder);
        outputWriter.flush();
    }

    private static int computeShortestLadder(int[][] vault) {
        PriorityQueue<State> priorityQueue = new PriorityQueue<>();
        boolean[][] visited = new boolean[vault.length][vault[0].length];
        int[][] bestLengths = new int[vault.length][vault[0].length];
        for (int[] values : bestLengths) {
            Arrays.fill(values, Integer.MAX_VALUE);
        }
        bestLengths[0][0] = 0;

        priorityQueue.offer(new State(0, 0, 0));
        while (!priorityQueue.isEmpty()) {
            State currentState = priorityQueue.poll();
            int row = currentState.row;
            int column = currentState.column;

            if (visited[row][column]) {
                continue;
            }
            visited[row][column] = true;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(vault, neighborRow, neighborColumn) && !visited[neighborRow][neighborColumn]) {
                    int jumpCost = getJumpCost(vault, row, column, neighborRow, neighborColumn);
                    int cost = Math.max(bestLengths[row][column], jumpCost);

                    if (cost < bestLengths[neighborRow][neighborColumn]) {
                        bestLengths[neighborRow][neighborColumn] = cost;
                        priorityQueue.offer(new State(neighborRow, neighborColumn, cost));
                    }
                }
            }
        }
        return bestLengths[vault.length - 1][vault[0].length - 1];
    }

    private static int getJumpCost(int[][] vault, int row, int column, int neighborRow, int neighborColumn) {
        int cost = 0;
        if (vault[neighborRow][neighborColumn] > vault[row][column]) {
            cost = vault[neighborRow][neighborColumn] - vault[row][column];
        }
        return cost;
    }

    private static boolean isValid(int[][] vault, int row, int column) {
        return row >= 0 && row < vault.length && column >= 0 && column < vault[0].length;
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
