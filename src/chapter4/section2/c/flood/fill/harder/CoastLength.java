package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/03/23.
 */
public class CoastLength {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final char WATER = '0';
    private static final char LAND = '1';
    private static final char VISITED = '2';

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        char[][] map = new char[rows + 2][columns + 2];
        Arrays.fill(map[0], '0');
        Arrays.fill(map[rows + 1], '0');
        for (int row = 1; row < map.length - 1; row++) {
            String rowValue = FastReader.next();
            rowValue = '0' + rowValue + '0';
            map[row] = rowValue.toCharArray();
        }

        int coastLength = computeCoastLength(map);
        outputWriter.printLine(coastLength);
        outputWriter.flush();
    }

    private static int computeCoastLength(char[][] map) {
        int coastLength = 0;
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(0, 0));
        map[0][0] = VISITED;

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)) {
                    if (map[neighborRow][neighborColumn] == LAND) {
                        coastLength++;
                    } else if (map[neighborRow][neighborColumn] == WATER) {
                        map[neighborRow][neighborColumn] = VISITED;
                        queue.offer(new Cell(neighborRow, neighborColumn));
                    }
                }
            }
        }
        return coastLength;
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
