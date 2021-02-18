package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 06/02/21.
 */
public class AddBricksInTheWall {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int[][] wall = new int[9][9];
            for (int i = 0; i < wall.length; i += 2) {
                for (int j = 0; j <= i; j += 2) {
                    wall[i][j] = scanner.nextInt();
                }
            }

            fillEvenRows(wall);
            fillOddRows(wall);

            printWall(wall);
        }
    }

    private static void fillEvenRows(int[][] wall) {
        for (int row = 0; row < wall.length - 2; row += 2) {
            for (int column = 0; column <= row; column += 2) {
                int nextEvenRow = row + 2;
                int missingNumber = (wall[row][column] - (wall[nextEvenRow][column] + wall[nextEvenRow][column + 2])) / 2;
                wall[nextEvenRow][column + 1] = missingNumber;
            }
        }
    }

    private static void fillOddRows(int[][] wall) {
        for (int row = wall.length - 2; row >= 1; row -= 2) {
            for (int column = 0; column <= row; column++) {
                wall[row][column] = wall[row + 1][column] + wall[row + 1][column + 1];
            }
        }
    }

    private static void printWall(int[][] wall) {
        for (int row = 0; row < wall.length; row++) {
            for (int column = 0; column <= row; column++) {
                System.out.print(wall[row][column]);

                if (column != row) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
