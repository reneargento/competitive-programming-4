package chapter1.section6.a.game.card;

import java.util.Scanner;

/**
 * Created by Rene Argento on 09/10/20.
 */
public class BridgeHandEvaluator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String[] cards = new String[13];

            for (int i = 0; i < cards.length; i++) {
                cards[i] = scanner.next();
            }

            int points = 0;

            for (String card : cards) {
                switch (card.charAt(0)) {
                    case 'A':
                        points += 4;
                        break;
                    case 'K':
                        points += 3;
                        if (countCardsOfSuit(cards, card.charAt(1)) < 2) {
                            points--;
                        }
                        break;
                    case 'Q':
                        points += 2;
                        if (countCardsOfSuit(cards, card.charAt(1)) < 3) {
                            points--;
                        }
                        break;
                    case 'J':
                        points++;
                        if (countCardsOfSuit(cards, card.charAt(1)) < 4) {
                            points--;
                        }
                        break;
                }
            }

            char[] suits = {'S', 'H', 'D', 'C'};
            int suitsStopped = 0;
            int pointsWithExtra = points;
            int maxFrequencySuitCount = 0;
            char maxFrequencySuit = 'Z';

            for (char suit : suits) {
                int cardsOfSuit = countCardsOfSuit(cards, suit);

                if (cardsOfSuit > maxFrequencySuitCount) {
                    maxFrequencySuitCount = cardsOfSuit;
                    maxFrequencySuit = suit;
                }

                if (cardsOfSuit == 2) {
                    pointsWithExtra++;
                } else if (cardsOfSuit < 2) {
                    pointsWithExtra += 2;
                }

                if (isStopped(cards, suit)) {
                    suitsStopped++;
                }
            }

            if (points >= 16 && suitsStopped == 4) {
                System.out.println("BID NO-TRUMP");
            } else if (pointsWithExtra >= 14) {
                System.out.println("BID " + maxFrequencySuit);
            } else {
                System.out.println("PASS");
            }
        }
    }

    private static int countCardsOfSuit(String[] cards, char suit) {
        int cardsOfSuit = 0;

        for (String card : cards) {
            if (card.charAt(1) == suit) {
                cardsOfSuit++;
            }
        }

        return cardsOfSuit;
    }

    private static boolean isStopped(String[] cards, char suit) {
        boolean containsAce = false;
        boolean containsKing = false;
        boolean containsQueen = false;
        int cardsOfSuit = 0;

        for (String card : cards) {
            if (card.charAt(1) == suit) {
                cardsOfSuit++;

                if (card.charAt(0) == 'A') {
                    containsAce = true;
                } else if (card.charAt(0) == 'K') {
                    containsKing = true;
                } else if (card.charAt(0) == 'Q') {
                    containsQueen = true;
                }
            }
        }

        return containsAce || (containsKing && cardsOfSuit > 1) || (containsQueen && cardsOfSuit > 2);
    }

}
