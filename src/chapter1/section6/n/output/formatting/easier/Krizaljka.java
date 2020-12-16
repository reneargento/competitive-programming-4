package chapter1.section6.n.output.formatting.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/12/20.
 */
public class Krizaljka {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String wordA = scanner.next();
        String wordB = scanner.next();

        char[][] grid = new char[wordB.length()][wordA.length()];
        initGrid(grid);

        int targetRow = -1;
        int targetColumn = -1;

        for (int i = 0; i < wordA.length(); i++) {
            int indexOnWordB = wordB.indexOf(wordA.charAt(i));
            if (indexOnWordB != -1) {
                targetRow = indexOnWordB;
                targetColumn = i;
                break;
            }
        }

        for (int i = 0; i < wordA.length(); i++) {
            grid[targetRow][i] = wordA.charAt(i);
        }
        for (int i = 0; i < wordB.length(); i++) {
            grid[i][targetColumn] = wordB.charAt(i);
        }
        printGrid(grid);
    }

    private static void initGrid(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                grid[row][column] = '.';
            }
        }
    }

    private static void printGrid(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                System.out.print(grid[row][column]);
            }
            System.out.println();
        }
    }
}
