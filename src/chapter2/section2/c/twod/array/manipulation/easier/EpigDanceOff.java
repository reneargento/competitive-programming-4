package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 02/02/21.
 */
public class EpigDanceOff {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        char[][] grid = new char[rows][columns];

        for (int row = 0; row < grid.length; row++) {
            grid[row] = scanner.next().toCharArray();
        }
        System.out.println(countMoves(grid));
    }

    private static int countMoves(char[][] grid) {
        int moves = 1;
        int maxColumn = grid[0].length - 1;
        Set<Integer> nonEmptyColumns = new HashSet<>();

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] == '$') {
                    nonEmptyColumns.add(column);
                }
            }
        }

        for (int column = 0; column <= maxColumn; column++) {
            if (!nonEmptyColumns.contains(column)) {
                moves++;
            }
        }
        return moves;
    }
}
