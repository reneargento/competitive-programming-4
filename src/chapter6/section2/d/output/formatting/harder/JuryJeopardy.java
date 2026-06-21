package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/06/2026.
 */
public class JuryJeopardy {

    private static final int DIRECTION_EAST = 0;
    private static final int DIRECTION_NORTH = 1;
    private static final int DIRECTION_WEST = 2;
    private static final int DIRECTION_SOUTH = 3;

    private static class Position {
        int row;
        int column;
        int direction;

        public Position(int row, int column, int direction) {
            this.row = row;
            this.column = column;
            this.direction = direction;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        outputWriter.printLine(tests);
        for (int t = 0; t < tests; t++) {
            String path = FastReader.getLine();
            printMaze(path, outputWriter);
        }
        outputWriter.flush();
    }

    private static void printMaze(String path, OutputWriter outputWriter) {
        char[][] maze = new char[202][202];
        for (char[] row : maze) {
            Arrays.fill(row, '#');
        }
        int minRow = 100;
        int maxRow = 100;
        int maxColumn = 0;
        int direction = DIRECTION_EAST;

        int currentRow = 100;
        int currentColumn = 0;
        maze[currentRow][currentColumn] = '.';

        for (char instruction : path.toCharArray()) {
            Position position = getNextPosition(currentRow, currentColumn, direction, instruction);
            if (position.row == 100 && position.column == 0) {
                break;
            }
            maze[position.row][position.column] = '.';

            currentRow = position.row;
            currentColumn = position.column;
            direction = position.direction;
            minRow = Math.min(minRow, currentRow);
            maxRow = Math.max(maxRow, currentRow);
            maxColumn = Math.max(maxColumn, currentColumn);
        }

        int totalRows = maxRow - minRow + 3;
        outputWriter.printLine(String.format("%d %d", totalRows, (maxColumn + 2)));
        for (int row = minRow - 1; row <= maxRow + 1; row++) {
            for (int column = 0; column <= maxColumn + 1; column++) {
                outputWriter.print(maze[row][column]);
            }
            outputWriter.printLine();
        }
    }

    private static Position getNextPosition(int row, int column, int direction, char instruction) {
        switch (direction) {
            case DIRECTION_EAST: {
                if (instruction == 'F') {
                    return new Position(row, column + 1, DIRECTION_EAST);
                } else if (instruction == 'L') {
                    return new Position(row - 1, column, DIRECTION_NORTH);
                } else if (instruction == 'R') {
                    return new Position(row + 1, column, DIRECTION_SOUTH);
                } else {
                    return new Position(row, column - 1, DIRECTION_WEST);
                }
            }
            case DIRECTION_WEST: {
                if (instruction == 'F') {
                    return new Position(row, column - 1, DIRECTION_WEST);
                } else if (instruction == 'L') {
                    return new Position(row + 1, column, DIRECTION_SOUTH);
                } else if (instruction == 'R') {
                    return new Position(row - 1, column, DIRECTION_NORTH);
                } else {
                    return new Position(row, column + 1, DIRECTION_EAST);
                }
            }
            case DIRECTION_NORTH: {
                if (instruction == 'F') {
                    return new Position(row - 1, column, DIRECTION_NORTH);
                } else if (instruction == 'L') {
                    return new Position(row, column - 1, DIRECTION_WEST);
                } else if (instruction == 'R') {
                    return new Position(row, column + 1, DIRECTION_EAST);
                } else {
                    return new Position(row + 1, column, DIRECTION_SOUTH);
                }
            }
            case DIRECTION_SOUTH: {
                if (instruction == 'F') {
                    return new Position(row + 1, column, DIRECTION_SOUTH);
                } else if (instruction == 'L') {
                    return new Position(row, column + 1, DIRECTION_EAST);
                } else if (instruction == 'R') {
                    return new Position(row, column - 1, DIRECTION_WEST);
                } else {
                    return new Position(row - 1, column,  DIRECTION_NORTH);
                }
            }
        }
        return null;
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