package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/12/23.
 */
public class Slikar {

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
        int minutes;

        public State(Cell cell, int minutes) {
            this.cell = cell;
            this.minutes = minutes;
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };
    private static final char PAINTER_HEDGEHOGS = 'S';
    private static final char BEAVERS_DEN = 'D';
    private static final char FLOOD = '*';
    private static final char EMPTY = '.';
    private static final int IMPOSSIBLE = -1;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Cell startCell = null;
        Queue<State> floodQueue = new LinkedList<>();

        char[][] forest = new char[FastReader.nextInt()][FastReader.nextInt()];
        for (int row = 0; row < forest.length; row++) {
            String columns = FastReader.getLine();
            for (int column = 0; column < columns.length(); column++) {
                char value = columns.charAt(column);
                if (value == PAINTER_HEDGEHOGS) {
                    startCell = new Cell(row, column);
                } else if (value == FLOOD) {
                    Cell cell = new Cell(row, column);
                    floodQueue.offer(new State(cell, 0));
                }
                forest[row][column] = value;
            }
        }

        int shortestTimeToEscape = computeShortestTimeToEscape(forest, floodQueue, startCell);
        if (shortestTimeToEscape != IMPOSSIBLE) {
            outputWriter.printLine(shortestTimeToEscape);
        } else {
            outputWriter.printLine("KAKTUS");
        }
        outputWriter.flush();
    }

    private static int computeShortestTimeToEscape(char[][] forest, Queue<State> floodQueue, Cell startCell) {
        Queue<State> charactersQueue = new LinkedList<>();
        boolean[][] visited = new boolean[forest.length][forest[0].length];

        charactersQueue.offer(new State(startCell, 0));
        visited[startCell.row][startCell.column] = true;

        int time = 1;
        while (!charactersQueue.isEmpty()) {
            processQueue(forest, floodQueue, null, time, false, PAINTER_HEDGEHOGS);
            int characterResult = processQueue(forest, charactersQueue, visited, time, true, BEAVERS_DEN);

            if (characterResult != IMPOSSIBLE) {
                return characterResult;
            }
            time++;
        }
        return IMPOSSIBLE;
    }

    private static int processQueue(char[][] forest, Queue<State> queue, boolean[][] visited, int time,
                                    boolean isCharacter, int acceptedCellState2) {
        while (!queue.isEmpty() && queue.peek().minutes < time) {
            State state = queue.poll();
            Cell cell = state.cell;
            if (forest[cell.row][cell.column] == BEAVERS_DEN) {
                return state.minutes;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(forest, neighborRow, neighborColumn)
                        && (!isCharacter || !visited[neighborRow][neighborColumn])
                        && (forest[neighborRow][neighborColumn] == EMPTY
                              || forest[neighborRow][neighborColumn] == acceptedCellState2)) {
                    if (isCharacter) {
                        visited[neighborRow][neighborColumn] = true;
                    } else {
                        forest[neighborRow][neighborColumn] = FLOOD;
                    }
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    queue.offer(new State(nextCell, state.minutes + 1));
                }
            }
        }
        return IMPOSSIBLE;
    }

    private static boolean isValid(char[][] forest, int row, int column) {
        return row >= 0 && row < forest.length && column >= 0 && column < forest[0].length;
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
