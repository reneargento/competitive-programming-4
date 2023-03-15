package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/03/23.
 */
public class OneZeroKindsOfPeople {

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
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        char[][] map = new char[rows][columns];
        for (int row = 0; row < map.length; row++) {
            map[row] = FastReader.next().toCharArray();
        }

        int[][] componentIDs = computeComponentIDs(map, neighborRows, neighborColumns);

        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            int row1 = FastReader.nextInt() - 1;
            int column1 = FastReader.nextInt() - 1;
            int row2 = FastReader.nextInt() - 1;
            int column2 = FastReader.nextInt() - 1;

            if (componentIDs[row1][column1] == componentIDs[row2][column2]) {
                if (map[row1][column1] == '0') {
                    outputWriter.printLine("binary");
                } else {
                    outputWriter.printLine("decimal");
                }
            } else {
                outputWriter.printLine("neither");
            }
        }
        outputWriter.flush();
    }

    private static int[][] computeComponentIDs(char[][] map, int[] neighborRows, int[] neighborColumns) {
        int[][] componentIDs = new int[map.length][map[0].length];
        int componentID = 1;

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[0].length; column++) {
                if (componentIDs[row][column] == 0) {
                    floodFill(map, neighborRows, neighborColumns, componentIDs, componentID, map[row][column], row,
                            column);
                    componentID++;
                }
            }
        }
        return componentIDs;
    }

    private static void floodFill(char[][] map, int[] neighborRows, int[] neighborColumns, int[][] componentIDs,
                                  int componentID, char value, int row, int column) {
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(row, column));
        componentIDs[row][column] = componentID;

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)
                        && componentIDs[neighborRow][neighborColumn] == 0
                        && map[neighborRow][neighborColumn] == value) {
                    componentIDs[neighborRow][neighborColumn] = componentID;
                    queue.offer(new Cell(neighborRow, neighborColumn));
                }
            }
        }
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
