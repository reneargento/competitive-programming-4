package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 10/02/21.
 */
public class Robbery {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private enum Location {
        UNKNOWN, NOT_POSSIBLE, POSSIBLE
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int columns = FastReader.nextInt();
        int rows = FastReader.nextInt();
        int time = FastReader.nextInt();
        int robberyNumber = 1;
        int[] neighborRows = { -1, 0, 0, 1, 0 };
        int[] neighborColumns = { 0, -1, 1, 0, 0 };

        while (columns != 0 || rows != 0 || time != 0) {
            int messages = FastReader.nextInt();
            Location[][][] cities = new Location[time][rows][columns];
            initializeCities(cities);

            for (int i = 0; i < messages; i++) {
                int timestamp = FastReader.nextInt() - 1;
                int column1 = FastReader.nextInt() - 1;
                int row1 = FastReader.nextInt() - 1;
                int column2 = FastReader.nextInt() - 1;
                int row2 = FastReader.nextInt() - 1;
                fillRectangle(cities[timestamp], column1, row1, column2, row2);
            }

            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    dfs(cities, 0, row, column, neighborRows, neighborColumns);
                }
            }

            System.out.printf("Robbery #%d:\n", robberyNumber);
            investigate(cities);

            robberyNumber++;
            columns = FastReader.nextInt();
            rows = FastReader.nextInt();
            time = FastReader.nextInt();
        }
    }

    private static void initializeCities(Location[][][] cities) {
        for (int time = 0; time < cities.length; time++) {
            for (int row = 0; row < cities[0].length; row++) {
                for (int column = 0; column < cities[0][0].length; column++) {
                    cities[time][row][column] = Location.UNKNOWN;
                }
            }
        }
    }

    private static void fillRectangle(Location[][] city, int column1, int row1, int column2, int row2) {
        for (int row = row1; row <= row2; row++) {
            for (int column = column1; column <= column2; column++) {
                city[row][column] = Location.NOT_POSSIBLE;
            }
        }
    }

    private static Location dfs(Location[][][] cities, int time, int row, int column, int[] neighborRows,
                            int[] neighborColumns) {
        if (time == cities.length) {
            return Location.POSSIBLE;
        }
        if (cities[time][row][column] != Location.UNKNOWN) {
            return cities[time][row][column];
        }

        Location locationResult = Location.NOT_POSSIBLE;
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(cities[time], neighborRow, neighborColumn)
                    && dfs(cities, time + 1, neighborRow, neighborColumn, neighborRows, neighborColumns) ==
                    Location.POSSIBLE) {
                locationResult = Location.POSSIBLE;
            }
        }

        cities[time][row][column] = locationResult;
        return locationResult;
    }

    private static void investigate(Location[][][] cities) {
        Cell[] robberLocations = new Cell[cities.length];
        boolean escaped = false;
        boolean nothingKnown = true;

        for (int time = 0; time < cities.length; time++) {
            List<Cell> currentLocations = new ArrayList<>();

            for (int row = 0; row < cities[0].length; row++) {
                for (int column = 0; column < cities[0][0].length; column++) {
                    if (cities[time][row][column] == Location.POSSIBLE) {
                        currentLocations.add(new Cell(row, column));
                    }
                }
            }

            if (currentLocations.isEmpty()) {
                escaped = true;
                break;
            } else if (currentLocations.size() == 1) {
                nothingKnown = false;
                robberLocations[time] = currentLocations.get(0);
            }
        }

        if (escaped) {
            System.out.println("The robber has escaped.");
        } else if (nothingKnown) {
            System.out.println("Nothing known.");
        } else {
            for (int time = 0; time < cities.length; time++) {
                if (robberLocations[time] != null) {
                    int row = robberLocations[time].row;
                    int column = robberLocations[time].column;
                    System.out.printf("Time step %d: The robber has been at %d,%d.\n", time + 1, column + 1, row + 1);
                }
            }
        }
        System.out.println();
    }

    private static boolean isValid(Location[][] city, int row, int column) {
        return row >= 0 && row < city.length && column >= 0 && column < city[0].length;
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
    }
}
