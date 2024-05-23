package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/05/24.
 */
public class WalkingAroundWisely {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final int[] neighborRows = { -1, 0 };
    private static final int[] neighborColumns = { 0, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dimension = FastReader.nextInt();
            boolean[][] adjacencyMatrix = new boolean[dimension * dimension][dimension * dimension];
            for (boolean[] values : adjacencyMatrix) {
                Arrays.fill(values, true);
            }
            Cell startPosition = readCell(dimension);
            Cell endPosition = readCell(dimension);

            int discontinuedPlaces = FastReader.nextInt();
            for (int i = 0; i < discontinuedPlaces; i++) {
                int column = FastReader.nextInt() - 1;
                int row = dimension - FastReader.nextInt();
                int vertexID = getVertexID(dimension, row, column);
                char direction = FastReader.next().charAt(0);

                int neighborID;
                switch (direction) {
                    case 'N': neighborID = vertexID - dimension; break;
                    case 'S': neighborID = vertexID + dimension; break;
                    case 'E': neighborID = vertexID + 1; break;
                    default: neighborID = vertexID - 1;
                }

                if (isValid(dimension, row, column)) {
                    if (direction == 'N' || direction == 'E') {
                        adjacencyMatrix[vertexID][neighborID] = false;
                    } else {
                        adjacencyMatrix[neighborID][vertexID] = false;
                    }
                }
            }

            long numberOfWays = countWays(adjacencyMatrix, dimension, startPosition, endPosition);
            outputWriter.printLine(numberOfWays);
        }
        outputWriter.flush();
    }

    private static Cell readCell(int dimension) throws IOException {
        int column = FastReader.nextInt() - 1;
        int row = dimension - FastReader.nextInt();
        return new Cell(row, column);
    }

    private static long countWays(boolean[][] adjacencyMatrix, int dimension, Cell startPosition, Cell endPosition) {
        long[][] ways = new long[dimension][dimension];
        boolean[][] visited = new boolean[dimension][dimension];
        ways[startPosition.row][startPosition.column] = 1;
        visited[startPosition.row][startPosition.column] = true;

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(startPosition);

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            if (cell.row == endPosition.row && cell.column == endPosition.column) {
                break;
            }
            int vertexID = getVertexID(dimension, cell.row, cell.column);

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];
                int neighborID = getVertexID(dimension, neighborRow, neighborColumn);

                if (isValid(dimension, neighborRow, neighborColumn)
                        && adjacencyMatrix[vertexID][neighborID]) {
                    ways[neighborRow][neighborColumn] += ways[cell.row][cell.column];
                    if (!visited[neighborRow][neighborColumn]) {
                        queue.offer(new Cell(neighborRow, neighborColumn));
                        visited[neighborRow][neighborColumn] = true;
                    }
                }
            }
        }
        return ways[endPosition.row][endPosition.column];
    }

    private static int getVertexID(int maxColumn, int row, int column) {
        return row * maxColumn + column;
    }

    private static boolean isValid(int dimension, int row, int column) {
        return row >= 0 && row < dimension && column >= 0 && column < dimension;
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
