package chapter1.section6.p.time.waster.problems.harder;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/12/20.
 */
public class GraphicalEditor {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        char[][] table = null;

        while (line.charAt(0) != 'X') {
            String[] data = line.split(" ");
            switch (data[0]) {
                case "I":
                    int columns = Integer.parseInt(data[1]);
                    int rows = Integer.parseInt(data[2]);
                    table = new char[rows][columns];
                    clearTable(table);
                    break;
                case "C":
                    clearTable(table);
                    break;
                case "L":
                    int column = Integer.parseInt(data[1]) - 1;
                    int row = Integer.parseInt(data[2]) - 1;
                    char color = data[3].charAt(0);
                    if (isValid(table, row, column)) {
                        table[row][column] = color;
                    }
                    break;
                case "V":
                    column = Integer.parseInt(data[1]) - 1;
                    int row1 = Integer.parseInt(data[2]) - 1;
                    int row2 = Integer.parseInt(data[3]) - 1;
                    color = data[4].charAt(0);
                    int minRow = Math.min(row1, row2);
                    int maxRow = Math.max(row1, row2);
                    paint(table, minRow, maxRow, column, column, color);
                    break;
                case "H":
                    int column1 = Integer.parseInt(data[1]) - 1;
                    int column2 = Integer.parseInt(data[2]) - 1;
                    row = Integer.parseInt(data[3]) - 1;
                    color = data[4].charAt(0);
                    int minColumn = Math.min(column1, column2);
                    int maxColumn = Math.max(column1, column2);
                    paint(table, row, row, minColumn, maxColumn, color);
                    break;
                case "K":
                    int topColumn = Integer.parseInt(data[1]) - 1;
                    int topRow = Integer.parseInt(data[2]) - 1;
                    int bottomColumn = Integer.parseInt(data[3]) - 1;
                    int bottomRow = Integer.parseInt(data[4]) - 1;
                    color = data[5].charAt(0);
                    minRow = Math.min(topRow, bottomRow);
                    maxRow = Math.max(topRow, bottomRow);
                    minColumn = Math.min(topColumn, bottomColumn);
                    maxColumn = Math.max(topColumn, bottomColumn);
                    paint(table, minRow, maxRow, minColumn, maxColumn, color);
                    break;
                case "F":
                    int floodColumn = Integer.parseInt(data[1]) - 1;
                    int floodRow = Integer.parseInt(data[2]) - 1;
                    color = data[3].charAt(0);
                    floodFill(table, floodRow, floodColumn, color);
                    break;
                case "S":
                    outputWriter.printLine(data[1]);
                    printTable(table, outputWriter);
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void clearTable(char[][] table) {
        if (table == null) {
            return;
        }
        for (int row = 0; row < table.length; row++) {
            for (int column = 0; column < table[0].length; column++) {
                table[row][column] = 'O';
            }
        }
    }

    private static void paint(char[][] table, int row1, int row2, int column1, int column2, char color) {
        for (int row = row1; row <= row2 && row < table.length; row++) {
            for (int column = column1; column <= column2 && column < table[0].length; column++) {
                table[row][column] = color;
            }
        }
    }

    private static void floodFill(char[][] table, int row, int column, char color) {
        boolean[][] visited = new boolean[table.length][table[0].length];
        char originalColor = table[row][column];
        int[] neighborRows = {-1, 0, 0, 1};
        int[] neighborColumns = {0, -1, 1, 0};

        Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell(row, column));
        visited[row][column] = true;

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            row = cell.row;
            column = cell.column;

            table[row][column] = color;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(table, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]
                        && table[neighborRow][neighborColumn] == originalColor) {
                    queue.offer(new Cell(neighborRow, neighborColumn));
                    visited[neighborRow][neighborColumn] = true;
                }
            }
        }
    }

    private static boolean isValid(char[][] table, int row, int column) {
        return row >= 0 && row < table.length && column >= 0 && column < table[0].length;
    }

    private static void printTable(char[][] table, OutputWriter outputWriter) {
        for (int row = 0; row < table.length; row++) {
            for (int column = 0; column < table[0].length; column++) {
                outputWriter.print(table[row][column]);
            }
            outputWriter.printLine();
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
