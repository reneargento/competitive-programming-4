package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 23/09/20.
 */
public class TennisRounds {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int rounds = scanner.nextInt();
            int player1 = scanner.nextInt();
            int player2 = scanner.nextInt();

            int round = 1;

            while (Math.abs(player1 - player2) != 1 || Math.min(player1, player2) % 2 != 1) {
                player1 = (int) Math.ceil(player1 / 2.0);
                player2 = (int) Math.ceil(player2 / 2.0);
                round++;
            }

            System.out.println(round);
        }
    }

}
