package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/09/21.
 */
public class Chocolates {

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
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        boolean[][] box = new boolean[rows][columns];

        int polygons = countPolygons(box);
        outputWriter.printLine(polygons);
        outputWriter.flush();
    }

    private static int countPolygons(boolean[][] box) {
        return countPolygons(box, 0, 0);
    }

    private static int countPolygons(boolean[][] box, int row, int column) {
        if (row == box.length) {
            return 0;
        }

        int count = 0;

        boolean[][] boxCopy = copyBox(box);

        boxCopy[row][column] = true;
        if (isPolygon(boxCopy)) {
            count++;
        }

        int nextRow;
        int nextColumn;

        if (column == box[0].length - 1) {
            nextRow = row + 1;
            nextColumn = 0;
        } else {
            nextRow = row;
            nextColumn = column + 1;
        }
        return count + countPolygons(box, nextRow, nextColumn) + countPolygons(boxCopy, nextRow, nextColumn);
    }

    private static boolean[][] copyBox(boolean[][] box) {
        boolean[][] boxCopy = new boolean[box.length][box[0].length];

        for (int row = 0; row < box.length; row++) {
            System.arraycopy(box[row], 0, boxCopy[row], 0, box[0].length);
        }
        return boxCopy;
    }

    private static boolean isPolygon(boolean[][] box) {
        return countComponents(box) == 1 && !hasHole(box);
    }

    private static boolean hasHole(boolean[][] box) {
        if (box.length < 3 || box[0].length < 3) {
            return false;
        }

        boolean[][] visited = new boolean[box.length][box[0].length];
        int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int row = 1; row < box.length - 1; row++) {
            for (int column = 1; column < box[0].length - 1; column++) {
                if (!visited[row][column] && !box[row][column]) {
                    visited[row][column] = true;
                    if (!floodFill(box, row, column, visited, neighborRows, neighborColumns, false)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static int countComponents(boolean[][] box) {
        int components = 0;
        boolean[][] visited = new boolean[box.length][box[0].length];
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int row = 0; row < box.length; row++) {
            for (int column = 0; column < box[0].length; column++) {
                if (!visited[row][column] && box[row][column]) {
                    components++;
                    visited[row][column] = true;
                    floodFill(box, row, column, visited, neighborRows, neighborColumns, true);
                }
            }
        }
        return components;
    }

    private static boolean floodFill(boolean[][] box, int row, int column, boolean[][] visited,
                                     int[] neighborRows, int[] neighborColumns, boolean filled) {
        Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell(row, column));

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                // Reached outside the box - not a hole
                if (!isValidCell(neighborRow, neighborColumn, box) && !filled) {
                    return true;
                }

                if (isValidCell(neighborRow, neighborColumn, box)
                        && box[neighborRow][neighborColumn] == filled
                        && !visited[neighborRow][neighborColumn]) {
                    visited[neighborRow][neighborColumn] = true;
                    queue.offer(new Cell(neighborRow, neighborColumn));
                }
            }
        }
        return false;
    }

    private static boolean isValidCell(int row, int column, boolean[][] box) {
        return row >= 0 && row < box.length && column >= 0 && column < box[0].length;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
