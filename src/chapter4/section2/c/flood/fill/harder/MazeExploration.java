package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/03/23.
 */
public class MazeExploration {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            List<String> rows = new ArrayList<>();

            while (line.charAt(0) != '_') {
                rows.add(line);
                line = FastReader.getLine();
            }
            int startRow = 0;
            int startColumn = 0;

            char[][] maze = new char[rows.size()][];
            for (int row = 0; row < rows.size(); row++) {
                maze[row] = rows.get(row).toCharArray();

                for (int column = 0; column < maze[row].length; column++) {
                    if (maze[row][column] == '*') {
                        startRow = row;
                        startColumn = column;
                    }
                }
            }

            paintMaze(maze, neighborRows, neighborColumns, startRow, startColumn);

            for (int row = 0; row < maze.length; row++) {
                for (int column = 0; column < maze[row].length; column++) {
                    outputWriter.print(maze[row][column]);
                }
                outputWriter.printLine();
            }
            outputWriter.printLine(line);
        }
        outputWriter.flush();
    }

    private static void paintMaze(char[][] maze, int[] neighborRows, int[] neighborColumns, int row, int column) {
        maze[row][column] = '#';

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(maze, neighborRow, neighborColumn)
                    && maze[neighborRow][neighborColumn] == ' ') {
                paintMaze(maze, neighborRows, neighborColumns, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean isValid(char[][] maze, int row, int column) {
        return row >= 0 && row < maze.length && column >= 0 && column < maze[row].length;
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
