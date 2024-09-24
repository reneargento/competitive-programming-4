package chapter4.section6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/05/24.
 */
public class HelpfulCurrents {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class Neighbors {
        int[] neighborRows;
        int[] neighborColumns;

        public Neighbors(int[] neighborRows, int[] neighborColumns) {
            this.neighborRows = neighborRows;
            this.neighborColumns = neighborColumns;
        }
    }

    private static class ReachabilityChecker {
        boolean hasPath;
    }

    private static final int MOD = 1000003;
    private static final int[] leftCurrentNeighborRows = { 0, -1};
    private static final int[] leftCurrentNeighborColumns = { -1, 0 };
    private static final int[] rightCurrentNeighborRows = { 0, -1 };
    private static final int[] rightCurrentNeighborColumns = { 1, 0 };
    private static final int[] openSeaNeighborRows = { -1 };
    private static final int[] openSeaNeighborColumns = { 0 };

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        char[][] grid = new char[inputReader.nextInt()][inputReader.nextInt()];
        int startColumn = inputReader.nextInt();
        Cell castleCell = null;

        for (int row = 0; row < grid.length; row++) {
            grid[row] = inputReader.next().toCharArray();

            for (int column = 0; column < grid[row].length; column++) {
                if (grid[row][column] == '@') {
                    castleCell = new Cell(row, column);
                    break;
                }
            }
        }

        ReachabilityChecker reachabilityChecker = new ReachabilityChecker();
        long pathsToCastle = countPathsToCastle(grid, startColumn, castleCell, reachabilityChecker);

        if (reachabilityChecker.hasPath) {
            outputWriter.printLine(pathsToCastle);
        } else {
            outputWriter.printLine("begin repairs");
        }
        outputWriter.flush();
    }

    private static int countPathsToCastle(char[][] grid, int startColumn, Cell castleCell,
                                          ReachabilityChecker reachabilityChecker) {
        int[] paths = new int[grid.length * grid[0].length];
        int startVertexId = getVertexId(grid[0].length, grid.length - 1, startColumn);
        int castleId = getVertexId(grid[0].length, castleCell.row, castleCell.column);
        paths[startVertexId] = 1;

        byte[] inDegrees = computeInDegrees(grid);
        int[] topologicalOrder = khanTopologicalSort(grid, inDegrees, castleId);

        for (int vertexId : topologicalOrder) {
            if (vertexId == castleId) {
                break;
            }
            int row = vertexId / grid[0].length;
            int column = vertexId % grid[0].length;

            if (grid[row][column] == '#') {
                continue;
            }
            Neighbors neighbors = getNeighbors(grid[row][column]);
            int[] neighborRows = neighbors.neighborRows;
            int[] neighborColumns = neighbors.neighborColumns;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];
                if (isValid(grid, neighborRow, neighborColumn)) {
                    int neighborId = getVertexId(grid[0].length, neighborRow, neighborColumn);

                    paths[neighborId] += paths[vertexId];
                    paths[neighborId] %= MOD;
                    if (neighborId == castleId && paths[castleId] > 0) {
                        reachabilityChecker.hasPath = true;
                        break;
                    }
                }
            }
        }
        return paths[castleId];
    }

    private static int[] khanTopologicalSort(char[][] grid, byte[] inDegrees, int castleId) {
        Queue<Integer> queue = new LinkedList<>();
        for (int vertexId = 0; vertexId < inDegrees.length; vertexId++) {
            if (inDegrees[vertexId] == 0) {
                queue.add(vertexId);
            }
        }

        int[] topologicalOrder = new int[grid.length * grid[0].length];
        int topologicalOrderIndex = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            topologicalOrder[topologicalOrderIndex++] = currentVertex;

            if (currentVertex == castleId) {
                break;
            }
            int row = currentVertex / grid[0].length;
            int column = currentVertex % grid[0].length;

            if (grid[row][column] == '#') {
                continue;
            }
            Neighbors neighbors = getNeighbors(grid[row][column]);
            int[] neighborRows = neighbors.neighborRows;
            int[] neighborColumns = neighbors.neighborColumns;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];
                if (isValid(grid, neighborRow, neighborColumn)) {
                    int neighborId = getVertexId(grid[0].length, neighborRow, neighborColumn);
                    inDegrees[neighborId]--;

                    if (inDegrees[neighborId] == 0) {
                        queue.add(neighborId);
                    }
                }
            }
        }
        return topologicalOrder;
    }

    private static byte[] computeInDegrees(char[][] grid) {
        byte[] inDegrees = new byte[grid.length * grid[0].length];

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] == '#') {
                    continue;
                }
                Neighbors neighbors = getNeighbors(grid[row][column]);
                int[] neighborRows = neighbors.neighborRows;
                int[] neighborColumns = neighbors.neighborColumns;

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];
                    if (isValid(grid, neighborRow, neighborColumn)) {
                        int vertexId = getVertexId(grid[0].length, neighborRow, neighborColumn);
                        inDegrees[vertexId]++;;
                    }
                }
            }
        }
        return inDegrees;
    }

    private static Neighbors getNeighbors(char value) {
        int[] neighborRows;
        int[] neighborColumns;

        if (value == '<') {
            neighborRows = leftCurrentNeighborRows;
            neighborColumns = leftCurrentNeighborColumns;
        } else if (value == '>') {
            neighborRows = rightCurrentNeighborRows;
            neighborColumns = rightCurrentNeighborColumns;
        } else {
            neighborRows = openSeaNeighborRows;
            neighborColumns = openSeaNeighborColumns;
        }
        return new Neighbors(neighborRows, neighborColumns);
    }

    private static int getVertexId(int columnSize, int row, int column) {
        return row * columnSize + column;
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length
                && grid[row][column] != '#';
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String next() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
