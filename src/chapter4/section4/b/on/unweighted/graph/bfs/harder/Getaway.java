package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/12/23.
 */
@SuppressWarnings("unchecked")
public class Getaway {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && column == cell.column;
        }
    }

    private static class State {
        Cell cell;
        int timeUnits;

        public State(Cell cell, int timeUnits) {
            this.cell = cell;
            this.timeUnits = timeUnits;
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            // Note that the problem description mentions these are "vertical roads", but these are in fact, horizontal
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);

            List<Cell>[][] restrictions = new List[rows][columns];
            int restrictionsNumber = FastReader.nextInt();
            for (int i = 0; i < restrictionsNumber; i++) {
                int row = FastReader.nextInt();
                int column = FastReader.nextInt();
                Cell restrictedCell = new Cell(FastReader.nextInt(), FastReader.nextInt());

                if (restrictions[row][column] == null) {
                    restrictions[row][column] = new ArrayList<>();
                }
                restrictions[row][column].add(restrictedCell);
            }

            Map<Integer, Cell> monitorByTimeMap = new HashMap<>();
            int crossroadsMonitored = FastReader.nextInt();
            for (int i = 0; i < crossroadsMonitored; i++) {
                int time = FastReader.nextInt();
                Cell cell = new Cell(FastReader.nextInt(), FastReader.nextInt());
                monitorByTimeMap.put(time, cell);
            }

            int minimumEscapeTime = computeMinimumEscapeTime(restrictions, monitorByTimeMap);
            outputWriter.printLine(minimumEscapeTime);
            line = FastReader.getLine();

            while (line != null && line.isEmpty()) {
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static int computeMinimumEscapeTime(List<Cell>[][] restrictions, Map<Integer, Cell> monitorByTimeMap) {
        boolean[][] visited = new boolean[restrictions.length][restrictions[0].length];
        Queue<State> queue = new LinkedList<>();
        Cell hideaway = new Cell(restrictions.length - 1, restrictions[0].length - 1);

        visited[0][0] = true;
        queue.offer(new State(new Cell(0, 0), 0));

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (cell.equals(hideaway)) {
                return state.timeUnits;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];
                Cell nextCell = new Cell(neighborRow, neighborColumn);

                if (isValid(visited, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]
                        && !isRestricted(restrictions, cell, nextCell)) {
                    if (monitorByTimeMap.containsKey(state.timeUnits + 1)
                            && monitorByTimeMap.get(state.timeUnits + 1).equals(nextCell)) {
                        Cell waitCell = new Cell(cell.row, cell.column);
                        queue.offer(new State(waitCell, state.timeUnits + 1));
                    } else {
                        visited[neighborRow][neighborColumn] = true;
                        queue.offer(new State(nextCell, state.timeUnits + 1));
                    }
                }
            }
        }
        return -1;
    }

    private static boolean isRestricted(List<Cell>[][] restrictions, Cell cell, Cell nextCell) {
        if (restrictions[cell.row][cell.column] == null) {
            return false;
        }

        for (Cell restrictedCell : restrictions[cell.row][cell.column]) {
            if (restrictedCell.equals(nextCell)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValid(boolean[][] visited, int row, int column) {
        return row >= 0 && row < visited.length && column >= 0 && column < visited[0].length;
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
