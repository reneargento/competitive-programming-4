package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/03/23.
 */
public class FreedomFighter {

    private static class Sector {
        int freedomFighterGroups;
        int enemyGroups;
        int fightingPositionGroups;

        public Sector(int freedomFighterGroups, int enemyGroups, int fightingPositionGroups) {
            this.freedomFighterGroups = freedomFighterGroups;
            this.enemyGroups = enemyGroups;
            this.fightingPositionGroups = fightingPositionGroups;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int gridDimension = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        while (gridDimension != 0) {
            char[][] grid = new char[gridDimension][gridDimension];
            for (int row = 0; row < grid.length; row++) {
                grid[row] = FastReader.next().toCharArray();
            }
            computeSectors(grid, neighborRows, neighborColumns, outputWriter);
            outputWriter.printLine();
            gridDimension = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void computeSectors(char[][] grid, int[] neighborRows, int[] neighborColumns,
                                       OutputWriter outputWriter) {
        int fightingPositionGroups = 0;
        int sectorID = 1;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int[][] sectorArea = new int[grid.length][grid[0].length];

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] != '.' && !visited[row][column]) {
                    computeSectorArea(grid, neighborRows, neighborColumns, sectorArea, sectorID, row, column);

                    Sector sector = floodFill(grid, neighborRows, neighborColumns, visited, sectorArea, sectorID);
                    fightingPositionGroups += sector.fightingPositionGroups;
                    outputWriter.printLine(String.format("Sector #%d: contain %d freedom fighter group(s) " +
                            "& %d enemy group(s)", sectorID, sector.freedomFighterGroups, sector.enemyGroups));
                    sectorID++;
                }
            }
        }
        outputWriter.printLine(String.format("Total %d group(s) are in fighting position.", fightingPositionGroups));
    }

    private static void computeSectorArea(char[][] grid, int[] neighborRows, int[] neighborColumns, int[][] sectorArea,
                                          int sectorID, int row, int column) {
        sectorArea[row][column] = sectorID;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)
                    && sectorArea[neighborRow][neighborColumn] == 0
                    && grid[neighborRow][neighborColumn] != '.') {
                computeSectorArea(grid, neighborRows, neighborColumns, sectorArea, sectorID, neighborRow, neighborColumn);
            }
        }
    }

    private static Sector floodFill(char[][] grid, int[] neighborRows, int[] neighborColumns, boolean[][] visited,
                                    int[][] sectorArea, int sectorID) {
        int freedomFighterGroups = 0;
        int enemyGroups = 0;
        int fightingPositionGroups = 0;

        for (int nextRow = 0; nextRow < grid.length; nextRow++) {
            for (int nextColumn = 0; nextColumn < grid[0].length; nextColumn++) {
                if (sectorArea[nextRow][nextColumn] != sectorID) {
                    continue;
                }

                if (!visited[nextRow][nextColumn]) {
                    if (grid[nextRow][nextColumn] == 'B' || grid[nextRow][nextColumn] == 'P') {
                        if (grid[nextRow][nextColumn] == 'B') {
                            freedomFighterGroups++;
                        } else if (grid[nextRow][nextColumn] == 'P') {
                            enemyGroups++;
                        }

                        boolean isGroupFighting = isGroupFighting(grid, neighborRows, neighborColumns, visited,
                                grid[nextRow][nextColumn], nextRow, nextColumn);
                        if (isGroupFighting) {
                            fightingPositionGroups++;
                        }
                    }
                }
                visited[nextRow][nextColumn] = true;
            }
        }
        return new Sector(freedomFighterGroups, enemyGroups, fightingPositionGroups);
    }

    private static boolean isGroupFighting(char[][] grid, int[] neighborRows, int[] neighborColumns, boolean[][] visited,
                                           char groupID, int row, int column) {
        boolean isGroupFighting = false;
        visited[row][column] = true;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(grid, neighborRow, neighborColumn)) {
                if (grid[neighborRow][neighborColumn] == groupID && !visited[neighborRow][neighborColumn]) {
                    isGroupFighting |= isGroupFighting(grid, neighborRows, neighborColumns, visited, groupID,
                            neighborRow, neighborColumn);
                } else if (grid[neighborRow][neighborColumn] != '*'
                        && grid[neighborRow][neighborColumn] != '.'
                        && grid[neighborRow][neighborColumn] != groupID) {
                    isGroupFighting = true;
                }
            }
        }
        return isGroupFighting;
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
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
