package chapter1.section6.b.game.chess;

import java.util.Scanner;

/**
 * Created by Rene Argento on 13/10/20.
 */
public class HowManyKnights {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        while (rows != 0 || columns != 0) {
            int knights;

            if (rows == 0 || columns == 0) {
                knights = 0;
            } else if (rows == 1) {
                knights = columns;
            } else if (columns == 1) {
                knights = rows;
            } else if (rows == 2) {
                knights = countKnightsWithDimension2(columns);
            } else if (columns == 2) {
                knights = countKnightsWithDimension2(rows);
            } else {
                knights = (int) Math.ceil(rows * columns / 2.0);
            }
            System.out.printf("%d knights may be placed on a %d row %d column board.\n", knights, rows, columns);

            rows = scanner.nextInt();
            columns = scanner.nextInt();
        }
    }

    private static int countKnightsWithDimension2(int otherDimension) {
        if (otherDimension % 4 == 0) {
            return otherDimension;
        }
        if ((otherDimension + 1) % 4 == 0) {
            return otherDimension + 1;
        }
        if (otherDimension % 2 == 0) {
            return otherDimension + 2;
        } else {
            return otherDimension + 1;
        }
    }

}
