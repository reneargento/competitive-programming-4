package chapter5.section2.g.grid;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/04/25.
 */
public class BeeHousePerimeter {

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

        int r = FastReader.nextInt();
        int k = FastReader.nextInt();
        Set<Integer> houseCells = new HashSet<>();
        for (int i = 0; i < k; i++) {
            houseCells.add(FastReader.nextInt());
        }

        int perimeter = computePerimeter(r, houseCells);
        outputWriter.printLine(perimeter);
        outputWriter.flush();
    }

    private static int computePerimeter(int r, Set<Integer> houseCells) {
        int perimeter = 0;
        int[][] grid = buildGrid(r);
        boolean[][] visited = new boolean[grid.length][grid.length];

        int[] neighborRows =    { 0, -1, 0, 1, -1, 1 };
        int[] neighborColumns = { -1, 0, 1, 0, -1, 1 };

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(0, 0));
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            int row = cell.row;
            int column = cell.column;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(grid, neighborRow, neighborColumn)) {
                    int neighborValue = grid[neighborRow][neighborColumn];
                    boolean isPartOfHouse = houseCells.contains(neighborValue);
                    if (isPartOfHouse) {
                        perimeter++;
                    }

                    if (!visited[neighborRow][neighborColumn]
                            && !isPartOfHouse) {
                        visited[neighborRow][neighborColumn] = true;
                        queue.offer(new Cell(neighborRow, neighborColumn));
                    }
                }
            }
        }
        return perimeter;
    }

    private static boolean isValid(int[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    private static int[][] buildGrid(int r) {
        int gridDimension = r * 2 + 1;
        int[][] grid = new int[gridDimension][gridDimension];
        for (int[] row : grid) {
            Arrays.fill(row, -1);
        }

        int middleRow = grid.length / 2;
        int numbersInRow = r;
        int houseId = 1;

        for (int row = 1; row < grid.length - 1; row++) {
            int startColumn;
            if (row < middleRow) {
                startColumn = 1;
            } else {
                startColumn = gridDimension - 1 - numbersInRow;
            }

            int numbersAdded = 0;
            for (int column = startColumn; column < grid[row].length - 1; column++) {
                numbersAdded++;
                grid[row][column] = houseId;

                houseId++;
                if (numbersAdded == numbersInRow) {
                    break;
                }
            }

            if (row < middleRow) {
                numbersInRow++;
            } else {
                numbersInRow--;
            }
        }
        return grid;
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
