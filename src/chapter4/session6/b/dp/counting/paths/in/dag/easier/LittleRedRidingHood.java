package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/05/24.
 */
public class LittleRedRidingHood {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final int[] neighborRows = { 0, -1 };
    private static final int[] neighborColumns = { 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int width = FastReader.nextInt();
        int height = FastReader.nextInt();

        while (width != 0 || height != 0) {
            boolean[][] wolfLocations = new boolean[height + 1][width + 1];

            int wolfLocationsNumber = FastReader.nextInt();
            for (int i = 0; i < wolfLocationsNumber; i++) {
                int column = FastReader.nextInt();
                int row = wolfLocations.length - 1 - FastReader.nextInt();
                wolfLocations[row][column] = true;
            }

            long paths = countPaths(wolfLocations);
            if (paths == 0) {
                outputWriter.printLine("There is no path.");
            } else {
                String pathNumber;
                if (paths == 1) {
                    pathNumber = "is one path";
                } else {
                    pathNumber = String.format("are %d paths", paths);
                }
                outputWriter.printLine(String.format("There %s from Little Red Riding Hood's house to her grandmother's house.",
                        pathNumber));
            }

            width = FastReader.nextInt();
            height = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long countPaths(boolean[][] wolfLocations) {
        long[][] paths = new long[wolfLocations.length][wolfLocations[0].length];
        boolean[][] visited = new boolean[wolfLocations.length][wolfLocations[0].length];
        Cell startCell = new Cell(wolfLocations.length - 1, 0);
        int endRow = 0;
        int endColumn = wolfLocations[0].length - 1;
        paths[startCell.row][startCell.column] = 1;

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(startCell);
        visited[startCell.row][startCell.column] = true;
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            if (cell.row == endRow && cell.column == endColumn) {
                break;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(wolfLocations, neighborRow, neighborColumn)
                        && !wolfLocations[neighborRow][neighborColumn]) {
                    paths[neighborRow][neighborColumn] += paths[cell.row][ cell.column];

                    if (!visited[neighborRow][neighborColumn]) {
                        visited[neighborRow][neighborColumn] = true;
                        queue.offer(new Cell(neighborRow, neighborColumn));
                    }
                }
            }
        }
        return paths[endRow][endColumn];
    }

    private static boolean isValid(boolean[][] wolfLocations, int row, int column) {
        return row >= 0 && row < wolfLocations.length && column >= 0 && column < wolfLocations[0].length;
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
