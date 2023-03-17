package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/03/23.
 */
public class SlashMaze {

    private static class Result {
        int cyclesNumber;
        int longestCycleLength;

        public Result(int cyclesNumber, int longestCycleLength) {
            this.cyclesNumber = cyclesNumber;
            this.longestCycleLength = longestCycleLength;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int columns = FastReader.nextInt();
        int rows = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        int mazeNumber = 1;

        while (rows != 0 || columns != 0) {
            char[][] maze = new char[rows][columns];
            for (int row = 0; row < maze.length; row++) {
                maze[row] = FastReader.next().toCharArray();
            }

            Result result = computeCycles(maze, neighborRows, neighborColumns);
            outputWriter.printLine(String.format("Maze #%d:", mazeNumber));
            if (result.cyclesNumber > 0) {
                outputWriter.printLine(String.format("%d Cycles; the longest has length %d.", result.cyclesNumber,
                        result.longestCycleLength));
            } else {
                outputWriter.printLine("There are no cycles.");
            }
            outputWriter.printLine();

            mazeNumber++;
            columns = FastReader.nextInt();
            rows = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeCycles(char[][] maze, int[] neighborRows, int[] neighborColumns) {
        char[][] graph = buildGraph(maze);

        int cyclesNumber = 0;
        int longestCycleLength = 0;
        boolean[][] visited = new boolean[graph.length][graph[0].length];

        for (int row = 0; row < graph.length; row++) {
            for (int column = 0; column < graph[0].length; column++) {
                if (graph[row][column] == '.' && !visited[row][column]) {
                    int cycleLength = computeCycleLength(graph, visited, neighborRows, neighborColumns, row, column) / 3;
                    if (cycleLength > 0) {
                        cyclesNumber++;
                        longestCycleLength = Math.max(longestCycleLength, cycleLength);
                    }
                }
            }
        }
        return new Result(cyclesNumber, longestCycleLength);
    }

    private static int computeCycleLength(char[][] graph, boolean[][] visited, int[] neighborRows,
                                          int[] neighborColumns, int row, int column) {
        int cycleLength = 1;
        visited[row][column] = true;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(graph, neighborRow, neighborColumn)) {
                if (!visited[neighborRow][neighborColumn] && graph[neighborRow][neighborColumn] == '.') {
                    int nextCellsNumber = computeCycleLength(graph, visited, neighborRows, neighborColumns,
                            neighborRow, neighborColumn);

                    if (cycleLength != -1 && nextCellsNumber > 0) {
                        cycleLength += nextCellsNumber;
                    } else {
                        cycleLength = -1;
                    }
                }
            } else {
                cycleLength = -1;
            }
        }
        return cycleLength;
    }

    private static char[][] buildGraph(char[][] maze) {
        char[][] graph = new char[maze.length * 3][maze[0].length * 3];
        for (char[] values : graph) {
            Arrays.fill(values, '.');
        }

        for (int row = 0; row < maze.length; row++) {
            int graphRow = row * 3;

            for (int column = 0; column < maze[0].length; column++) {
                int graphColumn = column * 3;

                if (maze[row][column] == '\\') {
                    graph[graphRow][graphColumn] = '\\';
                    graph[graphRow + 1][graphColumn + 1] = '\\';
                    graph[graphRow + 2][graphColumn + 2] = '\\';
                } else {
                    graph[graphRow][graphColumn + 2] = '/';
                    graph[graphRow + 1][graphColumn + 1] = '/';
                    graph[graphRow + 2][graphColumn] = '/';
                }
            }
        }
        return graph;
    }

    private static boolean isValid(char[][] graph, int row, int column) {
        return row >= 0 && row < graph.length && column >= 0 && column < graph[0].length;
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
