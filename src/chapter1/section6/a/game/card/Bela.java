package chapter1.section6.a.game.card;

import java.util.Scanner;

/**
 * Created by Rene Argento on 07/10/20.
 */
public class Bela {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int hands = scanner.nextInt();
        char dominantSuit = scanner.next().charAt(0);
        int gamePoints = 0;

        for (int i = 0; i < hands * 4; i++) {
            String card = scanner.next();
            char number = card.charAt(0);
            char suit = card.charAt(1);

            switch (number) {
                case 'A': gamePoints += 11; break;
                case 'K': gamePoints += 4; break;
                case 'Q': gamePoints += 3; break;
                case 'J':
                    if (suit == dominantSuit) {
                        gamePoints += 20;
                    } else {
                        gamePoints += 2;
                    }
                break;
                case 'T': gamePoints += 10; break;
                case '9':
                    if (suit == dominantSuit) {
                        gamePoints += 14;
                    }
                    break;
            }
        }
        System.out.println(gamePoints);
    }

}
