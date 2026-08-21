package chapter6.section4.b.in.twod.grid;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 19/08/2026.
 */
public class LostInSpace {

    private static class FoundLocation {
        Location location;
        String direction;

        public FoundLocation(Location location, String direction) {
            this.location = location;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return "(" + location.row + "," + location.column + ") - " + direction;
        }
    }

    private static class Location {
        int row;
        int column;

        public Location(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final String[] DIRECTIONS = { "N", "NE", "E", "SE", "S", "SW", "W", "NW" };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        FastReader.getLine();
        FastReader.getLine();
        String line = FastReader.getLine();
        int dateSet = 1;

        while (line != null) {
            if (dateSet > 1) {
                outputWriter.printLine();
            }
            int gridSize = Integer.parseInt(line);
            char[][] grid = new char[gridSize][gridSize];
            for (int row = 0; row < gridSize; row++) {
                grid[row] = FastReader.getLine().toCharArray();
            }

            line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                outputWriter.printLine();
                outputWriter.printLine(line);

                List<FoundLocation> foundLocations = searchWord(grid, line);
                if (foundLocations.isEmpty()) {
                    outputWriter.printLine("not found");
                } else {
                    for (FoundLocation foundLocation : foundLocations) {
                        outputWriter.printLine(foundLocation);
                    }
                }
                dateSet++;
                line = FastReader.getLine();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<FoundLocation> searchWord(char[][] grid, String word) {
        List<FoundLocation> foundLocations = new ArrayList<>();

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                if (grid[row][column] == ' ') {
                    continue;
                }
                for (String direction : DIRECTIONS) {
                    boolean result = searchWord(grid, word, 0, row, column, direction);
                    if (result) {
                        Location location = new Location(row + 1, column + 1);
                        FoundLocation foundLocation = new FoundLocation(location, direction);
                        foundLocations.add(foundLocation);
                    }
                }
            }
        }
        return foundLocations;
    }

    private static boolean searchWord(char[][] grid, String word, int wordIndex, int row, int column,
                                      String direction) {
        if (wordIndex == word.length()) {
            return true;
        }
        if (row < 0
                || column < 0
                || row == grid.length
                || column == grid[row].length
                || (grid[row][column] != word.charAt(wordIndex) && grid[row][column] != ' ')) {
            return false;
        }

        int nextWordIndex = wordIndex;
        if (grid[row][column] != ' ') {
            nextWordIndex++;
        }
        Location nextLocation = getNextLocation(row, column, direction);
        return searchWord(grid, word, nextWordIndex, nextLocation.row, nextLocation.column, direction);
    }

    private static Location getNextLocation(int row, int column, String direction) {
        if (direction.equals("N")) {
            row--;
        } else if (direction.equals("NE")) {
            row--;
            column++;
        } else if (direction.equals("E")) {
            column++;
        } else if (direction.equals("SE")) {
            row++;
            column++;
        } else if (direction.equals("S")) {
            row++;
        } else if (direction.equals("SW")) {
            row++;
            column--;
        } else if (direction.equals("W")) {
            column--;
        } else {
            row--;
            column--;
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