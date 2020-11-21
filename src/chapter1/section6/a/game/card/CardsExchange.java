package chapter1.section6.a.game.card;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 06/10/20.
 */
public class CardsExchange {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int aliceCards = scanner.nextInt();
        int bettyCards = scanner.nextInt();

        while (aliceCards != 0 || bettyCards != 0) {
            Set<Integer> aliceUniqueCards = new HashSet<>();
            for (int i = 0; i < aliceCards; i++) {
                aliceUniqueCards.add(scanner.nextInt());
            }

            Set<Integer> bettyUniqueCards = new HashSet<>();
            for (int i = 0; i < bettyCards; i++) {
                bettyUniqueCards.add(scanner.nextInt());
            }

            int cardsThatBettyDontHave = countCardsNotInSet(aliceUniqueCards, bettyUniqueCards);
            int cardsThatAliceDontHave = countCardsNotInSet(bettyUniqueCards, aliceUniqueCards);

            System.out.println(Math.min(cardsThatAliceDontHave, cardsThatBettyDontHave));

            aliceCards = scanner.nextInt();
            bettyCards = scanner.nextInt();
        }
    }

    private static int countCardsNotInSet(Set<Integer> set1, Set<Integer> set2) {
        int count = 0;

        for (int card : set1) {
            if (!set2.contains(card)) {
                count++;
            }
        }
        return count;
    }

}
