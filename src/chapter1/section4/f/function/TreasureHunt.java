package chapter1.section4.f.function;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class TreasureHunt {

    private static class Location {
        private int row;
        private int column;

        public Location(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int hashCode() {
            return row * 100 + column;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Location)) {
                return false;
            }

            Location otherLocation = (Location) other;
            return row == otherLocation.row && column == otherLocation.column;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        char[][] area = new char[rows][columns];
        for (int r = 0; r < rows; r++) {
            String row = scanner.next();

            for (int c = 0; c < columns; c++) {
                area[r][c] = row.charAt(c);
            }
        }

        Set<Location> visited = new HashSet<>();
        Location currentLocation = new Location(0, 0);
        int moves = 0;

        while (true) {
            int currentRow = currentLocation.row;
            int currentColumn = currentLocation.column;

            if (!isValid(area, currentRow, currentColumn)) {
                System.out.println("Out");
                break;
            }

            if (visited.contains(currentLocation)) {
                System.out.println("Lost");
                break;
            }

            int nextRow = currentRow;
            int nextColumn = currentColumn;

            switch (area[currentRow][currentColumn]) {
                case 'N': nextRow--; break;
                case 'S': nextRow++; break;
                case 'E': nextColumn++; break;
                case 'W': nextColumn--; break;
            }

            if (area[currentRow][currentColumn] == 'T') {
                System.out.println(moves);
                break;
            }

            visited.add(currentLocation);
            currentLocation = new Location(nextRow, nextColumn);
            moves++;
        }
    }

    private static boolean isValid(char[][] area, int row, int column) {
        return row >= 0 && row < area.length && column >= 0 && column < area[0].length;
    }

}
