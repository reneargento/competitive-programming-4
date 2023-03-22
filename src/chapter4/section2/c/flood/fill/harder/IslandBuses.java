package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 19/03/23.
 */
public class IslandBuses {

    private static class Result {
        int islands;
        int bridges;
        int busesNeeded;

        public Result(int islands, int bridges, int busesNeeded) {
            this.islands = islands;
            this.bridges = bridges;
            this.busesNeeded = busesNeeded;
        }
    }

    private static final char WATER = '.';
    private static final char LAND = '#';
    private static final char BRIDGE = 'B';
    private static final char ENDPOINT = 'X';
    private static final int SEARCH_ISLANDS = 0;
    private static final int SEARCH_BRIDGES = 1;
    private static final int SEARCH_BUSES = 2;
    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int mapNumber = 1;

        String line = FastReader.getLine();
        while (line != null) {
            List<String> rows = new ArrayList<>();

            while (line != null && !line.isEmpty()) {
                rows.add(line);
                line = FastReader.getLine();
            }

            char[][] map = new char[rows.size()][rows.get(0).length()];
            for (int row = 0; row < map.length; row++) {
                map[row] = rows.get(row).toCharArray();
            }

            if (mapNumber > 1) {
                outputWriter.printLine();
            }
            Result result = analyzeMap(map);
            outputWriter.printLine("Map " + mapNumber);
            outputWriter.printLine("islands: " + result.islands);
            outputWriter.printLine("bridges: " + result.bridges);
            outputWriter.printLine("buses needed: " + result.busesNeeded);

            mapNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result analyzeMap(char[][] map) {
        int[] result = new int[3];

        for (int searchType = 0; searchType < 3; searchType++) {
            boolean[][] visited = new boolean[map.length][map[0].length];

            for (int row = 0; row < map.length; row++) {
                for (int column = 0; column < map[0].length; column++) {
                    if (map[row][column] != WATER && !visited[row][column]) {
                        if (searchType == SEARCH_ISLANDS && map[row][column] == BRIDGE) {
                            continue;
                        }
                        if (searchType == SEARCH_BRIDGES && map[row][column] != BRIDGE) {
                            continue;
                        }
                        floodFill(map, searchType, visited, row, column);
                        result[searchType]++;
                    }
                }
            }
        }
        return new Result(result[SEARCH_ISLANDS], result[SEARCH_BRIDGES], result[SEARCH_BUSES]);
    }

    private static void floodFill(char[][] map, int searchType, boolean[][] visited, int row, int column) {
        visited[row][column] = true;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(map, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn]
                    && map[neighborRow][neighborColumn] != WATER) {
                if ((map[row][column] == BRIDGE && map[neighborRow][neighborColumn] == LAND)
                        || (map[row][column] == LAND && map[neighborRow][neighborColumn] == BRIDGE)) {
                    continue;
                }
                if (searchType != SEARCH_BUSES
                        && (map[row][column] == ENDPOINT && map[neighborRow][neighborColumn] == BRIDGE)) {
                    continue;
                }
                if (searchType == SEARCH_BRIDGES && map[neighborRow][neighborColumn] != BRIDGE) {
                    continue;
                }
                if (searchType == SEARCH_ISLANDS && map[neighborRow][neighborColumn] == BRIDGE) {
                    continue;
                }
                floodFill(map, searchType, visited, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean isValid(char[][] map, int row, int column) {
        return row >= 0 && row < map.length && column >= 0 && column < map[0].length;
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
