package chapter1.section6.a.game.card;

import java.util.Scanner;

/**
 * Created by Rene Argento on 03/10/20.
 */
public class WhatIsTheCard {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            String[] cards = new String[52];
            for (int i = 0; i < cards.length; i++) {
                cards[i] = scanner.next();
            }

            int totalValue = 0;
            int currentIndex = 26;
            for (int move = 0; move < 3; move++) {
                String card = cards[currentIndex];
                int value = 10;

                if (Character.isDigit(card.charAt(0))) {
                    value = Character.getNumericValue(card.charAt(0));
                }

                totalValue += value;
                currentIndex--;
                currentIndex -= (10 - value);
            }

            int targetIndex = currentIndex;
            if (totalValue > currentIndex) {
                int cardInHandIndex = totalValue - currentIndex - 1;
                targetIndex = 26 + cardInHandIndex;
            }

            System.out.printf("Case %d: %s\n", t, cards[targetIndex]);
        }
    }

}
