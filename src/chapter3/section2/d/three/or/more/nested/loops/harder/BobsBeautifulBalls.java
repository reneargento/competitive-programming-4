package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/11/21.
 */
public class BobsBeautifulBalls {

    enum Direction {
        EAST, SOUTH, WEST, NORTH
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String balls = FastReader.getLine();
            int beautifulGridDimensions = tryToMakeBeautifulGrid(balls);
            outputWriter.printLine(String.format("Case %d: %d", t, beautifulGridDimensions));
        }
        outputWriter.flush();
    }

    private static int tryToMakeBeautifulGrid(String balls) {
        int bestDimensions = Integer.MAX_VALUE;

        for (int rows = 2; rows <= 50; rows++) {
            for (int columns = 2; columns * rows <= 100; columns++) {
                if (rows * columns != balls.length()) {
                    continue;
                }

                Character[][] grid = createGridWithBalls(balls, rows, columns);
                if (isBeautifulGrid(grid)) {
                    int beautifulGridDimensions = rows + columns;
                    bestDimensions = Math.min(bestDimensions, beautifulGridDimensions);
                }
            }
        }

        if (bestDimensions == Integer.MAX_VALUE) {
            return -1;
        }
        return bestDimensions;
    }

    private static Character[][] createGridWithBalls(String balls, int rows, int columns) {
        Character[][] grid = new Character[rows][columns];
        int row = 0;
        int column = 0;
        Direction direction = Direction.EAST;

        for (int i = 0; i < balls.length(); i++) {
            char ball = balls.charAt(i);
            grid[row][column] = ball;

            switch (direction) {
                case EAST:
                    if (column == grid[0].length - 1 || grid[row][column + 1] != null) {
                        direction = Direction.SOUTH;
                        row++;
                    } else {
                        column++;
                    }
                    break;
                case SOUTH:
                    if (row == grid.length - 1 || grid[row + 1][column] != null) {
                        direction = Direction.WEST;
                        column--;
                    } else {
                        row++;
                    }
                    break;
                case WEST:
                    if (column == 0 || grid[row][column - 1] != null) {
                        direction = Direction.NORTH;
                        row--;
                    } else {
                        column--;
                    }
                    break;
                default:
                    if (row == 0 || grid[row - 1][column] != null) {
                        direction = Direction.EAST;
                        column++;
                    } else {
                        row--;
                    }
            }
        }
        return grid;
    }

    private static boolean isBeautifulGrid(Character[][] grid) {
        for (int column = 0; column < grid[0].length; column++) {
            Character color = grid[0][column];

            for (int row = 1; row < grid.length; row++) {
                if (grid[row][column] != color) {
                    return false;
                }
            }
        }
        return true;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
