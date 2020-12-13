package chapter1.section6.n.output.formatting.easier;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/12/20.
 */
public class RobotMaps {

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
            return row == cell.row &&
                    column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        while (rows != 0 || columns != 0) {
            int startRow = FastReader.nextInt() - 1;
            int startColumn = FastReader.nextInt() - 1;

            char[][] map = readMap(rows, columns);
            moveRobot(map, startRow, startColumn, outputWriter);

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static char[][] readMap(int rows, int columns) throws IOException {
        char[][] map = new char[rows][columns];
        for (int r = 0; r < rows; r++) {
            String[] cells = FastReader.getLine().split(" ");

            for (int c = 0; c < columns; c++) {
                map[r][c] = cells[c].charAt(0);
            }
        }
        return map;
    }

    private static void moveRobot(char[][] map, int startRow, int startColumn, OutputWriter outputWriter) {
        char[][] exploredMap = new char[map.length][map[0].length];
        initMap(exploredMap);

        int row = startRow;
        int column = startColumn;
        exploredMap[row][column] = map[row][column];
        int movements = 0;

        int[] neighborRows = new int[]{-1, 0, 1, 0};
        int[] neighborColumns = new int[]{0, 1, 0, -1};
        Set<Cell> visited = new HashSet<>();
        visited.add(new Cell(startRow, startColumn));

        while (true) {
            Cell nextCell = null;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)) {
                    exploredMap[neighborRow][neighborColumn] = map[neighborRow][neighborColumn];

                    Cell cell = new Cell(neighborRow, neighborColumn);
                    if (!visited.contains(cell) && map[neighborRow][neighborColumn] == '0') {
                        if (nextCell == null) {
                            nextCell = cell;
                        }
                    }
                }
            }

            if (nextCell != null) {
                visited.add(nextCell);
                row = nextCell.row;
                column = nextCell.column;
                movements++;
            } else {
                break;
            }
        }

        outputWriter.printLine();
        printExploredMap(exploredMap, outputWriter);
        outputWriter.printLine();
        outputWriter.printLine("NUMBER OF MOVEMENTS: " + movements);
    }

    private static void printExploredMap(char[][] exploredMap, OutputWriter outputWriter) {
        int columns = exploredMap[0].length;
        printSeparator(columns, outputWriter);

        for (int r = 0; r < exploredMap.length; r++) {
            outputWriter.print("|");
            for (int c = 0; c < columns; c++) {
                outputWriter.print(" " + exploredMap[r][c] + " |");
            }
            outputWriter.printLine();

            printSeparator(columns, outputWriter);
        }
    }

    private static void printSeparator(int columns, OutputWriter outputWriter) {
        outputWriter.print("|");
        for (int c = 0; c < columns; c++) {
            outputWriter.print("---|");
        }
        outputWriter.printLine();
    }

    private static boolean isValid(char[][] map, int row, int column) {
        return row >= 0 && row < map.length && column >= 0 && column < map[0].length;
    }

    private static void initMap(char[][] map) {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                map[r][c] = '?';
            }
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        // Used to check EOF
        // If getLine() == null, it is a EOF
        // Otherwise, it returns the next line
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
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
