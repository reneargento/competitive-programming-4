package chapter1.section6.a.game.card;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 06/10/20.
 */
public class Jollo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int princessCard1 = scanner.nextInt();
        int princessCard2 = scanner.nextInt();
        int princessCard3 = scanner.nextInt();
        int princeCard1 = scanner.nextInt();
        int princeCard2 = scanner.nextInt();

        while (princessCard1 != 0 || princessCard2 != 0 || princessCard3 != 0 || princeCard1 != 0 || princeCard2 != 0) {
            int targetCard = -1;
            int[] princessCards = { princessCard1, princessCard2, princessCard3 };
            Arrays.sort(princessCards);

            int nextCard = princessCards[1] + 1;

            if (nextCard == princessCards[2]) {
                nextCard++;
            }

            for (int i = 0; i < 2; i++) {
                if (nextCard == princeCard1 || nextCard == princeCard2) {
                    nextCard++;
                }
            }

            if (nextCard == princessCards[2]) {
                nextCard++;
            }

            for (int i = 0; i < 2; i++) {
                if (nextCard == princeCard1 || nextCard == princeCard2) {
                    nextCard++;
                }
            }

            if (princeCard1 > princessCards[2] && princeCard2 > princessCards[2]) {
                targetCard = 1;

                for (int i = 0; i < 3; i++) {
                    if (targetCard == princessCards[i]) {
                        targetCard++;
                    }
                }

                for (int i = 0; i < 2; i++) {
                    if (targetCard == princeCard1 || targetCard == princeCard2) {
                        targetCard++;
                    }
                }

                for (int i = 0; i < 3; i++) {
                    if (targetCard == princessCards[i]) {
                        targetCard++;
                    }
                }
            } else if (nextCard <= 52
                    && ((princeCard1 > princessCards[0] && princeCard1 > princessCards[1]
                    && princeCard2 > princessCards[0] && princeCard2 > princessCards[1])
                    || (nextCard > princessCards[2] && (princeCard1 > princessCards[2] || princeCard2 > princessCards[2])))) {
                targetCard = nextCard;
            } else {
                if (princessCards[2] != 52) {
                    nextCard = princessCards[2] + 1;
                }

                for (int i = 0; i < 2; i++) {
                    if (nextCard == princeCard1 || nextCard == princeCard2) {
                        nextCard++;
                    }
                }

                if (nextCard == princessCards[2]) {
                    nextCard++;
                }

                for (int i = 0; i < 2; i++) {
                    if (nextCard == princeCard1 || nextCard == princeCard2) {
                        nextCard++;
                    }
                }

                if (nextCard <= 52
                        && ((princeCard1 > princessCards[0] && princeCard1 > princessCards[1]
                        && princeCard2 > princessCards[0] && princeCard2 > princessCards[1])
                        || (nextCard > princessCards[2] && (princeCard1 > princessCards[2] || princeCard2 > princessCards[2])))) {
                    targetCard = nextCard;
                }
            }
            System.out.println(targetCard);

            princessCard1 = scanner.nextInt();
            princessCard2 = scanner.nextInt();
            princessCard3 = scanner.nextInt();
            princeCard1 = scanner.nextInt();
            princeCard2 = scanner.nextInt();
        }
    }

}
