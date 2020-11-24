package chapter1.section6.d.games.other.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 23/10/20.
 */
public class TicTacToe {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int xCount = 0;
            int oCount = 0;

            char[][] grid = new char[3][3];

            for (int i = 0; i < 3; i++) {
                String line = scanner.next();
                for (int c = 0; c < line.length(); c++) {
                    char value = line.charAt(c);
                    grid[i][c] = value;

                    if (value == 'X') {
                        xCount++;
                    } else if (value == 'O') {
                        oCount++;
                    }
                }
            }

            boolean possible = (xCount == oCount) || (xCount == oCount + 1);
            if (possible) {
                boolean xCompleted = isCompleted(grid, 'X');
                boolean oCompleted = isCompleted(grid, 'O');

                if (xCompleted && oCompleted) {
                    possible = false;
                }
                if (oCompleted && xCount == oCount + 1) {
                    possible = false;
                }
                if (xCompleted && xCount == oCount) {
                    possible = false;
                }
            }
            System.out.println(possible ? "yes" : "no");
        }
    }

    private static boolean isCompleted(char[][] grid, char value) {
        int completed = 0;

        // Check rows
        for (int row = 0; row < grid.length; row++) {
            boolean complete = true;
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] != value) {
                    complete = false;
                    break;
                }
            }
            if (complete) {
                completed++;
            }
        }

        // Check columns
        for (int column = 0; column < grid[0].length; column++) {
            boolean complete = true;
            for (int row = 0; row < grid.length; row++) {
                if (grid[row][column] != value) {
                    complete = false;
                    break;
                }
            }
            if (complete) {
                completed++;
            }
        }

        // Check diagonals
        if (grid[0][0] == value
                && grid[1][1] == value
                && grid[2][2] == value) {
            completed++;
        }
        if (grid[0][2] == value
                && grid[1][1] == value
                && grid[2][0] == value) {
            completed++;
        }
        return completed > 0;
    }
}
