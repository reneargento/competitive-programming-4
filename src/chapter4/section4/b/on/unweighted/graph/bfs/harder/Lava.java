package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/12/23.
 */
public class Lava {

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

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static class State {
        Cell cell;
        int moveNumber;

        public State(Cell cell, int moveNumber) {
            this.cell = cell;
            this.moveNumber = moveNumber;
        }
    }

    private static final char START_TILE = 'S';
    private static final char GOAL_TILE = 'G';
    private static final char LAVA_TILE = 'B';
    private static final int[] neighborRowsFather = { -1, 0, 0, 1 };
    private static final int[] neighborColumnsFather = { 0, -1, 1, 0 };
    private static final int LOST = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int elsaMaxStepLength = FastReader.nextInt();
        int fatherMaxStepLength = FastReader.nextInt();
        char[][] map = new char[FastReader.nextInt()][FastReader.nextInt()];
        Cell startTile = null;
        List<Cell> safeCells = new ArrayList<>();

        for (int row = 0; row < map.length; row++) {
            String columns = FastReader.getLine();
            for (int column = 0; column < columns.length(); column++) {
                char tile = columns.charAt(column);
                map[row][column] = tile;

                if (tile == START_TILE) {
                    startTile = new Cell(row, column);
                }
                if (tile != LAVA_TILE) {
                    safeCells.add(new Cell(row, column));
                }
            }
        }

        String result = computeGameResult(map, elsaMaxStepLength, fatherMaxStepLength, startTile, safeCells);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String computeGameResult(char[][] map, int elsaMaxStepLength, int fatherMaxStepLength,
                                            Cell startTile, List<Cell> safeCells) {
        Map<Cell, List<Cell>> nextCellElsaMap = computeNextElsaCells(safeCells, elsaMaxStepLength);
        int movesElsa = computeMovesNumber(map, elsaMaxStepLength, null, null,
                startTile, nextCellElsaMap, false);
        int movesFather = computeMovesNumber(map, fatherMaxStepLength, neighborRowsFather, neighborColumnsFather,
                startTile, null, true);

        if (movesFather == LOST && movesElsa == LOST) {
            return "NO WAY";
        } else if (movesElsa == LOST || movesFather < movesElsa) {
            return "NO CHANCE";
        } else if (movesFather == LOST || movesElsa < movesFather) {
            return "GO FOR IT";
        } else {
            return "SUCCESS";
        }
    }

    private static int computeMovesNumber(char[][] map, int maxStepLength, int[] neighborRows, int[] neighborColumns,
                                          Cell startTile, Map<Cell, List<Cell>> nextCellElsaMap, boolean isFather) {
        Queue<State> queue = new LinkedList<>();
        boolean[][] visited = new boolean[map.length][map[0].length];

        queue.offer(new State(startTile, 0));
        visited[startTile.row][startTile.column] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            if (map[cell.row][cell.column] == GOAL_TILE) {
                return state.moveNumber;
            }

            if (isFather) {
                for (int i = 0; i < neighborRows.length; i++) {
                    for (int length = 1; length <= maxStepLength; length++) {
                        int neighborRow = cell.row + neighborRows[i] * length;
                        int neighborColumn = cell.column + neighborColumns[i] * length;

                        if (isValid(map, neighborRow, neighborColumn)
                                && !visited[neighborRow][neighborColumn]
                                && map[neighborRow][neighborColumn] != LAVA_TILE) {
                            Cell nextCell = new Cell(neighborRow, neighborColumn);
                            visited[neighborRow][neighborColumn] = true;
                            queue.offer(new State(nextCell, state.moveNumber + 1));
                        }
                    }
                }
            } else {
                List<Cell> nextCellsElsa = nextCellElsaMap.get(cell);
                for (Cell nextCell : nextCellsElsa) {
                    int neighborRow = nextCell.row;
                    int neighborColumn = nextCell.column;

                    if (!visited[neighborRow][neighborColumn]) {
                        visited[neighborRow][neighborColumn] = true;
                        queue.offer(new State(nextCell, state.moveNumber + 1));
                    }
                }
            }
        }
        return LOST;
    }

    private static Map<Cell, List<Cell>> computeNextElsaCells(List<Cell> safeCells, int maxStepLength) {
        Map<Cell, List<Cell>> nextCellElsaMap = new HashMap<>();
        for (Cell cell : safeCells) {
            nextCellElsaMap.put(cell, new ArrayList<>());
        }

        for (int tile1 = 0; tile1 < safeCells.size(); tile1++) {
            Cell safeCell1 = safeCells.get(tile1);

            for (int tile2 = tile1 + 1; tile2 < safeCells.size(); tile2++) {
                Cell safeCell2 = safeCells.get(tile2);
                if (distanceBetweenPointsNoSqrt(safeCell1, safeCell2) <= maxStepLength * maxStepLength) {
                    nextCellElsaMap.get(safeCell1).add(safeCell2);
                    nextCellElsaMap.get(safeCell2).add(safeCell1);
                }
            }
        }
        return nextCellElsaMap;
    }

    private static double distanceBetweenPointsNoSqrt(Cell tile1, Cell tile2) {
        return Math.pow(tile1.row - tile2.row, 2) + Math.pow(tile1.column - tile2.column, 2);
    }

    private static boolean isValid(char[][] map, int row, int column) {
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
