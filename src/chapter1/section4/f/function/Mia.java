package chapter1.section4.f.function;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Mia {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int player1Roll1 = scanner.nextInt();
        int player1Roll2 = scanner.nextInt();
        int player2Roll1 = scanner.nextInt();
        int player2Roll2 = scanner.nextInt();

        while (player1Roll1 != 0 || player1Roll2 != 0 || player2Roll1 != 0 || player2Roll2 != 0) {
            int score1 = getScore(player1Roll1, player1Roll2);
            int score2 = getScore(player2Roll1, player2Roll2);

            if (score1 > score2) {
                System.out.println("Player 1 wins.");
            } else if (score2 > score1) {
                System.out.println("Player 2 wins.");
            } else {
                System.out.println("Tie.");
            }

            player1Roll1 = scanner.nextInt();
            player1Roll2 = scanner.nextInt();
            player2Roll1 = scanner.nextInt();
            player2Roll2 = scanner.nextInt();
        }
    }

    private static int getScore(int play1, int play2) {
        int high = Math.max(play1, play2);
        int low = Math.min(play1, play2);

        if (high == 2 && low == 1) {
            return Integer.MAX_VALUE;
        }
        if (high == low) {
            return high * 100;
        }
        return Integer.parseInt(high + "" + low);
    }

}
