package chapter1.section6.b.game.chess;

import java.util.Scanner;

/**
 * Created by Rene Argento on 12/10/20.
 */
public class Chess {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            char piece = scanner.next().charAt(0);
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();

            int numberOfPieces;
            switch (piece) {
                case 'r': numberOfPieces = countRooksOrQueens(rows, columns); break;
                case 'Q': numberOfPieces = countRooksOrQueens(rows, columns); break;
                case 'k': numberOfPieces = countKnights(rows, columns); break;
                default: numberOfPieces = countKings(rows, columns);
            }
            System.out.println(numberOfPieces);
        }
    }

    private static int countRooksOrQueens(int rows, int columns) {
        return Math.min(rows, columns);
    }

    private static int countKings(int rows, int columns) {
        int placedOnColumns = (int) Math.ceil((double) columns / 2.0);
        int placeOnRows = (int) Math.ceil((double) rows / 2.0);
        return placedOnColumns * placeOnRows;
    }

    private static int countKnights(int rows, int columns) {
        return (int) Math.ceil(rows * columns / 2.0);
    }

}
