package chapter6.section4.b.in.twod.grid;

import java.io.*;

/**
 * Created by Rene Argento on 19/08/2026.
 */
public class WordSearchWonder {

    private static class Location {
        int row;
        int column;

        public Location(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private enum Direction {
        DOWN, RIGHT, LEFT, DIAGONAL_LEFT_DOWN, DIAGONAL_LEFT_UP, DIAGONAL_RIGHT_DOWN, DIAGONAL_RIGHT_UP
    }

    private static final Direction[] ALL_DIRECTIONS = {
            Direction.DOWN, Direction.RIGHT, Direction.LEFT, Direction.DIAGONAL_LEFT_DOWN,
            Direction.DIAGONAL_LEFT_UP, Direction.DIAGONAL_RIGHT_DOWN, Direction.DIAGONAL_RIGHT_UP
    };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (!line.equals("0")) {
            int gridLength = Integer.parseInt(line);
            char[][] grid = new char[gridLength][gridLength];
            for (int row = 0; row < gridLength; row++) {
                grid[row] = FastReader.getLine().toCharArray();
            }

            line = FastReader.getLine();
            while (!Character.isDigit(line.charAt(0))) {
                Location[] locations = searchWord(grid, line);
                if (locations == null) {
                    outputWriter.printLine("Not found");
                } else {
                    outputWriter.printLine(String.format("%d,%d %d,%d", locations[0].row, locations[0].column,
                            locations[1].row, locations[1].column));
                }
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static Location[] searchWord(char[][] grid, String word) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                for (Direction direction : ALL_DIRECTIONS) {
                    boolean result = searchWord(grid, word, 0, row, column, direction);
                    if (result) {
                        Location startLocation = new Location(row + 1, column + 1);
                        Location endLocation = getEndLocation(startLocation, word.length(), direction);
                        return new Location[]{ startLocation, endLocation };
                    }
                }
            }
        }
        return null;
    }

    private static boolean searchWord(char[][] grid, String word, int wordIndex, int row, int column,
                                      Direction direction) {
        if (wordIndex == word.length()) {
            return true;
        }
        if (row < 0
                || column < 0
                || row == grid.length
                || column == grid[row].length
                || grid[row][column] != word.charAt(wordIndex)) {
            return false;
        }

        Location nextLocation = getNextLocation(row, column, direction);
        return searchWord(grid, word, wordIndex + 1, nextLocation.row, nextLocation.column, direction);
    }

    private static Location getNextLocation(int row, int column, Direction direction) {
        switch (direction) {
            case DOWN: {
                row++;
                break;
            }
            case RIGHT: {
                column++;
                break;
            }
            case LEFT: {
                column--;
                break;
            }
            case DIAGONAL_LEFT_DOWN: {
                row++;
                column++;
                break;
            }
            case DIAGONAL_LEFT_UP: {
                row--;
                column--;
                break;
            }
            case DIAGONAL_RIGHT_DOWN: {
                row++;
                column--;
                break;
            }
            case DIAGONAL_RIGHT_UP: {
                row--;
                column++;
                break;
            }
        }
        return new Location(row, column);
    }

    private static Location getEndLocation(Location startLocation, int wordLength, Direction direction) {
        int row = startLocation.row;
        int column = startLocation.column;
        wordLength--;

        switch (direction) {
            case DOWN: {
                row += wordLength;
                break;
            }
            case RIGHT: {
                column += wordLength;
                break;
            }
            case LEFT: {
                column -= wordLength;
                break;
            }
            case DIAGONAL_LEFT_DOWN: {
                row += wordLength;
                column += wordLength;
                break;
            }
            case DIAGONAL_LEFT_UP: {
                row -= wordLength;
                column -= wordLength;
                break;
            }
            case DIAGONAL_RIGHT_DOWN: {
                row += wordLength;
                column -= wordLength;
                break;
            }
            case DIAGONAL_RIGHT_UP: {
                row -= wordLength;
                column += wordLength;
                break;
            }
        }
        return new Location(row, column);
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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