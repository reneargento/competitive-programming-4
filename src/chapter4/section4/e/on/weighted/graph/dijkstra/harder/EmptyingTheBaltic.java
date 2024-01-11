package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/01/24.
 */
public class EmptyingTheBaltic {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class SquareInfo implements Comparable<SquareInfo> {
        Cell cell;
        int maxHeight;

        public SquareInfo(Cell cell, int maxHeight) {
            this.cell = cell;
            this.maxHeight = maxHeight;
        }

        @Override
        public int compareTo(SquareInfo other) {
            return Integer.compare(maxHeight, other.maxHeight);
        }
    }

    private static final int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[][] map = new int[FastReader.nextInt()][FastReader.nextInt()];
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[0].length; column++) {
                map[row][column] = FastReader.nextInt();
            }
        }
        Cell drainingDevice = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);
        long volumeDrained = computeVolumeDrained(map, drainingDevice);
        outputWriter.printLine(volumeDrained);
        outputWriter.flush();
    }

    private static long computeVolumeDrained(int[][] map, Cell drainingDevice) {
        int[][] maxHeightInPath = new int[map.length][map[0].length];

        PriorityQueue<SquareInfo> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new SquareInfo(drainingDevice, map[drainingDevice.row][drainingDevice.column]));
        maxHeightInPath[drainingDevice.row][drainingDevice.column] = map[drainingDevice.row][drainingDevice.column];

        while (!priorityQueue.isEmpty()) {
            SquareInfo squareInfo = priorityQueue.poll();
            int row = squareInfo.cell.row;
            int column = squareInfo.cell.column;

            if (squareInfo.maxHeight > maxHeightInPath[row][column]) {
                continue;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)) {
                    int nextMaxHeight = Math.max(maxHeightInPath[row][column], map[neighborRow][neighborColumn]);
                    if (nextMaxHeight < maxHeightInPath[neighborRow][neighborColumn]) {
                        maxHeightInPath[neighborRow][neighborColumn] = nextMaxHeight;
                        Cell nextCell = new Cell(neighborRow, neighborColumn);
                        priorityQueue.offer(new SquareInfo(nextCell, maxHeightInPath[neighborRow][neighborColumn]));
                    }
                }
            }
        }

        long volumeDrained = 0;
        for (int row = 0; row < maxHeightInPath.length; row++) {
            for (int column = 0; column < maxHeightInPath[0].length; column++) {
                volumeDrained -= maxHeightInPath[row][column];
            }
        }
        return volumeDrained;
    }

    private static boolean isValid(int[][] map, int row, int column) {
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
