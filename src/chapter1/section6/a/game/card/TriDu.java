package chapter1.section6.a.game.card;

import java.util.Scanner;

/**
 * Created by Rene Argento on 10/10/20.
 */
public class TriDu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int card1 = scanner.nextInt();
            int card2 = scanner.nextInt();
            System.out.println(Math.max(card1, card2));
        }
    }
}
