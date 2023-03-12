package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/03/23.
 */
public class TheDieIsCast {

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

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int columns = FastReader.nextInt();
        int rows = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };
        int throwNumber = 1;

        while (columns != 0 || rows != 0) {
            char[][] picture = new char[rows][columns];
            for (int row = 0; row < picture.length; row++) {
                picture[row] = FastReader.next().toCharArray();
            }
            List<Integer> dots = computeDots(picture, neighborRows, neighborColumns);

            outputWriter.printLine("Throw " + throwNumber);
            outputWriter.print(dots.get(0));
            for (int i = 1; i < dots.size(); i++) {
                outputWriter.print(" " + dots.get(i));
            }
            outputWriter.printLine();
            outputWriter.printLine();

            throwNumber++;
            columns = FastReader.nextInt();
            rows = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeDots(char[][] picture, int[] neighborRows, int[] neighborColumns) {
        List<Integer> dots = new ArrayList<>();
        boolean[][] visited = new boolean[picture.length][picture[0].length];

        for (int row = 0; row < picture.length; row++) {
            for (int column = 0; column < picture[0].length; column++) {
                Cell cell = new Cell(row, column);
                if (picture[row][column] != '.' && !visited[cell.row][cell.column]) {
                    Set<Cell> dieCells = new HashSet<>();
                    computeDieCells(picture, neighborRows, neighborColumns, dieCells, visited, row, column);
                    int dot = computeDotNumber(picture, neighborRows, neighborColumns, dieCells);
                    dots.add(dot);
                }
            }
        }
        Collections.sort(dots);
        return dots;
    }

    private static Set<Cell> computeDieCells(char[][] picture, int[] neighborRows, int[] neighborColumns,
                                             Set<Cell> dieCells, boolean[][] visited, int row, int column) {
        dieCells.add(new Cell(row, column));
        visited[row][column] = true;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(picture, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn]
                    && picture[neighborRow][neighborColumn] != '.') {
                computeDieCells(picture, neighborRows, neighborColumns, dieCells, visited, neighborRow, neighborColumn);
            }
        }
        return dieCells;
    }

    private static int computeDotNumber(char[][] picture, int[] neighborRows, int[] neighborColumns, Set<Cell> dieCells) {
        int dotNumber = 0;

        for (Cell dieCell : dieCells) {
            if (picture[dieCell.row][dieCell.column] != '.') {
                if (picture[dieCell.row][dieCell.column] == 'X') {
                    dotNumber++;
                    clearDot(picture, neighborRows, neighborColumns, dieCell.row, dieCell.column);
                }
            }
            picture[dieCell.row][dieCell.column] = '.';
        }
        return dotNumber;
    }

    private static void clearDot(char[][] picture, int[] neighborRows, int[] neighborColumns, int row, int column) {
        picture[row][column] = '.';
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(picture, neighborRow, neighborColumn) && picture[neighborRow][neighborColumn] == 'X') {
                clearDot(picture, neighborRows, neighborColumns, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean isValid(char[][] picture, int row, int column) {
        return row >= 0 && row < picture.length && column >= 0 && column < picture[0].length;
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
